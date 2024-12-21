import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Quiz {

    static class Question {
        String question;
        String[] options;
        int correctAnswerIndex;

        public Question(String question, String[] options, int correctAnswerIndex) {
            this.question = question;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public boolean isCorrect(int answer) {
            return answer == correctAnswerIndex;
        }
    }

    private static List<Question> questions = new ArrayList<>();

    public static void main(String[] args) {
        initializeQuestions();
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        System.out.println("QUIZ\n");

        for (Question q : questions) {
            System.out.println(q.question);
            for (int i = 0; i < q.options.length; i++) {
                System.out.printf("%d. %s%n", i + 1, q.options[i]);
            }

            int userAnswer = getUserAnswer(scanner, q.options.length);

            if (q.isCorrect(userAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer: " + (q.correctAnswerIndex + 1));
            }
            System.out.println();
        }

        System.out.printf("Your total score: %d out of %d%n", score, questions.size());
        scanner.close();
    }

    private static int getUserAnswer(Scanner scanner, int optionsLength) {
        int userAnswer = -1;
        while (true) {
            System.out.print("Your answer (enter the number): ");
            try {
                userAnswer = scanner.nextInt() - 1;
                if (userAnswer < 0 || userAnswer >= optionsLength) {
                    System.out.println("Please enter a number corresponding to an answer option.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.next();
            }
        }
        return userAnswer;
    }

    private static void initializeQuestions() {
        questions.add(new Question(
                "What year is considered the founding year of Rome?",
                new String[]{"753 BC", "500 BC", "1000 AD", "476 AD"},
                0
        ));

        questions.add(new Question(
                "What is the capital of France?",
                new String[]{"Berlin", "Madrid", "Paris", "Rome"},
                2
        ));

        questions.add(new Question(
                "What number is the square of 5?",
                new String[]{"20", "25", "30", "15"},
                1
        ));

        questions.add(new Question(
                "Who was the first president of the USA?",
                new String[]{"Abraham Lincoln", "George Washington", "Thomas Jefferson", "Barack Obama"},
                1
        ));

        questions.add(new Question(
                "Which continent is Egypt located on?",
                new String[]{"Asia", "Europe", "Africa", "Australia"},
                2
        ));

        questions.add(new Question(
                "How many angles does a triangle have?",
                new String[]{"2", "3", "4", "5"},
                1
        ));
    }
}