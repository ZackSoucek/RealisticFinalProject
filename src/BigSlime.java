import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class BigSlime extends Enemy {
    private Texture rageTexture;
    private Texture smolSlimeTexture;
    private boolean reaging;


    public BigSlime(Vector2 vector, int x, int y, Texture texture, Texture rageTexture, Texture smolSlimeTexture) {
        super(new Sprite(texture),
                vector,
                100,
                100,
                0,
                new Weapon("Acid Body", 65, 1, 0.75),
                4,
                15f);
        this.sprite.setX(x);
        this.sprite.setY(y);
        this.rageTexture = rageTexture;
        this.smolSlimeTexture = smolSlimeTexture;
        this.reaging = false;

    }


    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        this.vector.set(game.playerCharacter.sprite.getX() - this.sprite.getX(),
                game.playerCharacter.sprite.getY() - this.sprite.getY());
        this.vector.setLength(this.getMoveSpeed());

        this.sprite.setRotation(90 - 180f / (float) Math.PI * (float) (Math.atan2(this.vector.x, this.vector.y)));

        this.sprite.translate(delta * this.vector.x,
                delta * this.vector.y);

        if (health < healthTotal / 2.5) {
            reaging = true;
            this.sprite.setTexture(rageTexture);
        }
        if (reaging && (int) (Math.random() * 500) == 0) {
            int xval = (int) (this.sprite.getX() + Math.max((Math.random() - 0.5) * 200, 40));
            int yval = (int) (this.sprite.getY() + Math.max((Math.random() - 0.5 * 200), 40));
            ((TopDownScreen) game.getScreen()).addEntity(new SmallSlime(new Vector2(0, 0), xval, yval, smolSlimeTexture));
        }
    }

}
