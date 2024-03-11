package com.example.summativeui.controller;

import com.example.summativeui.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class TestDatabase {
    private static ResourceBundle lang = ResourceBundle.getBundle("lang/en", Locale.ENGLISH);

    // Create connection with test db
    static Connection InitDBConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = Env.readEnv();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                env.get("dburl") + env.get("testdbschema"), env.get("dbusername"), env.get("dbpassword"));

        return con;
    }

    static void ClearDBTable(Connection conn, String table) throws SQLException {
        conn.createStatement().execute("TRUNCATE TABLE " + table);
    }

}
