import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class AliveThing extends Entity implements CanAttack {
    public int healthTotal;
    public int health;
    public Weapon weapon;
    public int armor;
    private long lastHitTime;

    public AliveThing() {
        super();
        this.healthTotal = 1;
        this.health = 1;
        this.weapon = new Weapon();
        this.armor = 0;
        lastHitTime = TimeUtils.millis();
    }

    public AliveThing(Sprite sprite, Vector2 vector, int healthTotal, int health, int armor, Weapon weapon) {
        super(sprite, vector);
        this.healthTotal = healthTotal;
        this.health = health;
        this.armor = armor;
        this.weapon = weapon;
    }
    public void takeDamage(int damage){
        if(TimeUtils.timeSinceMillis(lastHitTime) > 500){
            this.health-=(damage > this.armor? damage-this.armor : 1);
            lastHitTime = TimeUtils.millis();
        }

    }

    @Override
    public void damage(AliveThing target) {
        target.takeDamage(this.weapon.damage);
    }


}
