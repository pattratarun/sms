package com.pat.sms.enums;

public enum StudentStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    PAID("PAID"),
	MOVED("MOVED"),
	PAYEMENTPENDING("PAYEMENTPENDING"),
	NEW("NEW"),
	PASS("PASS"),
	FAILED("FAILED"),
	REREGISTER("REREGISTER");

    String value;

    StudentStatusEnum(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
