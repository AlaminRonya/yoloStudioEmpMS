package com.alamin.employeeManagementSystem.service;

import com.alamin.employeeManagementSystem.dto.EmployeeDto;
import com.alamin.employeeManagementSystem.utils.ExcelUtils;
import com.alamin.employeeManagementSystem.utils.FileFactory;
import com.alamin.employeeManagementSystem.utils.ImportConfig;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileEmployeeService fileEmployeeService;
    public void fileImport(MultipartFile importFile){
        if (importFile.isEmpty()){
            return;
        }
        Workbook workbook = FileFactory.getWorkbookStream(importFile);
        if (workbook == null){
            return;
        }
        List<EmployeeDto> employeeDtoList = ExcelUtils.getImportData(workbook, ImportConfig.customerImport);
        System.out.println(employeeDtoList);
        fileEmployeeService.addFile(employeeDtoList);
    }


}
