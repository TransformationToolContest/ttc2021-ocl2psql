using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using TTC2021.OclToSql.Sql;

namespace TTC2021.OclToSql
{
    public static class QueryPrinter
    {
        public static string Print(SelectStatement selectStatement)
        {
            return Print(selectStatement.SelectBody);
        }

        public static string Print(IPlainSelect selectBody)
        {
            var resultBuilder = new StringBuilder();
            resultBuilder.Append($"SELECT {string.Join(", ", selectBody.SelItems.Select(sel => Print(sel, selectBody)))}");
            if (selectBody.FromItem != null)
            {
                resultBuilder.Append($" FROM {PrintFrom((dynamic)selectBody.FromItem)}");
            }
            foreach (var join in selectBody.Joins)
            {
                resultBuilder.Append($" {(join.Left.GetValueOrDefault() ? "LEFT" : "INNER")} JOIN {PrintFrom((dynamic)join.RightItem)} ON {PrintExpression((dynamic)join.OnExp, selectBody)}");
            }
            if (selectBody.WhereExp != null)
            {
                resultBuilder.Append($" WHERE {PrintExpression((dynamic)selectBody.WhereExp, selectBody)}");
            }
            if (selectBody.GroupBy != null)
            {
                resultBuilder.Append($" GROUP BY {string.Join(", ", selectBody.GroupBy.GroupByExps.Select(exp => PrintExpression((dynamic)exp, selectBody)))}");
            }
            return resultBuilder.ToString();
        }

        private static string PrintFrom(ISubSelect subSelect)
        {
            return $"({Print(subSelect.SelectBody)}) AS {subSelect.Alias.Name}";
        }

        private static string Print(ISelectItem selectItem, IPlainSelect selectBody)
        {
            var expression = PrintExpression((dynamic)selectItem.Exp, selectBody);
            if (selectItem.Alias != null)
            {
                expression += " " + selectItem.Alias.Name;
            }
            return expression;
        }

        private static string PrintFrom(Table table)
        {
            var suffix = table.Alias?.Name;
            if (suffix != null)
            {
                suffix = " AS " + suffix;
            }
            return table.Name + suffix;
        }

        private static string PrintExpression(StringValue stringValue, IPlainSelect selectBody)
        {
            return stringValue.Value;
        }

        private static string PrintExpression(LongValue longValue, IPlainSelect selectBody)
        {
            return longValue.Value.ToString();
        }

        private static string PrintExpression(GreaterThanExpression greaterThan, IPlainSelect selectBody)
        {
            return $"{PrintExpression((dynamic)greaterThan.LeftExp, selectBody)} > {PrintExpression((dynamic)greaterThan.RightExp, selectBody)}";
        }

        private static string PrintExpression(AndExpression andExpression, IPlainSelect selectBody)
        {
            return $"{PrintExpression((dynamic)andExpression.LeftExp, selectBody)} and {PrintExpression((dynamic)andExpression.RightExp, selectBody)}";
        }

        private static string PrintExpression(CountAllFunction countAll, IPlainSelect selectBody)
        {
            if (selectBody.Joins.Count > 0)
            {
                var lastJoin = selectBody.Joins.Last();
                if (lastJoin.OnExp is EqualsToExpression equals && equals.RightExp is Column targetColumn)
                {
                    return $"COUNT({targetColumn.Table.Alias.Name}.{targetColumn.Name})";
                }
            }
            return "COUNT(*)";
        }

        private static string PrintExpression(Column column, IPlainSelect selectBody)
        {
            var prefix = column.Table?.Alias?.Name;
            if (prefix != null)
            {
                prefix += ".";
            }
            return prefix + column.Name;
        }

        private static string PrintExpression(EqualsToExpression equalsTo, IPlainSelect selectBody)
        {
            return $"{PrintExpression((dynamic)equalsTo.LeftExp, selectBody)} = {PrintExpression((dynamic)equalsTo.RightExp, selectBody)}";
        }
    }
}
