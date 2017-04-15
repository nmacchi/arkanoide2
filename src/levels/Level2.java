/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import static levels.Level.BRICK_SEPARATOR;
import mygame.Brick;
import mygame.CommonBrick;
import mygame.MetallicBrick;

/**
 *
 * @author nicolas
 */
public class Level2 extends Level{

    @Override
    public void buildLevel(AssetManager assetManager, AppStateManager stateManager, Node parent) {
        float initialX = -0.64f;
        Vector3f position = new Vector3f(initialX, 0.4f, 1f);
        int brickNum = 0;
        
        int maxBricksInLine = 9;
        
        
        for(int i = 0; i <= 9; i++){
            for (int j = 0; j <= maxBricksInLine; j++) {
                if(i == 0){
                    if(j < maxBricksInLine){
                        parent.attachChild(new MetallicBrick(assetManager, position, stateManager));
                        //Calculate next position
                        calculateNextPosition(position, brickNum, parent);
                        
                       // position.setX(((Brick)parent.getChild(brickNum)).getLocalTranslation().getX() + ((Brick)parent.getChild(brickNum)).getWidth() * 2 + BRICK_SEPARATOR);
                        brickNum++;
                    }else{
                        parent.attachChild(new CommonBrick(assetManager, position, 2, stateManager));
                        //Calculate next position
                        calculateNextPosition(position, brickNum, parent);    
                        //position.setX(((Brick)parent.getChild(brickNum)).getLocalTranslation().getX() + ((Brick)parent.getChild(brickNum)).getWidth() * 2 + BRICK_SEPARATOR);
                        brickNum++;
                        break;
                    }
                    
//                    if(j == maxBricksInLine){
//                        
//                    }
                }
                
                if(i > 0){
                    parent.attachChild(new CommonBrick(assetManager, position, i-1, stateManager));         
                    //Calculate next position
                    calculateNextPosition(position, brickNum, parent);
                    //position.setX(((Brick)parent.getChild(brickNum)).getLocalTranslation().getX() + ((Brick)parent.getChild(brickNum)).getWidth() * 2 + BRICK_SEPARATOR);
                    brickNum++;
                }
               
                
               if(i > 0 && j == maxBricksInLine-i){
                   break; 
                }

            }
            
            position.setX(initialX);
            position.setY(position.getY() + ((Brick)parent.getChild(parent.getChildren().size() -1)).getHeight() * 2 + BRICK_SEPARATOR);
        }
        
        Brick.selectSpecialBrick(assetManager, parent, calculateNumberOfPowerups(parent));
        
    }
    
    private void calculateNextPosition(Vector3f position, int brickNum, Node parent){
        position.setX(((Brick)parent.getChild(brickNum)).getLocalTranslation().getX() + ((Brick)parent.getChild(brickNum)).getWidth() * 2 + BRICK_SEPARATOR);
    }
    
}
