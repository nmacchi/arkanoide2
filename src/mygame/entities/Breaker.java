/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entities;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResult;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import java.util.Random;

/**
 *
 * @author nicolas
 */
public class Breaker extends Geometry {

    private static float speed = 0.5f;
    private static Vector3f initialPosition = new Vector3f(0.25f, 0.08f, 1f);
    private Vector3f direction;
    private static int MIN_ANGLE = -5;
    private static int MAX_ANGLE = 5;
    Random rdn = new Random();

    public Breaker(AssetManager assetManager) {
        super("Breaker", new Sphere(8, 8, 0.022f, true, false));

        material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture("Textures/breaker.jpg"));

        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 32f);

        setLocalTranslation(initialPosition);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float speed) {
        Breaker.speed = speed;
    }

    public void resetBall(BreakerBar breakerBar) {
    }

    public static Vector3f getInitialPosition() {
        return initialPosition;
    }

    public void setInitialDirection() {
        this.direction = new Vector3f(getLocalTranslation().x + 1, getLocalTranslation().y + 1, 0);
    }

    public void changeDirection(CollisionResult collision, String zone) {

        if (zone.equals("Left") || zone.equals("Right")) {
            setDirection(rotateVector(this.getDirection().negateLocal()));
        } else if (zone.equals("Center")) {
            setDirection(Vector3f.UNIT_Y);
        } else {
            setDirection(reflectVector(collision));
        }
    }

    private Vector3f rotateVector(Vector3f v) {
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis(FastMath.PI * 10 / 180, Vector3f.UNIT_Z);
        quat.mult(v, v);
        
        return v;
    }

    private int getRandomAngle() {
        int angle = rdn.nextInt(MAX_ANGLE - MIN_ANGLE + 1) + MIN_ANGLE;
        System.out.println("Angulo rotado: " +  FastMath.PI * (FastMath.DEG_TO_RAD * angle) / 180);
        return angle;
    }

    public Vector3f reflectVector(CollisionResult collision) {
        Triangle tri = new Triangle();
        collision.getGeometry().getMesh().getTriangle(collision.getTriangleIndex(), tri);

        Vector3f normal = tri.getNormal().normalizeLocal();
        Vector3f v = direction.negate().add(normal.mult(normal.dot(direction.negate())).add(direction).mult(2));

        return v.normalizeLocal();
    }
}
