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

}
