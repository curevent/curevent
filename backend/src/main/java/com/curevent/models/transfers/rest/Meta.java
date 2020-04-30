
package com.curevent.models.transfers.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "code"
})
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    @JsonProperty("code")
    private Long code;

}
