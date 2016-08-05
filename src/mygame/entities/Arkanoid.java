/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.WireBox;
import states.GamePlayAppState;

/**
 *
 * @author nicolas
 */
public class Arkanoid extends BreakerBar{
    
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.03f, 1f);
    
//    protected boolean ballShooted;
    
    public Arkanoid(){}
    
    public Arkanoid(AssetManager assetManager){
        super(assetManager);
        createArkanoid();
        this.width = calculateGeometryWidth();
    }
    
    private void createArkanoid(){
        Geometry geometry = (Geometry)((Node)assetManager.loadModel("Models/arkanoide/Arkanoide.j3o")).getChild(0);
        
        //setName("BreakerBar");
        setMesh(geometry.getMesh());
        setMaterial(geometry.getMaterial());
        getMesh().setBound(new BoundingBox());
        
        setLocalTranslation(initialPosition);
        scale(0.095f, 0.07f, 0.045f);
        rotate(0f,1.60f,0f);
    }

    public static Vector3f getInitialPosition() {
        return initialPosition;
    }


    public void transformToSpaceship(AppStateManager stateManager, Node parentNode, Vector3f position){
        parentNode.detachAllChildren();
        
        Spaceship spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
        spaceship.setLocalTranslation(position);
        parentNode.attachChild(spaceship);
        spaceship.addFlammingFX(parentNode);
    }
    
    
 
}
