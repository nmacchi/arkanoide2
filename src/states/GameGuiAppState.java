/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author nicolas
 */
public class GameGuiAppState extends AbstractAppState {
   
    private AppStateManager stateManager;
//    private static int INITIAL_POSITION_LOCAL_NODE = 100;
    
    private BitmapText stateIndicator;
    private BitmapText scoreIndicator;
    
    private Node localNode = new Node ("Lives Node");
//    private Spatial arkanoid;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.stateManager = stateManager;
//        this.xPositionForLives = INITIAL_POSITION_LOCAL_NODE;
        
        initGUILights(app);
        
        initializeStateIndicator(app);
        initializeScoreIndicator(app, stateManager);
        updateLivesIndicator(app, stateManager.getState(PlayerState.class).getCurrentLives());   
    }

    @Override
    public void setEnabled(boolean active) {
        
    }

    @Override
    public void cleanup() {
        
    }
    
    private void initializeStateIndicator(Application app){
        stateIndicator = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false); 
        
        stateIndicator.setSize(25f);      // font size
        stateIndicator.setColor(ColorRGBA.Blue);                             // font color
        stateIndicator.setLocalTranslation(300, app.getContext().getSettings().getHeight() / 2, 0); // position
        
        ((SimpleApplication)app).getGuiNode().attachChild(stateIndicator);
    }
    
    private void initializeScoreIndicator(Application app, AppStateManager stateManager){
        scoreIndicator = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false); 
        
        scoreIndicator.setName("Score Indicator");
        scoreIndicator.setSize(18f);      // font size
        scoreIndicator.setColor(ColorRGBA.White);
        scoreIndicator.setLocalTranslation(app.getContext().getSettings().getWidth()/2 - 50, app.getContext().getSettings().getHeight(), 0); // position
        scoreIndicator.setText(stateManager.getState(PlayerState.class).getFormattedScore());
    
        ((SimpleApplication)app).getGuiNode().attachChild(scoreIndicator);
    }
    
    public void updateLivesIndicator(Application app, int lives){
        localNode.detachAllChildren();
        
        int xPositionForLives = 100;
        
        for(int x=0; x < lives; x++){
            Spatial arkanoid = app.getAssetManager().loadModel("Models/arkanoide/Arkanoide.j3o");
            arkanoid.scale(20f);
            arkanoid.rotate(-0.5f,2.5f,0f);
    
            arkanoid.setLocalTranslation(app.getContext().getSettings().getWidth() - xPositionForLives,50, 0);
            
            localNode.attachChild(arkanoid);
         
            xPositionForLives += 50;
        }
        
        ((SimpleApplication)app).getGuiNode().attachChild(localNode);
        
    }
    
    private void initGUILights(Application app){
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));
        
        sun.setDirection(new Vector3f(-1f,0f,0f));
        ((SimpleApplication)app).getGuiNode().addLight(sun);
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White);
        ((SimpleApplication)app).getGuiNode().addLight(al);
    }

    public BitmapText getStateIndicator() {
        return stateIndicator;
    }

    public BitmapText getScoreIndicator() {
        return scoreIndicator;
    }
    
    public void updateScoreIndicator(){
        scoreIndicator.setText(stateManager.getState(PlayerState.class).getFormattedScore());
    }
}
