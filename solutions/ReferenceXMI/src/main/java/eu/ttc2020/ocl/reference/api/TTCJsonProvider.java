package eu.ttc2020.ocl.reference.api;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class TTCJsonProvider extends JacksonJsonProvider {

	@Override
	protected boolean hasMatchingMediaType(MediaType mediaType) {
		/*
		 * API is returning "{application/json, q=1000}" as its return type.
		 * Temporary workaround while API developers fix this bit.
		 */
		return mediaType.toString().contains(MediaType.APPLICATION_JSON)
			|| super.hasMatchingMediaType(mediaType);
	}

}