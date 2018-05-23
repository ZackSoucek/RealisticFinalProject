
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;

public class GameOverScreen implements Screen {
    private BattleGame game;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera camera;
    private FitViewport viewport;
    private int score;

    public GameOverScreen(BattleGame game) {
        this.game = game;
        this.score = game.getScore();

    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Values.WORLD_WIDTH, Values.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        Vector2 center = new Vector2(Values.WORLD_WIDTH / 2,
                Values.WORLD_HEIGHT / 2);
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        //translate into world units
        Vector2 pos = viewport.unproject(new Vector2(x, y));
        boolean over = pos.dst(center) <= Values.MENU_CIRCLE_RADIUS;

        if (over)
            renderer.setColor(Color.ORANGE);
        renderer.circle(center.x, center.y, Values.MENU_CIRCLE_RADIUS);
        renderer.end();


        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        String text = "Game over\nYou scored: " + score + "\nCLick to try Again";
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, text,
                Values.WORLD_WIDTH / 2 - layout.width / 2,
                Values.WORLD_HEIGHT / 2 + layout.height / 2);
        batch.end();
        if (over) {
            renderer.setColor(Color.ORANGE);
            if (Gdx.input.justTouched()) {
                newGame(game.getHighScore());
            }
        }

    }

    private void newGame(int highScore) {
        game.playerCharacter = new PlayerCharacter(new Texture("FlabioFinal.png"));
        game.setGameLevel(0);
        game.setScore(0);
        game.setScreen(new MenuScreen(game));

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }


}
