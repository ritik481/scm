package com.scm.scm.services.impl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.scm.entities.User;
import com.scm.scm.helpers.ResourceNotFoundException;
import com.scm.scm.repositories.UserRepo;
import com.scm.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    

    @Autowired
    private UserRepo userRepo;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @Override
    public Optional<User> getUserById(String userId) {
       return userRepo.findById(userId);
    }

    @Override
    public Optional<User> updateUser(User user) {
      User user2= userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));

      user2.setEmail(user.getEmail());
        user2.setName(user.getName());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setAbout(user.getAbout());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        // MODIFIED: Added return statement to fix the method
        return Optional.of(userRepo.save(user2));
    }

    @Override
    public Void deleteUser(String Id) {
        User user2= userRepo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
       userRepo.delete(user2);
       return null;
        
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User user2= userRepo.findByEmail(email).orElse(null);
        return user2!=null ? true : false;
    }

    @Override
    public boolean isUserExists(String userId) {
        User user2= userRepo.findById(userId).orElse(null);
        return user2!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
     return userRepo.save(user);
    }

    @Override
    public boolean isUserExistsByPhoneNumber(String phoneNumber) {
        User user2= userRepo.findByPhoneNumber(phoneNumber).orElse(null);
        return user2!=null ? true : false;
    }


}
