package com.scm.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm.entities.Providers;
import com.scm.scm.entities.User;
import com.scm.scm.helpers.AppConstants;
import com.scm.scm.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessfulHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessfulHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        logger.info("User logged in: {}", oauth2User.getName());

        oauth2User.getAttributes().forEach((key, value) -> logger.info("{} => {}", key, value));

        logger.info("Authorities: {}", oauth2User.getAuthorities().toString());

        // Database save
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        String picture = oauth2User.getAttribute("picture");

        // Determine provider based on attributes
        Providers provider = Providers.SELF; // default
        String providerUserId = null;
        if (oauth2User.getAttribute("sub") != null) {
            provider = Providers.GOOGLE;
            providerUserId = oauth2User.getAttribute("sub").toString();
        } else if (oauth2User.getAttribute("id") != null) {
            provider = Providers.GITHUB;
            providerUserId = oauth2User.getAttribute("id").toString();
        } else {
            providerUserId = UUID.randomUUID().toString();
        }

        if (email != null && !userService.isUserExistsByEmail(email)) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword("dummy");
            user.setProvider(provider);
            user.setProviderUserId(providerUserId);
            user.setProfilePic(picture);
            user.setEmailVerified(true);
            user.setRolesList(List.of(AppConstants.ROLE_USER));
            userService.saveUser(user);
        } else if (email != null) {
            // Update existing user if needed
            User existingUser = userService.getUserByEmail(email);
            if (existingUser != null) {
                existingUser.setName(name);
                existingUser.setProfilePic(picture);
                existingUser.setProvider(provider);
                existingUser.setProviderUserId(providerUserId);
                userService.saveUser(existingUser);
            }
        }

        // Redirect to dashboard or home
        response.sendRedirect("/user/dashboard");
    }
}
