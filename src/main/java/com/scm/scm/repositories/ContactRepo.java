package com.scm.scm.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

@Repository
public interface ContactRepo extends MongoRepository<Contact, String> {

    // Find contacts by user
    List<Contact> findByUser(User user);

    // Find contacts by user and name containing case insensitive search
    @Query("{ 'user.$id': ?0, 'name': { $regex: ?1, $options: 'i' } }")
    List<Contact> findByUserAndNameContainingIgnoreCase(User user, String name);

    // Find contacts by user and email
    List<Contact> findByUserAndEmail(User user, String email);

    // Count contacts by user
    long countByUser(User user);
}
