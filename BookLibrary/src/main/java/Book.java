public class Book {
    private String title;
    private String author;
    private int year;
    private String isbn;

    public Book(String title, String author, int year, String isbn) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    void displayInfo() {
        System.out.printf("Title: %s, Author: %s, Year: %d, ISBN: %s%n\n", title, author, year, isbn);
    }
}