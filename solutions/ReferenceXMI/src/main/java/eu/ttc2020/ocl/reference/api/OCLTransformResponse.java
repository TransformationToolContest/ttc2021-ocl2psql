package eu.ttc2020.ocl.reference.api;

public class OCLTransformResponse {

    private String contentType;
    private String content;
    private int status;
    private long transformationTime;

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

    public long getTransformationTime() {
        return transformationTime;
    }

    public void setTransformationTime(long transformationTime) {
        this.transformationTime = transformationTime;
    }

    @Override
    public String toString() {
        return "OCLTransformResponse [contentType=" + contentType + ", content="
            + content + ", status=" + status + ", transformationTime="
            + transformationTime + "]";
    }

}
