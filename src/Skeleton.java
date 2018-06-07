import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class Skeleton extends Enemy {
    private static long COOL_DOWN = 5000;
    private long lastThrowTime;

    public Skeleton(Vector2 vector, int x, int y, Texture texture) {
        super(new Sprite(texture),
                vector,
                50,
                50,
                0,
                new Weapon("Bone splinters", 20, 1, 0.75),
                2,
                5f);
        this.sprite.setX(x);
        this.sprite.setY(y);
        lastThrowTime = TimeUtils.millis();
    }

    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        this.vector.set(game.playerCharacter.sprite.getX() - this.sprite.getX(),
                game.playerCharacter.sprite.getY() - this.sprite.getY());
        this.vector.setLength(this.getMoveSpeed());
        this.sprite.setRotation(90 - 180f / (float) Math.PI * (float) (Math.atan2(this.vector.x, this.vector.y)));
        this.sprite.translate(delta * this.vector.x,
                delta * this.vector.y);
        //Movement^^^
        //attack\/
        if (TimeUtils.timeSinceMillis(lastThrowTime) > COOL_DOWN) {
            if ((int) (Math.random() * 20) == 0) {
                throwBone(game);
                lastThrowTime = TimeUtils.millis();
            }
        }
    }

    private void throwBone(BattleGame game) {
        this.health -= 10;
        float dx = game.playerCharacter.sprite.getX() - this.sprite.getX();
        float dy = game.playerCharacter.sprite.getY() - this.sprite.getY();
        Vector2 vector2 = new Vector2(dx, dy);
        vector2.setLength2(10000);


        ((TopDownScreen) game.getScreen()).addEntity(new Bone(new Texture(Gdx.files.internal("Bone.png")),
                vector2,
                this.sprite.getX() + this.sprite.getWidth() / 2,
                this.sprite.getY() + this.sprite.getHeight() / 2
        ));

    }

}
