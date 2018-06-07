import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.awt.image.DirectColorModel;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PlayerCharacter extends AliveThing {
    private int xp;
    private int level;
    public final int EXP_PER_LEVEL = 10;
    private Direction direction;
    private Rectangle hitbox;

    //ANIMATION
    private boolean attacking;
    private short frame;
    private Texture[] animArray;


    public PlayerCharacter(Texture texture) {
        super(new Sprite(texture),
                new Vector2(),
                100,
                100,
                1,
                new Weapon("Basic Sword", 15, 150, 1));
        this.xp = 0;
        this.level = 1;
        direction = Direction.UP;
        this.sprite.scale(1.0f);
        attacking = false;
        frame = 0;
        animArray = new Texture[33];
        //Cancer but easy
        for (int i = 0; i <= 32; i++) {
            String path = "SWING/Swing Top00" + String.format("%02d", i) + ".png";
            animArray[i] = new Texture(path);
        }
        hitbox = new Rectangle();
        hitbox.y = 35;
        hitbox.x =  35;
        hitbox.width = 57;
        hitbox.height = 60;

    }

    public void animate() {
        if (attacking) {
            this.frame++;
            if (frame >= 32) {
                frame = 0;
                attacking = false;
            }
            this.sprite.setTexture(animArray[frame]);
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void addXP(int xp) {
        this.xp += xp;

        if (this.xp > EXP_PER_LEVEL * this.level) {
            level++;
            this.xp -= EXP_PER_LEVEL * level;
            levelUp();
        }
    }

    private void levelUp() {
        this.healthTotal += 10;
        this.health = healthTotal;
        this.armor++;
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


    @Override
    public void damage(AliveThing target) {
        target.takeDamage(this.weapon.damage + this.level);
        if (target.health <= 0 && target instanceof Enemy) {
            addXP(((Enemy) target).getDifficulty() * 3);
        }
    }

    public boolean isTouching(Entity e) {
        return hitbox.overlaps(e.sprite.getBoundingRectangle());
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {
        System.out.println("NO\nPLAYER DOES NOT NEED TO CHECK COLLISION WITH ITSELF\nNEVER RUN THIS");
        int i = 1 / 0;

    }

    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        animate();


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.vector.y = -Values.SPEED;
            this.vector.x = 0;
            this.direction = Direction.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.vector.y = Values.SPEED;
            this.vector.x = 0;
            this.direction = Direction.UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.vector.x = -Values.SPEED;
            this.vector.y = 0;
            this.direction = Direction.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.vector.x = Values.SPEED;
            this.vector.y = 0;
            this.direction = Direction.RIGHT;
        }
        this.vector.setLength(Values.SPEED);
        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            this.vector.setLength2(0.001f);
        }

        this.sprite.setRotation(180 - 180f / (float) Math.PI * (float) (Math.atan2(this.vector.x, this.vector.y)));

        this.sprite.translate(delta * this.vector.x,
                delta * this.vector.y);


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!attacking) {
                attacking = true;
                this.attack(this.direction, entities, game);
            }
        }


    }

    public void attack(Direction direction, ArrayList<Entity> entities, BattleGame game) {
        Rectangle attackRectangle;
        //Texture attacktexture;
        Rectangle charRect = hitbox;

        if (direction == Direction.UP) {
            attackRectangle = new Rectangle(charRect.x, charRect.y + charRect.height, charRect.width, this.weapon.reach);
            // attacktexture = new Texture(Gdx.files.internal("UPAttack.png"));
        } else if (direction == Direction.DOWN) {
            attackRectangle = new Rectangle(charRect.x, charRect.y - this.weapon.reach, charRect.width, this.weapon.reach);
            // attacktexture = new Texture(Gdx.files.internal("DOWNAttack.png"));
        } else if (direction == Direction.LEFT) {
            attackRectangle = new Rectangle(charRect.x - this.weapon.reach, charRect.y, this.weapon.reach, charRect.height);
            //  attacktexture = new Texture(Gdx.files.internal("LEFTAttack.png"));
        } else {//right
            attackRectangle = new Rectangle(charRect.x + charRect.width, charRect.y, this.weapon.reach, charRect.height);
            // attacktexture = new Texture(Gdx.files.internal("RightAttack.png"));
        }
        //attackrectangle is set
        for (Entity e : entities) {
            if (e instanceof AliveThing && attackRectangle.overlaps(e.sprite.getBoundingRectangle())) {
                this.damage((AliveThing) e);
            }

        }

    }



}
