/**
 * Created by User on 04.06.2017.
 */
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class ParserForTeachers {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private Map<String, ArrayList<String>> teachers;

    ParserForTeachers(String fileName) {
        teachers = new HashMap<String, ArrayList<String>>();
        File file = new File(fileName);
        try {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        teachers.clear();
    }

    public void parse() {
        try {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            int i = 0;
            ArrayList<String> fio = new ArrayList<String>();
            ArrayList<String> degree = new ArrayList<String>();//Степень звание
            ArrayList<String> status = new ArrayList<String>();//i ш е
            ArrayList<String> rate = new ArrayList<String>();//Размер ставки
            ArrayList<String> clockRate = new ArrayList<String>();//Размер ставки в часах
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (i != 5) {
                    i++;
                    continue;
                }
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                status.add(cell.getStringCellValue());
                            } else {
                                status.add("");
                            }
                            break;
                        case 1:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                fio.add(cell.getStringCellValue());
                            } else {
                                fio.add("");
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                rate.add(String.valueOf(cell.getNumericCellValue()));
                            } else {
                                rate.add("");
                            }
                            break;
                        case 3:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                degree.add(cell.getStringCellValue());
                            } else {
                                degree.add("");
                            }
                            break;
                        case 4:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                clockRate.add(String.valueOf(cell.getNumericCellValue()));
                            } else {
                                clockRate.add("");
                            }
                            break;
                    }
                }
            }
            teachers.put("ФИО", fio);
            teachers.put("Степень", degree);
            teachers.put("Статус", status);
            teachers.put("Ставка", rate);
            teachers.put("Норма часов", clockRate);
            //workbook.close();
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        for (int i = 0; i < teachers.get("ФИО").size() - 1; i++) {
            System.out.println(teachers.get("ФИО").get(i) + "\t" + teachers.get("Степень").get(i) + "\t" +
                    teachers.get("Норма часов").get(i) + "\t" + teachers.get("Статус").get(i) + "\t" +
                    teachers.get("Ставка").get(i));
        }
    }

    public void fillingTableTeachers(DBconnection dBconnection) {
        for (int i = 0; i < teachers.get("ФИО").size() - 1; i++) {
            try {
                // System.out.println("insert teacher"+i);
                dBconnection.updateQuery("call pro_insert_teachers('" + teachers.get("ФИО").get(i) + "','" +
                        teachers.get("Степень").get(i) + "')");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillingTableEmployement(DBconnection dBconnection) {
        for (int i = 0; i < teachers.get("ФИО").size() - 1; i++) {
            ResultSet temp = null;
            temp = dBconnection.selectQuery("select ID from teachers where teachers.NAME='" + teachers.get("ФИО").get(i) + "'");
            try {
                temp.next();
                dBconnection.updateQuery("call pro_insert_employment(" + temp.getInt("ID") + ",'" + teachers.get("Статус").get(i) + "','"
                        + teachers.get("Степень").get(i) + "'," + teachers.get("Ставка").get(i) + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delTableEmployement(DBconnection dBconnection) {
        try {
            dBconnection.updateQuery("call PRO_DEL_EMPLOYMENT()");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delTableTeachers(DBconnection dBconnection) {
     try{
        dBconnection.updateQuery("call PRO_DEL_TEACHERS()");
    }catch (Exception e) {
        e.printStackTrace();
    }
    }
}