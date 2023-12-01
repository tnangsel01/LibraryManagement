import java.util.ArrayList;
import java.util.List;

public class Library {
    private String name;
    private String address;
    private String phoneNumber;
    private List<Book> books;

    // Constructor
    public Library(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.books = new ArrayList<>();
    }

    // Getter and Setter methods for each attribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Book> getBooks() {
        return books;
    }

    // Add a book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", books=" + books +
                '}';
    }
}

