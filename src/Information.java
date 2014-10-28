import java.sql.*;


public class Information{

    public static String Categories[] = {"Arts","History","People","Science","Sports"};

    public static Connection connect(){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
        return c;
    }

    public static Question getQuestion(String category){
        Connection db =connect();
        Statement stmt = null;
        Question temp= null;
        try {
            stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + category + ";" );
            while ( rs.next() ) {

                String[] answers = {rs.getString("0"),rs.getString("1"),rs.getString("2"),rs.getString("3")};
                temp = new Question(category,
                        rs.getInt("ID"),
                        rs.getString("Question"),
                        answers,
                        rs.getInt("Answer")
                );
                //System.out.println(question.toString());
            }
            rs.close();
            stmt.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    public String toString() {
        return  answer +  "," + field + "," + question;
    }

    private String answer;
    private String field;
    private String question;

    public Information(String answer, String field, String question) {
        this.answer = answer;
        this.field = field;
        this.question = question;
    }

    ;
}
