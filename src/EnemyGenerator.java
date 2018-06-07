import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EnemyGenerator {
    /**
     * @param level the level for which enemies to generate
     * @return an array of enemies which add up to the difficulty for the level
     * this can be modifed to give diffeent ammounts of enemies of difficulties
     */


    public static ArrayList<Enemy> generate(int level, int xMin, int xMax, int yMin, int yMax) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        while (level > 0) {
            int diff = (int) (Math.random() * Math.min(level, Values.MAX_DIFFICULTY) + 1);

            int x = (int) (Math.random() * (xMax - xMin)) + xMin;
            int y = (int) (Math.random() * (yMax - yMin)) + yMin;
            enemies.add(enemyFinder(diff, x, y));
            level -= diff;
        }
        return enemies;
    }

    /**
     * @param dificulty the difficlty of ememy wanted
     * @return a randomly selected enemy from the specifies difficlty
     * @Precondition difficulty is a valid enemy difficulty
     */
    private static Enemy enemyFinder(int dificulty, int x, int y) {
        Vector2 vect = new Vector2();
        //return an enemy that fits the given difficulty
        int rand = (int) (Math.random() * 100);//random value from 0-99 for generation fo a random enemy in the difficulty value
        switch (dificulty) {
            case 1:
                return new Zombie(vect, x, y, new Texture(Gdx.files.internal("Zach/zombie/zombie facing right.png")));
            case 2:
                return new Skeleton(vect, x, y, new Texture(Gdx.files.internal("Skeleton.png")));
            case 3:
                return new Zombear(vect, x, y, new Texture(Gdx.files.internal("Zombear.png")));
            case 4:
                return new BigSlime(vect, x, x, new Texture(Gdx.files.internal("bigSlime.png"))
                        , new Texture(Gdx.files.internal("rageSlime.png")),
                        new Texture(Gdx.files.internal("smallSlime.png")));
            default:
                return new Zombie(vect, x, y, new Texture(Gdx.files.internal("FlabioFinal")));
        }

    }

}
