package shorter.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller

public class ShorterPageController {
    @GetMapping("")
    @ResponseBody
    public Resource index() {
        return new ClassPathResource("static/index.html");
    }
    
}
