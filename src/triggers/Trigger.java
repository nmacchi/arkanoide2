/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package triggers;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author nicolas
 */
public class Trigger implements TriggerInterface {

    private boolean enabled = true; //Por defecto
    private boolean running;
    private float time;
    private float lastTime;
    private float maxTime;
    private HashMap<Float, TimerEvent> timerEvents = new HashMap<Float, TimerEvent>();

    public interface TimerEvent {

        public Object[] call();
    }

    public void update(float tpf) {
        
        if (enabled == true && running == true) {
     
            time += tpf;
            
            Iterator<Float> it = timerEvents.keySet().iterator();
            while (it.hasNext()) {
                
                float t = it.next();
            
                if (t > lastTime && t <= time) {
                    
                    TimerEvent event = timerEvents.get(t);
                    if (event != null) {
                        event.call();
                    }
                } else if (t < lastTime) {
                    continue;
                }
                
                
            }
            
            if(time > maxTime){
                System.out.println("entra una y otra y otra vez");
                running = false;
                enabled = false;
            }
            
            lastTime = time;
        }else{
            trigger();
        }



    }

    public void trigger() {   
        if (enabled) {
            onTrigger();
        }
    }

    public void onTrigger() { 
        time = 0;
        running = true;
    }

    public void addTimerEvent(Float time, TimerEvent callback) {
        timerEvents.put(time, callback);
        if (time > maxTime) {
            maxTime = time;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isRunning() {
        return running;
    }
    
    
}
