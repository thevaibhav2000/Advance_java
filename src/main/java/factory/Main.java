package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    // 1. Initialize the official Factory Logger
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/smart_factory";
        String user = "postgres";
        String password = "admin1234";

        try {
            // Replace System.out.println with logger.info
            logger.info("Attempting to connect to the database...");
            Connection connection = DriverManager.getConnection(url, user, password);

            logger.info("✅ SUCCESS! The bridge to PostgreSQL is officially open.");

        } catch (SQLException e) {
            // 2. The Robust Logging Replacement
            // This logs the error at the 'SEVERE' level and securely attaches the stack trace
            logger.log(Level.SEVERE, "❌ FAILED! The bridge collapsed.", e);
        }
    }
}