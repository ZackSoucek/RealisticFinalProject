import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends AliveThing {
    private int difficulty;
    private float moveSpeed;

    public Enemy() {
        super();
        difficulty = 0;
        //no blank enemies
    }


    public Enemy(Sprite sprite, Vector2 vector, int healthTotal, int health, int armor, Weapon weapon, int difficulty, float moveSpeed) {
        super(sprite, vector, healthTotal, health, armor, weapon);
        this.difficulty = difficulty;
        this.moveSpeed = moveSpeed;

    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {
        this.damage(playerCharacter);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
