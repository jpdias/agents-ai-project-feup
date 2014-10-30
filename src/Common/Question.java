package Common;

import java.util.Arrays;

/**
 * Created by JP on 10/10/2014.
 */


public class Question {
    private String type;
    private int ID;
    private String question;
    private String[] answers;
    private int solution;

    public String getType() {
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

    public String makeQuestion(){
        String temp = type + "," + question +"," + Arrays.toString(answers);
        String replaced = temp.replace("[", "");
        String replaced1 = replaced.replace("]", "");
        return replaced1;
    }

    public int getSolution() {
        return solution;
    }

    public Question(String type, int ID, String question, String[] answers, int solution) {
        this.type = type;
        this.ID = ID;
        this.question = question;
        this.answers = answers;
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Common.Question{" +
                "type=" + type +
                ", ID=" + ID +
                ", question='" + question + '\'' +
                ", answers=" + Arrays.toString(answers) +
                ", solution='" + solution + '\'' +
                '}';
    }
}
