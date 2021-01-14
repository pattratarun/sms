package com.pat.sms.enums;

public enum ExamStatusEnum {
	NONE("NONE"),
	PASS("PASS"),
	FAILED("FAILED");

    String value;

    ExamStatusEnum(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
