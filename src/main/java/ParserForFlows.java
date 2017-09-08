import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by User on 04.06.2017.
 */
public class ParserForFlows {
    private XSSFWorkbook workbook;
    private FileInputStream fileInputStream;
    private Map<String, ArrayList<String>> flows;
    private Map<String, ArrayList<String>> flows_group;
    private ArrayList<String> group;
    private String tempNumOfFlows;
    private ArrayList<String> tempOfGroup;
    ParserForFlows(String fileName)
    {
        flows = new HashMap<String, ArrayList<String>>();
        flows_group = new HashMap<String, ArrayList<String>>();
        File file = new File(fileName);
        group = new ArrayList<String>();
        tempOfGroup = new ArrayList<String>();
        try
        {
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        flows.clear();
    }
    public void parse()
    {
        try
        {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            int i = 0;
            ArrayList<String> numOfFlows = new ArrayList<String>();
            ArrayList<String> numOfSemesters = new ArrayList<String>();
            ArrayList<String> disciplines = new ArrayList<String>();
            ArrayList<String> teachers = new ArrayList<String>();
            ArrayList<String> groupInFlow = new ArrayList<String>();

            while (rowIterator.hasNext())
            {
                tempNumOfFlows="";
                tempOfGroup.clear();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if(i != 3)
                {
                    i++;
                    continue;
                }
                String temp = "";
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    if(row.getRowNum() == 3 && cell.getColumnIndex() == 6)
                    {
                        //System.out.println(cell.getCellType());
                    }
                    switch (cell.getColumnIndex())
                    {
                        case 0:
                            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                            {
                                tempNumOfFlows = String.valueOf(cell.getNumericCellValue());
                                numOfFlows.add(String.valueOf(cell.getNumericCellValue()));
                            }
                            else
                            {
                                numOfFlows.add("");
                            }
                            break;
                        case 1:
                            if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                            {
                                numOfSemesters.add(cell.getStringCellValue());
                            }
                            else
                            {
                                numOfSemesters.add("");
                            }
                            break;
                        case 2:
                            if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                            {
                                disciplines.add(cell.getStringCellValue());
                            }
                            else
                            {
                                disciplines.add("");
                            }
                            break;
                        case 6:
                            if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                            {
                                teachers.add(cell.getStringCellValue());
                            }
                            else
                            {
                                teachers.add("");
                            }
                            break;
                    }

                    if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getColumnIndex() > 6 && cell.getColumnIndex() < 18)
                    {
                        tempOfGroup.add(cell.getStringCellValue());
                        group.add(cell.getStringCellValue());
                        temp += cell.getStringCellValue()+"\t";
                    }

                }
                flows_group.put(tempNumOfFlows, new ArrayList<String>(tempOfGroup));
                groupInFlow.add(temp);
            }
            flows.put("Номер потока", numOfFlows);
            flows.put("Номер семестра", numOfSemesters);
            flows.put("Дисциплина", disciplines);
            flows.put("Преподаватель", teachers);
            flows.put("Группы в потоке", groupInFlow);
            fileInputStream.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void show()
    {
        for (int i = 0; i < flows.get("Номер потока").size(); i++)
        {
            System.out.println(flows.get("Номер потока").get(i) + "\t" + flows.get("Номер семестра").get(i) + "\t" +
                    flows.get("Дисциплина").get(i) + "\t" + flows.get("Преподаватель").get(i) + "\t"+
                    flows.get("Группы в потоке").get(i));
        }
        for (int i = 0; i< group.size();i++){
            System.out.println(group.get(i));
        }
        /*for(int i = 0; i < flows.get("Номер потока").size(); i++)
        {
            System.out.print(flows.get("Номер потока").get(i)+" : ");
            //System.out.println(flows_group.size());
            for(int j = 0; j < flows_group.get(flows.get("Номер потока").get(i)).size(); j++)
            {
                //System.out.print(flows_group.get(flows.get("Номер потока").get(i)).size());
                System.out.print(flows_group.get(flows.get("Номер потока").get(i)).get(j)+"\t");
            }
            System.out.println();
        }*/
    }

    public void fillingTableFlow(DBconnection dBconnection)
    {        for (int i = 0; i < flows.get("Номер потока").size(); i++)
    {
        ResultSet temp=null;
        if(flows.get("Преподаватель").get(i).length() > 0) {//если в потоках есть преподаватель
            //System.out.println(flows.get("Преподаватель").get(i).substring(1, flows.get("Преподаватель").get(i).length()));
            String teacher = flows.get("Преподаватель").get(i).substring(1, flows.get("Преподаватель").get(i).length());
            temp = dBconnection.selectQuery("select id from teachers where name='" + teacher+"'");//выбираем id из занятости, с типом и именем
            try {
              if(  temp.next()) {
                      dBconnection.updateQuery("call pro_insert_flows(" + flows.get("Номер потока").get(i) + ",'" + flows.get("Дисциплина").get(i) + "'," + temp.getInt("ID") + ",'" +
                              flows.get("Номер семестра").get(i) + "')");
              }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            ResultSet check = dBconnection.selectQuery("select count(*) as num from flows where flows.ID=" + flows.get("Номер потока").get(i));
            try {
                check.next();
                if(check.getInt("num") == 0)
                {
                    dBconnection.updateQuery("call pro_insert_flows(" + flows.get("Номер потока").get(i) + ",'" + flows.get("Дисциплина").get(i) + "', NULL ,'" +
                            flows.get("Номер семестра").get(i) + "')");
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
    }

    public void fillingTableGroup(DBconnection dBconnection)
    {
        for (int i = 0; i < group.size()-1; i++)
        {
//            System.out.println(group.get(i));
            ResultSet temp=null;
            temp = dBconnection.selectQuery("select count(*) as num from groupp where id='" + group.get(i) + "'");
            try {
                temp.next();
                if(temp.getInt("num") == 0)
                    dBconnection.updateQuery("call pro_insert_groupp('" + group.get(i)+"')");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void fillingTableGroupFlow(DBconnection dBconnection)
    {
        ResultSet temp=null;
        int flow_id;
        try {
           for(int i = 0; i < flows.get("Номер потока").size(); i++)
            {
                temp = dBconnection.selectQuery("select id from flows where \"number_flow\"=" + flows.get("Номер потока").get(i));
                temp.next();
                flow_id = temp.getInt("id");

                for(int j = 0; j < flows_group.get(flows.get("Номер потока").get(i)).size(); j++)
                {
                    dBconnection.updateQuery("call pro_insert_group_flow('" + flows_group.get(flows.get("Номер потока").get(i)).get(j)
                        +"'," + flow_id + ")");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delTableFlows(DBconnection dBconnection)
    {
        try{
            dBconnection.updateQuery("call PRO_DEL_FLOWS()");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delTableGroup(DBconnection dBconnection)
    {
        try{
            dBconnection.updateQuery("call PRO_DEL_GROUP()");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delTableGroupFlow(DBconnection dBconnection)
    {
        try{
            dBconnection.updateQuery("call PRO_DEL_GROUP_FLOW()");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
