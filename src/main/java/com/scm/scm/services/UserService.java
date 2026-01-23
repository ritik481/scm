package com.scm.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(String userId);
    Optional<User> updateUser(User user);
    Void deleteUser(String Id);
    boolean isUserExistsByEmail(String email);
    boolean isUserExistsByPhoneNumber(String phoneNumber);
    boolean isUserExists(String userId);
    User getUserByEmail(String email);
    List<User> getAllUsers();

    // Contact related methods
    Contact saveContact(Contact contact);
    List<Contact> getContactsByUser(User user);
    Contact getContactById(String contactId);
    void deleteContact(String contactId);
}
