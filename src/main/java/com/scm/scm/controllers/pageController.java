package com.scm.scm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scm.scm.entities.User;
import com.scm.scm.forms.UserForm;
import com.scm.scm.helpers.Message;
import com.scm.scm.helpers.MessageType;
import com.scm.scm.services.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class pageController {
    @Autowired
    private UserService userService;
@RequestMapping("/home")
    public String home(Model model, HttpSession session){
        System.out.println("home page handler");
        model.addAttribute("name", "Substring");
        model.addAttribute("youtubeChannel", "code with harry");
        model.addAttribute("githubRepo", "https://github.com/ritik481/spotify-clone");
        return "home";
    }

    //about
   @RequestMapping("/about")
    public String aboutPage(){
        System.out.println("about page handler");
        return "about";
    }

    //services
     @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("services page handler");
        return "services";
    }
    //contact
    @RequestMapping("/contact")
    public String contactPage(){
        System.out.println("contact page handler");
        return "contact";
    }
    //login
    @RequestMapping("/login")
    public String loginPage(){
        System.out.println("login page handler");
        return "login";
    }
@RequestMapping("/signup")
public String signupPage(Model model) {
    UserForm userForm = new UserForm();
    model.addAttribute("userForm", userForm);
    return "signup";
}
@PostMapping("/set-theme")
@ResponseBody
public String setTheme(@RequestBody ThemeRequest themeRequest, HttpSession session) {
    session.setAttribute("theme", themeRequest.getTheme());
    return "Theme set to " + themeRequest.getTheme();
}

    //processing register form
    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm,HttpSession session){
       System.out.println("processing register form");
       System.out.println(userForm);

       // Check if user already exists by email
       if (userService.isUserExistsByEmail(userForm.getEmail())) {
           Message message=Message.builder().content("User with this email already exists").type(MessageType.red).build();
           session.setAttribute("message", message);
           return "redirect:/signup";
       }

       // Check if user already exists by phone number
       if (userService.isUserExistsByPhoneNumber(userForm.getPhoneNumber())) {
           Message message=Message.builder().content("User with this phone number already exists").type(MessageType.red).build();
           session.setAttribute("message", message);
           return "redirect:/signup";
       }

    //    User user = User.builder()
    //        .name(userForm.getName())
    //        .email(userForm.getEmail())
    //        .password(userForm.getPassword())
    //        .about(userForm.getAbout())
    //        .phoneNumber(userForm.getPhoneNumber())
    //        .profilePic("https://www.pngkit.com/png/detail/126-1262807_instagram-default-profile-picture-png.png")
    //        .build();

    User user = new User();
         user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());
            user.setAbout(userForm.getAbout());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setProfilePic("https://www.pngkit.com/png/detail/126-1262807_instagram-default-profile-picture-png.png");
       User savedUser=userService.saveUser(user);
       System.out.println("Saved user: " + savedUser);

       Message message = Message.builder()
        .content("Registration Successful")
        .type(MessageType.green)
        .build();

       session.setAttribute("message", message);

        return "redirect:/signup";
    }

    public static class ThemeRequest {
        private String theme;
        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }
    }
}
