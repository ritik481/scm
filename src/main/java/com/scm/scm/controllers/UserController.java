package com.scm.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm.entities.User;
import com.scm.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //user dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userDashboard(){
        return "user/dashboard";
    }
    //user profile page
     @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String userProfile(Authentication authentication, Model model){
        String email;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            email = oauthToken.getPrincipal().getAttribute("email");
        } else {
            email = authentication.getName();
        }
        System.out.println(email);
        User user = userService.getUserByEmail(email);
        if(user == null){
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user/profile";
    }

    //user add contactpage

    //user view contacts page
}
