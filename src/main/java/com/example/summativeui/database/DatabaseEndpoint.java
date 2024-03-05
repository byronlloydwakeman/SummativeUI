package com.example.summativeui.database;

import com.example.summativeui.Env;
import com.example.summativeui.model.DBLoginModel;
import com.example.summativeui.model.DBResponseModel;
import com.example.summativeui.model.LoginModel;
import java.sql.*;
import java.util.Map;

public class DatabaseEndpoint
{
    private Connection connection;

    public DatabaseEndpoint() throws ClassNotFoundException, SQLException {
        this.connection = InitDBConnection();
    }

    private Connection InitDBConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = Env.readEnv();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
    env.get("dburl") + "summativeusers", env.get("dbusername"), env.get("dbpassword"));

        return con;
    }

    public LoginModel Login(String username, String password){
        // Get database creds and check them against our username and password (hashed)
        String response = "";
        boolean successful = false;
        boolean isAdmin = false;
        try
        {
            DBLoginModel dbmodel = CallLoginEndpoint(username);
            // Check the username the user has given matches the one in the db
            isAdmin = dbmodel.getAdmin();
            if (dbmodel.getPassword().equals(password))
            {
                response = "Successfully Logged in!";
                successful = true;
            }
            else
            {
                response = "Incorrect username or password";
            }
        }
        catch(Exception e)
        {
            response = e.getMessage();
        }
        return new LoginModel(response, successful, isAdmin);
    }

    private DBLoginModel CallLoginEndpoint(String username) throws Exception {
        try
        {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = \"" + username + "\"");
            while (rs.next()) {
                String dbpassword = rs.getString(2);
                Boolean dbadmin = Boolean.valueOf(String.valueOf(rs.getInt(3)));
                return new DBLoginModel(dbpassword, dbadmin);
            }
            connection.close(); // Close connection outside of the loop
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //Shouldn't reach here
        throw new Exception("Error logging in");
    }

    public DBResponseModel CreateUser(String username, String password, boolean isAdmin) throws Exception {
        int inserted = CallCreateUserEndpoint(username, password, isAdmin);
        if(inserted != 1)
        {
            return new DBResponseModel(false, "Failed to create user");
        }
        else
        {
            return new DBResponseModel(true, "User " + "'" + username + "'" + " created successfully");
        }
    }

    private int CallCreateUserEndpoint(String username, String password, boolean isAdmin) throws Exception {
        try
        {
            Statement stmt = connection.createStatement();
            String query = "INSERT INTO users (username, password, admin) " +
                    "VALUES ('" + username + "', '" + password + "', " + isAdmin + ")";

            int inserted = stmt.executeUpdate(query);

            connection.close(); // Close connection outside of the loop

            return inserted;
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
    }
}

