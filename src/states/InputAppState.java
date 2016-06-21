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
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
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
    
    //TEST
    private Node rootNode;
    private Vector3f direction;
    private float rangeLimit;
    private float maxLeftLimit = -0.60f;
    private float maxRightLimit = 0.60f;
    
    SimpleApplication app; 
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = (SimpleApplication)app;
        this.inputManager = ((SimpleApplication) app).getInputManager();
        this.arkanoid = stateManager.getState(GamePlayAppState.class).getArkanoid();
        this.ball = stateManager.getState(GamePlayAppState.class).getBall();
        this.spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
        this.rootNode = this.app.getRootNode();
        
        this.direction = arkanoid.getDirection();
        
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
//        arkanoid.move(name, value, ball);
        //spaceship.move(name, value, ball);
        
        direction.set(Vector3f.UNIT_X).normalizeLocal();
        if(name.equals(InputMapping.LEFT.name()) && rangeLimit >= maxLeftLimit){
            direction.multLocal(-0.7f * tpf);
            ((Node)rootNode.getChild("BreakerBarNode")).move(direction);
        }
        
        if(name.equals(InputMapping.RIGHT.name()) && rangeLimit <= maxRightLimit){
             direction.multLocal(0.7f * tpf);  
            ((Node)rootNode.getChild("BreakerBarNode")).move(direction);
        }
        
        rangeLimit = ((BreakerBar)rootNode.getChild("BreakerBar")).getWorldTranslation().x; 
        
//        if (name.equals(InputMapping.SHOOT.name()) && spaceship.getCooldownTime() <= 0) {
//                spaceship.fire();
//        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(InputMapping.SHOOT.name()) && !isPressed && !arkanoid.isBallShooted()) {
            ball.setLocalTranslation(ball.getWorldTranslation());
            rootNode.attachChild(ball);
            arkanoid.setBallShooted(Boolean.TRUE);
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
