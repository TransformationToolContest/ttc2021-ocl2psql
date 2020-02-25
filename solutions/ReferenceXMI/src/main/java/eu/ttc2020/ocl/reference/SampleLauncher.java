package eu.ttc2020.ocl.reference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import eu.ttc2020.ocl.reference.api.CaseService;
import eu.ttc2020.ocl.reference.api.ContentRequest;
import eu.ttc2020.ocl.reference.api.TTCJsonProvider;

/**
 * Shows an example of how to use the JAX-RS interfaces to invoke the API.
 */
public class SampleLauncher {

	private static final String API_URL = "http://researcher-paper.ap-southeast-1.elasticbeanstalk.com/rest";
	
	public static void main(String[] args) throws IOException {

		final JacksonJsonProvider jsonProvider = new TTCJsonProvider();
		final CaseService transformer = JAXRSClientFactory.create(API_URL,
			CaseService.class, Collections.singletonList(jsonProvider));

		// Test the "transform to SQL" service
		final String sampleQuery = "Car.allInstances()->collect(c|c.color = 'black')";
		final ContentRequest transformRequest =
			new ContentRequest(CaseService.OCL_TEXT_MEDIATYPE, sampleQuery);
		System.out.println(transformer.transform(CaseService.SQL_AS_XMI, transformRequest));
		System.out.println(transformer.transform(CaseService.SQL_AS_TEXT, transformRequest));

		// Test the "run tests with this SQL query" service

		// TODO does this support plain SQL as well?
		final String sqlQueryXMI = String.join(System.lineSeparator(),
			Files.readAllLines(Paths.get("resources", "sampleQuery.xmi")));
		final ContentRequest testRequest =	new ContentRequest(
			CaseService.SQL_XML_MEDIATYPE, sqlQueryXMI);
		System.out.println(transformer.runTests(0, 0, testRequest));
	}
	
}
