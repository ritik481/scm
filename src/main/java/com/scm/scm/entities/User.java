package com.scm.scm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
import jakarta.persistence.Version;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails {
    // MODIFIED: Added UUID generation for primary key so Hibernate can auto-generate IDs
    // If you prefer to supply the ID from the application, remove these generator annotations.
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @Version
    private Long version;
    @Column(name="user_name",nullable=false)
   private String name;
   @Column(unique=true,nullable=false)
   private String email;
    // MODIFIED: Large text fields changed to TEXT (CLOB) to avoid MySQL row-size limit
    // Use @Lob with columnDefinition="TEXT" so Hibernate creates TEXT columns
    @Lob
    @Column(nullable=false, columnDefinition = "TEXT")
    @Getter(value= lombok.AccessLevel.NONE)
     private String password;

    @Lob
    @Column(columnDefinition = "TEXT")
     private String about;
    
    @Lob
    @Column(columnDefinition = "TEXT")
     private String profilePic;
    @Column(unique=true)
    private String phoneNumber;
     
    @Getter(value= lombok.AccessLevel.NONE)
    private boolean enabled=true;

    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    @Enumerated(value=EnumType.STRING)
    private Providers provider;
    private String providerUserId;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private List<Contact> contacts=new ArrayList<>();

    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> rolesList=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = rolesList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
   }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    public void setImageUrl(String picture) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setImageUrl'");
    }

    public void setRolesList(List<String> of) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEmailVerified(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
