package com.ferdi.restapi.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JavaFile {

    List<String> annotations;

    String javaName;
    String extenedClass;
    List<String> implementedInterfaces;

    List<Field> fields;
    List<Method>  methods;

}
