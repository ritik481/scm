package com.scm.scm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.scm.scm.entities.Providers;
import com.scm.scm.entities.User;
import com.scm.scm.helpers.AppConstants;
import com.scm.scm.repositories.UserRepo;

@Service
public class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oauthUser =
            new DefaultOAuth2UserService().loadUser(userRequest);

        String provider =
            userRequest.getClientRegistration().getRegistrationId();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        // DB में user save/update logic
        User user = null;
        if (email != null) {
            user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                // Create new user
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPassword("dummy");
                user.setProvider(Providers.valueOf(provider.toUpperCase()));
                user.setProviderUserId(oauthUser.getName());
                user.setEmailVerified(true);
                user.setRolesList(List.of(AppConstants.ROLE_USER));
                userService.saveUser(user);
            } else {
                // Update existing user
                user.setName(name);
                user.setProvider(Providers.valueOf(provider.toUpperCase()));
                user.setProviderUserId(oauthUser.getName());
                user.setEmailVerified(true);
                userService.updateUser(user);
            }
        }

        return oauthUser;
    }
}
