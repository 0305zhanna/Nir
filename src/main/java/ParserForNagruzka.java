/**
 * Created by User on 04.06.2017.
 */
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParserForNagruzka {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private Map<String, ArrayList<String>> plan;

    ParserForNagruzka(String fileName)
    {

        plan = new HashMap<String, ArrayList<String>>();

        File file = new File(fileName);
        try
        {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void parse(){
        try{
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        ArrayList<String> block = new ArrayList<String>();
        ArrayList<String> name_disc = new ArrayList<String>();

        ArrayList<String> teacher= new ArrayList<String>();
        ArrayList<String> study_plan= new ArrayList<String>();
        ArrayList<String> kind_occupat= new ArrayList<String>();
        ArrayList<String> audience= new ArrayList<String>();
        ArrayList<String> other= new ArrayList<String>();
        ArrayList<String> group= new ArrayList<String>();
            ArrayList<String> num_gr= new ArrayList<String>();
        String t;
        int i = 0;
        while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if(i != 3)
                {
                    i++;
                    continue;
                }
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 2:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                study_plan.add(cell.getStringCellValue());
                            }else {
                                study_plan.add("");
                            }
                         break;
                        case 4:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                block.add(cell.getStringCellValue());
                            }else {
                                block.add("");
                            }
                         break;
                        case 5:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                if (cell.getStringCellValue().indexOf(",") == -1) {
                                    name_disc.add(cell.getStringCellValue());
                                } else {
                                    t = cell.getStringCellValue().substring(0, cell.getStringCellValue().indexOf(","));
                                    name_disc.add(t);
                                }
                            }else{
                                name_disc.add("");
                            }
                         break;
                        case 8:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                group.add(cell.getStringCellValue());
                            }else{
                                group.add("");
                            }
                        case 9:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                num_gr.add(String.valueOf(cell.getNumericCellValue()));
                            }else{
                                num_gr.add(String.valueOf(0));
                            }
                            break;
                        case 11:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                kind_occupat.add(cell.getStringCellValue());
                            }else{
                                kind_occupat.add("");
                            }
                         break;
                        case 23:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                audience.add(String.valueOf(cell.getNumericCellValue()));
                                }else{
                                audience.add(String.valueOf(0));
                            }
                            break;
                        case 24:
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                other.add(String.valueOf(cell.getNumericCellValue()));
                            }else{
                                other.add(String.valueOf(0));
                            }
                            break;
                        case 27:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                teacher.add(cell.getStringCellValue().substring(1,cell.getStringCellValue().length()));
                            }else{
                                teacher.add("");
                            }
                         break;
                    }
                }
        }
            plan.put("Дисциплина", name_disc);
            plan.put("Блок", block);
            plan.put("УчПлан",study_plan);
            plan.put("ВидЗанятий",kind_occupat);
            plan.put("АудитЧас",audience);
            plan.put("Другое",other);
            plan.put("ФИО",teacher);
            plan.put("Группа",group);
            plan.put("Количество",num_gr);

            fileInputStream.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    public void show(){
        for (int i = 0; i < plan.get("Дисциплина").size() - 1; i++) {
            System.out.println(plan.get("Дисциплина").get(i) + "\t" + plan.get("Блок").get(i) +"\t"+plan.get("УчПлан").get(i)+
            "\t"+plan.get("ВидЗанятий").get(i)+"\t"+plan.get("АудитЧас").get(i)+"\t"+plan.get("Другое").get(i)+
            "\t"+plan.get("ФИО").get(i)+"\t"+plan.get("Группа").get(i)+"\t"+plan.get("Количество").get(i));
        }
    }
    public void fillingTableStudyLoad(DBconnection dBconnection){
        //System.out.println("SIZE="+(plan.get("Дисциплина").size() - 1));
        for (int i = 0; i < plan.get("Дисциплина").size() - 1; i++) {
            ResultSet temp1=null;
            ResultSet temp2=null;
            temp1 = dBconnection.selectQuery("select id FROM DISCIPLINE WHERE NAME='"+plan.get("Дисциплина").get(i)+"'");
            temp2 = dBconnection.selectQuery("select id FROM TEACHERS WHERE NAME='"+plan.get("ФИО").get(i)+"'");

            try {
                if(temp1.next()) {
                    if (temp2.next()) {
                        dBconnection.updateQuery("call PRO_INSERT_STUDY_LOAD(" + temp1.getInt("id") + ","
                                + temp2.getInt("id") + ",'" + plan.get("УчПлан").get(i) + "','"
                                + plan.get("ВидЗанятий").get(i) + "'," + plan.get("АудитЧас").get(i) + ","
                                + plan.get("Другое").get(i) + ")");
                    } else {
                        dBconnection.updateQuery("call PRO_INSERT_STUDY_LOAD2(" + temp1.getInt("id")
                                + ",'" + plan.get("УчПлан").get(i) + "','"
                                + plan.get("ВидЗанятий").get(i) + "'," + plan.get("АудитЧас").get(i) + ","
                                + plan.get("Другое").get(i) + ")");
                    }
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void fillingTableDiscipline(DBconnection dBconnection){
            for (int i = 0; i < plan.get("Дисциплина").size() - 1; i++) {
                ResultSet temp=null;
                temp = dBconnection.selectQuery("select count(*) as num from discipline where name='" + plan.get("Дисциплина").get(i) + "'");
                try {
                    if(temp.next()){
                        if(temp.getInt("num") == 0)
                            dBconnection.updateQuery("call pro_insert_discipline('" + plan.get("Дисциплина").get(i)+"', '"+plan.get("Блок").get(i)+"')");
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
    }
    public void updatingTableGroup(DBconnection dBconnection){
        for (int i = 0; i < plan.get("Группа").size() - 1; i++) {
            try {
                dBconnection.updateQuery("call PRO_UPDATE_GROUPP('"+plan.get("Группа").get(i)+"', "+plan.get("Количество").get(i)+")");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void delTableDiscipline(DBconnection dBconnection) {
        try{
            dBconnection.updateQuery("call PRO_DEL_DISCIPLINE()");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delTableStudyLoad(DBconnection dBconnection) {
        try{
            dBconnection.updateQuery("call PRO_DEL_STUDY_LOAD()");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
