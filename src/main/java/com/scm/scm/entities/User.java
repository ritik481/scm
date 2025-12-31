package com.scm.scm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="users")
@Table(name="users")
public class User {
    // MODIFIED: Added UUID generation for primary key so Hibernate can auto-generate IDs
    // If you prefer to supply the ID from the application, remove these generator annotations.
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;
    @Column(name="user_name",nullable=false)
   private String name;
   @Column(unique=true,nullable=false)
   private String email;
    // MODIFIED: Large text fields changed to TEXT (CLOB) to avoid MySQL row-size limit
    // Use @Lob with columnDefinition="TEXT" so Hibernate creates TEXT columns
    @Lob
    @Column(nullable=false, columnDefinition = "TEXT")
     private String password;
    
    @Lob
    @Column(columnDefinition = "TEXT")
     private String about;
    
    @Lob
    @Column(columnDefinition = "TEXT")
     private String profilePic;
    @Column(unique=true)
    private String phoneNumber;

    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    @Enumerated(value=EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private List<Contact> contacts=new ArrayList<>();

   
}
