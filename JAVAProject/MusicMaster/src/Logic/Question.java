package Logic;
/**
 * class of Questions object that contain the details about every Question.
 */
public class Question {
    //members
    Song song;
    String currentAnswer;
    String confAns1;
    String confAns2;
    String confAns3;

    /**
     * constructor
     * @param song is the name of song
     * @param currentAnswer is the correct answer
     * @param confAns1 is confuse ans 1
     * @param confAns2 is confuse ans 2
     * @param confAns3 is confuse ans 3
     */
    public Question(Song song, String currentAnswer, String confAns1, String confAns2, String confAns3) {
        this.song = song;
        this.currentAnswer = currentAnswer;
        this.confAns1 = confAns1;
        this.confAns2 = confAns2;
        this.confAns3 = confAns3;
    }
    /**
     * function that get the name of song
     * @return name of song
     */
    public Song getSongName(){ return this.song;}
    /**
     * function that get the correct answer
     * @return correct answer
     */
    public String getCurrentAnswer() {return this.currentAnswer;}

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
     * function that update the name of song
     * @param song is the name of song
     */
    public void setSongName (Song song) {this.song = song;}

    /**
     * function that update the correct answer
     * @param currentAnswer is correct answer
     */
    public void setCurrentAnswer (String currentAnswer) {this.currentAnswer = currentAnswer;}

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
