package com.scm.scm.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    //user dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userDashboard(){
        return "user/dashboard";
    }
    //user profile page
     @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Principal principal){
        String name=principal.getName();
        System.out.println(name);
        return "user/profile";
    }

    //user add contactpage

    //user view contacts page
}
