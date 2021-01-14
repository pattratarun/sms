package com.pat.sms.user.v1.reqresp;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriviledgeCreateRequest {
	
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String priviledgeName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String priviledgeID;
    
}
