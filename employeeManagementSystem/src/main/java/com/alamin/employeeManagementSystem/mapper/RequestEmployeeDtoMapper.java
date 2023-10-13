package com.alamin.employeeManagementSystem.mapper;

import com.alamin.employeeManagementSystem.dto.EmployeeDto;
import com.alamin.employeeManagementSystem.model.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestEmployeeDtoMapper implements Function<EmployeeDto,Employee> {
    @Override
    public Employee apply(EmployeeDto dto) {
        return Employee.builder()
                .employeeId(dto.getEmployeeId())
                .employeeName(dto.getEmployeeName())
                .designation(dto.getDesignation())
                .date(dto.getDate())
                .checkInTime(dto.getCheckInTime())
                .checkOutTime(dto.getCheckOutTime())
                .build();
    }
}

