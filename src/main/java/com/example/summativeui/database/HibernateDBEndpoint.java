package com.example.summativeui.database;

import com.example.summativeui.Env;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HibernateDBEndpoint
{
    static Session databaseSession = null;
    static SessionFactory sessionFactory = null;

    private static String dbschema;
    private static String dbtableusers;
    private static String dbtablegroceries;
    private String dbtableinventory;

    private final Map<String, String> env = Env.readEnv();

    public HibernateDBEndpoint() {
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
    }

    public static List<?> getGrocery(String groceryId)
    {
        openDBSession();
        Query query = databaseSession.createQuery("FROM " + dbschema + "." + dbtablegroceries);
        List<?> groceries = query.list();
        closeDBSession();
        return groceries;
    }

//    public static void addEmployeeToDatabase(Employee employeeToAdd)
//    {
//        openDBSession();
//        employeeToAdd.setEmployeeID(getNextID());
//        databaseSession.beginTransaction();
//        databaseSession.save(employeeToAdd);
//        databaseSession.getTransaction().commit();
//        closeDBSession();
//    }
//    public static List<?> getEmployee(int employeeID)
//    {
//        openDBSession();
//        Query query = databaseSession.createQuery("from Employee where employeeID = "+employeeID);
//        List<?> list = query.list();
//        closeDBSession();
//        return list;
//    }
    private static void openDBSession()
    {
        // configure hibernate for application (one per database) & allows creation of sessions
        sessionFactory = new Configuration().configure().buildSessionFactory();
        // used to get a physical connection with the database
        databaseSession = sessionFactory.openSession();
    }
    private static void closeDBSession()
    {
        // close session and the session factory instances
        sessionFactory.close();
        databaseSession.close();
        sessionFactory = null;
        databaseSession = null;
    }
//    private static int getNextID()
//    {
//        Query query = databaseSession.createQuery("select max(employeeID) from Employee");
//        System.out.println( query.list().get(0));
//        return (Integer) query.list().get(0)+1;
//    }
}

