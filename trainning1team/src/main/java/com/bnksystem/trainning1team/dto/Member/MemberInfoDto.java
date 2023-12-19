package com.bnksystem.trainning1team.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private int id;
    private String empNo;
    private String roleNm;
}
