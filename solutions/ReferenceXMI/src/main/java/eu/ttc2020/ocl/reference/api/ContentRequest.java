package eu.ttc2020.ocl.reference.api;

public class ContentRequest {

	private String contentType;
	private String content;

	public ContentRequest() {
		// no-arg for JAX-RS
	}

	public ContentRequest(String type, String content) {
		this.contentType = type;
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "OCLTransformRequest [contentType=" + contentType + ", content=" + content + "]";
	}

}
