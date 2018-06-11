import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
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

    //ANIMATION
    private boolean attacking;
    private short frame;
    private Texture[] animArray;


    public PlayerCharacter(Texture texture) {
        super(new Sprite(texture),
                new Vector2(),
                200,
                200,
                1,
                new Weapon("Basic Sword", 15, 80, 1));
        this.xp = 0;
        this.level = 1;
        direction = Direction.UP;

        attacking = false;
        frame = 0;
        animArray = new Texture[33];
        //Cancer but easy
        for (int i = 0; i <= 32; i++) {
            String path = "SWING/Swing Top00" + String.format("%02d", i) + ".png";
            animArray[i] = new Texture(path);
        }
        this.sprite.setScale(2);
        hitbox = new Polygon(new float[]{47f, 128 - 82f,
                47f, 128 - 48f,
                47 + 35f, 128 - 48f,
                47 + 35f, 128 - 82f});
        hitbox.scale(2);
        hitbox.setOrigin(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);


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
        return Intersector.overlapConvexPolygons(hitbox.getTransformedVertices(), e.hitbox.getTransformedVertices(), null);
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public Polygon getHitbox() {
        return hitbox;
    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {
        System.out.println("NO\nPLAYER DOES NOT NEED TO CHECK COLLISION WITH ITSELF\nNEVER RUN THIS");
        int i = 1 / 0;

    }

    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        animate();


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.vector.y = -Values.SPEED;
            this.vector.x = 0;
            this.direction = Direction.DOWN;
            this.vector.setLength(Values.SPEED);

        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.vector.y = Values.SPEED;
            this.vector.x = 0;
            this.direction = Direction.UP;
            this.vector.setLength(Values.SPEED);

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.vector.x = -Values.SPEED;
            this.vector.y = 0;
            this.direction = Direction.LEFT;
            this.vector.setLength(Values.SPEED);

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.vector.x = Values.SPEED;
            this.vector.y = 0;
            this.direction = Direction.RIGHT;
            this.vector.setLength(Values.SPEED);

        } else {
            this.vector.setLength2(0.001f);
        }

        this.sprite.setRotation(180 - 180f / (float) Math.PI * (float) (Math.atan2(this.vector.x, this.vector.y)));
        this.hitbox.setRotation(180 - 180f / (float) Math.PI * (float) (Math.atan2(this.vector.x, this.vector.y)));

        this.sprite.translate(delta * this.vector.x,
                delta * this.vector.y);
        this.hitbox.translate(delta * this.vector.x,
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
        Rectangle charRect = hitbox.getBoundingRectangle();//TODO FIX ME SOON

        if (direction == Direction.UP) {
            attackRectangle = new Rectangle(charRect.x, charRect.y, charRect.width, this.weapon.reach + charRect.height);
        } else if (direction == Direction.DOWN) {
            attackRectangle = new Rectangle(charRect.x, charRect.y - this.weapon.reach, charRect.width, this.weapon.reach + charRect.height);
        } else if (direction == Direction.LEFT) {
            attackRectangle = new Rectangle(charRect.x - this.weapon.reach, charRect.y, this.weapon.reach + charRect.width, charRect.height);
        } else {//right
            attackRectangle = new Rectangle(charRect.x, charRect.y, this.weapon.reach + charRect.width, charRect.height);
        }
        for (Entity e : entities) {
            if (e instanceof AliveThing && attackRectangle.overlaps(e.sprite.getBoundingRectangle())) {
                this.damage((AliveThing) e);
            }

        }

    }


}
