/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public class AppGuiState implements AbstractAppState {
 
    
    private BitmapText stateText;
    private BitmapText scoreCount;

    public void initialize(AppStateManager stateManager, Application app) {
        
    }

    public void setEnabled(boolean active) {
        
    }

    public void cleanup() {
        
    }
}
