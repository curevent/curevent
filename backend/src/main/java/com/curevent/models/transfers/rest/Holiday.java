
package com.curevent.models.transfers.rest;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "description",
    "country",
    "date",
    "type",
    "locations",
    "states"
})
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("country")
    private Country country;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("type")
    private List<String> type;
    @JsonProperty("locations")
    private String locations;
    @JsonProperty("states")
    private String states;

}
