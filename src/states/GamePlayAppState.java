/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import effects.ScriptAppState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FadeFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import customcontrols.BreakerControl;
import effects.VisualEffects;
import factories.BreakerBarFactory;
import levels.LevelManager;
import mygame.commons.BreakerBarTypes;
import mygame.commons.CommonTextures;
import mygame.entities.Breaker;

/**
 * Init principal entities, audio effects and key inputs
 *
 * @author nicolas
 */
public class GamePlayAppState extends AbstractAppState {

    private boolean gameStarted = false;
    private boolean gameRunning = false;
    private boolean gameFinished = true;
    private boolean gameStop = false;
    private boolean sceneLoaded = false; 
    private boolean levelLoaded;
    
    private Node breakerBarNode = new Node("BreakerBarNode");
    private Node breakerNode = new Node("BreakerNode");
    private Node powerupsNode = new Node("PowerupsNode");
    private Node gamefield = new Node("Gamefield");
    private Node bricksNode = new Node("BricksNode");
    
    AppStateManager stateManager;
    SimpleApplication app;
    AssetManager assetManager;
    InputAppState inputState;
    PlayerState playerState;
    LostLifeState lostLifeState;
    BreakerBarFactory breakerBarCreator;
    
    FadeFilter fadeFilter;
    
    
    LevelManager levelManager;
    
    private static final Vector3f CAM_LOCATION = new Vector3f(-0.005f, 0.52f, 3.19f);

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        //setEnabled(false);
        
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.assetManager = ((SimpleApplication) app).getAssetManager();
        this.breakerBarCreator = new BreakerBarFactory();

        levelManager = new LevelManager(assetManager, stateManager, bricksNode);
        LevelManager.loadLevels(); //Mover al inicio
        
        this.app.getRootNode().attachChild(breakerBarNode);
        this.app.getRootNode().attachChild(breakerNode);
        this.app.getRootNode().attachChild(powerupsNode);
        this.app.getRootNode().attachChild(gamefield);
        this.app.getRootNode().attachChild(bricksNode);
        
        
        //this.app.getViewPort().setEnabled(false);
        
        //initScene();

        //initMainEntities();
        
        //Initialize main states
        lostLifeState = new LostLifeState();
        playerState = new PlayerState();
        inputState = new InputAppState();
        //changeLevelState  = new ChangeLevelState(new LevelManager(assetManager, stateManager, bricksNode));
        
        stateManager.attachAll(playerState, lostLifeState);
       
        //loadBackgroundImage();
        
        //configureCameraSettings();
        
        //initSceneLights();
        
        //initLevels();
        
        
        
    }
    
    
    
    private void loadScene(){
        configureCameraSettings();
        initSceneLights();
        initScene();
        //initMainEntities();
        loadBackgroundImage();
    }
    
    

    private void initMainEntities() {
        breakerBarCreator.createrBar(BreakerBarTypes.ARKANOID, breakerBarNode, app, null);
        breakerBarNode.attachChild(new Breaker(assetManager));
   }

    private void configureCameraSettings() {
        app.getFlyByCamera().setEnabled(false);
        app.getCamera().setLocation(CAM_LOCATION);
    }


    private void initSceneLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));

        sun.setDirection(app.getCamera().getDirection().normalizeLocal());
        app.getRootNode().addLight(sun);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White);
        app.getRootNode().addLight(al);
    }

    public void reset() {
        //playerState.restLife();

        initMainEntities();
        VisualEffects.getChangeEffect(((Geometry) breakerBarNode.getChild(0)).getWorldTranslation());

        stateManager.attach(inputState);
        this.setGameStop(Boolean.FALSE);

    }

    
    public SimpleApplication getApp() {
        return app;
    }

    @Override
    public void update(float tpf) {
        
        if(!isGameStarted() && isGameFinished()){
            System.out.println("FADE OUT");
            //fadeFilter.fadeOut();
            
            gameFinished = Boolean.FALSE;
            
            levelLoaded = false;
        }
        
        if(isGameStarted() && !levelLoaded){
            System.out.println("CARGANDO NIVEL");
            
            //Do this only for the first level
            if(!sceneLoaded){
                System.out.println("CARGA ESCENA");
                loadScene();
                sceneLoaded = true;
               
            }
            
            reset();
            
            levelManager.nextLevel();
            
            
            
            levelLoaded = true;
            System.out.println("FADE IN");
            //fadeFilter.fadeIn();
        }
        
        
        //Exceute state when player lose a life
        if (isGameStop() && !lostLifeState.isEnabled()) {
            lostLifeState.setEnabled(Boolean.TRUE);
        }

        //Check if all bricks were removed
        //TODO: Cuando no haya mas ladrillos pasar al proximo nivel
        if (bricksNode.getChildren().isEmpty() && isGameRunning()) {
            //stateManager.detach(stateManager.getState(InputAppState.class));
            gameStarted = Boolean.FALSE;
            gameRunning = Boolean.FALSE;
            gameFinished = Boolean.TRUE;
            
            stateManager.getState(GameGuiAppState.class).fadeInScene();
            
            //levelManager.nextLevel();
        }

        //Game is finished when player lose all his lifes
        //TODO: Permitir cntinuar o salir (implementar un menu)
        if (isGameFinished()) {
            stateManager.detach(stateManager.getState(InputAppState.class));
            
        }
    }
    
    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
    
    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
    
    public boolean isGameStop() {
        return gameStop;
    }

    public void setGameStop(boolean gameStop) {
        this.gameStop = gameStop;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public void addExtraBalls() {
        Node node = (Node) app.getRootNode().getChild("BreakerNode");
        Breaker breaker = (Breaker) app.getRootNode().getChild("Breaker");

        for (int i = 0; i < 2; i++) {
            Quaternion quat = new Quaternion();
            Vector3f direction = new Vector3f();

            int rotationDegree;
            if (node.getChildren().size() < 1) {
                rotationDegree = 10;
            } else {
                rotationDegree = -10;
            }

            quat.fromAngleAxis(FastMath.PI * rotationDegree / 180, Vector3f.UNIT_Z);
            quat.mult(breaker.getDirection(), direction);

            Breaker extraBall = new Breaker(stateManager.getApplication().getAssetManager(), breaker.getLocalTranslation(), breaker.getSpeed(), direction);
            extraBall.addControl(new BreakerControl(app.getRootNode(), stateManager));
            node.attachChild(extraBall);
        }
    }

    public void removeExtraballsFromScene() {
        int i = 0;
        while (breakerNode.getChildren().size() > 1) {
            Breaker extraBall = (Breaker) breakerNode.getChild(i);
            extraBall.executeExplosionEffect(extraBall.getWorldTranslation());
            extraBall.removeFromParent();

            i++;
        }

    }

    private void initScene() {
        //Floor
        Box floor = new Box(2.0f, 0.01f, 2.0f);
        Geometry geomFloor = new Geometry("Floor", floor);
        geomFloor.setLocalTranslation(0.0f, -0.12f, 0.0f);
        Material matFloor = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matFloor.setColor("Color", new ColorRGBA(23f / 255f, 207f / 255f, 246f / 255f, 1f));

        geomFloor.setMaterial(matFloor);
        geomFloor.setCullHint(Spatial.CullHint.Always);
        gamefield.attachChild(geomFloor);

        //Bars
        Material matBars = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matBars.setColor("Color", new ColorRGBA(198f / 255f, 9f / 255f, 26f / 255f, 1f));

        Box leftBar = new Box(0.025f, 0.66f, 0.025f);
        Geometry geomLeftBar = new Geometry("LeftBar", leftBar);
        geomLeftBar.setLocalTranslation(new Vector3f(-0.75f, 0.64f, 1f));

        geomLeftBar.setMaterial(matBars);

        gamefield.attachChild(geomLeftBar);

        Box rightBar = new Box(0.025f, 0.66f, 0.025f);
        Geometry geomRightBar = new Geometry("RightBar", rightBar);
        geomRightBar.setLocalTranslation(new Vector3f(0.80f, 0.64f, 1f));

        geomRightBar.setMaterial(matBars);
        gamefield.attachChild(geomRightBar);

        Box topBar = new Box(0.85f, 0.025f, 0.025f);
        Geometry geomTopBar = new Geometry("TopBar", topBar);
        geomTopBar.setLocalTranslation(new Vector3f(0.0f, 1.33f, 1f));

        geomTopBar.setMaterial(matBars);
        gamefield.attachChild(geomTopBar);
    }
    
    
    private void loadBackgroundImage(){
        Picture backgroundImage = new Picture("background");
        backgroundImage.setTexture(assetManager, (Texture2D)CommonTextures.BACKGROUND, false);
        backgroundImage.setWidth(app.getContext().getSettings().getWidth());
        backgroundImage.setHeight(app.getContext().getSettings().getHeight());
        backgroundImage.setPosition(0, 0);
        
        ViewPort pv = app.getRenderManager().createPreView("background", app.getCamera());
        pv.setClearFlags(true, true, true);
        pv.attachScene(backgroundImage);
        
        app.getViewPort().setClearFlags(false, true, true);
        
        backgroundImage.updateGeometricState();
    }
    
    /*private void initLevels(){
        levelManager = new LevelManager(assetManager, stateManager, bricksNode);
        levelManager.loadLevels();
        levelManager.initLevel(); //Get First level
    }*/
}
