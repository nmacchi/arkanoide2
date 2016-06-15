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

/**
 *
 * @author nicolas
 */
public class InputAppState extends AbstractAppState implements AnalogListener, ActionListener {

    private InputManager inputManager;
    private BreakerBar arkanoid;
    private Breaker ball;
 
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.inputManager = ((SimpleApplication) app).getInputManager();
        this.arkanoid = stateManager.getState(GamePlayAppState.class).getArkanoid();
        this.ball = stateManager.getState(GamePlayAppState.class).getBall();
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
        arkanoid.move(name, value, ball);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Shoot") && !isPressed && !arkanoid.isBallShooted()) {
            arkanoid.setBallShooted(Boolean.TRUE);
        }
    }
}
