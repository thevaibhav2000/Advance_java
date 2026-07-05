package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        // 1. Your Database Credentials
        String url = "jdbc:postgresql://localhost:5432/smart_factory";
        String user = "postgres";
        String password = "admin1234";

        // 2. The SQL Blueprint (The Conveyor Belt)
        // We use ? as secure placeholders for our variables
        String sql = "INSERT INTO machine (id, name, status) VALUES (?, ?, ?)";

        try {
            // Open the bridge
            Connection connection = DriverManager.getConnection(url, user, password);
            logger.info("✅ Bridge opened successfully.");

            // 3. Prepare the Conveyor Belt
            PreparedStatement statement = connection.prepareStatement(sql);

            // 4. Load the Data into the ? placeholders
            statement.setInt(1, 101);                        // First ?: ID
            statement.setString(2, "Hydraulic Press");       // Second ?: Name
            statement.setString(3, "ACTIVE");                // Third ?: Status

            // 5. Execute the Shipment
            logger.info("Shipping the machine data...");
            int rowsAffected = statement.executeUpdate();

            // 6. Verify the Delivery
            if (rowsAffected > 0) {
                logger.info("✅ SUCCESS! " + rowsAffected + " machine(s) safely stored in the warehouse.");
            }

            // Clean up the factory floor
            statement.close();
            connection.close();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "❌ FAILED! The shipment crashed.", e);
        }
    }
}