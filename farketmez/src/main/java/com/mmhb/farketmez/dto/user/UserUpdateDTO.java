package com.mmhb.farketmez.dto.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO extends UserCreateDTO {
    Long id;
    Timestamp updatedAt;
}
