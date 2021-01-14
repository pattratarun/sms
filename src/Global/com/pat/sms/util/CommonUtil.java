package com.pat.sms.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("rawtypes")
public class CommonUtil {
	
	private static DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	private static DateFormat dfWithTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

	/**
     * This method checks to see if the given Collection is null or contains no object.
     * @param val passed Collection object
     * @return boolean
     */
    public static boolean isEmpty(Collection val) {
        return (val == null || (val.size() == 0));
    }

    /**
     * This method checks to see if the given Map is null or contains no object.
     * @param val passed Map object
     * @return boolean
     */
    public static boolean isEmpty(Map val) {
        return (val == null || (val.size() == 0));
    }

    /**
     * This method checks to see if the given Object is null or not.
     * @param obj object to be checked
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isEmpty((String) obj);
        }
        return false;
    }

    /**
     * This method checks to see if the given String is null or blank.
     * @param val passed String object
     * @return boolean
     */
    public static boolean isEmpty(String val) {
        return (val == null || val.trim().equals(""));
    }

    /**
     * This method checks whether the given Array is null or contain no element. 
     * @param val this is the passed String Array
     * @return boolean
     */
    public static boolean isEmpty(String[] val) {
        return (val == null || val.length == 0);
    }
    
    /**
     * This method checks to see if the given Collection is null or contains no object.
     * @param val passed Collection object
     * @return boolean
     */
    public static boolean isNotEmpty(Collection val) {
        return (val != null && (val.size() > 0));
    }

    /**
     * This method checks to see if the given Map is null or contains no object.
     * @param val passed Map object
     * @return boolean
     */
    public static boolean isNotEmpty(Map val) {
        return (val != null && (val.size() > 0));
    }

    /**
     * This method checks to see if the given Object is null or not.
     * @param obj object to be checked
     * @return boolean
     */
    public static boolean isNotEmpty(Object obj) {
        if (obj != null) {
            return true;
        }
        if (obj instanceof String) {
            return isNotEmpty((String) obj);
        }
        return false;
    }

    /**
     * This method checks to see if the given String is null or blank.
     * @param val passed String object
     * @return boolean
     */
    public static boolean isNotEmpty(String val) {
        return (val != null && !val.trim().equals(""));
    }

    /**
     * This method checks whether the given Array is null or contain no element. 
     * @param val this is the passed String Array
     * @return boolean
     */
    public static boolean isNotEmpty(String[] val) {
        return (val != null && val.length > 0);
    }
    
    public static LocalDate getCurrentLocalDateTime() {
    	return LocalDate.now();
    }
    
    public static String getNumberFormat(long value, int numberOfDigits) {
    	NumberFormat numberFormat = NumberFormat.getInstance();
    	numberFormat.setGroupingUsed(false);
    	numberFormat.setMaximumIntegerDigits(numberOfDigits);
    	numberFormat.setMinimumIntegerDigits(numberOfDigits);
    	return numberFormat.format(value);
    }
    
    public static UUID strToUUID(String uidStr) {
    	if(!isEmpty(uidStr)) {
    		return UUID.fromString(uidStr);
    	}
    	return null;
    }
    
	public static String stringToCamel(String str) {
		
		if(isEmpty(str)) return null;
		// Capitalize first letter of string
		str = str.substring(0, 1).toUpperCase() + str.substring(1);

		// Convert to StringBuilder
		StringBuilder builder = new StringBuilder(str);

		// Traverse the string character by
		// character and remove underscore
		// and capitalize next letter
		for (int i = 0; i < builder.length(); i++) {

			// Check char is underscore
			if (builder.charAt(i) == '_') {

				builder.deleteCharAt(i);
				builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
			}
		}

		// Return in String type
		return builder.toString();
	}
	
	public static String convertDateToStringWithTime(Object da) {
		if (da == null) {
			return null;
		}
		try {
			return dfWithTime.format((Date) da);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String convertDateToString(Object da) {
		if (da == null) {
			return null;
		}
		try {
			return df.format((Date) da);
		} catch (Exception e) {
			return null;
		}
	}
	
}
