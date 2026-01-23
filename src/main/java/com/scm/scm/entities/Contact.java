package com.scm.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
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
    @Column(length=10000)
    private String description;
    private boolean favorite=false;
    private String websiteLink;
    private String Linkedin;
    private String profilePic;
    private String phone2;

    @ManyToOne
    private User user;
     @OneToMany(mappedBy="contact", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<SocialLink> socialLinks=new ArrayList<>();
}

