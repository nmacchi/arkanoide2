package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import states.GameGuiAppState;
import states.GamePlayAppState;
import states.InputAppState;
import states.PlayerState;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication /*implements PhysicsCollisionListener*/ {
    
    
    private Node bricks;
    private Node gamefield;
    private Node powerupsNode;
    ParticleEmitter smoke;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        bricks = new Node("BricksNode");
        gamefield = new Node("Gamefield");
        powerupsNode = new Node("PowerupsNode");
        rootNode.attachChild(bricks);
        rootNode.attachChild(gamefield);
        rootNode.attachChild(powerupsNode);
       
        initScene();
        makeWall();
        
        GamePlayAppState initState = new GamePlayAppState();
        stateManager.attach(initState);
        
        GameGuiAppState guiState = new GameGuiAppState();
        stateManager.attach(guiState);
        
       
    }

    private void makeWall() {
        float initialX = -0.60f;
        Vector3f position = new Vector3f(initialX, 0.60f, 1f);
        Brick brick = null;
        //rows
        for (int i = 0; i <= 5; i++) {
            
            //bricks per line
            for (int j = 0; j <= 7; j++) {
                if(i == 5){
                    brick = new MetallicBrick(assetManager, position, stateManager);
                }else{
                    brick = new CommonBrick(assetManager, position, i, stateManager);
                }
                
                bricks.attachChild(brick);

                //Calculate next position
                position.setX(brick.getLocalTranslation().getX() + Brick.getWidth() * 2 + 0.02f);
            }
            position.setX(initialX);
            position.setY(position.getY() + Brick.getHeight() * 2 + 0.04f);
        }
        
        
        
        Brick.selectSpecialBrick(assetManager, bricks);
    }

    private void initScene() {
        //Floor
        Box floor = new Box(2.0f, 0.01f, 2.0f);
        Geometry geomFloor = new Geometry("Floor", floor);
        geomFloor.setLocalTranslation(0.0f, -0.1f, 0.0f);
        Material matFloor = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matFloor.setColor("Color", new ColorRGBA(23f / 255f, 207f / 255f, 246f / 255f, 1f));

        geomFloor.setMaterial(matFloor);
        geomFloor.setCullHint(CullHint.Always);
        gamefield.attachChild(geomFloor);

        //Bars
        Material matBars = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBars.setColor("Color", new ColorRGBA(198f / 255f, 9f / 255f, 26f / 255f, 1f));

        Box leftBar = new Box(0.025f, 0.60f, 0.025f);
        Geometry geomLeftBar = new Geometry("LeftBar", leftBar);
        geomLeftBar.setLocalTranslation(new Vector3f(-0.75f, 0.60f, 1f));

        geomLeftBar.setMaterial(matBars);

        gamefield.attachChild(geomLeftBar);

        Box rightBar = new Box(0.025f, 0.60f, 0.025f);
        Geometry geomRightBar = new Geometry("RightBar", rightBar);
        geomRightBar.setLocalTranslation(new Vector3f(0.75f, 0.60f, 1f));

        geomRightBar.setMaterial(matBars);
        gamefield.attachChild(geomRightBar);

        Box topBar = new Box(0.85f, 0.025f, 0.025f);
        Geometry geomTopBar = new Geometry("TopBar", topBar);
        geomTopBar.setLocalTranslation(new Vector3f(0.0f, 1.2f, 1f));

        geomTopBar.setMaterial(matBars);
        gamefield.attachChild(geomTopBar);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

}
