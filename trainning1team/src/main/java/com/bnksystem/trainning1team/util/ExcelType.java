package com.bnksystem.trainning1team.util;public enum ExcelType {    XLS("XLS"),    XLSX("XLSX");    private String value = "";    ExcelType(String value) {        this.value = value;    }    public String value() {        return this.value;    }}