package com.ferdiyondemli.hbs;

import com.ferdiyondemli.fileCreator.fileComponent.JavaFile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hbs")
@RequiredArgsConstructor
public class ControllerHbs {

    private final HbsFileCreator hbsFileCreator;

    @PostMapping("/createfiles")
    public String createhbs(@RequestBody JavaFile javaFile) {

        String path = System.getProperty("user.dir");
        hbsFileCreator.createFile(javaFile, path + "\\security\\src\\main\\java\\com\\ferdi");

        return "Ok. I've created them'll ";
    }


    @GetMapping("/test")
    public String getll() {

        return "Ok. tits'r teasin";
    }

}
