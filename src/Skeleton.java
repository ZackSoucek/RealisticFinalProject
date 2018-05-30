import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Skeleton extends Enemy {
    public Skeleton(int x, int y, Texture texture) {
        super(new Sprite(texture),
                50,
                50,
                0,
                new Weapon("Bone death", 3, 1, 0.75),
                2,
                20f);
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    @Override
    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        if ((int) (Math.random() * 100) == 0) {
            throwBone(game);
        }
    }

    private void throwBone(BattleGame game) {
        this.health -= 10;
        float dx = this.sprite.getX() - game.playerCharacter.sprite.getX();
        float dy = this.sprite.getX() - game.playerCharacter.sprite.getY();
        Vector2 vector2 = new Vector2(dx, dy);


        ((TopDownScreen) game.getScreen()).addEntity(new Bone(new Texture(Gdx.files.internal("Bone.png")),
                this.sprite.getX() + this.sprite.getWidth() / 2,
                this.sprite.getY() + this.sprite.getHeight() / 2,
                vector2));

    }
}
