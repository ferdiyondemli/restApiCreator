package com.ferdiyondemli.fileCreator.fileComponent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DirectoryFile {
    String directoryName;
    String directoryPath;
    List<JavaFile> javaFiles;
    List<DirectoryFile>  directoryFiles;

}
