import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerCharacter extends AliveThing {
    private int xp;
    private int level;
    public final int EXP_PER_LEVEL = 10;

    public PlayerCharacter(Texture texture) {
        super(new Sprite(texture),
                20,
                20,
                1,
                new Weapon("Basic Sword", 2, 50, 1));
        this.xp = 0;
    }

    public void addXP(int xp) {
        this.xp += xp;

        if (this.xp > EXP_PER_LEVEL * this.level) {
            level++;
            this.xp -= EXP_PER_LEVEL * level;
        }
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public int getEXP_PER_LEVEL() {
        return EXP_PER_LEVEL;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public void damage(AliveThing target) {
        target.takeDamage(this.weapon.damage);
        if (target.health <= 0 && target instanceof Enemy) {
            addXP(((Enemy) target).getDifficulty());
        }
    }

    public boolean isTouching(Entity e) {
        return this.sprite.getBoundingRectangle().overlaps(e.sprite.getBoundingRectangle());
    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {
        System.out.println("NO\nPLAYER DOES NOT NEED TO CHECK COLLISION WITH ITSELF\nNEVER RUN THIS");
        int i = 1 / 0;
    }

    @Override
    public void think(BattleGame game) {//potentially put player input here?
        System.out.println("NO\nPLAYER DOES NOT NEED TO CHECK COLLISION WITH ITSELF\nNEVER RUN THIS");
        int i = 1 / 0;
    }
}
