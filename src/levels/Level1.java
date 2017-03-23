/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.Brick;
import mygame.CommonBrick;
import mygame.MetallicBrick;

/**
 *
 * @author nicolas
 */
public class Level1 extends Level{

    @Override
    public void buildLevel(AssetManager assetManager, AppStateManager stateManager, Node parent) {
        
        float initialX = -0.64f;
        Vector3f position = new Vector3f(initialX, 0.70f, 1f);
        int brickNum = 0; 
        
        
        for (int i = 0; i <= 1 ; i++) {
            
            //bricks per line
            for (int j = 0; j <= 9; j++) {
                if(i == 7){
                    parent.attachChild(new MetallicBrick(assetManager, position, stateManager));
                }else{
                    parent.attachChild(new CommonBrick(assetManager, position, i, stateManager));
                }

                //Calculate next position
                position.setX(((Brick)parent.getChild(brickNum)).getLocalTranslation().getX() + ((Brick)parent.getChild(brickNum)).getWidth() * 2 + BRICK_SEPARATOR);
                
                brickNum++;
            }
            position.setX(initialX);
            position.setY(position.getY() + ((Brick)parent.getChild(parent.getChildren().size() -1)).getHeight() * 2 + BRICK_SEPARATOR);
        }

        
        Brick.selectSpecialBrick(assetManager, parent, calculateNumberOfPowerups(parent));
        
    }
    
}
