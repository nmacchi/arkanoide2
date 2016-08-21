/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.state.AbstractAppState;

/**
 *
 * @author nicolas
 */
public class PlayerState extends AbstractAppState {
    
    private static int INITIAL_DEFAULT_LIVES = 3;
    private int currentLives;
    private int score;

    
    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = currentLives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
    
}
