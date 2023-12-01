import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    public void addBook(Book book) {
        String query = "INSERT INTO books (name, isbn, author, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setDouble(4, book.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately in your application
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String isbn = resultSet.getString("isbn");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");

                Book book = new Book(name, isbn, author, price);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately in your application
        }
        return books;
    }

    // Search for a book by name and display where it is available
    public List<Book> searchByBookName(String bookName) {
        String query = "SELECT b.*, l.name AS library_name, l.address AS library_address, l.phone_number AS library_phone" +
        "FROM books b JOIN library_books lb ON b.id = lb.book_id JOIN libraries l ON lb.library_id = l.id WHERE b.name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String bookISBN = resultSet.getString("isbn");
                String bookAuthor = resultSet.getString("author");
                double bookPrice = resultSet.getDouble("price");
                String libraryName = resultSet.getString("library_name");
                String libraryAddress = resultSet.getString("library_address");
                String libraryPhone = resultSet.getString("library_phone");

                System.out.println("Book Name: " + bookName);
                System.out.println("ISBN: " + bookISBN);
                System.out.println("Author: " + bookAuthor);
                System.out.println("Price: " + bookPrice);
                System.out.println("Available at:");
                System.out.println("Library Name: " + libraryName);
                System.out.println("Library Address: " + libraryAddress);
                System.out.println("Library Phone: " + libraryPhone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            ErrorLog.logException(e);
        }
        return null;
    }

    public Book searchByISBN(String isbn) {
        Book book = null;

        String query = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, isbn);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = extractBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return book;
    }

    public List<Book> searchByAuthor(String authorName) {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books WHERE author = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, authorName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = extractBookFromResultSet(resultSet);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return books;
    }

    // other methods...

    private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setName(resultSet.getString("name"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getDouble("price"));
        // Set other book properties as needed
        return book;
    }

    // Implement update and delete methods as needed
}