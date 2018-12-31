package Logic;

import DataBase.Search;
import javafx.animation.Timeline;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;
import java.util.Timer;

public class GameLogic {
    private final PropertyChangeSupport support;
    private static final int timeOfTurn = 15;
    private static final int comboOfcorrectAnswersForExtraLife = 10;
    private Timer timer = new Timer();
    private int score = 0;
    private int life = 3;
    private int correctAnswer = 0;
    private int combo = 0;
    private String songId;
    private Search search = new Search();

    public GameLogic(){
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * start the game
     */
    public void startGame() {
        playOneTurn();
    }

    /**
     * this function
     * @param id
     */
    public void answer(String id) {

        String correctId = "btnAnswer" + correctAnswer;

        if(correctId.equals(id)) {
            System.out.println("correct answer");
            combo++;
            if(combo == comboOfcorrectAnswersForExtraLife) {
                setLife(life+1);
                combo = 0;
            }
        } else {
            setLife(life-1);
            combo = 0;
        }
    }


    private void playOneTurn(){
        System.out.println("playOneTurn");

        //pick random correct answer.
        Random rand = new Random();
        correctAnswer = rand.nextInt(4)+1;
        System.out.println("correctAnswer is: " + correctAnswer);
        String songId =search.searchSong("omer adam");
        System.out.println("songId is in game logic: " + songId);
        setSongId(songId);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void setScore(int value) {
        int oldValue = this.score;
        this.score = value;
        support.firePropertyChange("score", oldValue, this.score);
    }

    public void setLife(int value) {
        int oldValue = this.life;
        this.life = value;
        support.firePropertyChange("life", oldValue, this.life);
    }

    public void setSongId(String value) {
        String oldValue = this.songId;
        this.songId = value;
        support.firePropertyChange("songId", oldValue, this.songId);
    }
}
