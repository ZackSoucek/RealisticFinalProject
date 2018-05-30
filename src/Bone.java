import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Bone extends Entity{

    public Bone(Texture texture, float x, float y, Vector2 vector2) {
        super(new Sprite(texture));
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {

    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {

    }
}
