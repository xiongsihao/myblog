package com.xsh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : xsh
 * @create : 2020-02-15 - 1:50
 * @describe:
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
