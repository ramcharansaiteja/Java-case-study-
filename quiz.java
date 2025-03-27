import java.util.*;

// Interface for scoring
interface Scorable {
    int calculateScore();
    
    public static void main(String[] args) {
        OnlineQuizSystem.main(args);
    }
}

// Abstract class for User
abstract class User {
    protected int userId;
    protected String name, email;
    protected String password;

    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password)) {
            System.out.println(name + " logged in successfully.");
            return true;
        } else {
            System.out.println("Invalid login credentials.");
            return false;
        }
    }
}

// Student class
class Student extends User {
    private List<Integer> quizScores;

    public Student(int userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.quizScores = new ArrayList<>();
    }

    public void attemptQuiz(Quiz quiz, Scanner scanner) {
        System.out.print("Enter quiz password: ");
        String enteredPassword = scanner.nextLine();

        if (quiz.validatePassword(enteredPassword)) {
            int score = quiz.startQuiz();
            quizScores.add(score);
        } else {
            System.out.println("Incorrect quiz password. Access denied.");
        }
    }
}

// Admin class
class Admin extends User {
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    public void createQuiz(Quiz quiz) {
        System.out.println("Quiz \"" + quiz.getTitle() + "\" created.");
    }
}

// Quiz class
class Quiz implements Scorable {
    private String title;
    private List<Question> questions;
    private int timeLimit;
    private Scanner scanner;
    private String quizPassword;

    public Quiz(String title, int timeLimit, Scanner scanner, String quizPassword) {
        this.title = title;
        this.questions = new ArrayList<>();
        this.timeLimit = timeLimit;
        this.scanner = scanner;
        this.quizPassword = quizPassword;
    }

    public String getTitle() {
        return title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public boolean validatePassword(String enteredPassword) {
        return quizPassword.equals(enteredPassword);
    }

    public int startQuiz() {
        int score = 0;
        long startTime = System.currentTimeMillis();
        Collections.shuffle(questions);

        System.out.println("\nStarting Quiz: " + title + " (Time Limit: " + timeLimit + " sec)");
        for (Question q : questions) {
            q.displayQuestion();
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine();

            if (q.checkAnswer(userAnswer)) {
                score++;
            }

            if ((System.currentTimeMillis() - startTime) / 1000 > timeLimit) {
                System.out.println("Time's up!");
                break;
            }
        }

        System.out.println("\nQuiz Completed! Your Score: " + score + "/" + questions.size());
        return score;
    }

    @Override
    public int calculateScore() {
        return questions.size();
    }
}

// Abstract Question class
abstract class Question {
    protected String text;

    public Question(String text) {
        this.text = text;
    }

    public abstract void displayQuestion();
    public abstract boolean checkAnswer(String answer);
}

// Multiple Choice Question
class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private String correctAnswer;

    public MultipleChoiceQuestion(String text, List<String> options, String correctAnswer) {
        super(text);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n" + text);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        return options.get(Integer.parseInt(answer) - 1).equalsIgnoreCase(correctAnswer);
    }
}

// Main class
public class OnlineQuizSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create Admin
        Admin admin = new Admin(1, "Admin1", "admin@example.com", "adminpass");
        admin.login("admin@example.com", "adminpass");

        // Create Quiz with password
        Quiz quiz = new Quiz("Java Basics", 30, scanner, "quiz123");
        quiz.addQuestion(new MultipleChoiceQuestion("Which language is used for Android development?", 
                Arrays.asList("Java", "C++", "Python", "JavaScript"), "Java"));

        admin.createQuiz(quiz);

        // Create Student
        Student student = new Student(2, "Ram Charan", "charan@example.com", "studentpass");
        student.login("charan@example.com", "studentpass");

        // Student attempts quiz
        student.attemptQuiz(quiz, scanner);
        
        scanner.close();
    }
}