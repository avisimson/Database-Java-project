package Logic;

import DataBase.*;
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
     * set support for handling events.
     */
    public GameLogic(List<Genre> genres){
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
        if(currentQuestion.type == QuestionType.SONG_NAME){
            this.question = "What's the song's name?";
        }
        else if(currentQuestion.type == QuestionType.WHO_SINGS){
            this.question = "Who is singing the song?";
        }
        setQuestion(this.question);

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
     * send event of change in life value to listeners.
     * @param value
     */
    public void setQuestion(String value) {
        this.question = value;
        support.firePropertyChange("question", null, this.question);
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


    public void Create20Questions(List<Artist> artistFilter) {
        Question questionsForGame[] = new Question[20];
        Question question = null;
        boolean byAlbum = false;

        for (int i = 0;i < 20;i = i+2) {
            System.out.println("--------------createQustion number " + i +"---------------");
            questionsForGame[i] =  createQustion(QuestionType.WHO_SINGS,artistFilter,false);
            System.out.println("--------------createQustion number " + (i+1) +"---------------");
            questionsForGame[i+1] = createQustion(QuestionType.SONG_NAME,artistFilter,byAlbum);
            if(byAlbum == false)
                byAlbum = true;
            else
                byAlbum = false;

        }

        this.questions =  questionsForGame;
    }


    private Question createQustion(QuestionType type,List<Artist> artistFilter,boolean byAlbum){
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
                question = new Question(questionSong, CAns.getArtistName(),
                        wrongAnswer.get(0),wrongAnswer.get(1),wrongAnswer.get(2),QuestionType.WHO_SINGS);
            }
            else if(type == QuestionType.SONG_NAME){
                System.out.println("createQustion SONG_NAME");
                wrongAnswer = getThreeSongConfusionAns(questionSong);
                question = new Question(questionSong, questionSong.getTitle(),
                        wrongAnswer.get(0),wrongAnswer.get(1),wrongAnswer.get(2),QuestionType.SONG_NAME);
            }
        }

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

           while(wrongAnswer.size() < 3){

               Song song = songs.get(randS.nextInt(songs.size()));
               wrongAnswer.add(song.getTitle());
               songs.remove(song);
           }

            question = new Question(questionSong, questionSong.getTitle(),
                    wrongAnswer.get(0),wrongAnswer.get(1),wrongAnswer.get(2),QuestionType.SONG_NAME);
        }

       return question;
    }


    private List<String> getThreeSongConfusionAns (Song song) {
        List<Song> confusionSongs =  DB_songs.getListOfSimilarSongs(song, this.genres);
        List<String> confusionAnsSongs = new ArrayList<>();

        if (confusionSongs.size() < 3) {
            confusionSongs = DB_songs.getDiffrentSongsByGenre(song, confusionSongs, genres);
        }

        for(int i = 0 ; i<confusionSongs.size(); i++){
            confusionAnsSongs.add(confusionSongs.get(i).getTitle());
        }

        return createConfusionAns(confusionAnsSongs);
    }

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
