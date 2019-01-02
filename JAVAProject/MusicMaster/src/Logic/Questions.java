package Logic;

public class Questions {
    String songName;
    String currentAnswer;
    String confAns1;
    String confAns2;
    String confAns3;
    public Questions(String songName,String currentAnswer,String confAns1,String confAns2, String confAns3) {
        this.songName = songName;
        this.currentAnswer = currentAnswer;
        this.confAns1 = confAns1;
        this.confAns2 = confAns2;
        this.confAns3 = confAns3;
    }

    public String getSongName(){ return this.songName;}

    public String getCurrentAnswer() {return this.currentAnswer;}

    public String getConfAns1() { return this.confAns1; }

    public String getConfAns2() { return this.confAns2; }

    public String getConfAns3() { return this.confAns3; }

    public void setSongName (String songName) {this.songName = songName;}

    public void setCurrentAnswer (String currentAnswer) {this.currentAnswer = currentAnswer;}

    public void setConfAns1 (String confAns1) {this.confAns1 = confAns1;}

    public void setConfAns2 (String confAns2) {this.confAns2 = confAns2;}

    public void setConfAns3 (String confAns3) {this.confAns3 = confAns3;}
}
