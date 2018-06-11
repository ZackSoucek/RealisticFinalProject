import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Bone extends AliveThing {
    private int rotation;

    public Bone(Texture texture, Vector2 vector2, float x, float y) {
        super(new Sprite(texture),
                vector2,
                40,
                40,
                2,
                new Weapon("Bone explosion", 30, 1, 0));
        this.sprite.setX(x);
        this.sprite.setY(y);
        this. hitbox = new Polygon(new float[]{sprite.getX(), sprite.getY(),
                sprite.getX(), sprite.getY() + sprite.getHeight(),
                sprite.getX() + sprite.getHeight(), sprite.getY() + sprite.getHeight(),
                sprite.getX() + sprite.getWidth(), sprite.getY()});
        hitbox.setOrigin(sprite.getX()+ sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2);
        this.rotation = 0;
    }

    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        this.sprite.translate(delta * vector.x,
                delta * vector.y);
        this.sprite.rotate(delta * vector.len());
        this.hitbox.translate(delta * vector.x,
                delta * vector.y);
        this.hitbox.rotate(delta * vector.len());


    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {
        this.damage(playerCharacter);
        this.health = -1000;
    }

    @Override
    public void checkPos() {
        if (this.sprite.getX() < 0)
            this.health = -1000;
        if (this.sprite.getY() < 0)
            this.health = -1000;
        if (this.sprite.getX() > Values.WORLD_WIDTH - this.sprite.getWidth())
            this.health = -1000;
        if (this.sprite.getY() > Values.WORLD_HEIGHT - this.sprite.getHeight())
            this.health = -1000;
    }
}

