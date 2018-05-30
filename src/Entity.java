import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public abstract class Entity {
    public Sprite sprite;
    //all entitys need a sprite which has location and picture


    public Entity() {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("assets/Flabio.png")));
    }

    public Entity(Sprite sprite) {
        this.sprite = sprite;
    }

    public abstract void think(BattleGame game, float delta, ArrayList<Entity> entities);//should handle movement and such

    public abstract void collidePlayer(PlayerCharacter playerCharacter);//run when player touches them

    public void checkPos() {
        if (this.sprite.getX() < 0)
            this.sprite.setX(0);
        if (this.sprite.getY() < 0)
            this.sprite.setY(0);
        if (this.sprite.getX() > Values.WORLD_WIDTH - this.sprite.getWidth())
            this.sprite.setX(Values.WORLD_WIDTH - this.sprite.getWidth());
        if (this.sprite.getY() > Values.WORLD_HEIGHT - this.sprite.getHeight())
            this.sprite.setY(Values.WORLD_HEIGHT - this.sprite.getHeight());
    }

}
