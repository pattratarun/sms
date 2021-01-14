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
public class UserPasswordChangeRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String username;

    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String oldPassword;
    
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String newPassword;
    
}
