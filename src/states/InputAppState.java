/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.entities.Arkanoid;
import mygame.entities.Breaker;
import mygame.entities.BreakerBar;
import mygame.entities.Spaceship;

/**
 *
 * @author nicolas
 */
public class InputAppState extends AbstractAppState implements AnalogListener, ActionListener {

    private InputManager inputManager;
    private AppStateManager stateManager;
    private Node rootNode;
    private float xPosition;
    //private Vector3f direction;
    SimpleApplication app;
    
    private Vector3f direction;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.inputManager = ((SimpleApplication) app).getInputManager();
        this.stateManager = stateManager;

        //this.direction = BreakerBar.getDirection();
        direction = new Vector3f();
        
        
        
        
        addInputMappings();
    }

    public enum InputMapping {

        LEFT, RIGHT, SHOOT
    }

    public void addInputMappings() {
        if(!inputManager.hasMapping(InputMapping.LEFT.name())) inputManager.addMapping(InputMapping.LEFT.name(), new KeyTrigger(KeyInput.KEY_LEFT));
        if(!inputManager.hasMapping(InputMapping.RIGHT.name())) inputManager.addMapping(InputMapping.RIGHT.name(), new KeyTrigger(KeyInput.KEY_RIGHT));
        if(!inputManager.hasMapping(InputMapping.SHOOT.name())) inputManager.addMapping(InputMapping.SHOOT.name(), new KeyTrigger(KeyInput.KEY_SPACE));
        
        for (InputMapping i : InputMapping.values()) {
            inputManager.addListener(this, i.name());
        }

    }

    public void onAnalog(String name, float value, float tpf) {
        direction.set(Vector3f.UNIT_X).normalizeLocal();
        
        
//        Geometry leftBar = (Geometry)rootNode.getChild("LeftBar");
//        
//        System.out.println("LEFT BAR POS: " + leftBar.getLocalTranslation().getX());
//        System.out.println("LEFT BAR POS (WORLD POS): " + leftBar.getWorldTranslation().getX());
//        System.out.println("MITAD BARRA: (MODEL BOUND)" + ((BoundingBox)leftBar.getModelBound()).getXExtent() /2);
//        System.out.println("MITAD BARRA: (WORLD BOUND)" + ((BoundingBox)leftBar.getWorldBound()).getXExtent() /2);
//        System.out.println("MITAD ARKANOID: (WORLD BOUND)" + ((BoundingBox)((BreakerBar)rootNode.getChild("BreakerBar")).getWorldBound()).getXExtent() / 2);
//        System.out.println("MITAD ARKANOID: (MODEL BOUND)" + ((BoundingBox)((BreakerBar)rootNode.getChild("BreakerBar")).getModelBound()).getXExtent() / 2);
//        System.out.println("MAX LEFT LIMIT : " + leftBar.getLocalTranslation().getX() + ((BoundingBox)leftBar.getModelBound()).getXExtent() /2  + ((BoundingBox)((BreakerBar)rootNode.getChild("BreakerBar")).getWorldBound()).getXExtent() / 2);
//        
//        float limit = leftBar.getLocalTranslation().getX() + ((BoundingBox)leftBar.getModelBound()).getXExtent() + ((BoundingBox)((BreakerBar)rootNode.getChild("BreakerBar")).getWorldBound()).getXExtent(); 
        
        
        if (name.equals(InputMapping.LEFT.name()) && xPosition >= ((float)((Node)rootNode.getChild("Gamefield")).getUserData("leftSideLimit") + ((BreakerBar)rootNode.getChild("BreakerBar")).getWidth())) {
//            direction.multLocal(-0.7f * tpf);
            ((Node) rootNode.getChild("BreakerBarNode")).move(direction.multLocal(-0.7f * tpf));
        }

        if (name.equals(InputMapping.RIGHT.name()) && xPosition <= ((float)((Node)rootNode.getChild("Gamefield")).getUserData("rightSideLimit") - ((BreakerBar)rootNode.getChild("BreakerBar")).getWidth())) {
            //direction.multLocal(0.7f * tpf);
            ((Node) rootNode.getChild("BreakerBarNode")).move(direction.multLocal(0.7f * tpf));
        }

        xPosition = ((Geometry) rootNode.getChild("BreakerBar")).getWorldTranslation().x;
        
        if(name.equals(InputMapping.SHOOT.name())){
            if (rootNode.getChild("BreakerBar") instanceof Spaceship) {
                if (((Spaceship)rootNode.getChild("BreakerBar")).getCooldownTime() <= 0) {
                    ((Spaceship)rootNode.getChild("BreakerBar")).fire();
                    ((AudioNode) rootNode.getChild("laserShootAudio")).playInstance();
                }
            }
            
        }
        
        
    }

    public void onAction(String name, boolean isPressed, float tpf) {

        if (((Geometry) ((Node) rootNode.getChild("BreakerBarNode")).getChild(0)) instanceof Arkanoid) {
            if (name.equals(InputMapping.SHOOT.name()) && !isPressed && !stateManager.getState(GamePlayAppState.class).isGameRunning()) {
             
                ((Breaker) rootNode.getChild("Breaker")).release(stateManager);
                //stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.TRUE);
                stateManager.getState(GamePlayAppState.class).setGameRunning(Boolean.TRUE);

            }
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        removeInputMappings();
    }

    private void removeInputMappings() {
        for (InputMapping i : InputMapping.values()) {
            inputManager.removeListener(this);
        }
    }
}
