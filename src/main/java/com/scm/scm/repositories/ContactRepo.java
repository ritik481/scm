package com.scm.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // Find contacts by user
    List<Contact> findByUser(User user);

    // Find contacts by user and name containing case insensitive search
    @Query("SELECT c FROM Contact c WHERE c.user = :user AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Contact> findByUserAndNameContainingIgnoreCase(@Param("user") User user, @Param("name") String name);

    // Find contacts by user and email
    List<Contact> findByUserAndEmail(User user, String email);

    // Count contacts by user
    long countByUser(User user);
}
