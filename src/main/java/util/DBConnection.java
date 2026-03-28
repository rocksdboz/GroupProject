package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Use root with empty password if your MySQL root has no password
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/group_project_db",
                "root",   // MySQL username
                "chnkrs2004"        // MySQL password, leave empty if none
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}