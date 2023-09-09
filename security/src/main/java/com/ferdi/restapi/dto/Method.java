package com.ferdi.restapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Method {

    String accessModifier;
    String returnType;
    String name;
    String implementation;
}
