package com.pat.sms.search;

public enum SearchParamTypeEnum {
	NUM_PARAM("N-"), STRING_PARAM("S-"), DATE_TIME_PARAM("D-"), BOOLEAN_PARAM("B-");

	private String type;

	SearchParamTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static SearchParamTypeEnum valueOfType(String label) {
		for (SearchParamTypeEnum e : values())
			if (e.type.equals(label))
				return e;
		return null;
	}
}
