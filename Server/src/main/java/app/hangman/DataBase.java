package app.hangman;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.Exception;
import java.nio.file.Files;
import java.nio.file.Paths;
 

public class DataBase {
    private static final String URL = "jdbc:mariadb://localhost:";
    private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/../configs/db_config.conf";
    private static final String DB_DRIVER = "org.mariadb.jdbc.Driver";
    private static Connection connection = null;

    static {
        try {
            Class.forName(DB_DRIVER);

            String configJson = new String(Files.readAllBytes(Paths.get(CONFIG_FILE_PATH)));
            DBConfig dbConfig = new ObjectMapper().readValue(configJson, DBConfig.class);

            connection = DriverManager.getConnection(formattedUrl(dbConfig), dbConfig.user, dbConfig.password);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    private static String formattedUrl(DBConfig dbConfig)
    {
        return URL + dbConfig.port + "/" + dbConfig.dbName;
    }

    public List<String> readWords() {
        List<String> words = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT word FROM  words";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                String word = resultSet.getString("word");
                words.add(word);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  words;
    }
}
