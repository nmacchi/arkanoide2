package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.anim.Animation;
import com.simsilica.lemur.anim.SpatialTweens;
import com.simsilica.lemur.anim.Tween;
import com.simsilica.lemur.anim.TweenAnimation;
import com.simsilica.lemur.effect.AbstractEffect;
import com.simsilica.lemur.effect.Effect;
import com.simsilica.lemur.effect.EffectInfo;
import com.simsilica.lemur.style.BaseStyles;
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
        gameState = new GamePlayAppState();
        
        
        stateManager.attachAll(guiAppState, loadingGameState);   
     
    }


    @Override
    public void simpleUpdate(float tpf) {
        if(!loadingGameState.isEnabled()/* && !gameState.isInitialized()*/){
            
            timeout += tpf;
            
            //Wait 2 seconds after loading the game before starts 
            if(timeout > 2f){
                //stateManager.detach(loadingGameState);
                
                //Once the loading game is finished proceed to start the game
                stateManager.attach(gameState);
                
                timeout = 0f;
            }
           
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    

}
