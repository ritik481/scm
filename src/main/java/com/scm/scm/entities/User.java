package com.scm.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String userId;

    private String name;
    private String email;
    @Getter(value= lombok.AccessLevel.NONE)
    private String password;
    private String about;
    private String profilePic;
    private String phoneNumber;

    @Getter(value= lombok.AccessLevel.NONE)
    private boolean enabled=true;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    private Providers provider;
    private String providerUserId;

    @DBRef
    private List<Contact> contacts=new ArrayList<>();

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

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

}
