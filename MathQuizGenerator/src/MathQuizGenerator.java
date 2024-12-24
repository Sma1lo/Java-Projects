import java.util.Random;
import java.util.Scanner;

public class MathQuizGenerator {

    enum Operation {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int score = 0;

        System.out.println("1. Easy (numbers from 1 to 10)");
        System.out.println("2. Medium (numbers from 1 to 50)");
        System.out.println("3. Hard (numbers from 1 to 100)");
        System.out.print("\nChoose difficulty level: ");

        int level = scanner.nextInt();
        int maxNumber;

        switch (level) {
            case 1:
                maxNumber = 10;
                break;
            case 2:
                maxNumber = 50;
                break;
            case 3:
                maxNumber = 100;
                break;
            default:
                System.out.println("Invalid difficulty level.");
                scanner.close();
                return;
        }

        for (int i = 0; i < 5; i++) {
            score += generateQuestion(scanner, random, maxNumber, i + 1);
        }

        System.out.printf("Your score: %d out of 5%n", score);
        scanner.close();
    }

    private static int generateQuestion(Scanner scanner, Random random, int maxNumber, int questionNumber) {
        int firstNumber = random.nextInt(maxNumber) + 1;
        int secondNumber = random.nextInt(maxNumber) + 1;
        Operation operation = Operation.values()[random.nextInt(Operation.values().length)];

        int correctAnswer;
        String operationSymbol;

        switch (operation) {
            case ADDITION:
                correctAnswer = firstNumber + secondNumber;
                operationSymbol = "+";
                break;
            case SUBTRACTION:
                correctAnswer = firstNumber - secondNumber;
                operationSymbol = "-";
                break;
            case MULTIPLICATION:
                correctAnswer = firstNumber * secondNumber;
                operationSymbol = "*";
                break;
            case DIVISION:
                correctAnswer = secondNumber != 0 ? firstNumber / secondNumber : 0;
                operationSymbol = "/";
                break;
            default:
                throw new IllegalStateException("Unexpected operation: " + operation);
        }

        System.out.printf("Question %d: %d %s %d = ?%n", questionNumber, firstNumber, operationSymbol, secondNumber);
        int userAnswer = scanner.nextInt();

        if (userAnswer == correctAnswer) {
            System.out.println("Correct!");
            return 1;
        } else {
            System.out.printf("Incorrect. The correct answer is: %d%n", correctAnswer);
            return 0;
        }
    }
          }
