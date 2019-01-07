package Logic;

enum QuestionType
{
    WHO_SINGS, SONG_NAME;
}

/**
 * class of Questions object that contain the details about every Question.
 */
public class Question {
    //members
    private Song song;
    private String correctAnswer;
    private String confAns1;
    private String confAns2;
    private String confAns3;
    private QuestionType type;

    /**
     * constructor
     * @param song is the name of song
     * @param correctAnswer is the correct answer
     * @param confAns1 is confuse ans 1
     * @param confAns2 is confuse ans 2
     * @param confAns3 is confuse ans 3
     */
    public Question(Song song, String correctAnswer, String confAns1, String confAns2, String confAns3,QuestionType type) {
        this.song = song;
        this.correctAnswer = correctAnswer;
        this.confAns1 = confAns1;
        this.confAns2 = confAns2;
        this.confAns3 = confAns3;
        this.type = type;

        System.out.println("\n");
        System.out.println("***Question***");
        System.out.println("type = " +  type);
        System.out.println("song = " +  this.song.getTitle());
        System.out.println("confAns1 = " +  confAns1);
        System.out.println("confAns2 = " +  confAns2);
        System.out.println("confAns3 = " +  confAns3);
        System.out.println("***************");

    }
    /**
     * function that get the name of song
     * @return name of song
     */
    public Song getSong(){ return this.song;}

    /**
     * function that get the correct answer
     * @return correct answer
     */
    public String getCorrectAnswer() {return this.correctAnswer;}

    /**
     * function that get the first confuse ans
     * @return the first confuse ans
     */
    public String getConfAns1() { return this.confAns1; }

    /**
     * function that get the second confuse ans
     * @return the second confuse ans
     */
    public String getConfAns2() { return this.confAns2; }

    /**
     * function that get the third confuse ans
     * @return the third confuse ans
     */
    public String getConfAns3() { return this.confAns3; }

    /**
     * function that get the question type.
     * @return type
     */
    public QuestionType getType(){ return this.type;}

    /**
     * function that update the song
     * @param song - song object
     */
    public void setSong (Song song) {this.song = song;}

    /**
     * function that update the correct answer
     * @param correctAnswer is correct answer
     */
    public void setCorrerctAnswer (String correctAnswer) {this.correctAnswer = correctAnswer;}

    /**
     * function that update the first confuse ans
     * @param confAns1 is first confuse ans
     */
    public void setConfAns1 (String confAns1) {this.confAns1 = confAns1;}

    /**
     * function that update the second confuse ans
     * @param confAns2 is second confuse ans
     */
    public void setConfAns2 (String confAns2) {this.confAns2 = confAns2;}

    /**
     * function that update the third confuse ans
     * @param confAns3 is third confuse ans
     */
    public void setConfAns3 (String confAns3) {this.confAns3 = confAns3;}
}
