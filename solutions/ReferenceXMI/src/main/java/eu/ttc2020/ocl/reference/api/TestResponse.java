package eu.ttc2020.ocl.reference.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResponse {

	@JsonProperty("scenarii")
	private List<TestResult> results = new ArrayList<>();

	public List<TestResult> getResults() {
		return results;
	}

	public void setResults(List<TestResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "TestResponse [results=" + results + "]";
	}
	
}
