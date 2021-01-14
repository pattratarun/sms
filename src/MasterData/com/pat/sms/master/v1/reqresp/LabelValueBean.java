package com.pat.sms.master.v1.reqresp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelValueBean implements Comparable<LabelValueBean> {
	private String label;
	private String value;
	
	public LabelValueBean(String label, String value){
		this.label = label;
		this.value= value;
	}

	@Override
	public int compareTo(LabelValueBean o) {
		return this.value.compareTo(o.value);
	}
}
