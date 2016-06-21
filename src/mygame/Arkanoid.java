/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public class Arkanoid extends BreakerBar{
    
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.03f, 1f);
    private Vector3f direction = new Vector3f().set(Vector3f.UNIT_X);
    
    public Arkanoid(){}
    
    public Arkanoid(AssetManager assetManager){
        super(assetManager);
        createArkanoid();
    }
    
    private void createArkanoid(){
        Geometry geometry = (Geometry)((Node)assetManager.loadModel("Models/arkanoide/Arkanoide.j3o")).getChild(0);
        
        setName("BreakerBar");
        setMesh(geometry.getMesh());
        setMaterial(geometry.getMaterial());
        
        setLocalTranslation(initialPosition);
        scale(0.06f, 0.07f, 0.045f);
        rotate(0f,1.60f,0f);
    }

    public static Vector3f getInitialPosition() {
        return initialPosition;
    }

    public Vector3f getDirection() {
        return direction;
    }
    
    
    
}
