/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.anim.AnimationState;
import com.simsilica.lemur.anim.SpatialTweens;
import com.simsilica.lemur.anim.Tween;
import com.simsilica.lemur.anim.Tweens;
import effects.VisualEffects;
import mygame.commons.CommonModels;

/**
 *
 * @author nicolas
 */
public class Arkanoid extends BreakerBar{
    
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.01f, 1f);
    
//    protected boolean ballShooted;
    
    public Arkanoid(){}
    
    public Arkanoid(AssetManager assetManager, Node parent){
        super(assetManager);
        createArkanoid(parent);
        setLocalTranslation(initialPosition);
    }
    
    public Arkanoid(AssetManager assetManager, Vector3f position, Node parent){
        super(assetManager);
        createArkanoid(parent);
        
        if(position != null){
            setLocalTranslation(position);    
        }else{
            setLocalTranslation(initialPosition);
        }
        
    }
    
    private void createArkanoid(Node parent){
        Spatial model = CommonModels.ARKANOID; 
        Geometry geometry = (Geometry)((Node)model).getChild(0);
        
        setMesh(geometry.getMesh());
        setMaterial(geometry.getMaterial());
        
        scale(0.095f, 0.07f, 0.045f);
        rotate(0f,1.60f,0f);
        
        this.width = calculateGeometryWidth();
        
        parent.attachChild(this);
        setParent(parent);
    }

    public static Vector3f getInitialPosition() {
        return initialPosition;
    }


//    public void transformToSpaceship(AppStateManager stateManager, Node parentNode, Vector3f position){
//        parentNode.detachAllChildren();
//        
//        Spaceship spaceship = stateManager.getState(GamePlayAppState.class).getSpaceship();
//        spaceship.addControl(new BreakerBarControl(parentNode.getParent(), stateManager));
//        
//        spaceship.setLocalTranslation(position);
//        parentNode.attachChild(spaceship);
//        spaceship.addFlammingFX(parentNode);
//    }
    
    
    public void expand(AppStateManager stateManager){
        VisualEffects.getChangeEffect(getWorldTranslation());
        Tween scaleTransform = SpatialTweens.scale(this, new Vector3f(0.095f,0.07f,0.045f), new Vector3f(0.095f,0.07f,0.065f), 0.5);
        
        Tween expandAnimation = Tweens.smoothStep(scaleTransform);
        stateManager.getState(AnimationState.class).add(expandAnimation);
    }
    
    
    public void contract(AppStateManager stateManager){
        Tween scaleTransform = SpatialTweens.scale(this, new Vector3f(0.095f,0.07f,0.065f), new Vector3f(0.095f,0.07f,0.045f), 0.5);
        
        Tween contractAnimation = Tweens.smoothStep(scaleTransform);
        stateManager.getState(AnimationState.class).add(contractAnimation);
    }
 
}
