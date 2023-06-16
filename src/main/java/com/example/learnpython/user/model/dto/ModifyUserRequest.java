package com.example.learnpython.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModifyUserRequest {
        private String firstname;
        private String nickname;
        private String lastname;


}
