package com.alamin.employeeManagementSystem.dto;

import lombok.Data;

import java.util.Date;


@Data
public class EmployeeDto {
    private Integer employeeId;
    private String employeeName;
    private String designation;
    private String date;
    private String checkInTime;
    private String checkOutTime;

}
