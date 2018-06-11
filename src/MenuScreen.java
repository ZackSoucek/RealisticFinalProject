
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;

public class MenuScreen implements Screen {
    private BattleGame game;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera camera;
    private FitViewport viewport;
    float red;
    float blue;
    float green;
    int rsign;
    int bsign;
    int gsign;
    Texture chartext;
    Texture enemytext;


    public MenuScreen(BattleGame game) {
        this.red = 0;
        this.blue = 0;
        this.green = 0;
        this.game = game;
        rsign = 1;
        bsign = 1;
        gsign = 1;
        chartext = new Texture(Gdx.files.internal("FlabioFinal.png"));
        enemytext = new Texture(Gdx.files.internal("zombie.png"));


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
        renderer.setColor(Color.BLACK);
        Vector2 center = new Vector2(Values.WORLD_WIDTH / 2,
                Values.WORLD_HEIGHT / 2);
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        //translate into world units
        Vector2 pos = viewport.unproject(new Vector2(x, y));
        boolean over = pos.dst(center) <= Values.MENU_CIRCLE_RADIUS;
        if (over) {//set color to changing
            switch ((int) (Math.random() * 3)) {
                case 0:
                    red += rsign * 0.01;
                    break;
                case 1:
                    blue += bsign * 0.01;
                    break;
                default:
                    green += gsign * 0.01;
                    break;
            }
            //too high check
            if (red > 0.9)
                rsign = -1;
            if (green > 0.9)
                gsign = -1;
            if (blue > 0.9)
                bsign = -1;
            //too low check
            if (red < 0.1)
                rsign = 1;
            if (green < 0.1)
                gsign = 1;
            if (blue < 0.1)
                bsign = 1;
            renderer.setColor(red, green, blue, (float) Math.random());
        }
        renderer.circle(center.x, center.y, Values.MENU_CIRCLE_RADIUS);
        renderer.end();


        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        String titleText = "SUPER ULTRA FIGHTER X" + "\nHighscore = "+ game.getHighScore();
        GlyphLayout layout = new GlyphLayout(font, titleText);
        font.draw(batch, titleText,
                Values.WORLD_WIDTH / 2 - layout.width / 2,
                Values.WORLD_HEIGHT / 2 + layout.height / 2);
        String instructionText = "Press arrow keys or WASD to move.\nPress space to attack";
        GlyphLayout instructLayout = new GlyphLayout(font, instructionText);
        font.draw(batch, instructionText,
                Values.WORLD_WIDTH / 2 - instructLayout.width / 2,
                Values.WORLD_HEIGHT / 5 + instructLayout.height / 2);
        batch.end();
        if (over) {
            if (Gdx.input.justTouched()) {
                game.setGameLevel(1);
                game.setScreen(new TopDownScreen(game));
            }
        }

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

    }

}
