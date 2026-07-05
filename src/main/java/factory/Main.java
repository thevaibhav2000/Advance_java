package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // 1. Master Credentials (accessible to all methods)
    static String url = "jdbc:postgresql://localhost:5432/smart_factory";
    static String user = "postgres";
    static String password = "admin1234";

    public static void main(String[] args) {
        logger.info("Starting Factory Operations...");

        // Step 1: Insert a new machine into the database
        addMachine(102, "Robotic Arm", "ACTIVE");

        // Step 2: Retrieve and print the full inventory
        viewInventory();
    }

    // --- METHOD 1: THE INSERT LOGIC ---
    public static void addMachine(int id, String name, String status) {
        String sql = "INSERT INTO machine (id, name, status) VALUES (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, status);

            int rows = statement.executeUpdate();
            if (rows > 0) {
                logger.info("✅ SUCCESS! " + name + " saved to database.");
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "❌ FAILED to insert data.", e);
        }
    }

    // --- METHOD 2: THE SELECT LOGIC ---
    public static void viewInventory() {
        String sql = "SELECT id, name, status FROM machine";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n--- 🏭 FACTORY INVENTORY REPORT ---");
            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                String dbName = resultSet.getString("name");
                String dbStatus = resultSet.getString("status");
                System.out.println("ID: " + dbId + " | Name: " + dbName + " | Status: " + dbStatus);
            }
            System.out.println("-----------------------------------\n");

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "❌ FAILED to retrieve data.", e);
        }
    }
}