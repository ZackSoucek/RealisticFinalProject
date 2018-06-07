import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class SmallSlime extends Enemy {
    public SmallSlime(Vector2 vector, int x, int y, Texture texture) {
        super(new Sprite(texture),
                vector,
                25,
                25,
                0,
                new Weapon("Acid Body", 30, 1, 0.75),
                1,
                45f);
        this.sprite.setX(x);
        this.sprite.setY(y);
    }


    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        this.vector.set(game.playerCharacter.sprite.getX() - this.sprite.getX(),
                game.playerCharacter.sprite.getY() - this.sprite.getY());
        this.vector.setLength(this.getMoveSpeed());

        this.sprite.setRotation(90 - 180f / (float) Math.PI * (float) (Math.atan2(this.vector.x, this.vector.y)));

        this.sprite.translate(delta * this.vector.x,
                delta * this.vector.y);
    }
}