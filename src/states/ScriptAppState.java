/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.state.AbstractAppState;
import java.util.ArrayList;
import java.util.List;
import triggers.Trigger;

/**
 *
 * @author nicolas
 */
public class ScriptAppState extends AbstractAppState{
    
    float timer;
    private List<Trigger> triggerObjects = new ArrayList<Trigger>();

    @Override
    public void update(float tpf){
        if(isEnabled()){
            
            for(Trigger triggerObject : triggerObjects){
                triggerObject.update(tpf);
            }
        }
    }
    
    
    public List<Trigger> getTriggerObjects() {
        return triggerObjects;
    }

    public void addTriggerObject(Trigger triggerObject) {
        this.triggerObjects.add(triggerObject);
    }

}
