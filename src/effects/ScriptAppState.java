/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.effect.ParticleEmitter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import triggers.PlayEffect;
import triggers.Trigger;

/**
 *
 * @author nicolas
 */
public class ScriptAppState extends AbstractAppState{
    private AppStateManager stateManager;
    private Map<Float, List<String>> customTriggerEffect;
    
    
    private boolean triggerRunning;
    private List<Trigger> triggerObjects = new ArrayList<Trigger>();
    
    public ScriptAppState(AppStateManager stateManager){
          
        this.stateManager = stateManager;
    }
    
    @Override
    public void update(float tpf){
        if(isEnabled()){
            
            for(Trigger triggerObject : triggerObjects){
                System.out.println("LLEGA11111111111111");
                triggerObject.update(tpf);
            }
            
            checkNonRunningTriggers();
        }
    }
    
    @Override
    public void stateAttached(AppStateManager stateManager) {
        
        //Construir la cadena de triggers
        Trigger customTrigger = new Trigger();
        Iterator<Float> it = customTriggerEffect.keySet().iterator();
        
        while(it.hasNext()){
            final List<PlayEffect> playEffectsList = new ArrayList<PlayEffect>();
            
            float time = it.next();
            List<String> effectsNamesList = customTriggerEffect.get(time);
            
            fillPlayEffects(effectsNamesList, playEffectsList);
            
            customTrigger.addTimerEvent(time, new Trigger.TimerEvent(){
                 public Object[] call(){
                     
                     for(PlayEffect playEffect : playEffectsList){
                         playEffect.trigger();
                     }
                     return null;
                 }
            });
        }
        
        triggerObjects.add(customTrigger);
        
    }
    
    private void fillPlayEffects(List<String> effectsNames, List<PlayEffect> playEffectsList){
        for(String effect: effectsNames){
            playEffectsList.add(new PlayEffect(VisualEffects.getEffectsMap().get(effect)));
        }
    }
    
    private void checkNonRunningTriggers(){
        for(Trigger triggerObject : triggerObjects){
            if(!triggerObject.isEnabled() && !triggerObject.isRunning()){
                triggerRunning = false;
            }else if(triggerObject.isEnabled()){
                triggerRunning = true;
            }
        }
        
        if(!triggerRunning){
            System.out.println("DISABLE STATE");
            this.setEnabled(false);
        }
        
    }
    
    
    public List<Trigger> getTriggerObjects() {
        return triggerObjects;
    }

    public void addTriggerObject(Trigger triggerObject) {
        this.triggerObjects.add(triggerObject);
    }

    public Map<Float, List<String>> getCustomTriggerEffect() {
        return customTriggerEffect;
    }

    public void setCustomTriggerEffect(Map<Float, List<String>> customTriggerEffect) {
        this.customTriggerEffect = customTriggerEffect;
    }
    
    
    
}
