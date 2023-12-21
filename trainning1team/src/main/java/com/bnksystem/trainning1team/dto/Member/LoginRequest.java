package com.bnksystem.trainning1team.dto.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String loginId;
        private String loginPw;
}
