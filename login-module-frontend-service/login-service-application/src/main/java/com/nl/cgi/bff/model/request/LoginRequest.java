package com.nl.cgi.bff.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest implements Serializable {


    @JsonProperty(value = "username")
    @ApiModelProperty(required = true,notes = "Unique Id of the user" )
    @NotNull(message = "userName cannot be null")
    @Size(min = 3, max = 50)
    private String userName;

    @ApiModelProperty(required = true,notes = "password of the user" )
    @JsonProperty(value = "password")
    @NotNull(message = "password cannot be null")
    @Size(min = 3, max = 50)
    private String password;
}
