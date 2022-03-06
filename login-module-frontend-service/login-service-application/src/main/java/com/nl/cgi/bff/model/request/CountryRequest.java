package com.nl.cgi.bff.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryRequest implements Serializable {
    @JsonProperty(value = "countryname")
    @ApiModelProperty(required = true,notes = "name of the country" )
    @NotBlank(message = "country cannot be null")
    private String countryName;

    @JsonProperty(value = "id")
    @ApiModelProperty(required = true,notes = "unique id for country" )
    private Long id;
    @JsonProperty(value = "updatename")
    @ApiModelProperty(required = true,notes = "name of the country" )
    private String updateCountryName;
}
