package com.example.ploygardenplants.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractDataTableResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = Access.READ_ONLY)
    private Integer no;
}
