/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResult;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import customcontrols.BreakerControl;
import effects.VisualEffects;
import java.util.Random;

/**
 *
 * @author nicolas
 */
public class Breaker extends Node {
    
    private static float initialSpeed = 0.5f;
    private static float reducedSpeed = 0.25f;
    private static final float maxSpeed = 1.5f;
    
    private float speed;
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.04f, 1f);
    private Vector3f direction;
    private static int MIN_ANGLE = -5;
    private static int MAX_ANGLE = 5;
    Random rdn = new Random();
    
    private int hits;
    
    //private Node localNode;
    private Geometry geometry;
    private Boolean fireballActivated = false;
    private Boolean slowerActivated = false;
    
    public Breaker(AssetManager assetManager) {
       // super("Breaker", new Sphere(8, 8, 0.022f, true, false));
       super("Breaker");
        
        createBallMaterial(assetManager);
        
        this.speed = initialSpeed;
        setLocalTranslation(initialPosition);

    }
    
    public Breaker(AssetManager assetManager, Vector3f position, float speed, Vector3f direction){
        //super("Breaker", new Sphere(8, 8, 0.022f, true, false));
        super("Breaker");
                
        createBallMaterial(assetManager);
        
        this.speed = speed;
        this.direction = direction;
        setLocalTranslation(position);
        
    }
    
    private void createBallMaterial(AssetManager assetManager){
        geometry = new Geometry("Ball", new Sphere(8, 8, 0.022f, true, false));
        
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture("Textures/breaker.jpg"));
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);
        
        geometry.setMaterial(material);
        
        attachChild(geometry);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public  float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
    public static float getReducedSpeed(){
        return reducedSpeed;
    }
    
    public static float getInitialSpeed(){
        return initialSpeed;
    }
    
    public void resetBall(BreakerBar breakerBar) {
    }

    public static Vector3f getInitialPosition() {
        return initialPosition;
    }

    public void setInitialDirection() {
        this.direction = new Vector3f(getLocalTranslation().x + 1, getLocalTranslation().y + 1, 0);
    }
    
    public void decreaseSpeed(){
        this.speed = reducedSpeed;
    }
    
    public void changeDirection(CollisionResult collision, String zone) {

        if (zone.equals("Left")) {
            setDirection(rotateVector(this.getDirection().negateLocal(), -15));
        }else if (zone.equals("Right")){
            setDirection(rotateVector(this.getDirection().negateLocal(), 15));
        }else if (zone.equals("Center")) {
            setDirection(Vector3f.UNIT_Y);
        } else {
            setDirection(rotateVector(new Vector3f(getDirection().x, getDirection().y * -1, getDirection().z), getRandomAngle()));
        }
    }

    private Vector3f rotateVector(Vector3f v, int angle) {
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis(FastMath.PI * angle / 180, Vector3f.UNIT_Z);
        quat.mult(v, v);
        
        return v;
    }

    private int getRandomAngle() {
        int angle = rdn.nextInt(MAX_ANGLE - MIN_ANGLE + 1) + MIN_ANGLE;
        System.out.println("Angulo rotado: " +  FastMath.PI * (FastMath.DEG_TO_RAD * angle) / 180);
        return angle;
    }

    public Vector3f reflectVector(CollisionResult collision) {            
        Vector3f v;
        
        if(collision.getGeometry() != null){
            Triangle tri = new Triangle();
            collision.getGeometry().getMesh().getTriangle(collision.getTriangleIndex(), tri);

            Vector3f normal = tri.getNormal().normalizeLocal();
            v = direction.negate().add(normal.mult(normal.dot(direction.negate())).add(direction).mult(2));
        }else{
            v = direction.setY(getDirection().getY() * -1);
        }
        

        return v.normalizeLocal();
    }
    
    public void countHit(){
        if(speed < maxSpeed && (!fireballActivated
                || !slowerActivated)){
            hits++;
  
            if(hits % 3 == 0){
                speed += 0.07f;
            }   
        }
    }
    
    public void executeExplosionEffect(Vector3f position){
        VisualEffects.getBallExplosionEffect(position);
    }
    
    
    public void release(AppStateManager stateManager){
        //Set initial position and direction 
        this.setLocalTranslation(this.getWorldTranslation());
        this.setInitialDirection();
        
        //Attach the entity to the rootNode
        Node rootNode = this.getParent().getParent();
        ((Node)rootNode.getChild("BreakerNode")).attachChild(this);
        this.addControl(new BreakerControl(rootNode, stateManager));
    }
    
    
    public void changeToFireBall(){
        fireballActivated = true;
        
        Geometry ball = (Geometry)this.getChild("Ball");
        
        ball.getMaterial().setColor("Diffuse", ColorRGBA.Yellow);
        ball.getMaterial().setColor("Ambient", ColorRGBA.Orange);

        VisualEffects.getFlammingBall(ball.getLocalTranslation(), this);
    }
    
    public void deactivateFireBall(){
        Geometry ball = (Geometry)this.getChild("Ball");
        
        ball.getMaterial().setColor("Diffuse", ColorRGBA.White);
        ball.getMaterial().setColor("Ambient", ColorRGBA.White);
        
        this.detachChildNamed("Fire");
    }

    public Boolean isFireballActivated() {
        return fireballActivated;
    }

    public void setFireballActivated(Boolean fireballActivated) {
        this.fireballActivated = fireballActivated;
    }

    public Boolean isSlowerActivated() {
        return slowerActivated;
    }

    public void setSlowerActivated(Boolean slowerActivated) {
        this.slowerActivated = slowerActivated;
    }
    
    public void playFireballAudio(){
        ((AudioNode)this.getParent().getParent().getChild("fireballAudio")).play();
    }
    
    public void stopFireballAudio(){
        ((AudioNode)this.getParent().getParent().getChild("fireballAudio")).stop();
    }
}
