import java.util.*;

// User class
class User {
    String name, email, password;
    User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

// Question class
class Question {
    String question;
    String[] options;
    Set<String> correctAnswers;
    boolean isMultiCorrect;
    boolean isFillInTheBlank;

    Question(String question, String[] options, String correctAnswer, boolean isFillInTheBlank) {
        this.question = question;
        this.options = options;
        this.correctAnswers = new HashSet<>(Arrays.asList(correctAnswer.split(",")));
        this.isMultiCorrect = correctAnswers.size() > 1;
        this.isFillInTheBlank = isFillInTheBlank;
    }
}

// Quiz class
class Quiz {
    List<Question> questions = new ArrayList<>();
    int score = 0;
    Scanner sc = new Scanner(System.in);

    void addQuestions() {
        questions.add(new Question("Java is platform-independent?", new String[]{"True", "False"}, "1", false));
        questions.add(new Question("Fill in the blank: Java is a ___ language.", new String[]{}, "Compiled", true));
        // questions.add(new Question("Which of these are OOP principles?", new String[]{"Encapsulation", "Polymorphism", "Abstraction", "Inheritance"}, "1,2,3,4", true));
        questions.add(new Question("Select valid Java keywords:", new String[]{"class", "static", "int", "function"}, "1,2,3", false));
        questions.add(new Question("java is invented by:", new String[]{"ryan gosling", "james gosling", "rajamani", "gorli ravi"}, "2", false));
    }

    void startQuiz() {
        for (Question q : questions) {
            System.out.println(q.question);
            if (q.isFillInTheBlank) {
                System.out.print("Your answer: ");
                String ans = sc.nextLine().trim();
                if (q.correctAnswers.contains(ans)) score++;
            } else {
                for (int i = 0; i < q.options.length; i++)
                    System.out.println((i + 1) + ". " + q.options[i]);
                System.out.print("Your answer (for multiple, separate with commas): ");
                String ans = sc.next();
                Set<String> userAnswers = new HashSet<>(Arrays.asList(ans.split(",")));
                if (userAnswers.equals(q.correctAnswers)) score++;
                sc.nextLine(); // Consume the newline character
            }
        }
    }

    void displayScore() {
        System.out.println("Quiz completed! Your score: " + score + "/" + questions.size());
        System.out.print("Any feedback? ");
        String feedback = sc.nextLine();
        System.out.println("Thank you for your feedback!");
    }
}

// Main class
public class OnlineQuizSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        User user = new User(name, email, password);
        Quiz quiz = new Quiz();
        quiz.addQuestions();
        quiz.startQuiz();
        quiz.displayScore();
    }
}
