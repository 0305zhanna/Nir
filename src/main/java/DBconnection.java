import java.sql.*;
import java.util.Locale;

/**
 * Created by User on 04.06.2017.
 */
public class DBconnection {
    private String root;
    private String password;
    private String url;

    private Connection oracleConnection;

    public DBconnection(String name, String pswd)
    {
        this.root = name;
        this.password = pswd;
        url = "jdbc:oracle:thin:@localhost:1521/orcl";
    }

    public void init()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Locale.setDefault(Locale.ENGLISH);
            oracleConnection = DriverManager.getConnection(url, root, password);
            System.out.println("connect!");
            Locale.setDefault(Locale.getDefault());
            System.out.println("Удачное подключение");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void finiliaze()
    {
        try {
            oracleConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectQuery(String text)
    {
        ResultSet resultSet=null;
        try {
            Statement statement = oracleConnection.createStatement();
            resultSet = statement.executeQuery(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateQuery(String text)
    {
        try {
            Statement statement = oracleConnection.createStatement();
            statement.executeUpdate(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void execute(String text)
    {
        try {
            Statement statement = oracleConnection.createStatement();
            statement.execute(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
