import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public abstract class Entity {
    public Sprite sprite;
    public Vector2 vector;
    public Polygon hitbox;
    //all entitys need a sprite which has location and picture


    public Entity() {
        this.sprite = new Sprite(new Texture(Gdx.files.internal("assets/Flabio.png")));
        vector = new Vector2();
        hitbox = new Polygon(new float[]{sprite.getX(), sprite.getY(),
                sprite.getX(), sprite.getY() + sprite.getHeight(),
                sprite.getX() + sprite.getHeight(), sprite.getY() + sprite.getHeight(),
                sprite.getX() + sprite.getWidth(), sprite.getY()});
    }

    public Entity(Sprite sprite, Vector2 vector) {
        this.sprite = sprite;
        this.vector = vector;
        this.hitbox = new Polygon(new float[]{sprite.getX(), sprite.getY(),
                sprite.getX(), sprite.getY() + sprite.getHeight(),
                sprite.getX() + sprite.getHeight(), sprite.getY() + sprite.getHeight(),
                sprite.getX() + sprite.getWidth(), sprite.getY()});
    }

    public abstract void think(BattleGame game, float delta, ArrayList<Entity> entities);//should handle movement and such

    public abstract void collidePlayer(PlayerCharacter playerCharacter);//run when player touches them

    public void checkPos() {
        if (this.sprite.getX() < 0) {
            this.sprite.translateX(10);
            this.hitbox.translate(10, 0);
        }
        if (this.sprite.getY() < 0) {
            this.sprite.translateY(10);
            this.hitbox.translate(0, 10);
        }
        if (this.sprite.getX() > Values.WORLD_WIDTH - this.sprite.getWidth()){
            this.sprite.translateX(-10);
            this.hitbox.translate(-10,0);
        }
        if (this.sprite.getY() > Values.WORLD_HEIGHT - this.sprite.getHeight()){
            this.sprite.translateY(-10);
            this.hitbox.translate(0,-10);
        }


    }

}
