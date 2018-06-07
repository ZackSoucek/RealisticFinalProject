import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import sun.awt.SunHints;

import java.awt.*;
import java.util.ArrayList;

public class TopDownScreen implements Screen {
    private BattleGame game;
    private ShapeRenderer renderer;
    private int score;
    private boolean levelWon;
    private boolean EXPGiven;
    private Texture floorTexture;
    private Texture hitsplash;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private ArrayList<Entity> entities = new ArrayList<>();


    public TopDownScreen(BattleGame game) {
        this.game = game;
        levelWon = false;
        EXPGiven = false;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Values.WORLD_WIDTH, Values.WORLD_HEIGHT, camera);
        score = 0;
        game.batch.begin();
        floorTexture = new Texture(Gdx.files.internal("dirt.png"));
        hitsplash = new Texture(Gdx.files.internal("bloodspash.png"));

        entities.addAll(EnemyGenerator.generate(game.getLevel(),
                Values.WORLD_WIDTH / 3,
                Values.WORLD_WIDTH - Values.WORLD_WIDTH / 10,
                Values.WORLD_HEIGHT / 3,
                Values.WORLD_HEIGHT - Values.WORLD_HEIGHT / 10));


        draw();


        game.batch.end();

    }

    private void draw() {
        game.playerCharacter.sprite.draw(game.batch);
        game.batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        Rectangle rect = game.playerCharacter.getHitbox();
        renderer.rect(rect.x,rect.y,rect.width,rect.height);
        renderer.end();
        game.batch.begin();


        for (Entity e : entities) {//draw all entities
            e.sprite.draw(game.batch);
        }
    }

    private void gameOver() {
        //game.setScreen(new GameOverScreen(game));
    }

    public void doCollisionsWithPlayer() {
        for (Entity e : entities) {//for each entity in the level
            if (game.playerCharacter.isTouching(e)) {//if they are colliding with the player
                e.collidePlayer(game.playerCharacter);//do what they do when the collide with the player\
                //the entities need the player character so they know what player character to hit
                //FLash Screen Red sligtly when you get hit.
                game.batch.begin();
                game.batch.draw(hitsplash,0,0);
                game.batch.end();
            }
        }
        if (game.playerCharacter.isDead()) {
            gameOver();
        }
    }

    public void doThink(float delta) {
        if (entities.size() == 0) {
            if (!levelWon) {
                levelWon = true;
                EXPGiven = false;
            }
        }
        game.playerCharacter.think(game, delta, entities);
        game.playerCharacter.checkPos();
        for (int i = 0; i < entities.size(); i++) {
            //for each entity in the level
            entities.get(i).think(game, delta, entities);//do their AI
            entities.get(i).checkPos();
            if (entities.get(i) instanceof AliveThing && ((AliveThing) entities.get(i)).health <= 0) {
                entities.remove(entities.get(i));
            }
        }
    }


    private void winLevel() {
        game.winLevel();
        EXPGiven = true;

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
        //draw the floor
        game.batch.begin();
        game.batch.draw(floorTexture,0,0);
        game.batch.end();
        doCollisionsWithPlayer();
        doThink(delta);

        camera.update();

        game.batch.begin();
        draw();

        game.batch.end();
        drawHealthBar();
        drawEXP();
        drawGameLevel();

        if (levelWon) {
            game.batch.begin();
            String titleText = "Level cleared! Score: " + game.getScore() + "\nPress Space to go to the next level";
            GlyphLayout layout = new GlyphLayout(game.font, titleText);
            game.font.draw(game.batch, titleText,
                    Values.WORLD_WIDTH / 2 - layout.width / 2,
                    Values.WORLD_HEIGHT / 2 + layout.height / 2);
            game.batch.end();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {//start next level
                winLevel();
                entities.addAll(EnemyGenerator.generate(game.getLevel(),
                        Values.WORLD_WIDTH / 10,
                        Values.WORLD_WIDTH - Values.WORLD_WIDTH / 10,
                        Values.WORLD_HEIGHT / 10,
                        Values.WORLD_HEIGHT - Values.WORLD_HEIGHT / 10));
                levelWon = false;
                game.playerCharacter.sprite.setPosition(0, 0);
            }

        }

    }

    private void drawGameLevel() {
        game.batch.begin();
        String titleText = "Level: " + game.getLevel() + "\nScore: " + game.getScore() + "\nHigh score: " + game.getHighScore();
        GlyphLayout layout = new GlyphLayout(game.font, titleText);
        game.font.draw(game.batch, titleText,
                10,
                Values.WORLD_HEIGHT - 85);
        game.batch.end();
    }

    private void drawHealthBar() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(0, Values.WORLD_HEIGHT - 40, game.playerCharacter.healthTotal + 4, 40);
        renderer.setColor(Color.RED);
        renderer.rect(2, Values.WORLD_HEIGHT - 38, game.playerCharacter.health, 36);
        renderer.end();
    }

    private void drawEXP() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.rect(0, Values.WORLD_HEIGHT - 80, game.playerCharacter.getLevel() * game.playerCharacter.getEXP_PER_LEVEL() + 4, 40);
        renderer.setColor(Color.YELLOW);
        renderer.rect(2, Values.WORLD_HEIGHT - 78, game.playerCharacter.getXp(), 36);
        renderer.end();
    }

    public boolean addEntity(Entity e) {
        return entities.add(e);
    }

    public boolean removeEntity(Entity e) {
        return this.entities.remove(e);
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
