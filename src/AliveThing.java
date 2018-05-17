import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class AliveThing extends Entity implements CanAttack {
    public int healthTotal;
    public int health;
    public Weapon weapon;
    public int armor;

    public AliveThing() {
        super();
        this.healthTotal = 1;
        this.health = 1;
        this.weapon = new Weapon();
        this.armor = 0;
    }

    public AliveThing(Sprite sprite, int healthTotal, int health, int armor, Weapon weapon) {
        super(sprite);
        this.healthTotal = healthTotal;
        this.health = health;
        this.armor = armor;
        this.weapon = weapon;
    }
    public void takeDamage(int damage){
        this.health-=(damage> this.armor? damage-this.armor : 0);
    }

    @Override
    public void damage(AliveThing target) {
        target.takeDamage(this.weapon.damage);
    }

    @Override
    public void attack(boolean left) {
        //TODO swing weapon towards where facing
    }
}
