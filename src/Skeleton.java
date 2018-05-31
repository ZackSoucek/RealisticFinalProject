import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;

public class Skeleton extends Enemy {
    private static long COOL_DOWN = 2000;
    private long lastThrowTime;

    public Skeleton(Vector2 vector, int x, int y, Texture texture) {
        super(new Sprite(texture),
                vector,
                50,
                50,
                0,
                new Weapon("Bone splinters", 3, 1, 0.75),
                2,
                20f);
        this.sprite.setX(x);
        this.sprite.setY(y);
        lastThrowTime = TimeUtils.millis();
    }

    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {

        if ( TimeUtils.timeSinceMillis(lastThrowTime) > COOL_DOWN && (int) (Math.random() * 100) == 0) {
            throwBone(game);
            lastThrowTime = TimeUtils.millis();
        }
    }

    private void throwBone(BattleGame game) {
        this.health -= 10;
        float dx = game.playerCharacter.sprite.getX() - this.sprite.getX();
        float dy = game.playerCharacter.sprite.getY() - this.sprite.getY();
        Vector2 vector2 = new Vector2(dx, dy);
        vector2.setLength2(1000);


        ((TopDownScreen) game.getScreen()).addEntity(new Bone(new Texture(Gdx.files.internal("Bone.png")),
                vector2,
                this.sprite.getX() + this.sprite.getWidth() / 2,
                this.sprite.getY() + this.sprite.getHeight() / 2
        ));

    }

}
