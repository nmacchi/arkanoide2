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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import effects.VisualEffects;

/**
 *
 * @author nicolas
 */
public class LostLifeState extends AbstractAppState {

    private int state;
    private float timeElapsed;
    SimpleApplication app;
    AppStateManager stateManager;
    Vector3f position;
    Spatial spatial;
    boolean done;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = (SimpleApplication) app;
        this.stateManager = stateManager;
        this.done = Boolean.FALSE;
        setEnabled(false);
    }

    @Override
    public void update(float tpf) {

        if (isEnabled()) {
            
            timeElapsed += tpf / 1;

            if (state == 0) {
                ((AudioNode)app.getRootNode().getChild("arkanoidExplosionAudio")).playInstance();
                
                for (Spatial entity : app.getRootNode().getChildren()) {
                    if (entity != null) {
                        if (entity.getName().equals("BreakerBarNode")) {
                            spatial = entity;
                            position = ((Geometry) ((Node) entity).getChild("BreakerBar")).getWorldTranslation();
                            break;
                        }

                    }
                }


                //Reset entites
                ((Node) spatial).detachAllChildren();
                app.getRootNode().detachChildNamed("Breaker");

                //Build multiple effect trigger
                /*Map<Float, List<ParticleEmitter>> customTriggerEffect = new HashMap<Float, List<ParticleEmitter>>();
                 customTriggerEffect.put(0.25f, Arrays.asList(VisualEffects.getFlash(position), VisualEffects.getSpark(position), VisualEffects.getSmoketrail(position), VisualEffects.getDebris(position), VisualEffects.getShockwave(position)));
                 customTriggerEffect.put(0.3f, Arrays.asList(VisualEffects.getFlash(position), VisualEffects.getRoundspark(position)));

                 effects.ScriptAppState appState = new effects.ScriptAppState(stateManager);
                 appState.setCustomTriggerEffect(customTriggerEffect);
                 stateManager.attach(appState);*/
                
                
                VisualEffects.getFlash(position);
                VisualEffects.getSpark(position);
                VisualEffects.getSmoketrail(position);
                VisualEffects.getDebris(position);
                VisualEffects.getShockwave(position);
                

                state++;

            }


            if (timeElapsed > 1f + .05f && state == 1) {
                VisualEffects.getFlame(position);
                VisualEffects.getRoundspark(position);
                state++;
            }


            if (timeElapsed >= 4f && done == Boolean.FALSE) {
                app.getRootNode().detachChildNamed("explosionFX");
                stateManager.getState(GamePlayAppState.class).reset();
                done = Boolean.TRUE;
            }

            if (timeElapsed > 5f) {
                setEnabled(Boolean.FALSE);
                timeElapsed = 0;
                state = 0;
                done = Boolean.FALSE;
            }
        }

    }
}
