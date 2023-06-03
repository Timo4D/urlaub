package hs.aalen.urlaub.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{


    @GetMapping("/")
    public String home(){
        return "Willkommen auf der Seite!";
    }

    @GetMapping("/secured")
    public String secured(){
        return "Willkommen! Du bist sicher!";
    }
}