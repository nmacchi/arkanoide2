/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.commons;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;

/**
 *
 * @author nicolas
 */
public class CommonModels {
    
    private static AssetManager assetManager;
    
    public static Spatial ARKANOID;
    public static Spatial SPACESHIP;
    
    
    public CommonModels(){ }
    
    public CommonModels(AssetManager assetManager){
        CommonModels.assetManager = assetManager;
    }
    
     public void loadModels(){
         ARKANOID = assetManager.loadModel("Models/arkanoide/Arkanoide.j3o");
         SPACESHIP = assetManager.loadModel("Models/spaceship/spaceship_model.j3o");
     }
    
}
