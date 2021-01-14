package com.pat.sms.search;



public class SearchSetting {

	public static String getSearchStringFrom(String text, int opr) {
		String finalStr = text;
		switch (opr) {
		case 1:
			finalStr = "" + text + "";
			break;
		case 2:
			finalStr = text + "%";
			break;
		case 3:
			finalStr = "%" + text;
			break;
		case 4:
			finalStr = "%" + text + "%";
			break;
		}

		return finalStr;
	}
}
