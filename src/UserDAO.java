import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
        // Ensure that the "users" table exists
        createUsersTable();
    }

    private void createUsersTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(255) UNIQUE NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) UNIQUE NOT NULL," +
                "full_name VARCHAR(255) NOT NULL" +
                ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorLog.logException(e); // Log the exception
        }
    }

    // Add a new user to the "users" table
    public void addUser(User user) {
        String addUserQuery = "INSERT INTO users (username, password, email, full_name) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFullName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorLog.logException(e); // Log the exception
        }
    }

    // Check if a user with the given username already exists
    public boolean doesUserExist(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ErrorLog.logException(e); // Log the exception
        }

        // Return false in case of an exception or no match found
        return false;
    }

    public String getStoredHashedPassword(String username) {
        String hashedPassword = null;

        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorLog.logException(e);
        }

        return hashedPassword;
    }
}
