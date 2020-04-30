
package com.curevent.models.transfers.rest;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "holidays"
})
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @JsonProperty("holidays")
    private List<Holiday> holidays;

}
