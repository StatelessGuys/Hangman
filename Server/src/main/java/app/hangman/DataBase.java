package app.hangman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public static final String URL = "jdbc:mariadb://localhost:3306/wordscollection";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Goroan1974";

    private  static Connection connection;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<WordJDBC> index() {
        List<WordJDBC> word = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM  words";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                WordJDBC wordJDBC = new WordJDBC();
                wordJDBC.setId(resultSet.getInt("ID"));
                wordJDBC.setWord(resultSet.getString("words"));
                word.add(wordJDBC);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  word;
    }
}
