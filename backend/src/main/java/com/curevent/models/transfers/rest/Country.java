
package com.curevent.models.transfers.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name"
})
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;

}
