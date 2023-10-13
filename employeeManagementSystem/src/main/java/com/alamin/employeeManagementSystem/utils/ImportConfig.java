package com.alamin.employeeManagementSystem.utils;

import com.alamin.employeeManagementSystem.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportConfig {
    private int sheetIndex;

    private int headerIndex;

    private int startRow;

    private Class<?> dataClazz;

    private List<CellConfig> cellImportConfigs;
    public static final ImportConfig customerImport;
    static{
        customerImport = new ImportConfig();
        customerImport.setSheetIndex(0);
        customerImport.setHeaderIndex(0);
        customerImport.setStartRow(1);
        customerImport.setDataClazz(EmployeeDto.class);
        List<CellConfig> customerImportCellConfigs = new ArrayList<>();
//        final CellConfig employeeId = new CellConfig(0, "Employee ID");
//        final CellConfig employeeName = new CellConfig(1, "Employee Name");
//        final CellConfig designation = new CellConfig(2, "Designation");
//        final CellConfig date = new CellConfig(3, "Date");
//        final CellConfig checkInTime = new CellConfig(4, "Check-In Time");
//        final CellConfig checkOutTime = new CellConfig(5, "Check-Out Time");

        customerImportCellConfigs.add(new CellConfig(0, "employeeId"));
        customerImportCellConfigs.add(new CellConfig(1, "employeeName"));
        customerImportCellConfigs.add(new CellConfig(2, "designation"));
        customerImportCellConfigs.add(new CellConfig(3, "date"));
        customerImportCellConfigs.add(new CellConfig(4, "checkInTime"));
        customerImportCellConfigs.add(new CellConfig(5, "checkOutTime"));
        customerImport.setCellImportConfigs(customerImportCellConfigs);



    }
}
