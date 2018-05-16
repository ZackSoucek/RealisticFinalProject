import com.badlogic.gdx.Game;

public class BattleGame extends Game {

    private int highScore;

    public BattleGame(int highScore) {
        super();
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }
    public void  score(int newScore){
        if(newScore> highScore){
            highScore = newScore;
        }
    }

    public void create() {
        setScreen(new MenuScreen(this));
    }
}
