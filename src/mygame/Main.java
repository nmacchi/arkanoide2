package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.ProgressBar;
import com.simsilica.lemur.style.BaseStyles;
import effects.AudioEffects;
import effects.VisualEffects;
import mygame.commons.CommonModels;
import mygame.commons.CommonTextures;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication /*implements PhysicsCollisionListener*/ {
    
    private CommonModels models;
    private CommonTextures textures; 
    private VisualEffects visualFX; 
    private AudioEffects audioFX;
    
    Double progress = 0.0D;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //Initialize Lemur GUI
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        
        
        models = new CommonModels(assetManager);
        textures = new CommonTextures(assetManager);
        visualFX = new VisualEffects();
        audioFX = new AudioEffects(assetManager, rootNode);
        
        
        
        
        
        //:TODO Implementar una pantalla de carga para esta instancia
        //:TODO Cargar efectos visuales, sonidos
        /*models.loadModels();
        textures.loadTextures();
        visualFX.initVisualEffect(assetManager, rootNode);
        audioFX.loadAudioFXs();
        */
        //bricks = new Node("BricksNode");

        //rootNode.attachChild(bricks);

//        makeWall();
        


        



        //GamePlayAppState initState = new GamePlayAppState();
        //stateManager.attach(initState);

        
       
    }

    

   /* private void makeWall() {
        float initialX = -0.64f;
        Vector3f position = new Vector3f(initialX, 0.70f, 1f);
        Brick brick = null;
        //rows
        int brickNum = 0; 
        
        for (int i = 0; i <= 7 ; i++) {
            
            //bricks per line
            for (int j = 0; j <= 9; j++) {
                if(i == 7){
                    brick = new CommonBrick(assetManager, position, 1, stateManager);
                }else{
                    brick = new MetallicBrick(assetManager, position, stateManager);
                    
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
    }*/

    @Override
    public void simpleUpdate(float tpf) {
        progress += 0.1;
        ((ProgressBar)((Container)guiNode.getChild(0)).getChild(0)).setProgressValue(progress);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    
    
}
