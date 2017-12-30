package com.thepracticaldeveloper.reactiveweb.benchmark;

import org.springframework.http.ResponseEntity;

public class BenchmarkRequestResult {

    private final int requestId;
    private final long tookTimeNs;
    private final ResponseEntity<String> responseEntity;

    public BenchmarkRequestResult(final int requestId, final ResponseEntity<String> responseEntity, final long tookTimeNs) {
        this.requestId = requestId;
        this.responseEntity = responseEntity;
        this.tookTimeNs = tookTimeNs;
    }

    public int getRequestId() {
        return requestId;
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public long getTookTimeNs() {
        return tookTimeNs;
    }

    @Override
    public String toString() {
        return "BenchmarkRequestResult{" +
                "requestId=" + requestId +
                ", tookTimeNs=" + tookTimeNs +
                ", responseEntity=" + responseEntity +
                '}';
    }
}
