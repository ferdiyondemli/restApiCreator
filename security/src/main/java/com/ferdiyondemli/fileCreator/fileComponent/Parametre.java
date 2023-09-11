package com.ferdiyondemli.fileCreator.fileComponent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Parametre {
    public String type;
    public String name;

    public List<String> annotations;
}
