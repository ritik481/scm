package com.scm.scm.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.scm.entities.User;
import com.scm.scm.forms.UserForm;
import com.scm.scm.helpers.Message;
import com.scm.scm.helpers.MessageType;
import com.scm.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //user dashboard
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String userDashboard(Authentication authentication, Model model){
        String email;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            email = oauthToken.getPrincipal().getAttribute("email");
        } else {
            email = authentication.getName();
        }
        User user = userService.getUserByEmail(email);
        if(user == null){
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("contacts", user.getContacts());
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

    // save profile
    @RequestMapping(value = "/profile/save", method = RequestMethod.POST)
    public String saveProfile(@Valid UserForm userForm, BindingResult result, Authentication authentication,
                              @RequestParam("avatar") MultipartFile file, @RequestParam(value = "deleteAvatar", required = false) String deleteAvatar,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        String email;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            email = oauthToken.getPrincipal().getAttribute("email");
        } else {
            email = authentication.getName();
        }
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/login";
        }

        // Handle delete avatar
        if (deleteAvatar != null && user.getProfilePic() != null) {
            try {
                String filePath = "src/main/resources/static" + user.getProfilePic();
                File fileToDelete = new File(filePath);
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }
                user.setProfilePic(null);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", Message.builder().content("Failed to delete image").type(MessageType.red).build());
                return "redirect:/user/profile";
            }
        }

        // Handle file upload
        if (!file.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/static/images/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String fileName = user.getUserId() + "_" + file.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                user.setProfilePic("/images/" + fileName);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", Message.builder().content("Image upload failed").type(MessageType.red).build());
                return "redirect:/user/profile";
            }
        }

        // Update user details
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());

        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message", Message.builder().content("Profile updated successfully").type(MessageType.green).build());
        return "redirect:/user/profile";
    }

    // delete account
    @RequestMapping(value = "/profile/delete", method = RequestMethod.POST)
    public String deleteAccount(Authentication authentication, HttpSession session) {
        String email;
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            email = oauthToken.getPrincipal().getAttribute("email");
        } else {
            email = authentication.getName();
        }
        User user = userService.getUserByEmail(email);
        if (user != null) {
            userService.deleteUser(user.getUserId());
        }
        session.invalidate();
        return "redirect:/login";
    }

    //user add contactpage

    //user view contacts page
}
