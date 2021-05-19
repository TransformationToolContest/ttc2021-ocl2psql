using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using MySql.Data.MySqlClient;
using NMF.Models.Repository;
using TTC2021.OclToSql.Ocl.Exp;

namespace TTC2021.OclToSql
{
    class Program
    {
        private static readonly Dictionary<(int scenario, int stage, int challenge), List<string>> ExpectedResults = new Dictionary<(int, int, int), List<string>>
        {
            // scenario 1
            { (1, 0, 0), new List<string> { "2" } },
            { (1, 0, 1), new List<string> { "Peter" } },
            { (1, 0, 2), new List<string> { "1" } },
            { (1, 1, 0), new List<string> { "0" } },
            { (1, 1, 1), new List<string> { "1" } },
            { (1, 1, 2), new List<string> { "1" } },
            { (1, 2, 0), new List<string> { } },
            { (1, 3, 0), new List<string> { "0" } },
            { (1, 3, 1), new List<string> { "0" } },
            { (1, 4, 0), new List<string> { } },
            { (1, 4, 1), new List<string> { } },
            { (1, 4, 2), new List<string> { } },
            { (1, 5, 0), new List<string> { } },
            { (1, 5, 1), new List<string> { } },
            { (1, 6, 0), new List<string> { } },
            { (1, 6, 1), new List<string> { } },
            { (1, 7, 0), new List<string> { "0" } },
            { (1, 7, 1), new List<string> { "0" } },
            { (1, 7, 2), new List<string> { "0" } },
            { (1, 7, 3), new List<string> { "0" } },
            { (1, 8, 0), new List<string> { "0" } },

            // scenario 2
            { (2, 0, 0), new List<string> { "2" } },
            { (2, 0, 1), new List<string> { "Peter" } },
            { (2, 0, 2), new List<string> { "1" } },
            { (2, 1, 0), new List<string> { "0" } },
            { (2, 1, 1), new List<string> { "1" } },
            { (2, 1, 2), new List<string> { "1" } },
            { (2, 2, 0), new List<string> { "1" } },
            { (2, 3, 0), new List<string> { "1" } },
            { (2, 3, 1), new List<string> { "1" } },
            { (2, 4, 0), new List<string> { "5" } },
            { (2, 4, 1), new List<string> { "1" } },
            { (2, 4, 2), new List<string> { "0" } },
            { (2, 5, 0), new List<string> { "black" } },
            { (2, 5, 1), new List<string> { "1" } },
            { (2, 6, 0), new List<string> { "0" } },
            { (2, 6, 1), new List<string> { "1" } },
            { (2, 7, 0), new List<string> { "1" } },
            { (2, 7, 1), new List<string> { "0" } },
            { (2, 7, 2), new List<string> { "1" } },
            { (2, 7, 3), new List<string> { "0" } },
            { (2, 8, 0), new List<string> { "0" } },

            // scenario 3
            { (3, 0, 0), new List<string> { "2" } },
            { (3, 0, 1), new List<string> { "Peter" } },
            { (3, 0, 2), new List<string> { "1" } },
            { (3, 1, 0), new List<string> { "0" } },
            { (3, 1, 1), new List<string> { "1" } },
            { (3, 1, 2), new List<string> { "1" } },
            { (3, 2, 0), new List<string> { "1", "2" } },
            { (3, 3, 0), new List<string> { "2" } },
            { (3, 3, 1), new List<string> { "0" } },
            { (3, 4, 0), new List<string> { "5", "5" } },
            { (3, 4, 1), new List<string> { "1", "2" } },
            { (3, 4, 2), new List<string> { "0", "0" } },
            { (3, 5, 0), new List<string> { "black", "red" } },
            { (3, 5, 1), new List<string> { "1", "0" } },
            { (3, 6, 0), new List<string> { "0", "0" } },
            { (3, 6, 1), new List<string> { "1", "1" } },
            { (3, 7, 0), new List<string> { "1" } },
            { (3, 7, 1), new List<string> { "0" } },
            { (3, 7, 2), new List<string> { "1" } },
            { (3, 7, 3), new List<string> { "0" } },
            { (3, 8, 0), new List<string> { "0" } },

            // scenario 4
            { (4, 0, 0), new List<string> { "2" } },
            { (4, 0, 1), new List<string> { "Peter" } },
            { (4, 0, 2), new List<string> { "1" } },
            { (4, 1, 0), new List<string> { "0" } },
            { (4, 1, 1), new List<string> { "1" } },
            { (4, 1, 2), new List<string> { "1" } },
            { (4, 2, 0), new List<string> { "1", "2" } },
            { (4, 3, 0), new List<string> { "2" } },
            { (4, 3, 1), new List<string> { "0" } },
            { (4, 4, 0), new List<string> { "5", "5" } },
            { (4, 4, 1), new List<string> { "1", "2" } },
            { (4, 4, 2), new List<string> { "0", "0" } },
            { (4, 5, 0), new List<string> { "black", "red" } },
            { (4, 5, 1), new List<string> { "1", "0" } },
            { (4, 6, 0), new List<string> { "1", "0" } },
            { (4, 6, 1), new List<string> { "0", "1" } },
            { (4, 7, 0), new List<string> { "1" } },
            { (4, 7, 1), new List<string> { "0" } },
            { (4, 7, 2), new List<string> { "1" } },
            { (4, 7, 3), new List<string> { "1" } },
            { (4, 8, 0), new List<string> { "1" } },

            // scenario 5
            { (5, 0, 0), new List<string> { "2" } },
            { (5, 0, 1), new List<string> { "Peter" } },
            { (5, 0, 2), new List<string> { "1" } },
            { (5, 1, 0), new List<string> { "0" } },
            { (5, 1, 1), new List<string> { "1" } },
            { (5, 1, 2), new List<string> { "1" } },
            { (5, 2, 0), new List<string> { "1", "2" } },
            { (5, 3, 0), new List<string> { "2" } },
            { (5, 3, 1), new List<string> { "0" } },
            { (5, 4, 0), new List<string> { "5", "5" } },
            { (5, 4, 1), new List<string> { "1", "2" } },
            { (5, 4, 2), new List<string> { "0", "0" } },
            { (5, 5, 0), new List<string> { "black", "red" } },
            { (5, 5, 1), new List<string> { "1", "0" } },
            { (5, 6, 0), new List<string> { "1", "0" } },
            { (5, 6, 1), new List<string> { "0", "1" } },
            { (5, 7, 0), new List<string> { "1" } },
            { (5, 7, 1), new List<string> { "0" } },
            { (5, 7, 2), new List<string> { "1" } },
            { (5, 7, 3), new List<string> { "1" } },
            { (5, 8, 0), new List<string> { "1" } },

            // scenario 6
            { (6, 0, 0), new List<string> { "2" } },
            { (6, 0, 1), new List<string> { "Peter" } },
            { (6, 0, 2), new List<string> { "1" } },
            { (6, 1, 0), new List<string> { "0" } },
            { (6, 1, 1), new List<string> { "1" } },
            { (6, 1, 2), new List<string> { "1" } },
            { (6, 2, 0), new List<string> { "1", "2" } },
            { (6, 3, 0), new List<string> { "2" } },
            { (6, 3, 1), new List<string> { "0" } },
            { (6, 4, 0), new List<string> { "5", "5" } },
            { (6, 4, 1), new List<string> { "1", "2" } },
            { (6, 4, 2), new List<string> { "0", "0" } },
            { (6, 5, 0), new List<string> { "black", "red" } },
            { (6, 5, 1), new List<string> { "1", "0" } },
            { (6, 6, 0), new List<string> { "2", "0" } },
            { (6, 6, 1), new List<string> { "0", "1" } },
            { (6, 7, 0), new List<string> { "1" } },
            { (6, 7, 1), new List<string> { "0" } },
            { (6, 7, 2), new List<string> { "1" } },
            { (6, 7, 3), new List<string> { "0" } },
            { (6, 8, 0), new List<string> { "1" } },

            // scenario 7
            { (7, 0, 0), new List<string> { "2" } },
            { (7, 0, 1), new List<string> { "Peter" } },
            { (7, 0, 2), new List<string> { "1" } },
            { (7, 1, 0), new List<string> { "0" } },
            { (7, 1, 1), new List<string> { "1" } },
            { (7, 1, 2), new List<string> { "1" } },
            { (7, 2, 0), new List<string> { "1", "2" } },
            { (7, 3, 0), new List<string> { "2" } },
            { (7, 3, 1), new List<string> { "0" } },
            { (7, 4, 0), new List<string> { "5", "5" } },
            { (7, 4, 1), new List<string> { "1", "2" } },
            { (7, 4, 2), new List<string> { "0", "0" } },
            { (7, 5, 0), new List<string> { "black", "red" } },
            { (7, 5, 1), new List<string> { "1", "0" } },
            { (7, 6, 0), new List<string> { "2", "1" } },
            { (7, 6, 1), new List<string> { "0", "0" } },
            { (7, 7, 0), new List<string> { "1" } },
            { (7, 7, 1), new List<string> { "0" } },
            { (7, 7, 2), new List<string> { "1" } },
            { (7, 7, 3), new List<string> { "1" } },
            { (7, 8, 0), new List<string> { "1" } },
        };

        static void Main(string[] args)
        {
            var userName = Environment.GetEnvironmentVariable("MySQLUsername");
            var password = Environment.GetEnvironmentVariable("MySQLPassword");
            var port = Environment.GetEnvironmentVariable("MySQLPort");
            var stage = Environment.GetEnvironmentVariable("StageIndex");
            var challenge = Environment.GetEnvironmentVariable("ChallengeIndex");
            var path = Environment.GetEnvironmentVariable("PathToOCLXMI");
            var run = Environment.GetEnvironmentVariable("RunIndex");
            var tool = Environment.GetEnvironmentVariable("Tool");

            void Report(string metricName, string value)
            {
                Console.WriteLine($"{tool};{stage};{challenge};{run};{metricName};{value}");
            }

            var repository = new ModelRepository();
            var queryModel = repository.Resolve(path);
            var queryExp = (IOclExpression)queryModel.RootElements[0];
            var solution = new Solution();

            var stopwatch = new Stopwatch();
            stopwatch.Start();
            string statement = null;
            for (int i = 0; i < 100; i++)
            {
                statement = QueryPrinter.Print(solution.ToSql(queryExp));
            }
            stopwatch.Stop();
            Report("TransformTimeNanos", (stopwatch.Elapsed.Ticks).ToString());

            Console.Error.WriteLine(statement);

            var connectionStringBuilder = new MySqlConnectionStringBuilder();
            connectionStringBuilder.UserID = userName;
            connectionStringBuilder.Password = password;
            connectionStringBuilder.Port = uint.Parse(port);
            connectionStringBuilder.Server = "localhost";
            var isCorrect = true;
            using (var connection = new MySqlConnection(connectionStringBuilder.ConnectionString))
            {
                connection.Open();

                var stageParsed = int.Parse(stage);
                var challengeParsed = int.Parse(challenge);
                stopwatch.Restart();
                for (int i = 1; i <= 7; i++)
                {
                    using (var command = connection.CreateCommand())
                    {
                        command.CommandText = $"USE cardb{i};\n" + statement;
                        var resultList = new List<object>();
                        using (var result = command.ExecuteReader())
                        {
                            while (result.Read())
                            {
                                resultList.Add(result["res"]);
                            }
                        }
                        var expectedResults = ExpectedResults[(i, stageParsed, challengeParsed)];
                        isCorrect &= Assert(expectedResults, resultList, i);
                    }
                }
                stopwatch.Stop();
                Report("TestTimeNanos", (stopwatch.Elapsed.Ticks * 100).ToString());
                connection.Close();
            }
            Report("ScenarioID", isCorrect ? "passed" : "failed");
        }

        private static bool Assert(List<string> expectedResults, List<object> resultList, int scenario)
        {
            if (expectedResults.Count != resultList.Count)
            {
                Console.Error.WriteLine($"Expected {expectedResults.Count} but got {resultList.Count} results in scenario {scenario}");
                Console.Error.WriteLine("Got " + string.Join(", ", resultList));
                return false;
            }
            foreach (var item in resultList)
            {
                if (!expectedResults.Contains(item.ToString()))
                {
                    Console.Error.WriteLine($"Result {item} was unexpected in scenario {scenario}");
                    return false;
                }
            }
            return true;
        }
    }
}
