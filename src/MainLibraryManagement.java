import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainLibraryManagement {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnector.connect()) {
            BookDAO bookDAO = new BookDAO(connection);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the Library Management System!");

            int loginChoice;
            do {
                printLoginOptions();
                loginChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (loginChoice) {
                    case 1:
                        handleLogin(bookDAO, scanner, connection);
                        break;
                    case 2:
                        handleAccountCreation(scanner, connection);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (loginChoice != 1);

        } catch (SQLException e) {
            handleDatabaseConnectionFailure(e);
        }
    }

    private static void printLoginOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Login");
        System.out.println("2. Create an account");
        System.out.print("Enter your choice: ");
    }

    private static void handleLogin(BookDAO bookDAO, Scanner scanner, Connection connection) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (authenticateUser(username, password, connection)) {
            System.out.println("Login successful!\n");
            performBookSearch(bookDAO, scanner);
        } else {
            handleLoginFailure(scanner, connection);
        }
    }

    private static void handleLoginFailure(Scanner scanner, Connection connection) {
        System.out.println("Login failed. What would you like to do?");
        System.out.println("1. Try login again");
        System.out.println("2. Create a new account");
        System.out.print("Enter your choice: ");
        int retryChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (retryChoice) {
            case 1:
                handleLoginRetry(scanner, connection);
                break;
            case 2:
                handleAccountCreation(scanner, connection);
                break;
            default:
                System.out.println("Invalid choice. Exiting.");
                System.exit(0);
        }
    }

    private static void handleLoginRetry(Scanner scanner, Connection connection) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (authenticateUser(username, password, connection)) {
            System.out.println("Login successful!\n");
            performBookSearch(new BookDAO(connection), scanner);
        } else {
            System.out.println("Login failed. Goodbye.");
        }
    }

    private static boolean authenticateUser(String username, String password, Connection connection) {
        UserDAO userDAO = new UserDAO(connection);
        String storedHashedPassword = userDAO.getStoredHashedPassword(username);
        return storedHashedPassword != null && storedHashedPassword.equals(hashPassword(password));
    }

    private static void handleAccountCreation(Scanner scanner, Connection connection) {
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your desired username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String newPassword = scanner.nextLine();
        String hashPassword = hashPassword(newPassword);

        User newUser = new User(fullName, newUsername, email, hashPassword);
        UserDAO userDAO = new UserDAO(connection);
        userDAO.addUser(newUser);

        System.out.println("Account created successfully. Please log in.\n");
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void performBookSearch(BookDAO bookDAO, Scanner scanner) {
        System.out.println("Choose an option:");
        System.out.println("1. Search by book name");
        System.out.println("2. Search by author");
        System.out.println("3. Search by ISBN");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                System.out.print("Enter book name: ");
                String bookName = scanner.nextLine();
                searchByBookName(bookDAO, bookName);
                break;
            case 2:
                System.out.print("Enter author name: ");
                String authorName = scanner.nextLine();
                searchByAuthor(bookDAO, authorName);
                break;
            case 3:
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                searchByISBN(bookDAO, isbn);
                break;
            case 0:
                System.out.println("Exiting. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private static void searchByBookName(BookDAO bookDAO, String bookName) {
        List<Book> books = bookDAO.searchByBookName(bookName);

        if (books.isEmpty()) {
            System.out.println("No books found with the given name.");
        } else {
            System.out.println("Books found:");
            for (Book book : books) {
                displayBookDetails(book);
            }
        }
    }

    private static void searchByAuthor(BookDAO bookDAO, String authorName) {
        List<Book> books = bookDAO.searchByAuthor(authorName);

        if (books.isEmpty()) {
            System.out.println("No books found by the given author.");
        } else {
            System.out.println("Books found:");
            for (Book book : books) {
                displayBookDetails(book);
            }
        }
    }

    private static void searchByISBN(BookDAO bookDAO, String isbn) {
        Book book = bookDAO.searchByISBN(isbn);

        if (book == null) {
            System.out.println("No book found with the given ISBN.");
        } else {
            System.out.println("Book found:");
            displayBookDetails(book);
        }
    }

    private static void displayBookDetails(Book book) {
        System.out.println("Title: " + book.getName());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Price: " + book.getPrice());
        System.out.println();
    }
    private static void handleDatabaseConnectionFailure(SQLException e) {
        e.printStackTrace();
        ErrorLog.logException(e);
        System.out.println("Database connection failure.");
    }
}
