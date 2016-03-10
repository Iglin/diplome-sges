package ru.aosges.dao;

/**
 * Created by user on 10.03.2016.
 */
public class QueryResult {
    private boolean success;
    private String errorMessage;
    private Object result;

    public QueryResult(boolean success) {
        this.success = success;
    }

    public QueryResult(boolean success, Object result) {
        this.success = success;
        this.result = result;
    }

    public QueryResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getResult() {
        return result;
    }
}
