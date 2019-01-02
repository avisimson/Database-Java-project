package Logic;

public class Question {
    Song song;
    String currentAnswer;
    String confAns1;
    String confAns2;
    String confAns3;
    public Question(Song song, String currentAnswer, String confAns1, String confAns2, String confAns3) {
        this.song = song;
        this.currentAnswer = currentAnswer;
        this.confAns1 = confAns1;
        this.confAns2 = confAns2;
        this.confAns3 = confAns3;
    }

    public Song getSongName(){ return this.song;}

    public String getCurrentAnswer() {return this.currentAnswer;}

    public String getConfAns1() { return this.confAns1; }

    public String getConfAns2() { return this.confAns2; }

    public String getConfAns3() { return this.confAns3; }

    public void setSongName (Song song) {this.song = song;}

    public void setCurrentAnswer (String currentAnswer) {this.currentAnswer = currentAnswer;}

    public void setConfAns1 (String confAns1) {this.confAns1 = confAns1;}

    public void setConfAns2 (String confAns2) {this.confAns2 = confAns2;}

    public void setConfAns3 (String confAns3) {this.confAns3 = confAns3;}
}
