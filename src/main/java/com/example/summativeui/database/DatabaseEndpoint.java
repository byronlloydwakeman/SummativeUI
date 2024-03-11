package com.example.summativeui.database;

import com.example.summativeui.Env;
import com.example.summativeui.model.DBLoginModel;
import com.example.summativeui.model.DBResponseModel;
import com.example.summativeui.model.LoginModel;
import java.sql.*;
import java.util.Map;

public class DatabaseEndpoint
{
    private final Connection connection;
    private String dbschema;
    private String dbtableusers;
    private final Map<String, String> env = Env.readEnv();

    public DatabaseEndpoint() throws ClassNotFoundException, SQLException {
        String state = System.getenv("ENVIRONMENT");

        // Set prob to default
        dbschema = env.get("proddbschema");
        dbtableusers = env.get("proddbtableusers");
        // Connect the right db depending on whether its prod being run or a test

        switch (state) {
            case "production" -> {
                dbschema = env.get("proddbschema");
                dbtableusers = env.get("proddbtableusers");
            }
            case "test" -> {
                dbschema = env.get("testdbschema");
                dbtableusers = env.get("testdbtableusers");
            }
        }

        this.connection = InitDBConnection();
    }

    private Connection InitDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
    env.get("dburl") + dbschema, env.get("dbusername"), env.get("dbpassword"));
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + dbtableusers + " WHERE username = \"" + username + "\"");
            while (rs.next()) {
                String dbpassword = rs.getString(2);
                boolean dbadmin = Boolean.parseBoolean(String.valueOf(rs.getInt(3)));
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
            String query = "INSERT INTO " + dbtableusers + " (username, password, admin) " +
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

