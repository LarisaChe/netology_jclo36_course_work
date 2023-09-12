package ru.netology.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code {

    @JsonProperty("code")
    private  String code;

    @JsonProperty("operationId")
    private  String operationId;

    public Code(String code, String operationId) {
        this.code = code;
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

    public String getOperationId() {
        return operationId;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", operationId='" + operationId + '\'' +
                '}';
    }
}
