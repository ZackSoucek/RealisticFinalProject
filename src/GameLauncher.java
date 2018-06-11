import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class GameLauncher {
    public static void main(String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Values.SCREEN_WIDTH;
        config.height = Values.SCREEN_HEIGHT;
        config.resizable = false;

        LwjglApplication launcher = new LwjglApplication(new BattleGame(), config);
    }
}
