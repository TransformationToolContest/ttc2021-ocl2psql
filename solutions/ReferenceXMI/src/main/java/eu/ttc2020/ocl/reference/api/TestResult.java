package eu.ttc2020.ocl.reference.api;

public class TestResult {

    public static final String STATUS_PASSED = "passed";
    public static final String STATUS_FAILED = "failed";

    private int scenario;
    private String status;
    private long executionTime;

    public int getScenario() {
        return scenario;
    }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "TestResult [scenario=" + scenario + ", status=" + status
            + ", executionTime=" + executionTime + "]";
    }

}
