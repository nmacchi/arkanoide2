/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package customcontrols;

import com.jme3.asset.AssetManager;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import mygame.MetallicBrick;

/**
 *
 * @author nicolas
 */
public class MetallicBrickLightingControl extends AbstractControl {

    private SpotLight spot;
    private Node lightNode;
    private Node parent;
    private MetallicBrick metallicBrick;
    private Vector3f direction;
    private boolean enabled;
    
    private AssetManager assetManager;
    private Geometry lightMdl;
    
    public MetallicBrickLightingControl(Node parent, AssetManager assetManager) {
        enabled = false; //By default

        /*
         Geometry lightMdl = new Geometry("Light", new Sphere(8, 8, 0.018f));
         lightMdl.setMaterial(assetManager.loadMaterial("Common/Materials/RedColor.j3m"));
         lightMdl.setLocalTranslation(spot.getPosition());
         lightMdl.setLocalScale(1);
         */
        lightMdl = new Geometry("Light", new Sphere(8, 8, 0.018f));
        
        
        direction = new Vector3f();
        direction.set(Vector3f.UNIT_X).normalizeLocal();

        lightNode = new Node("Light");
        spot = new SpotLight();
       
        this.parent = parent;
        this.assetManager = assetManager;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        System.out.println("Agrega spatial");
        this.metallicBrick = (MetallicBrick) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        System.out.println("enabled:  " + isEnabled() + "Spatial: " + metallicBrick);
        if (isEnabled() && metallicBrick != null) {
           
            direction.multLocal(-0.7f * tpf);

            //lightNode.setLocalTranslation(spot.getPosition().getX() + 0.01f, spot.getPosition().getY(), spot.getPosition().getZ());
//            spot.setPosition(direction);
            //spot.setPosition(new Vector3f(spot.getPosition().getX() + 0.01f, spot.getPosition().getY(), spot.getPosition().getZ()));
            //lightMdl.setLocalTranslation(spot.getPosition());
        }

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void createSpotLight(){

        spot.setSpotRange(10);
        spot.setSpotInnerAngle(15 * FastMath.DEG_TO_RAD);
        spot.setSpotOuterAngle(30 * FastMath.DEG_TO_RAD);
        spot.setPosition(new Vector3f(metallicBrick.getLocalTranslation().x , metallicBrick.getLocalTranslation().y, metallicBrick.getLocalTranslation().z + 0.1f));
//        spot.setDirection(metallicBrick.getWorldTranslation().subtract(spot.getPosition()));
        spot.setDirection(new Vector3f(spot.getPosition().x, spot.getPosition().y, spot.getPosition().z - 1));
        spot.setColor(ColorRGBA.White.mult(25));
        
        
        lightMdl.setMaterial(assetManager.loadMaterial("Common/Materials/RedColor.j3m"));
        lightMdl.setLocalTranslation(spot.getPosition());
        lightMdl.setLocalScale(1);
        
        Line line = new Line(spot.getPosition(), spot.getDirection());
        Geometry l1 = new Geometry("line", line);
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", ColorRGBA.Blue);
        l1.setMaterial(m);
        
        parent.getParent().addLight(spot);
        //parent.attachChild(lightNode); //attach lightNode to BricksNode
//        parent.getParent().attachChild(lightMdl);
        parent.getParent().attachChild(l1);
    }
}
