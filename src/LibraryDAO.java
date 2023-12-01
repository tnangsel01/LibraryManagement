import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {
    private Connection connection;

    public LibraryDAO(Connection connection) {
        this.connection = connection;
    }

    public void addLibrary(Library library) {
        String query = "INSERT INTO libraries (name, address, phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, library.getName());
            preparedStatement.setString(2, library.getAddress());
            preparedStatement.setString(3, library.getPhoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately in your application
        }
    }

    public List<Library> getAllLibraries() {
        List<Library> libraries = new ArrayList<>();
        String query = "SELECT * FROM libraries";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phone_number");

                Library library = new Library(name, address, phoneNumber);
                libraries.add(library);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately in your application
        }
        return libraries;
    }

    // Implement update and delete methods as needed
}