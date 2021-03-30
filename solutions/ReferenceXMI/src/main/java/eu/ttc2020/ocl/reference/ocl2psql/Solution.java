package eu.ttc2020.ocl.reference.ocl2psql;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.vgu.ocl2psql.main.OCL2PSQL_2;
import org.vgu.ocl2psql.ocl.roberts.exception.OclParseException;
import org.vgu.se.ocl.parser.OCLParser;
import org.vgu.se.sql.parser.SQLParser;

import com.vgu.se.jocl.expressions.Expression;

import eu.ttc2020.ocl.reference.Configuration;
import eu.ttc2020.ocl.reference.CorrectnessTest;
import net.sf.jsqlparser.statement.Statement;

public class Solution {

	// Transformation time from OCL expression model to SQL statement model
	private static final String METRIC_OCL2PSQL_TIME = "TransformTimeNanos";
	// Execution time of SQL statements
	private static final String METRIC_TEST_TIME = "TestTimeNanos";
	// Database scenarios, from 1 to 7. More detail in docs/scenarios.
	private static final String METRIC_SCENARIO_PREFIX = "Scenario%d";
	private static final int scenarios = 7;

	public void run(Configuration c) {
//		Transformation time
		long transformationTimeNano = 0;
		sql.SelectStatement sqlStmXMI = null;
		try {
			OCL2PSQL_2 ocl2psql = new OCL2PSQL_2();
			final File oclXMIFile = c.getOCLQueryXMIFile();
			final long nanosOCL2PSQLTransformationStart = System.nanoTime();
			Expression oclExp = OCLParser.convertToExp(oclXMIFile.getAbsolutePath());
			Statement sqlStm = ocl2psql.mapOCLExpressionToSQLStatement(oclExp);
			sqlStmXMI = SQLParser.transform(sqlStm);
			final long nanosOCL2PSQLTransformationEnd = System.nanoTime();
			transformationTimeNano = nanosOCL2PSQLTransformationEnd - nanosOCL2PSQLTransformationStart;
		} catch (IOException | OclParseException | ParseException e) {
			e.printStackTrace();
			transformationTimeNano = -1;
		} finally {
			printMetric(c, METRIC_OCL2PSQL_TIME, transformationTimeNano);
		}

		if (sqlStmXMI != null) {
			String sqlStmString;
			try {
				boolean totalCorrect = true;
				long totalTestTime = 0L;
				sqlStmString = SQLParser.outputEStatementAsString(sqlStmXMI);
//				Correctness and execution time of the generated query
				for (int iScenario = 1; iScenario <= scenarios; iScenario++) {
					final long nanosTestSQLStart = System.nanoTime();
					final boolean localCorrect = CorrectnessTest.check(c, iScenario, sqlStmString);
					final long nanosTestSQLEnd = System.nanoTime();
					totalTestTime += nanosTestSQLEnd - nanosTestSQLStart;
					totalCorrect = totalCorrect && localCorrect;
					final String result = localCorrect?"passed":"failed";
					printMetric(c, String.format(METRIC_SCENARIO_PREFIX, iScenario), result);
				}
				if (totalCorrect) {
					printMetric(c, METRIC_TEST_TIME, totalTestTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void printMetric(Configuration c, String metricName, Object metricValue) {
		// Tool;Stage;Challenge;RunIndex;MetricName;MetricValue

		System.out.println(String.format("%s;%d;%d;%d;%s;%s", c.getTool(), c.getStageIndex(), c.getChallengeIndex(),
				c.getRunIndex(), metricName, metricValue.toString()));
	}

}
