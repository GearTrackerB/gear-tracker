package com.bnksystem.trainning1team.dto.Equip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EquipRentalStatusResponse {
    public String serial_no;
    private String eq_nm;
    private String status_nm;
    private String emp_no;
}
