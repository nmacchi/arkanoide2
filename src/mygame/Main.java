package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.anim.AnimationState;
import com.simsilica.lemur.anim.SpatialTweens;
import com.simsilica.lemur.anim.Tween;
import com.simsilica.lemur.anim.Tweens;
import states.GameGuiAppState;
import states.GamePlayAppState;
import states.LoadingGameState;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication /*implements PhysicsCollisionListener*/ {
        
    private GamePlayAppState gameState;
    private LoadingGameState loadingGameState;
    private GameGuiAppState guiAppState;
    
    private float timeout = 0f;
    
    Label myLabel;
    Boolean firstAnimationElapsed = false;
    Boolean secondAnimationElapsed = false;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        //Start GUI appState
        guiAppState = new GameGuiAppState();
        loadingGameState = new LoadingGameState();        
        //gameState = new GamePlayAppState();
        
        
        stateManager.attachAll(guiAppState, loadingGameState);   
     
        
        
        /*Box box = new Box(1,1,1);
        Geometry cube = new Geometry("cube", box);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color",ColorRGBA.Blue);
        cube.setMaterial(material);
        
        cube.setLocalTranslation(0,0,0);
        cube.scale(0.095f, 0.07f, 0.045f);
        
        rootNode.attachChild(cube);
        */
        
        
        
  

       
        
        
        
        
        
    }


    @Override
    public void simpleUpdate(float tpf) {
        if(!loadingGameState.isEnabled() && !loadingGameState.isLoad()){
            
            timeout += tpf;
            
            //Wait 2 seconds after loading the game before starts 
            if(timeout > 2f){
                //stateManager.detach(loadingGameState);
                
                //Once the loading game is finished proceed to start the game
                //stateManager.attach(gameState);
                stateManager.getState(GameGuiAppState.class).showMainMenu();
                timeout = 0f;
            }
           
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    

}
