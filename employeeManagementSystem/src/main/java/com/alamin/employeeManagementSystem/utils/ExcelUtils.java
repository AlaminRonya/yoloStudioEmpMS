package com.alamin.employeeManagementSystem.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class ExcelUtils {
    //import config
    public static <T> List<T> getImportData(Workbook workbook, ImportConfig importConfig) {
        List<T> list = new ArrayList<>();

        System.out.println("*************> ExcelUtils");
        List<CellConfig> cellConfigs = importConfig.getCellImportConfigs();

        int countSheet = 0;

        for (Sheet sheet : workbook) {
            if (countSheet != importConfig.getSheetIndex()) {
                countSheet++;
                continue;
            }

            int countRow = 0;
            for (Row row : sheet) {
                if (countRow < importConfig.getStartRow()) {
                    countRow++;
                    continue;
                }
                T rowData = getRowData(row, cellConfigs, importConfig.getDataClazz());
                list.add(rowData);
                countRow++;
            }
            countSheet++;
        }
        list.forEach(System.out::println);
        return list;
    }

    private static <T> T getRowData(Row row, List<CellConfig> cellConfigs, Class dataClazz) {
        T instance = null;
        try {
            instance = (T) dataClazz.getDeclaredConstructor().newInstance();

            for (int i = 0; i < cellConfigs.size(); i++) {
                System.out.println("=============>"+cellConfigs.size());
                CellConfig currentCell = cellConfigs.get(i);
                try {
                    Field field = getDeclaredField(dataClazz, currentCell.getFieldName());

                    Object cellValue = new Object();
                    Cell cell = row.getCell(currentCell.getColumnIndex());
                    if (!ObjectUtils.isEmpty(cell)) {
//                        if (cell.getCellType() == CellType.NUMERIC) {
//                            if (i == 0){
//                                cell.setCellType(CellType.STRING);
//                                cellValue = Integer.valueOf(cellValue.toString());
//                                System.out.println(i+"=============>"+Integer.valueOf(cellValue.toString()));
//                            }else if (DateUtil.isCellDateFormatted(cell)){
//                                Date timeDate = cell.getDateCellValue();
//                                String timeValue;
//                                timeValue = new SimpleDateFormat("HH:mm:ss").format(timeDate);
//                                cellValue = timeValue;
////                                if (i == 5 || i == 4){
////                                    timeValue = new SimpleDateFormat("HH:mm:ss").format(timeDate);
////                                    cellValue = timeValue;
////                                }else {
////                                    String pattern = "MM-dd-yyyy";
////                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
////                                    timeValue = simpleDateFormat.format(timeDate);
////                                }
//
//                                System.out.println(cell.getDateCellValue()+"Time: " + timeValue);
//                            }
//                        }else {
////                            cell.setCellType(CellType.STRING);
//
//                            cellValue = cell.getStringCellValue();
//                        }

                        cell.setCellType(CellType.STRING);
                        cellValue = cell.getStringCellValue();
                        setFieldValue(instance, field, cellValue);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return instance;

    }

    private static Field getDeclaredField(Class clazz, String fieldName) {
        if (ObjectUtils.isEmpty(clazz) || ObjectUtils.isEmpty(fieldName)) {
            return null;
        }
        do {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                log.info("" + e);
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        return null;
    }

    private static <T> void setFieldValue(Object instance, Field field, Object cellValue) {
        if (ObjectUtils.isEmpty(instance) || ObjectUtils.isEmpty(field)) {
            return;
        }

        Class<?> clazz = field.getType();

        Object valueConverted = parseValueByClass(clazz, cellValue);

        field.setAccessible(true);

        try {
            field.set(instance, valueConverted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object parseValueByClass(Class clazz, Object cellValue) {
        if (ObjectUtils.isEmpty(cellValue) || ObjectUtils.isEmpty(clazz)) {
            return null;
        }
        String clazzName = clazz.getSimpleName();

        switch (clazzName) {
            case "char" -> cellValue = parseChar(cellValue);
            case "String" -> cellValue = cellValue.toString().trim();
            case "boolean", "Boolean" -> cellValue = parseBoolean(cellValue);
            case "byte", "Byte" -> cellValue = parseByte(cellValue);
            case "short", "Short" -> cellValue = parseShort(cellValue);
            case "int", "Integer" -> cellValue = parseInt(cellValue);
            case "long", "Long" -> cellValue = parseLong(cellValue);
            case "float", "Float" -> cellValue = parseFloat(cellValue);
            case "double", "Double" -> cellValue = parseDouble(cellValue);
            case "Date" -> cellValue = parseDate(cellValue);
            case "Instant" -> cellValue = parseInstant(cellValue);
            case "Enum" -> cellValue = parseEnum(cellValue, clazz);
            case "Map" -> cellValue = parseMap(cellValue);
            case "BigDecimal" -> cellValue = parseBigDecimal(cellValue);
            default -> {
            }
        }
        return cellValue;
    }

    private static Object parseChar(Object value) {
        return ObjectUtils.isEmpty(value) ? null : (char) value;
    }


    private static Object parseBoolean(Object value) {
        return ObjectUtils.isEmpty(value) ? null : (Boolean) value;
    }

    private static Object parseMap(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return (Map) value;
    }

    private static Object parseEnum(Object value, Class clazz) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        String valueStr = value.toString().trim();
        return Enum.valueOf(clazz, valueStr);
    }

    private static Date parseDate(Object value) {
        String[] formatsDate = {"yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"};

        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        String dateStr = value.toString();
        for (String format : formatsDate) {
            Date date = null;

            try {
                DateFormat dateFormat = new SimpleDateFormat(format);
                date = dateFormat.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isEmpty(date)) {
                return date;
            }
        }

        try {
            Date date = (Date) value;
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private static Object parseInstant(Object value) {
        return ObjectUtils.isEmpty(value) ? null : Objects.requireNonNull(parseDate(value)).toInstant();
    }

    private static Double parseDouble(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private static Object parseFloat(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double doubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(doubleValue) ? null : doubleValue.floatValue();
    }


    private static Object parseLong(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double longDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(longDoubleValue) ? null : longDoubleValue.longValue();
    }

    private static Object parseShort(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double shortDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(shortDoubleValue) ? null : shortDoubleValue.shortValue();
    }

    private static Object parseInt(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double intDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(intDoubleValue) ? null : intDoubleValue.intValue();
    }

    private static Object parseBigDecimal(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            return BigDecimal.valueOf(Double.parseDouble(value.toString()));
        } catch (Exception e) {
            return null;
        }
    }

    private static Object parseByte(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double byteDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(byteDoubleValue) ? null : byteDoubleValue.byteValue();
    }
}
