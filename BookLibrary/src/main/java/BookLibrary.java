import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 *@author Sma1lo
 */

public class BookLibrary {
    private static List<Book> library = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Add a book");
                System.out.println("2. Search for a book by title");
                System.out.println("3. Display all books");
                System.out.println("0. Exit\n");
                System.out.print("Enter number: ");

                int input;

                input = Integer.parseInt(scanner.nextLine());

                System.out.println("Invalid input, please enter a number.");

                switch (input) {
                    case 1:
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
                        break;
                    case 2:
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
                        break;
                    case 3:
                        System.out.println("\nList of all books:");
                        for (Book book : library) {
                            book.displayInfo();
                        }
                        break;
                    case 0:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }
}