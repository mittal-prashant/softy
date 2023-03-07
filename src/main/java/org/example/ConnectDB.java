package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

    public static Connection connectDB() {

        /*
        Function to connect Java to the backend PostgreSQL using JDBC driver.
         */

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/aims",
                            "postgres", "hiprashant");
            return conn;

            /*
            Catch exception if database not connected properly.
             */

        } catch (Exception e) {
            System.err.println("Unable to connect to the Database!");
            System.err.println("Exception occurred: " + e.getMessage() + "\n");
            return null;
        }

    }
}