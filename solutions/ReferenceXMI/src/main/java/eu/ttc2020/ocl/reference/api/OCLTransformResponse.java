package eu.ttc2020.ocl.reference.api;

public class OCLTransformResponse {

	private String contentType;
	private String content;
	private int status;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OCLTransformResponse [contentType="
				+ contentType + ", content=" + content
				+ ", status=" + status + "]";
	}

}
