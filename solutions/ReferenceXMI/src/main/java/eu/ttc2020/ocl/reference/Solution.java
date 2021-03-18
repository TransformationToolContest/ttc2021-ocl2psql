package eu.ttc2020.ocl.reference;

import java.io.File;
import java.io.IOException;

import org.vgu.ocl2psql.main.OCL2PSQL_2;

import sql.Statement;

public class Solution {

	// Transformation time from OCL expression model to SQL statement model
	private static final String METRIC_OCL2PSQL_TIME = "TransformTimeNanos";
	// Execution time of SQL statements
	private static final String METRIC_TEST_TIME = "TestTimeNanos";
	// Database scenarios, from 1 to 7. More detail in docs/scenarios.
	private static final String METRIC_SCENARIO_PREFIX = "Scenario";

	public void run(Configuration c) {
		try {
			OCL2PSQL_2 ocl2psql_2 = new OCL2PSQL_2();
			final File oclXMIFile = c.getOCLQueryXMIFile();
			final long nanosOCL2PSQLTransformationStart = System.nanoTime();
			Statement sqlStmXMI = ocl2psql_2.fromOCLXMIFileToSQLXMIStatement(oclXMIFile).getEStatement();
			final long nanosOCL2PSQLTransformationEnd = System.nanoTime();
			printMetric(c, METRIC_OCL2PSQL_TIME, nanosOCL2PSQLTransformationEnd - nanosOCL2PSQLTransformationStart);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		String sqlStmString = SQLParser.outputEStatementAsString(sqlStmXMI);
//		
//		// Run the SQL query
//		final long nanosTestSQLStart = System.nanoTime();
//		final ContentRequest testRequest = new ContentRequest(CaseService.SQL_XML_MEDIATYPE, transformResult.getContent());
//		final TestResponse testResult = service.runTests(c.getStageIndex(), c.getChallengeIndex(), testRequest);
//		final long nanosTestSQLEnd = System.nanoTime();
//		printMetric(c, METRIC_TEST_TIME, nanosTestSQLEnd - nanosTestSQLStart);
//		for (TestResult tr : testResult.getResults()) {
//			printMetric(c, METRIC_SCENARIO_PREFIX + tr.getScenario(), tr.getStatus());
//		}
	}

	private void printMetric(Configuration c, String metricName, Object metricValue) {
		// Tool;Stage;Challenge;RunIndex;MetricName;MetricValue
		
		System.out.println(String.format("%s;%d;%d;%d;%s;%s",
			c.getTool(), c.getStageIndex(), c.getChallengeIndex(), c.getRunIndex(),
			metricName, metricValue.toString()));
	}

}
