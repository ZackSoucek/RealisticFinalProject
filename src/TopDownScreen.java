import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class TopDownScreen implements Screen {
    private BattleGame game;
    private ShapeRenderer renderer;
    private int score;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private ArrayList<Entity> entities = new ArrayList<>();


    public TopDownScreen(BattleGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Values.WORLD_WIDTH, Values.WORLD_HEIGHT, camera);
        score = 0;
        game.batch.begin();
        entities.addAll(EnemyGenerator.generate(game.getLevel(),
                Values.WORLD_WIDTH / 10,
                Values.WORLD_WIDTH - Values.WORLD_WIDTH / 10,
                Values.WORLD_HEIGHT / 10,
                Values.WORLD_HEIGHT - Values.WORLD_HEIGHT / 10));


        draw();


        game.batch.end();
    }

    private void draw() {
        game.playerCharacter.sprite.draw(game.batch);
        for (Entity e : entities) {//draw all entities
            e.sprite.draw(game.batch);
        }
    }

    private void gameOver() {
        game.setScreen(new GameOverScreen(game));
    }

    public void doCollisionsWithPlayer() {
        for (Entity e : entities) {//for each entity in the level
            if (game.playerCharacter.isTouching(e)) {//if they are colliding with the player
                e.collidePlayer(game.playerCharacter);//do what they do when the collide with the player\
                //the entities need the player character so they know what player character to hit
            }
        }
        if (game.playerCharacter.isDead()) {
            gameOver();
        }
    }

    public void doThink(float delta) {
        if (entities.size() == 0) {
            winLevel();
        }
        game.playerCharacter.think(game, delta, entities);
        for (int i = 0; i < entities.size(); i++) {
            //for each entity in the level
            entities.get(i).think(game, delta, entities);//do their AI
            if (entities.get(i) instanceof AliveThing && ((AliveThing) entities.get(i)).health <= 0) {
                entities.remove(entities.get(i));
            }
            //TODO ememies push off of each other
        }
    }

    private void winLevel() {
        //todo stuf from beating the level
        //exp, level goes up, heal, put up a win message, press any to contuniue(AFTER DELAY)
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
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        doCollisionsWithPlayer();
        doThink(delta);

        camera.update();

        game.batch.begin();
        draw();

        game.batch.end();
        drawHealthBar();
        drawEXP();

    }

    private void drawHealthBar() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(0, Values.WORLD_HEIGHT - 40, game.playerCharacter.healthTotal + 4, 40);
        renderer.setColor(Color.RED);
        renderer.rect(2, Values.WORLD_HEIGHT - 38, game.playerCharacter.health, 36);
        renderer.end();
    }
    private void drawEXP(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(0, Values.WORLD_HEIGHT - 80, game.playerCharacter.getLevel()*game.playerCharacter.getEXP_PER_LEVEL() + 4, 40);
        renderer.setColor(Color.YELLOW);
        renderer.rect(2, Values.WORLD_HEIGHT - 78, game.playerCharacter.getXp(), 36);
        renderer.end();
    }


    @Override
    public void resize(int i, int i1) {
        viewport.update(i, i1);
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
