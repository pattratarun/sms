package com.pat.sms.user.v1.reqresp;

import java.util.List;

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
public class RoleCreateRequest {
	
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String roleName;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String status;
    
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private List<String> priviledgeStr;
    
}
