package com.bnksystem.trainning1team.dto.Member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinRequest {
    private String loginId;
    private String loginPw;
    private String empNo;
    private String roleNm;
}
