package com.alamin.employeeManagementSystem.service;

import com.alamin.employeeManagementSystem.dto.EmployeeDto;
import com.alamin.employeeManagementSystem.mapper.RequestEmployeeDtoMapper;
import com.alamin.employeeManagementSystem.model.Employee;
import com.alamin.employeeManagementSystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RequestEmployeeDtoMapper mapper;
    public synchronized void addFile(List<EmployeeDto> dtos){
        List<Employee> employees = new ArrayList<>();
        for (EmployeeDto dto: dtos){
            employees.add(mapper.apply(dto));
        }
        for (Employee employee: employees){
            add(employee);
        }
    }
    private synchronized void add(Employee employee){
        if (searchEmployee(employee.getEmployeeId()) != null){
            System.out.println("Already present");
            return;
        }
        employeeRepository.save(employee);
        System.out.println("Data save");
    }
    private synchronized Employee searchEmployee(Integer employeeId){
        return employeeRepository.findByEmployeeId(employeeId).orElse(null);
    }
}
