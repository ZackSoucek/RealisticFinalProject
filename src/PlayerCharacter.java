import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.sun.javafx.scene.traversal.Direction;

import java.awt.*;
import java.awt.image.DirectColorModel;
import java.util.ArrayList;

public class PlayerCharacter extends AliveThing {
    private int xp;
    private int level;
    public final int EXP_PER_LEVEL = 10;
    private Direction direction;

    public PlayerCharacter(Texture texture) {
        super(new Sprite(texture),
                100,
                100,
                1,
                new Weapon("Basic Sword", 5, 150, 1));
        this.xp = 0;
        this.level = 1;
        direction = Direction.UP;
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

    public void setLevel(int level) {
        this.level = level;
    }


    @Override
    public void damage(AliveThing target) {
        target.takeDamage(this.weapon.damage);
        if (target.health <= 0 && target instanceof Enemy) {
            addXP(((Enemy) target).getDifficulty()*3);
        }
    }

    public boolean isTouching(Entity e) {
        return this.sprite.getBoundingRectangle().overlaps(e.sprite.getBoundingRectangle());
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    @Override
    public void collidePlayer(PlayerCharacter playerCharacter) {
        System.out.println("NO\nPLAYER DOES NOT NEED TO CHECK COLLISION WITH ITSELF\nNEVER RUN THIS");
        int i = 1 / 0;
    }

    public void think(BattleGame game, float delta, ArrayList<Entity> entities) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            game.playerCharacter.setDirection(Direction.DOWN);
            game.playerCharacter.sprite.translateY(-delta * Values.SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            game.playerCharacter.setDirection(Direction.LEFT);
            game.playerCharacter.sprite.translateX(-delta * Values.SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.playerCharacter.setDirection(Direction.RIGHT);
            game.playerCharacter.sprite.translateX(delta * Values.SPEED);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            game.playerCharacter.setDirection(Direction.UP);
            game.playerCharacter.sprite.translateY(delta * Values.SPEED);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.attack(this.direction, entities, game);
        }


    }

    public void attack(Direction direction, ArrayList<Entity> entities, BattleGame game) {
        Rectangle attackRectangle;
        Texture attacktexture;
        Rectangle charRect = sprite.getBoundingRectangle();
        if (direction == Direction.UP) {
            attackRectangle = new Rectangle(charRect.x, charRect.y + charRect.height, charRect.width, this.weapon.reach);
            attacktexture = new Texture(Gdx.files.internal("UPAttack.png"));
        } else if (direction == Direction.DOWN) {
            attackRectangle = new Rectangle(charRect.x, charRect.y - charRect.height - this.weapon.reach, charRect.width, this.weapon.reach);
            attacktexture = new Texture(Gdx.files.internal("DOWNAttack.png"));
        } else if (direction == Direction.LEFT) {
            attackRectangle = new Rectangle(charRect.x - charRect.width, charRect.y, this.weapon.reach, charRect.height);
            attacktexture = new Texture(Gdx.files.internal("LEFTAttack.png"));
        } else {//right
            attackRectangle = new Rectangle(charRect.x + charRect.width, charRect.y, this.weapon.reach, charRect.height);
            attacktexture = new Texture(Gdx.files.internal("RightAttack.png"));
        }
        game.batch.begin();
        game.batch.draw(attacktexture, attackRectangle.x, attackRectangle.y);
        game.batch.end();
        //attackrectangle is set
        for (Entity e : entities) {
            if (e instanceof AliveThing && attackRectangle.overlaps(e.sprite.getBoundingRectangle())) {
                this.damage((AliveThing) e);
            }

        }
    }
}
