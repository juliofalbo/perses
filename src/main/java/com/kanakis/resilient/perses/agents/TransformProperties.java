package com.kanakis.resilient.perses.agents;

public class TransformProperties {
    private String methodName;
    private OperationMode mode;
    private long latency;

    public TransformProperties(String methodName, OperationMode mode, long latency) {
        this.methodName = methodName;
        this.mode = mode;
        this.latency = latency;
    }

    public TransformProperties(String methodName, OperationMode mode) {
        this.methodName = methodName;
        this.mode = mode;
        this.latency = 0;
    }

    public String getMethodName() {
        return methodName;
    }

    public OperationMode getMode() {
        return mode;
    }

    public long getLatency() {
        return latency;
    }
}
