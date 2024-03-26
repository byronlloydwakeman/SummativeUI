package com.example.summativeui.database;

import com.example.summativeui.Env;
import com.example.summativeui.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class DatabaseEndpoint
{
    private static Connection connection = null;
    private String dbschema;
    private static String dbtableusers;
    private static String dbtablegroceries;
    private String dbtableinventory;
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
                dbtablegroceries = env.get("prodtablegroceries");
                dbtableinventory = env.get("prodtableinventory");
            }
            case "test" -> {
                dbschema = env.get("testdbschema");
                dbtableusers = env.get("testdbtableusers");
                dbtablegroceries = env.get("testdbtablegroceries");
                dbtableinventory = env.get("testdbtableinventory");
            }
        }

        this.connection = InitDBConnection();
    }

    private Connection InitDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
    env.get("dburl") + dbschema, env.get("dbusername"), env.get("dbpassword"));
    }

    public static LoginModel Login(String username, String password) throws SQLException{
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

    private static DBLoginModel CallLoginEndpoint(String username) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + dbtableusers + " WHERE username = \"" + username + "\"");
        while (rs.next()) {
            String dbpassword = rs.getString(2);
            boolean dbadmin = rs.getInt(3) == 1;
            // Find the user in the db
            if (username.equals(rs.getString(1)))
            {
                return new DBLoginModel(dbpassword, dbadmin);
            }
        }
        connection.close(); // Close connection outside of the loop

        //Shouldn't reach here
        throw new SQLException("Error logging in");
    }

    public static DBResponseModel CreateUser(String username, String password, boolean isAdmin) throws Exception {
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

    private static int CallCreateUserEndpoint(String username, String password, boolean isAdmin) throws Exception {
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

    public static GroceryModel ReadAllGroceries() throws Exception {
        ArrayList<DBGroceryModel> dbGroceryModels = CallReadAllGroceriesEndpoint();

        String response = "Data retrieval successful";

        return new GroceryModel(true, response, dbGroceryModels);
    }

    private static ArrayList<DBGroceryModel> CallReadAllGroceriesEndpoint() throws SQLException {
        ArrayList<DBGroceryModel> dbGroceryModels = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + dbtablegroceries);
        while (rs.next()) {
            String grocery = rs.getString(1);
            String grade = rs.getString(2);
            Date sellByDate = rs.getDate(3);
            int weight = rs.getInt(4);
            String origin = rs.getString(5);
            boolean freezable = Boolean.parseBoolean(String.valueOf(rs.getInt(6)));
            String foodCategory = rs.getString(7);
            int volume = rs.getInt(8);
            int price = rs.getInt(9);
            String image = rs.getString(10);
            String brand = rs.getString(11);
            String allergies = rs.getString(12);
            String storage = rs.getString(13);
            String countryOrigin = rs.getString(14);
            String description = rs.getString(15);
            int alcoholicPercentage = rs.getInt(16);
            int quantity = rs.getInt(17);
            int nutritionalContent = rs.getInt(18);
            dbGroceryModels.add(new DBGroceryModel(grocery, grade, sellByDate, weight, origin, freezable, foodCategory, volume,
                    price, brand, allergies, storage, countryOrigin, description, alcoholicPercentage, quantity, nutritionalContent, image));
        }
        connection.close(); // Close connection outside of the loop

        return dbGroceryModels;
    }

    public static GroceryModel ReadGroceryById(String groceryId) throws SQLException {
        String response = "Data retrieval successful";

        return new GroceryModel(true, response, CallReadGroceryByIdEndpoint(groceryId));
    }

    private static DBGroceryModel CallReadGroceryByIdEndpoint(String groceryId) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + dbtablegroceries + " WHERE grocery = \"" + groceryId + "\"");
        while (rs.next()) {
            String grocery = rs.getString(1);
            String grade = rs.getString(2);
            Date sellByDate = rs.getDate(3);
            int weight = rs.getInt(4);
            String origin = rs.getString(5);
            boolean freezable = Boolean.parseBoolean(String.valueOf(rs.getInt(6)));
            String foodCategory = rs.getString(7);
            int volume = rs.getInt(8);
            int price = rs.getInt(9);
            String brand = rs.getString(10);
            String image = rs.getString(11);
            String allergies = rs.getString(12);
            String storage = rs.getString(13);
            String countryOrigin = rs.getString(14);
            String description = rs.getString(15);
            int alcoholicPercentage = rs.getInt(16);
            int quantity = rs.getInt(17);
            int nutritionalContent = rs.getInt(18);
            return new DBGroceryModel(grocery, grade, sellByDate, weight, origin, freezable, foodCategory, volume,
                    price, brand, allergies, storage, countryOrigin, description, alcoholicPercentage, quantity, nutritionalContent, image);
        }

        throw new SQLException("Error retrieving " + groceryId + " from DB");
    }

    public InventoryModel ReadAllInventory() throws SQLException {
        ArrayList<DBInventoryModel> dbInventoryModels = CallReadAllInventoryEndpoint();

        String response = "Data retrieval successful";

        return new InventoryModel(true, response, dbInventoryModels);
    }

    private ArrayList<DBInventoryModel> CallReadAllInventoryEndpoint() throws SQLException {
        ArrayList<DBInventoryModel> dbInventoryModels = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + dbtableinventory);
        while (rs.next()) {
            String groceryId = rs.getString(1);
            int quantity = rs.getInt(2);
            int totalPrice = rs.getInt(3);
            Date expirationDate = rs.getDate(4);
            Date arrivalDate = rs.getDate(5);
            Date expectedDepartureDate = rs.getDate(6);
            String location = rs.getString(7);
            dbInventoryModels.add(new DBInventoryModel(groceryId, quantity, totalPrice, expirationDate, arrivalDate, expectedDepartureDate, location));
        }

        return dbInventoryModels;
    }
}

