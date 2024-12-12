import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookLib {
    private static List<Book> library = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add a book");
            System.out.println("2. Search for a book by title");
            System.out.println("3. Display all books");
            System.out.println("0. Exit\n");
            System.out.print("Enter number: ");
            
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
                continue;
            }

            if (input == 1) {
                System.out.print("Enter the book title: ");
                String title = scanner.nextLine();
                System.out.print("Enter the book author: ");
                String author = scanner.nextLine();
                System.out.print("Enter the publication year: ");
                int year = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter the ISBN: ");
                String isbn = scanner.nextLine();
                
                library.add(new Book(title, author, year, isbn));
                System.out.println("Book added");

            } else if (input == 2) {
                System.out.print("\nEnter the book title to search: ");
                String titleToFind = scanner.nextLine();
                boolean found = false;

                for (Book book : library) {
                    if (book.getTitle().equalsIgnoreCase(titleToFind)) {
                        book.displayInfo();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Book not found.\n");
                }

            } else if (input == 3) {
                System.out.println("\nList of all books:");
                for (Book book : library) {
                    book.displayInfo();
                }

            } else if (input == 0) {
                break;
            } else {
                System.out.println("Error, please try again.");
            }
        }
      scanner.close(); 
    }
}

class Book {
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
