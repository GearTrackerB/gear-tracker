package com.bnksystem.trainning1team.dto.Equip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RentalStatusResponse {
    private Long id;
    private String serialNo;
    private String equipmentName;
    private String statusName;
    private String employeeNo;
    private LocalDateTime regAt;
}