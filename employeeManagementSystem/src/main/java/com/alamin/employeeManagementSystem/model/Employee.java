package com.alamin.employeeManagementSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_employee")
public class Employee {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer employeeId;
    private String employeeName;
    private String designation;
    private String date;
    private String checkInTime;
    private String checkOutTime;
}
