import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import sun.awt.SunHints;

import java.util.ArrayList;

public class Zombie extends Enemy {


    public Zombie(Vector2 vector, int x, int y, Texture texture) {
        super(new Sprite(texture),
                vector,
                20,
                20,
                0,
                new Weapon("Zombie Fists", 2, 1, 0.75),
                1,
                20f);
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        this.vector.set(game.playerCharacter.sprite.getX() - this.sprite.getX(),
                game.playerCharacter.sprite.getY() - this.sprite.getY());
        this.vector.setLength(this.getMoveSpeed());

        this.sprite.setRotation(90-180f/(float)Math.PI*(float)(Math.atan2(this.vector.x, this.vector.y)));

        this.sprite.translate(delta * this.vector.x,
                delta * this.vector.y);
    }

}
