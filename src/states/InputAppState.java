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
import mygame.Breaker;
import mygame.BreakerBar;
import mygame.Spaceship;

/**
 *
 * @author nicolas
 */
public class InputAppState extends AbstractAppState implements AnalogListener, ActionListener {

    private InputManager inputManager;
    private BreakerBar arkanoid;
    private Breaker ball;
    private Spaceship spaceship;
 
    SimpleApplication app; 
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = (SimpleApplication)app;
        this.inputManager = ((SimpleApplication) app).getInputManager();
        this.arkanoid = stateManager.getState(GamePlayAppState.class).getArkanoid();
        this.ball = stateManager.getState(GamePlayAppState.class).getBall();
        this.spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
        
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
        spaceship.move(name, value, ball);
        
        if (name.equals(InputMapping.SHOOT.name()) && spaceship.getCooldownTime() <= 0) {
                spaceship.fire(app.getRootNode());
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
//        if (name.equals(InputMapping.SHOOT.name()) && !isPressed && !arkanoid.isBallShooted()) {
//            arkanoid.setBallShooted(Boolean.TRUE);
//        }
        
        if (name.equals(InputMapping.SHOOT.name())) {
            if(isPressed && spaceship.getCooldownTime() <= 0){
                spaceship.fire(app.getRootNode());
            }
            
//            arkanoid.createSpaceship(arkanoid.getLocalTranslation());
        }
    }
}
