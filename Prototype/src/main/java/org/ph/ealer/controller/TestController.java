package org.ph.ealer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping("/ajax")
    public String ajax(){
        return "ajax";
    }

    @RequestMapping (value = "/testAjax/{type}/{value}", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(@PathVariable ("type") String type, @PathVariable("value") String value){
        return type + " " + value;
    }
}
