package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ErrorMsgs {
    public Map<String, String> errors;

    @Builder
    public ErrorMsgs(Map<String, String> errors) {
        this.errors = errors;
    }
}
