/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.Arkanoid;
import mygame.Breaker;
import mygame.BreakerBar;
import mygame.Spaceship;

/**
 *
 * @author nicolas
 */
public class InputAppState extends AbstractAppState implements AnalogListener, ActionListener {

    private InputManager inputManager;
    private Arkanoid arkanoid;
    private Breaker ball;
    private Spaceship spaceship;
    private AppStateManager stateManager;
    
    private Node rootNode;
    private float xPosition;
    private Vector3f direction;
    
    SimpleApplication app; 
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = (SimpleApplication)app;
        this.rootNode = this.app.getRootNode();
        this.inputManager = ((SimpleApplication) app).getInputManager();
        this.stateManager = stateManager;
        
        this.arkanoid = stateManager.getState(GamePlayAppState.class).getArkanoid();
        this.ball = stateManager.getState(GamePlayAppState.class).getBall();
        this.spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
        
        this.direction = BreakerBar.getDirection();
        
        addInputMappings();
    }

    public enum InputMapping {
        LEFT, RIGHT, SHOOT
    }

    public void addInputMappings() {
        inputManager.addMapping(InputMapping.LEFT.name(), new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(InputMapping.RIGHT.name(), new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(InputMapping.SHOOT.name(), new KeyTrigger(KeyInput.KEY_SPACE));


        for (InputMapping i : InputMapping.values()) {
            inputManager.addListener(this, i.name());
        }

    }

    public void onAnalog(String name, float value, float tpf) {
        direction.set(Vector3f.UNIT_X).normalizeLocal();
        if(name.equals(InputMapping.LEFT.name()) && xPosition >= BreakerBar.getMaxLeftLimit()){
            direction.multLocal(-0.7f * tpf);
            ((Node)rootNode.getChild("BreakerBarNode")).move(direction);
        }
        
        if(name.equals(InputMapping.RIGHT.name()) && xPosition <= BreakerBar.getMaxRightLimit()){
             direction.multLocal(0.7f * tpf);  
            ((Node)rootNode.getChild("BreakerBarNode")).move(direction);
        }
        
        xPosition = ((Geometry)rootNode.getChild("BreakerBar")).getWorldTranslation().x; 
        
//        if (name.equals(InputMapping.SHOOT.name()) && spaceship.getCooldownTime() <= 0) {
//                spaceship.fire();
//        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(InputMapping.SHOOT.name()) && !isPressed && !stateManager.getState(GamePlayAppState.class).isGameStarted()) {
            ball.setLocalTranslation(ball.getWorldTranslation());
            ball.setInitialDirection();
            rootNode.attachChild(ball);
            stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.TRUE);
        }
        
//        if (name.equals(InputMapping.SHOOT.name())) {
//            if(isPressed && spaceship.getCooldownTime() <= 0){
//                spaceship.fire(app.getRootNode());
//            }
//            
////            arkanoid.createSpaceship(arkanoid.getLocalTranslation());
//        }
    }
}
