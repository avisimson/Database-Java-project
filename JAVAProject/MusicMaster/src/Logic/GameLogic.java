package Logic;

import DataBase.Search;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class GameLogic {
    private final PropertyChangeSupport support;
    private static final int timeOfTurn = 15;
    private static final int comboOfcorrectAnswersForExtraLife = 10;
    private Timer timer = new Timer();
    private int score = 0;
    private int life = 3;
    private int correctAnswer = 0;
    private int combo = 0;
    private Search search = new Search();
    private Question[] questions;
    private String[] answers = new String[4];
    private Song song;

    public GameLogic(){
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * start the game
     */
    public void startGame(Question[] questions) {
        this.questions = questions;
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

        for (int i=0;i< answers.length;i++){
            answers[i] = null;
        }
        //pick random correct answer.
        Random rand = new Random();
        correctAnswer = rand.nextInt(4);

        System.out.println("correctAnswer is: " + correctAnswer);

        //get current questions
        Question currentQuestion =  questions[0];
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
        this.song.setSongYoutubeId(songId);
        setSong(this.song);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void setScore(int value) {
        this.score = value;
        support.firePropertyChange("score", null, this.score);
    }

    public void setLife(int value) {
        this.life = value;
        support.firePropertyChange("life", null, this.life);
    }

    public void setSong(Song value) {
        this.song = value;
        support.firePropertyChange("song", null, this.song);
    }

    public void setAnswers(String[] value) {
        this.answers = value;
        support.firePropertyChange("answers", null, this.answers);
    }
}
