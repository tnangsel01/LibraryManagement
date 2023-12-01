public class Book {
    private String name;
    private String isbn;
    private String author;
    private double price;

    // Constructor
    public Book(String name, String isbn, String author, double price) {
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.price = price;
    }

    public Book() {

    }

    // Getter and Setter methods for each attribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}

