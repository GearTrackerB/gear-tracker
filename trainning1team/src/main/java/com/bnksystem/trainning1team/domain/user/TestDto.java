package com.bnksystem.trainning1team.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {
    public String idenfication;
    public String password;

    @Override
    public String toString() {
        return idenfication + ", " + password;
    }
}
