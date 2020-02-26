package eu.ttc2020.ocl.reference;

import java.util.Collections;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import eu.ttc2020.ocl.reference.api.CaseService;
import eu.ttc2020.ocl.reference.api.ContentRequest;
import eu.ttc2020.ocl.reference.api.OCLTransformResponse;
import eu.ttc2020.ocl.reference.api.TTCJsonProvider;
import eu.ttc2020.ocl.reference.api.TestResponse;
import eu.ttc2020.ocl.reference.api.TestResult;

public class Solution {

	private static final String METRIC_OCL2SQL_TIME = "TransformTimeNanos";
	private static final String METRIC_TEST_TIME = "TestTimeNanos";
	private static final String METRIC_SCENARIO_PREFIX = "Scenario";

	private static final String API_URL = "http://researcher-paper.ap-southeast-1.elasticbeanstalk.com/rest";

	public void run(Configuration c) {
		final JacksonJsonProvider jsonProvider = new TTCJsonProvider();
		final CaseService service = JAXRSClientFactory.create(API_URL,
			CaseService.class, Collections.singletonList(jsonProvider));

		// Transform the OCL expression to SQL (as XMI)
		final long nanosTransformOCLStart = System.nanoTime();
		final String oclQuery = c.getOCLQuery();
		final ContentRequest transformRequest = new ContentRequest(CaseService.OCL_TEXT_MEDIATYPE, oclQuery);
		final OCLTransformResponse transformResult = service.transform(CaseService.SQL_AS_XMI, transformRequest);
		final long nanosTransformOCLEnd = System.nanoTime();
		printMetric(c, METRIC_OCL2SQL_TIME, nanosTransformOCLEnd - nanosTransformOCLStart);
		// TODO memory usage?

		// Run the SQL query
		final long nanosTestSQLStart = System.nanoTime();
		final ContentRequest testRequest = new ContentRequest(CaseService.SQL_XML_MEDIATYPE, transformResult.getContent());
		final TestResponse testResult = service.runTests(c.getPhaseIndex(), c.getChallengeIndex(), testRequest);
		final long nanosTestSQLEnd = System.nanoTime();
		printMetric(c, METRIC_TEST_TIME, nanosTestSQLEnd - nanosTestSQLStart);
		for (TestResult tr : testResult.getResults()) {
			printMetric(c, METRIC_SCENARIO_PREFIX + tr.getScenario(), tr.getStatus());
		}
	}

	private void printMetric(Configuration c, String metricName, Object metricValue) {
		// Tool;Phase;Challenge;RunIndex;MetricName;MetricValue
		
		System.out.println(String.format("%s;%d;%d;%d;%s;%s",
			c.getTool(), c.getPhaseIndex(), c.getChallengeIndex(), c.getRunIndex(),
			metricName, metricValue.toString()));
	}

}
