package com.scm.scm.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Contact {

    @Id
  private String name;
  private String email;
  private String phoneNumber;
  private String address;
    private String description;
    private boolean favorite=false;
    private String websiteLink;
    private String Linkedin;
    private String profilePic;
    private String phone2;

    @DBRef
    private User user;
    private List<SocialLink> socialLinks=new ArrayList<>();
}

