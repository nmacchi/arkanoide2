package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import states.GameGuiAppState;
import states.GamePlayAppState;
import states.InputAppState;
import states.PlayerState;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication /*implements PhysicsCollisionListener*/ {
    
    
    private Node bricks;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        bricks = new Node("BricksNode");

        rootNode.attachChild(bricks);

        makeWall();
        
        GamePlayAppState initState = new GamePlayAppState();
        stateManager.attach(initState);

        
       
    }

    private void makeWall() {
        float initialX = -0.64f;
        Vector3f position = new Vector3f(initialX, 0.70f, 1f);
        Brick brick = null;
        //rows
        int brickNum = 0; 
        
        for (int i = 0; i <= 7 ; i++) {
            
            //bricks per line
            for (int j = 0; j <= 9; j++) {
                if(i == 7){
                    brick = new MetallicBrick(assetManager, position, stateManager);
                }else{
                    brick = new CommonBrick(assetManager, position, i, stateManager);
                }
                
                bricks.attachChild(brick);

                //Calculate next position
//                position.setX(brick.getLocalTranslation().getX() + Brick.getWidth() * 2 + 0.02f);
                position.setX(brick.getLocalTranslation().getX() + ((Brick)bricks.getChild(brickNum)).getWidth() * 2 + 0.01f);
                
                brickNum++;
            }
            position.setX(initialX);
            position.setY(position.getY() + ((Brick)bricks.getChild(bricks.getChildren().size() -1)).getHeight() * 2 + 0.01f);
        }
        
        
        
        Brick.selectSpecialBrick(assetManager, bricks);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

}
