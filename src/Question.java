import java.util.Arrays;

/**
 * Created by JP on 10/10/2014.
 */

enum QuestionType{
    SPORTS,ARTS,SCIENCE,HISTORY,PEOPLE
}

public class Question {
    private QuestionType type;
    private int ID;
    private String question;
    private String[] answers;
    private String solution;

    public QuestionType getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getSolution() {
        return solution;
    }

    public Question(QuestionType type, int ID, String question, String[] answers, int solution) {
        this.type = type;
        this.ID = ID;
        this.question = question;
        this.answers = answers;
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Question{" +
                "type=" + type +
                ", ID=" + ID +
                ", question='" + question + '\'' +
                ", answers=" + Arrays.toString(answers) +
                ", solution='" + solution + '\'' +
                '}';
    }
}
