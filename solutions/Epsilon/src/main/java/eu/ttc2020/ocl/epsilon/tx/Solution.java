package eu.ttc2020.ocl.epsilon.tx;

import java.io.File;

import org.eclipse.epsilon.emc.emf.EmfModel;

import eu.ttc2020.ocl.epsilon.Configuration;
import eu.ttc2020.ocl.epsilon.CorrectnessTest;

public class Solution {

	// Transformation time from OCL expression model to SQL statement model (first run, without warming up JVM)
	private static final String METRIC_OCL2PSQL_TIME_WARMUP = "TransformTimeNanosWarmup";

	// Transformation time from OCL expression model to SQL statement model (average of N runs afterwards)
	private static final String METRIC_OCL2PSQL_TIME = "TransformTimeNanos";

	// Number of runs to use for TransformTimeNanos
	private static final int N_RUNS = 10;

	// Execution time of SQL statements
	private static final String METRIC_TEST_TIME = "TestTimeNanos";

	// Database scenarios, from 1 to 7. More detail in docs/scenarios.
	private static final String METRIC_SCENARIO_PREFIX = "Scenario%d";
	private static final String METRIC_SCENARIO_ALL_PREFIX = "ScenarioID";

	private static final int scenarios = 7;

	public void run(Configuration c) {
		String sqlStmString = null;

		/*
		 * Reviewer 1 suggested that the results should not reflect the JVM startup, but the fact
		 * is that we *do* have some startup costs, and throwing those away could impact the
		 * validity of the results. As a compromise, we can show the results from a fresh JVM
		 * startup, and also the average over N runs after that first run.
		 */
		final long nanosFirstTransfoStart = System.nanoTime();
		sqlStmString = runOCL2PSQL(c);
		printMetric(c, METRIC_OCL2PSQL_TIME_WARMUP, System.nanoTime() - nanosFirstTransfoStart);

		final long nanosTransfoStart = System.nanoTime();
		for (int i = 0; i < N_RUNS; i++) {
			sqlStmString = runOCL2PSQL(c);
		}
		printMetric(c, METRIC_OCL2PSQL_TIME, (System.nanoTime() - nanosTransfoStart)/N_RUNS);

		if (sqlStmString != null) {
			try {
				boolean totalCorrect = true;
				long totalTestTime = 0L;

				//	Correctness and execution time of the generated query
				for (int iScenario = 1; iScenario <= scenarios; iScenario++) {
					final long nanosTestSQLStart = System.nanoTime();
					final boolean localCorrect = CorrectnessTest.check(c, iScenario, sqlStmString);
					final long nanosTestSQLEnd = System.nanoTime();
					totalTestTime += nanosTestSQLEnd - nanosTestSQLStart;
					totalCorrect = totalCorrect && localCorrect;
					final String result = localCorrect ? "passed" : "failed";
					printMetric(c, String.format(METRIC_SCENARIO_PREFIX, iScenario), result);
				}
				final String result = totalCorrect ? "passed" : "failed";
				printMetric(c, METRIC_SCENARIO_ALL_PREFIX, result);
				if (totalCorrect) {
					printMetric(c, METRIC_TEST_TIME, totalTestTime);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String runOCL2PSQL(Configuration c) {
		try {
			final File oclXMIFile = c.getOCLQueryXMIFile();
			final OCL2SQL ocl2psql = new OCL2SQL(oclXMIFile);

			// Run the M2M transformation for OCL2PSQL
			EmfModel sqlModel = ocl2psql.run();

			/*
			 * Reviewer 2 suggested including M2T in measurement to mimic the extra M2M
			 * transformation in the reference implementation.
			 */
			return new SQL2Text(sqlModel.getResource()).run();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void printMetric(Configuration c, String metricName, Object metricValue) {
		// Tool;Stage;Challenge;RunIndex;MetricName;MetricValue

		System.out.println(String.format("%s;%d;%d;%d;%s;%s", c.getTool(), c.getStageIndex(), c.getChallengeIndex(),
				c.getRunIndex(), metricName, metricValue.toString()));
	}

}
