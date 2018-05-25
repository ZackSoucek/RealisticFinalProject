import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sun.awt.SunHints;

import java.util.ArrayList;

public class Zombie extends Enemy {


    public Zombie(int x, int y, Texture texture) {
        super(new Sprite(texture),
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
        this.sprite.translate(delta * this.getMoveSpeed()* (game.playerCharacter.sprite.getX() - this.sprite.getX() > 0 ? 1 : -1),
                              delta * this.getMoveSpeed()* (game.playerCharacter.sprite.getY() - this.sprite.getY() > 0 ? 1 : -1));
    }

}
