import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;

public class TopDownScreen implements Screen {
    private BattleGame game;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private int score;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private ArrayList<Entity> entities = new ArrayList<>();



    public TopDownScreen(BattleGame game){
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Values.WORLD_WIDTH,Values.WORLD_HEIGHT,camera);
        batch = new SpriteBatch();
        score = 0;

    }

    private void gameOver(){
        game.setScreen(new GameOverScreen(game));
    }
    public void doCollisionsWithPlayer() {
        for (Entity e : entities) {//for each entity in the level
            if (game.playerCharacter.isTouching(e)) {//if they are colliding with the player
                e.collidePlayer(game.playerCharacter);//do what they do when the collide with the player\
                //the entities need the player character so they know what player character to hit
            }
        }
    }

    @Override
    public void render(float delta) {
        /*
        Steps to render:
        1. wipe previous screen
        2. check for collisions
        3. computer interactions
        4. player input
        5. draw effect of all of this
         */
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        doCollisionsWithPlayer();

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
