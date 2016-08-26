/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package triggers;

/**
 *
 * @author nicolas
 */
public interface TriggerInterface {
    
    public void update(float tpf);
    public void trigger();
    public void onTrigger();
    
}
