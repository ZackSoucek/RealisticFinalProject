import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BattleGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public PlayerCharacter playerCharacter;
    private int score;
    private int highScore;
    private int level;//keeps track of what level we are on
    // if level 0, show main menu
    // used to calculate enemies and whether boss(level%10==0)
    private Preferences prefs;


    public int getLevel() {
        return level;
    }

    public BattleGame() {

    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        playerCharacter = new PlayerCharacter(new Texture(Gdx.files.internal("SWING/Swing Top0000.png")));
        score = 0;
        level = 0;

        prefs = Gdx.app.getPreferences("High Score Folder");
        if (!prefs.contains("highscore")) {
            prefs.putInteger("highscore", 0);
        }
        prefs.flush();
        this.highScore = prefs.getInteger("highscore");
        this.setScreen(new MenuScreen(this));


    }

    @Override
    public void render() {
        super.render();//calls game's render, which i think call s the screen refreshes
    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public void gameOver() {
        calculateScore();
        this.setScreen(new GameOverScreen(this));
    }

    public int getScore() {
        calculateScore();
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setGameLevel(int level) {
        this.level = level;
    }

    public void calculateScore() {
        score = playerCharacter.getXp();
        for (int l = playerCharacter.getLevel(); l > 0; l--) {
            score += playerCharacter.EXP_PER_LEVEL * l;
        }
        if (score > highScore) {
            highScore = score;
            prefs.putInteger("highscore", highScore);
            prefs.flush();
        }
    }


    public void setScore(int newScore) {
        score = newScore;
    }

    public void winLevel() {
        playerCharacter.addXP(level);
        level++;
    }

}
