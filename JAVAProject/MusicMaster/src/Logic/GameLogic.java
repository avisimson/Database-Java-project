package Logic;

import DataBase.Search;
import javafx.application.Platform;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class GameLogic {
    private final PropertyChangeSupport support;
    private final int timeOfTurn = 30;
    private static final int comboOfcorrectAnswersForExtraLife = 10;
    private Timer timer = new Timer();
    private int timeLeft;
    private TimerTask timerTask;
    private int score = 0;
    private int life = 3;
    private int correctAnswer = 0;
    private int combo = 0;
    private Search search = new Search();
    private Question[] questions;
    private String[] answers = new String[4];
    private Song song;
    private int turnNumber;


    /**
     * constructor.
     * set support for handling events.
     */
    public GameLogic(){
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * start the game
     */
    public void startGame(Question[] questions) {
        turnNumber = 0;
        this.questions = questions;
        playOneTurn();
    }

    /**
     * this function
     * @param id
     */
    public void answer(String id) {
        timerTask.cancel();
        String correctId = "btnAnswer" + (correctAnswer+1);

        if(correctId.equals(id)) {
            correctAnswer();
        } else {
            wrongAnswer();
        }

        //play another turn.
        if(life > 0) {
            playOneTurn();
        } else { timer.cancel(); }
    }

    //user picked correct answer.
    public void correctAnswer() {
        System.out.println("correct answer");
        combo++;
        setScore(score + timeLeft);
        sendResultAnswer(true);
        if(combo == comboOfcorrectAnswersForExtraLife) {
            //User reached combo of correct answers in a row- life increments.
            setLife(life + 1);
            combo = 0;
        }
    }

    //user picked wrong answer.
    public void wrongAnswer() {
        if(score > 0) {
            setScore(score - 1);
        }
        sendResultAnswer(false);
        setLife(life - 1);
        if(life <= 0) {
            //no more life - Game Over.
            informGameOver();
        }
        combo = 0;
    }


    private void playOneTurn(){
        System.out.println("playOneTurn");

        for (int i=0;i< answers.length;i++){
            answers[i] = null;
        }
        //pick random correct answer.
        Random rand = new Random();
        correctAnswer = rand.nextInt(4);

        //get current questions
        if(turnNumber == questions.length) {
            informGameOver();
            return;
        }
        Question currentQuestion =  questions[turnNumber];


        this.song = currentQuestion.song;

        //create array of answers
        answers[correctAnswer] = currentQuestion.currentAnswer;
        Stack<String> stack = new Stack<>();
        stack.push(currentQuestion.confAns1);
        stack.push(currentQuestion.confAns2);
        stack.push(currentQuestion.confAns3);

        for (int i=0;i< answers.length;i++){
            if (answers[i] == null)
                answers[i] = stack.pop();
        }
        setAnswers(answers);

        //get song youtube id
        String forSearch = currentQuestion.song.getTitle() + " " + currentQuestion.currentAnswer;
        String songId = search.searchSong(forSearch);
        if (songId == null) {
            turnNumber++;
            playOneTurn();
            return;
        }
        this.song.setSongYoutubeId(songId);
        setSong(this.song);
        System.out.println("correctAnswer is: " + currentQuestion.currentAnswer);
        System.out.println("correctAnswer is: " + correctAnswer);

        //set timer for turn.
        timeLeft = timeOfTurn;
        timer = new Timer(true);
        timerTask = new TimerTask() {

            @Override
            public void run () {
                //check if turn time ended.
                if (timeLeft == 0) {
                    //time of turn ended, treat like wrong answer.
                    Platform.runLater(()->{answer("0");});
                    return;
                }
                //lowers timer in one second.
                Platform.runLater(()->{setTimer(String.valueOf(timeLeft));});
                timeLeft--;
            }
        };
        timer.schedule(timerTask, 0, 1 * 1000);

        turnNumber++;
    }

    /**
     * add listener.
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * remove listener.
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * send event of change in score value to listeners.
     * @param value
     */
    public void setScore(int value) {
        this.score = value;
        support.firePropertyChange("score", null, this.score);
    }

    /**
     * send event of change in life value to listeners.
     * @param value
     */
    public void setLife(int value) {
        this.life = value;
        support.firePropertyChange("life", null, this.life);
    }

    /**
     * send event of change in song value to listeners.
     * @param value
     */
    public void setSong(Song value) {
        this.song = value;
        support.firePropertyChange("song", null, this.song);
    }

    public void setAnswers(String[] value) {
        this.answers = value;
        support.firePropertyChange("answers", null, this.answers);
    }

    /**
     * send event of change in timer value to listeners.
     * @param value
     */
    public void setTimer(String value) {
        int temp = Integer.parseInt(value) + 1;
        if(temp > 15) { temp = 15; }
        String oldValue = String.valueOf(temp);
        support.firePropertyChange("timer", oldValue, value);
    }

    /**
     * send event of inform about Game Over to listeners.
     */
    public void informGameOver() {
        String gameOver = "Game Over";
        support.firePropertyChange("game-over", "", gameOver);
    }

    /**
     * send event of inform about show answer correct or not.
     */
    public void sendResultAnswer(boolean correct) {
        String msg = "false";
        if(correct) { msg = "true"; }
        support.firePropertyChange("AnswerResult", "", msg);
    }

    public int getTimeOfTurn(){
        return this.timeOfTurn;
    }

    public int getScore() {
        return this.score;
    }
}
