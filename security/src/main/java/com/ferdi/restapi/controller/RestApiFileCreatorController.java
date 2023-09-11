package com.ferdi.restapi.controller;

import com.ferdi.fileCreator.fileComponent.RequestFile;
import com.ferdi.restapi.service.RestApiFileCreatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@AllArgsConstructor
@Controller
public class RestApiFileCreatorController {
    private final RestApiFileCreatorService restApiFileCreatorService;
    @PostMapping("/createRestApiFiles")
    @ResponseBody
    public void createFiles(@RequestBody RequestFile requestFile) {
        restApiFileCreatorService.createFiles(requestFile);
    }
    @GetMapping("/createRestApiFiles")
    public String   loadClientCodes() {
        return "index.html";
    }


    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "It is running...";
    }


}
