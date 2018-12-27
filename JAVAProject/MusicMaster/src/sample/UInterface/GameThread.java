package sample.UInterface;

public class GameThread extends Thread {
    public void run(){
        GameController game = new GameController();
        game.startGame();
    }
}
