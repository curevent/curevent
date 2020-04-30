
package com.curevent.models.transfers.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "year",
    "month",
    "day"
})
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Datetime {

    private Long year;
    private Long month;
    private Long day;

}
