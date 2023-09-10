package ru.netology.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class Code {
    @NotNull
    @JsonProperty("code")
    private String code;

    public Code(String code) {
        this.code = code;
    }
    public Code() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
