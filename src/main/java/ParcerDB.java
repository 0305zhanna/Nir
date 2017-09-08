/**
 * Created by User on 07.06.2017.
 */
public class ParcerDB {
    private ParserForTeachers parserForTeachers;
    private ParserForFlows parserForFlows;
    private ParserForNagruzka parserForNagruzka;
    private DBconnection db;

    ParcerDB(String flows, String teachers, String nagruzka){
        parserForTeachers = new ParserForTeachers(teachers);
        parserForFlows = new ParserForFlows(flows);
        parserForNagruzka = new ParserForNagruzka(nagruzka);
    }
    public void initConnection(String name, String pswd){
        db = new DBconnection(name, pswd);
        db.init();
    }
    public void finilizeConnection(){
        db.finiliaze();
    }
    public void creadeDB(){
        parserForTeachers.parse();
        parserForFlows.parse();
        parserForNagruzka.parse();
        parserForNagruzka.fillingTableDiscipline(db);
        System.out.println("fillingTableDiscipline");
        parserForNagruzka.fillingTableStudyLoad(db);
        System.out.println("fillingTableStudyLoad");
        parserForTeachers.fillingTableTeachers(db);
        System.out.println("fillingTableTeachers");
        parserForTeachers.fillingTableEmployement(db);
        System.out.println("fillingTableEmployement");
        parserForFlows.fillingTableFlow(db);
        System.out.println("fillingTableFlow");
        parserForFlows.fillingTableGroup(db);
        System.out.println("fillingTableGroup");
        parserForFlows.fillingTableGroupFlow(db);
        System.out.println("fillingTableGroupFlow");
        parserForNagruzka.updatingTableGroup(db);
        System.out.println("updatingTableGroup");
    }
    public  void deleteDB(){
        parserForNagruzka.delTableStudyLoad(db);
        System.out.println("delTableStudyLoad");
        parserForTeachers.delTableTeachers(db);
        System.out.println("delTableTeachers");
        parserForTeachers.delTableEmployement(db);
        System.out.println("delTableEmployement");
        parserForFlows.delTableGroupFlow(db);
        System.out.println("delTableGroupFlow");
        parserForFlows.delTableFlows(db);
        System.out.println("delTableFlows");
        parserForFlows.delTableGroup(db);
        System.out.println("delTableGroup");
        parserForNagruzka.delTableDiscipline(db);
        System.out.println("delTableDiscipline");
    }
}
