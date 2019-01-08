package Logic;

import DataBase.*;
import javafx.application.Platform;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * A class that manage game logic.
 */
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
    private String question;
    private Song song;
    private int turnNumber;
    private DBArtists DB_artists = new DBArtists();
    private DBAlbums DB_albums = new DBAlbums();
    private DBGenre DB_genre = new DBGenre();
    private DBSongs DB_songs = new DBSongs();
    private List<Genre> genres;


    /**
     * constructor.
     * @param genres - the genres that chosen by the user.
     */
    public GameLogic(List<Genre> genres){
         //set support for handling events.
        this.support = new PropertyChangeSupport(this);
        this.genres =genres;
    }

    /**
     * start the game
     */
    public void startGame() {
        turnNumber = 0;
        playOneTurn();
    }

    /**
     * The function is activated when the user selects an answer.
     * @param id - id of the button that selected.
     */
    public void answer(String id) {
        timerTask.cancel();
        String correctId = "btnAnswer" + (correctAnswer+1);

        //check answer
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

    /**
     * this function is activated when the user
     * selects the correct answer.
     */
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

    /**
     * this function is activated when the user
     * selects a wrong answer.
     */
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


    /**
     * play one turn of the game.
     */
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

        //get currentQuestion
        Question currentQuestion =  questions[turnNumber];

        //set string question
        if(currentQuestion.getType() == QuestionType.SONG_NAME){
            this.question = "What's the song's name?";
        }
        else if(currentQuestion.getType() == QuestionType.WHO_SINGS){
            this.question = "Who is singing the song?";
        }
        setQuestion(this.question);

        //set song
        this.song = currentQuestion.getSong();

        //create array of answers
        answers[correctAnswer] = currentQuestion.getCorrectAnswer();
        Stack<String> stack = new Stack<>();
        stack.push(currentQuestion.getConfAns1());
        stack.push(currentQuestion.getConfAns2());
        stack.push(currentQuestion.getConfAns3());

        //set answers
        for (int i=0;i< answers.length;i++){
            if (answers[i] == null)
                answers[i] = stack.pop();
        }
        setAnswers(answers);

        //get song youtube id
        String forSearch = currentQuestion.getSong().getTitle() + " " + currentQuestion.getCorrectAnswer();
        String songId = search.searchSong(forSearch);
        if (songId == null) {
            turnNumber++;
            playOneTurn();
            return;
        }
        this.song.setSongYoutubeId(songId);
        setSong(this.song);
        System.out.println("correctAnswer is: " + currentQuestion.getCorrectAnswer());
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
                    Platform.runLater(()->{answer("-1");});
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
     * @param listener - listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * remove listener.
     * @param listener - listener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * invoke an event on a change in score variable.
     * @param value - the new value.
     */
    public void setScore(int value) {
        this.score = value;
        support.firePropertyChange("score", null, this.score);
    }

    /**
     * invoke an event on a change in life variable.
     * @param value - the new value.
     */
    public void setLife(int value) {
        this.life = value;
        support.firePropertyChange("life", null, this.life);
    }

    /**
     * invoke an event on a change in question variable.
     * @param value - the new value.
     */
    public void setQuestion(String value) {
        this.question = value;
        support.firePropertyChange("question", null, this.question);
    }

    /**
     * invoke an event on a change in song variable.
     * @param value - the new value.
     */
    public void setSong(Song value) {
        this.song = value;
        support.firePropertyChange("song", null, this.song);
    }

    /**
     * invoke an event on a change in answers variable.
     * @param value - the new value.
     */
    public void setAnswers(String[] value) {
        this.answers = value;
        support.firePropertyChange("answers", null, this.answers);
    }

    /**
     * invoke an event on a change in timer variable.
     * @param value - the new value.
     */
    public void setTimer(String value) {
        int temp = Integer.parseInt(value) + 1;
        if(temp > 15) { temp = 15; }
        String oldValue = String.valueOf(temp);
        support.firePropertyChange("timer", oldValue, value);
    }

    /**
     * invoke an event of game over.
     */
    public void informGameOver() {
        String gameOver = "Game Over";
        support.firePropertyChange("game-over", "", gameOver);
    }

    /**
     * invoke an event to inform about show answer correct or not.
     * @param correct
     */
    public void sendResultAnswer(boolean correct) {
        String msg = "false";
        if(correct) { msg = "true"; }
        support.firePropertyChange("AnswerResult", "", msg);
    }

    /**
     * @return timeOfTurn.
     */
    public int getTimeOfTurn(){
        return this.timeOfTurn;
    }

    /**
     * @return score.
     */
    public int getScore() {
        return this.score;
    }


    /**
     * Create 20 questions.
     * @param artistFilter - list of artist.
     */
    public void Create20Questions(List<Artist> artistFilter) {
        Question questionsForGame[] = new Question[20];
        boolean byAlbum = false;

        for (int i = 0;i < 20;i = i+2) {

            System.out.println("--------------createQustion number " + i +"---------------");
            questionsForGame[i] =  createQuestion(QuestionType.WHO_SINGS,artistFilter,false);
            System.out.println("--------------createQustion number " + (i+1) +"---------------");
            questionsForGame[i+1] = createQuestion(QuestionType.SONG_NAME,artistFilter,byAlbum);

            if(byAlbum == false)
                byAlbum = true;
            else
                byAlbum = false;

        }
        this.questions =  questionsForGame;
    }


    /**
     * Create a question.
     * @param type - question type.
     * @param artistFilter - list of artist.
     * @param byAlbum - if true taking songs from the same album.
     * @return Question object.
     */
    private Question createQuestion(QuestionType type,List<Artist> artistFilter,boolean byAlbum){
        Artist CAns;
        Song questionSong;
        Random randA = new Random();
        Random randS = new Random();
        List<String> wrongAnswer = new ArrayList<>();
        Question question = null;
        List<Song> songs;

        if(byAlbum == false){
            //choose random artist from the list.
            CAns = artistFilter.get(randA.nextInt(artistFilter.size()));

            //create songs list
            songs = DB_songs.FilterSong(CAns);

            //choose random song from the list.
            questionSong = songs.get(randS.nextInt(songs.size()));

            if(type == QuestionType.WHO_SINGS){
                System.out.println("createQustion WHO_SINGS");
                wrongAnswer = getThreeArtistConfusionAns(CAns);
                //create question
                question = new Question(questionSong, CAns.getArtistName(),
                        wrongAnswer.get(0),wrongAnswer.get(1),wrongAnswer.get(2),QuestionType.WHO_SINGS);
            }
            else if(type == QuestionType.SONG_NAME){
                System.out.println("createQustion SONG_NAME");
                wrongAnswer = getThreeSongConfusionAns(questionSong);
                //create question
                question = new Question(questionSong, questionSong.getTitle(),
                        wrongAnswer.get(0),wrongAnswer.get(1),wrongAnswer.get(2),QuestionType.SONG_NAME);
            }
        }

        //taking songs from the same album.
        else if(type == QuestionType.SONG_NAME && byAlbum == true){
            System.out.println("createQustion SONG_NAME (albums)");

            //create list of albums
            List<Album> albums = DB_albums.getListOfAlbumsWithMoreThen3Songs();

            //choose random album from the list.
            Album album = albums.get(randS.nextInt(albums.size()));

            System.out.println("album chosen " + album.getAlbumId() + " " + album.getAlbumName());

            //get list of songs
            songs = DB_songs.getListSognsByAlbum(album);

            System.out.println("songs: ");
            for(int i = 0 ; i <songs.size() ; i ++){
                System.out.println(songs.get(i).getTitle());
            }

            //choose random song from the list
            questionSong = songs.get(randS.nextInt(songs.size()));
            songs.remove(questionSong);

            //get 3 wrong answer
            while(wrongAnswer.size() < 3){
               Song song = songs.get(randS.nextInt(songs.size()));
               wrongAnswer.add(song.getTitle());
               songs.remove(song);
            }

            //create question
            question = new Question(questionSong, questionSong.getTitle(),
                    wrongAnswer.get(0),wrongAnswer.get(1),wrongAnswer.get(2),QuestionType.SONG_NAME);
        }

       return question;
    }


    /**
     * this function return three wrong song answers.
     * @param song - the song which is the correct answer.
     * @return - list of three wrong answers.
     */
    private List<String> getThreeSongConfusionAns (Song song) {
        List<Song> confusionSongs =  DB_songs.getListOfSimilarSongs(song, this.genres);
        List<String> confusionAnsSongs = new ArrayList<>();

        if (confusionSongs.size() < 3) {
            confusionSongs = DB_songs.getDifferentSongsByGenre(song, confusionSongs, genres);
        }

        for(int i = 0 ; i<confusionSongs.size(); i++){
            confusionAnsSongs.add(confusionSongs.get(i).getTitle());
        }

        return createConfusionAns(confusionAnsSongs);
    }

    /**
     * this function return three wrong artist answers.
     * @param artist - the artist who is correct answer.
     * @return - list of three wrong answers.
     */
    private List<String> getThreeArtistConfusionAns (Artist artist) {
        List<Artist> confusionArtists = DB_artists.CreateConfusionAns(artist);
        List<String> confusionAnsArtists  = new ArrayList<>();

        if (confusionArtists.size() < 3){
            confusionArtists = DB_artists.FilterArtistDifferent(artist,confusionArtists,genres);
        }

        for(int i = 0 ; i<confusionArtists.size(); i++){
            confusionAnsArtists.add(confusionArtists.get(i).getArtistName());

        }
        return createConfusionAns(confusionAnsArtists);
    }

    /**
     *this function return three wrong  answers.
     * @param list - list of wrong answers.
     * @return - list of three wrong answers.
     */
    private List<String> createConfusionAns (List<String> list){
        List<String> ans = new LinkedList<>();

        Random rand = new Random();
        System.out.println("confusionArtists.size:" + list.size());

       while(ans.size() != 3){
            int x = rand.nextInt(list.size());
            String s = list.get(x);
            if (!ans.contains(s)) {
                ans.add(s);
            }
            list.remove(x);
        }

        return ans;
    }

}
