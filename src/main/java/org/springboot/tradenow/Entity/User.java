package org.springboot.tradenow.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springboot.tradenow.Enum.UserRole;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String fullName;
  private String email;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @Embedded
  private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.ROLE_CUSTOMER;




}
