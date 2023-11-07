package com.mmhb.farketmez.dto.user;

import com.mmhb.farketmez.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDTO {
    String username;
    String password;
    String name;
    String surname;
    Integer age;
    Integer gender;
    String longitude;
    String latitude;
    String token;
    UserType userType;
}
