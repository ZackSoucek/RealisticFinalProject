import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class EnemyGenerator {
    /**
     * @param level the level for which enemies to generate
     * @return an array of enemies which add up to the difficulty for the level
     * this can be modifed to give diffeent ammounts of enemies of difficulties
     */
    public static ArrayList<Enemy> generate(int level, int xMin, int xMax, int yMin, int yMax) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        while (level <= 0) {
            int diff = (int) (Math.random() * level + 1);
            int x = (int)(Math.random()*(xMax-xMin))+xMin;
            int y = (int)(Math.random()*(yMax-yMin))+yMin;
            enemies.add(enemyFinder(diff,x,y));
            level -= diff;
        }
        return enemies;
    }

    /**
     * @param dificulty the diffuclty of ememy wanted
     * @return a randomly selected enemy from the specifies difficlty
     * @Precondition difficulty is a valid enemy difficulty
     */
    private static Enemy enemyFinder(int dificulty, int x, int y) {
        //return an enemy that fits the given difficulty
        int rand = (int) (Math.random() * 100);//random value from 0-99 for generation fo a random enemy in the difficulty value
        switch (dificulty) {
            case 1:
                if (rand < 30) {
                    //return new thisTypeOfEnemy();
                } else if (rand < 60) {
                    //return new OtherTypeOfEnemy();
                } else {
                    return new Zombie(x,y,new Texture(Gdx.files.internal("FlabioFinal.png")));
                }
                break;

            case 2:
                if (rand < 30) {
                    //return new thisTypeOfEnemy();
                } else if (rand < 60) {
                    //return new OtherTypeOfEnemy();
                } else {
                    //return new NormalEnemy();
                }
                break;

            default:
                //return new DefaultEnemy(diff);
        }
        //stub out
        return new Zombie(x,y, new Texture(Gdx.files.internal("FlabioFinal.png")));
    }

}
