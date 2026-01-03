package com.scm.scm.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString

public class UserForm {
   @NotBlank(message = "Name cannot be blank")
   @Size(min = 3, max = 30, message = "Name must be between 2 and 30 characters")
   private String name;

   @NotBlank(message = "Email cannot be blank")
   private String email;

   @NotBlank(message = "Phone number cannot be blank")
   @Size(min = 10, max = 12, message = "Phone number must be between 10 and 15 characters")
   private String phoneNumber;

   @NotBlank(message = "Password cannot be blank")
   private String password;


   private String about;
}
