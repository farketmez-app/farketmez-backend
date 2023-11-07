package com.mmhb.farketmez.dto.user;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends UserUpdateDTO {
    Date deletedAt;
}