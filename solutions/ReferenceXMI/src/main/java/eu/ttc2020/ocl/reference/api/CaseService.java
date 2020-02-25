package eu.ttc2020.ocl.reference.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import eu.ttc2020.ocl.reference.SampleLauncher;

/**
 * Proxy interface for the TTC 2020 web service. For an example on how to use
 * it, check {@link SampleLauncher} at the root of the application.
 */
public interface CaseService {

	public static final String OCL_TEXT_MEDIATYPE = "expression/text";
	public static final String OCL_XML_MEDIATYPE = "expression/xml";

	public static final String SQL_TEXT_MEDIATYPE = "statement/text";
	public static final String SQL_XML_MEDIATYPE = "statement/xml";

	public static final boolean SQL_AS_XMI = true;
	public static final boolean SQL_AS_TEXT = false;

	@Path("/map/carperson")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	OCLTransformResponse transform(
		@QueryParam("sqlAsXmi") boolean sqlAsXMI,
		ContentRequest request);

   @Path("/ttc")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   TestResponse runTests(
		  @QueryParam("stage") int stage,
		  @QueryParam("challenge") int challenge,
		  ContentRequest request);

}
