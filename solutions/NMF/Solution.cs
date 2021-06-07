using System;
using System.Collections.Generic;
using System.Linq;
using NMF.Models;
using TTC2021.OclToSql.Ocl.Dm;
using TTC2021.OclToSql.Ocl.Exp;
using TTC2021.OclToSql.Sql;

namespace TTC2021.OclToSql
{
    internal class Solution
    {
        public SelectStatement ToSql(IOclExpression expression)
        {
            var context = new SelectContext { Body = new PlainSelect() };
            var result = GetExpression(context, (dynamic)expression);
            var body = context.Body;
            body.SelItems.Add(new SelectItem
            {
                Exp = result,
                Alias = new Alias
                {
                    Name = "res"
                }
            });
            Complete(body, context);
            return new SelectStatement
            {
                SelectBody = body
            };
        }

        private void Prune(IPlainSelect selectBody)
        {
            if (selectBody.SelItems.Select(s => s.Exp).OfType<CountAllFunction>().Any() && !(selectBody.FromItem is SubSelect))
            {
                return;
            }
            var expressionsToCheck = selectBody.SelItems.Select(s => s.Exp).ToList();
            if (selectBody.WhereExp != null)
            {
                expressionsToCheck.Add(selectBody.WhereExp);
            }
            var usedAliases = (from selectExp in expressionsToCheck
                               from column in selectExp.Descendants().OfType<Column>()
                               select column.Table.Alias.Name).Distinct();
            for (int i = selectBody.Joins.Count - 1; i >= 0; i--)
            {
                var join = selectBody.Joins[i];
                if (join.RightItem is Table table && !usedAliases.Contains(table.Alias.Name))
                {
                    selectBody.Joins.RemoveAt(i);
                }
            }

            if (selectBody.FromItem is SubSelect subSelect)
            {
                Prune(subSelect.SelectBody);
            }
        }

        private void Complete(IPlainSelect body, SelectContext context)
        {
            if (body.SelItems[0].Exp == null)
            {
                var table = body.FromItem as Table;

                body.SelItems[0].Exp = CreateColumn(table);
            }
            Prune(body);
        }

        private IExpression GetExpression(SelectContext context, BooleanLiteralExp booleanLiteral)
        {
            return new EqualsToExpression
            {
                LeftExp = new LongValue
                {
                    Value = 1
                },
                RightExp = new LongValue
                {
                    Value = booleanLiteral.BooleanValue.GetValueOrDefault() ? 1 : 0
                }
            };
        }

        private IExpression GetExpression(SelectContext context, StringLiteralExp stringLiteral)
        {
            return new StringValue
            {
                Value = stringLiteral.StringValue
            };
        }

        private IExpression GetExpression(SelectContext context, IntegerLiteralExp integerLiteral)
        {
            return new LongValue
            {
                Value = integerLiteral.IntegerValue.GetValueOrDefault()
            };
        }

        private IExpression GetExpression(SelectContext context, PropertyCallExp propertyCall)
        {
            switch (propertyCall.Source)
            {
                case VariableExp variableRef:
                    var table = context.Variables[variableRef.ReferredVariable.Name];
                    return new Column
                    {
                        Table = new Table
                        {
                            Name = table,
                            Alias = new Alias
                            {
                                Name = variableRef.ReferredVariable.Name
                            }
                        },
                        Name = propertyCall.ReferredProperty.Name
                    };
                default:
                    throw new NotSupportedException();
            }
        }

        private IExpression GetExpression(SelectContext context, AssociationClassCallExp association)
        {
            switch (association.Source)
            {
                case VariableExp variableRef:
                    var variable = variableRef.ReferredVariable.Name;
                    var associationEnd = association.ReferredAssociationEnds;
                    var alias = variable + "_" + associationEnd.Association;
                    context.Body.Joins.Add(new Join
                    {
                        Left = false,
                        RightItem = new Table
                        {
                            Name = associationEnd.Association,
                            Alias = new Alias { Name = alias }
                        },
                        OnExp = new EqualsToExpression
                        {
                            LeftExp = new Column
                            {
                                Table = new Table
                                {
                                    Name = context.Variables[variable],
                                    Alias = new Alias { Name = variable }
                                },
                                Name = context.Variables[variable] + "_id",
                            },
                            RightExp = new Column
                            {
                                Table = new Table
                                {
                                    Name = associationEnd.Association,
                                    Alias = new Alias { Name = alias }
                                },
                                Name = associationEnd.Opp.Name,
                            }
                        }
                    });
                    context.LastJoin = Tuple.Create(variable, associationEnd);
                    return null;
                default:
                    throw new NotSupportedException();
            }
        }

        private IExpression GetAllInstances(SelectContext context, IEntity referredType)
        {
            var table = new Table
            {
                Name = referredType.Name,
            };
            if (context.Body.FromItem == null)
            {
                context.Body.FromItem = table;
            }
            else
            {
                context.Body.Joins.Add(new Join
                {
                    RightItem = table
                });
            }
            return null;
        }

        private IExpression GetExpression(SelectContext context, OperationCallExp callExpression)
        {
            switch (callExpression.ReferredOperation)
            {
                case Operator.AllInstances:
                    return GetAllInstances(context, (callExpression.Source as TypeExp).ReferredType);
                case Operator.Size:
                    return GetSize(context, callExpression.Source, null, false);
                case Operator.AND:
                    return new AndExpression
                    {
                        LeftExp = GetExpression(context, (dynamic)callExpression.Source),
                        RightExp = GetExpression(context, (dynamic)callExpression.Argument[0])
                    };
                default:
                    return new EqualsToExpression
                    {
                        LeftExp = GetExpression(context, (dynamic)callExpression.Source),
                        RightExp = GetExpression(context, (dynamic)callExpression.Argument[0])
                    };
            }
        }

        private IExpression GetExpression(SelectContext context, VariableExp variableExp)
        {
            return CreateColumn(context.Variables[variableExp.ReferredVariable.Name], variableExp.ReferredVariable.Name);
        }

        private IExpression GetExpression(SelectContext context, IteratorExp iterator)
        {
            GetExpression(context, (dynamic)iterator.Source );
            var alias = iterator.Iterator[0].Name;
            if (iterator.Source is OperationCallExp operationCall && operationCall.ReferredOperation == Operator.AllInstances)
            {
                var table = context.Body.FromItem as Table;
                table.Alias = new Alias
                {
                    Name = alias
                };
                context.Variables.Add(alias, table.Name);
            }
            else
            {
                var lastJoin = context.LastJoin;
                var joinTarget = lastJoin.Item2.Target.Name;
                context.Body.Joins.Add(new Join
                {
                    Left = false,
                    RightItem = new Table
                    {
                        Name = joinTarget,
                        Alias = new Alias
                        {
                            Name = alias
                        }
                    },
                    OnExp = new EqualsToExpression
                    {
                        LeftExp = new Column
                        {
                            Table = new Table
                            {
                                Alias = new Alias
                                {
                                    Name = lastJoin.Item1 + "_" + lastJoin.Item2.Association
                                },
                                Name = lastJoin.Item2.Association
                            },
                            Name = lastJoin.Item2.Opp.Name
                        },
                        RightExp = new Column
                        {
                            Table = new Table
                            {
                                Alias = new Alias
                                {
                                    Name = alias
                                },
                                Name = joinTarget
                            },
                            Name = joinTarget + "_id"
                        }
                    }
                });
                context.Variables.Add(alias, joinTarget);
            }
            try
            {
                switch (iterator.Kind)
                {
                    case IteratorKind.Collect:
                        return GetExpression(context, (dynamic)iterator.Body);
                    case IteratorKind.Exists:
                        return new GreaterThanExpression
                        {
                            LeftExp = GetSize(context, iterator.Body, alias, true),
                            RightExp = new LongValue
                            {
                                Value = 0
                            }
                        };
                    default:
                        throw new NotSupportedException();
                }
            }
            finally
            {
                context.Variables.Remove(alias);
            }
        }

        private IExpression GetSize(SelectContext context, IOclExpression source, string sizeVariable, bool addAsWhereClause)
        {
            var inner = GetExpression(context, (dynamic)source);
            var table = context.Body.FromItem as ITable;
            if (addAsWhereClause)
            {
                context.Body.WhereExp = inner;
            }
            if (table?.Alias != null)
            {
                var groupBy = new GroupByElement();
                foreach (var variable in context.Variables)
                {
                    if (variable.Key != sizeVariable)
                    {
                        groupBy.GroupByExps.Add(CreateColumn(variable.Value, variable.Key));
                        context.Body.SelItems.Add(new SelectItem { Exp = CreateColumn(variable.Value, variable.Key) });
                    }
                }
                if (groupBy.GroupByExps.Count > 0)
                {
                    context.Body.GroupBy = groupBy;
                }
            }
            context.Temporaries++;
            var subSelect = new SubSelect
            {
                Alias = new Alias
                {
                    Name = $"tmp{context.Temporaries}"
                },
                SelectBody = context.Body
            };
            context.Body.SelItems.Add(new SelectItem
            {
                Exp = new CountAllFunction(),
                Alias = new Alias
                {
                    Name = "res"
                }
            });
            foreach (var join in context.Body.Joins)
            {
                join.Left = true;
            }
            context.Body = new PlainSelect
            {
                FromItem = subSelect
            };
            foreach (var variable in context.Variables)
            {
                context.Body.Joins.Add(new Join
                {
                    Left = false,
                    RightItem = new Table
                    {
                        Alias = new Alias { Name = variable.Key },
                        Name = variable.Value
                    },
                    OnExp = new EqualsToExpression
                    {
                        LeftExp = new Column { Table = new Table { Alias = new Alias { Name = subSelect.Alias.Name } }, Name = variable.Value + "_id" },
                        RightExp = CreateColumn(variable.Value, variable.Key)
                    }
                });
            }
            return new Column
            {
                Table = new Table
                {
                    Name = subSelect.Alias.Name,
                    Alias = new Alias
                    {
                        Name = subSelect.Alias.Name
                    }
                },
                Name = "res"
            };
        }

        private static Column CreateColumn(ITable table) => CreateColumn(table.Name, table.Alias?.Name);

        private static Column CreateColumn(string table, string alias)
        {
            return new Column
            {
                Table = new Table
                {
                    Alias = alias != null ? new Alias { Name = alias } : null,
                    Name = table
                },
                Name = table + "_id"
            };
        }

        private class SelectContext
        {
            public int Temporaries { get; set; }

            public IPlainSelect Body { get; set; }

            public IDictionary<string, string> Variables { get; } = new Dictionary<string, string>();

            public Tuple<string, IAssociationEnd> LastJoin { get; set; }
        }
    }
}
