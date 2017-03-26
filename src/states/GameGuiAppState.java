/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.ProgressBar;
import com.simsilica.lemur.anim.Animation;
import com.simsilica.lemur.anim.PanelTweens;
import com.simsilica.lemur.anim.SpatialTweens;
import com.simsilica.lemur.anim.Tween;
import com.simsilica.lemur.anim.TweenAnimation;
import com.simsilica.lemur.anim.Tweens;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.effect.AbstractEffect;
import com.simsilica.lemur.effect.Effect;
import com.simsilica.lemur.effect.EffectInfo;
import com.simsilica.lemur.style.BaseStyles;
import static jdk.nashorn.internal.objects.NativeRegExpExecResult.length;
import levels.LevelManager;
import mygame.commons.CommonModels;

/**
 *
 * @author nicolas
 */
public class GameGuiAppState extends AbstractAppState {
    
    private Boolean isPlayerStateLoaded = false;
    private AppStateManager stateManager;
    private SimpleApplication app;
//    private static int INITIAL_POSITION_LOCAL_NODE = 100;
    
    private BitmapText stateIndicator;
    private BitmapText scoreIndicator;
    private ProgressBar progressBar;
    private Node guiNode;
    
    private Node localNode = new Node ("Lives Node");

    
    
    private float timer = 0f; 
    Boolean firstAnimationElapsed = false;
    Boolean secondAnimationElapsed = false;

    
    private Container panel;
    //private Label label;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.stateManager = stateManager;
        this.guiNode = ((SimpleApplication)app).getGuiNode();
        this.app = (SimpleApplication)app;
        
        //Initialize Lemur GUI API
        GuiGlobals.initialize(this.app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        
        //Main container
        panel = new Container();
        panel.setName("mainContainer");
        
        guiNode.attachChild(panel);
        
        
        createBlackoutPanel();
        //createLevelMessage();
    }

    @Override
    public void setEnabled(boolean active) {
        
    }

    @Override
    public void cleanup() {
        
    }
    
    private void initializeStateIndicator(Application app){
        stateIndicator = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false); 
        
        stateIndicator.setSize(25f);      // font size
        stateIndicator.setColor(ColorRGBA.Blue);                             // font color
        stateIndicator.setLocalTranslation(300, app.getContext().getSettings().getHeight() / 2, 0); // position
        
        guiNode.attachChild(stateIndicator);
    }
    
    private void initializeScoreIndicator(Application app, AppStateManager stateManager){
        scoreIndicator = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false); 
        
        scoreIndicator.setName("Score Indicator");
        scoreIndicator.setSize(18f);      // font size
        scoreIndicator.setColor(ColorRGBA.White);
        scoreIndicator.setLocalTranslation(app.getContext().getSettings().getWidth()/2 - 50, app.getContext().getSettings().getHeight(), 0); // position
        scoreIndicator.setText(stateManager.getState(PlayerState.class).getFormattedScore());
    
        guiNode.attachChild(scoreIndicator);
    }
    
    public void updateLivesIndicator(Application app, int lives){
        localNode.detachAllChildren();
        
        int xPositionForLives = 100;

        for(int x=0; x < lives -1; x++){
            
            Spatial arkanoid = CommonModels.ARKANOID.clone();
            arkanoid.scale(20f);
            arkanoid.rotate(-0.5f,2.5f,0f);
    
            arkanoid.setLocalTranslation(app.getContext().getSettings().getWidth() - xPositionForLives,50, 0);
            
            localNode.attachChild(arkanoid);
         
            xPositionForLives += 50;
        }
        
        
        
    }
    
    private void initGUILights(Application app){
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));
        
        sun.setDirection(new Vector3f(-1f,0f,0f));
        ((SimpleApplication)app).getGuiNode().addLight(sun);
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White);
        ((SimpleApplication)app).getGuiNode().addLight(al);
    }

    public BitmapText getStateIndicator() {
        return stateIndicator;
    }

    public BitmapText getScoreIndicator() {
        return scoreIndicator;
    }
    
    public void updateScoreIndicator(){
        scoreIndicator.setText(stateManager.getState(PlayerState.class).getFormattedScore());
    }
    
    /**
     * Progress Bar
     */
    public void initProgressBar(){ 
        progressBar = new ProgressBar();
        progressBar.setPreferredSize(new Vector3f(300,20,1));
        //progressBar.setMessage(PROGRESS_BAR_MESSAGE);

        panel.addChild(progressBar);
        
        
        panel.setLocalTranslation(app.getContext().getSettings().getWidth()/2 - progressBar.getPreferredSize().x / 2, app.getContext().getSettings().getHeight()/2,0);
        
    }
    
    public void setProgressBarValues(Double progress, String message){
        progressBar.setMessage(message);
        progressBar.setProgressValue(progress);
    }
    
    private void initGameIndicators(){
        guiNode.attachChild(localNode);
        
        initGUILights(app);
        
        initializeStateIndicator(app);
        initializeScoreIndicator(app, stateManager);
        updateLivesIndicator(app, stateManager.getState(PlayerState.class).getCurrentLives());
    }
    
    @Override
    public void update(float tpf){
        //Init game indicators when playerstate is initialize 
        if(stateManager.getState(PlayerState.class) != null && stateManager.getState(PlayerState.class).isInitialized() &&  !isPlayerStateLoaded){
           
            initGameIndicators();
            isPlayerStateLoaded = true;
        }
        
        
        if(stateManager.getState(GamePlayAppState.class) != null && !stateManager.getState(GamePlayAppState.class).isGameStarted()){
            timer += tpf;
            
            if(!firstAnimationElapsed){
                createLevelMessage();
                firstAnimationElapsed = true;
                
                ((Label)guiNode.getChild("myLabel")).runEffect("slideIn");
            
            }
        
            if(timer > 5 && firstAnimationElapsed && !secondAnimationElapsed){
                secondAnimationElapsed = true;
                ((Label)guiNode.getChild("myLabel")).runEffect("slideOut");
            }
            
            if(timer > 7){
                ((Label)guiNode.getChild("myLabel")).removeFromParent();
                stateManager.getState(GamePlayAppState.class).setGameStarted(Boolean.TRUE);
                
                fadeOutScene();
                
                
                timer = 0;
                firstAnimationElapsed = false;
                secondAnimationElapsed = false;        
                
            }
            
       }
        
        
        
        
    }
    
    public void detachChildrenFromPanel(){
        ((Node)guiNode.getChild("mainContainer")).detachAllChildren();
    }
    
    /**
     * Level Message
     */
    public void createLevelMessage(){     
        Label label = new Label("Level " + LevelManager.getCurrentLevel());
//        Label label = new Label("Level 1");
        label.setName("myLabel");
        label.setFont(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        label.setFontSize(25);
        label.setColor(ColorRGBA.Orange);
        
        label.addEffect("slideIn", labelIn);
        label.addEffect("slideOut", labelOut);
        
        guiNode.attachChild(label);
        System.out.println(label.getText());
    }

    /**
     * Fade effect over scene 
     */
    private void createBlackoutPanel(){       
        Container myPanel = new Container();
        myPanel.setName("blackoutPanel");
        myPanel.setPreferredSize(new Vector3f(app.getContext().getSettings().getWidth(), app.getContext().getSettings().getHeight(), 0));
        myPanel.setLocalTranslation(0,app.getContext().getSettings().getHeight(),0);
        
        QuadBackgroundComponent  quad = new QuadBackgroundComponent();
        quad.setColor(ColorRGBA.Black);
        quad.setAlpha(1f);
        quad.setZOffset(-1f);
        
        myPanel.setBackground(quad);
        
        myPanel.addEffect("fadeIn", fadeIn);
        myPanel.addEffect("fadeOut", fadeOut);
        
        guiNode.attachChild(myPanel);
    }
    
    public void fadeOutScene(){
        ((Container)guiNode.getChild("blackoutPanel")).runEffect("fadeOut");
    }
    
    /**
     * Turn off scene
     */
    public void fadeInScene(){
        ((Container)guiNode.getChild("blackoutPanel")).runEffect("fadeIn");
    }
    
    
    /**
     * Effects
     */
    
    Effect<Panel> labelIn = new AbstractEffect<Panel>("slideIn/slideOut") {
            @Override
            public Animation create( Panel target, EffectInfo existing ) {
                
                Vector3f startPosition;
                Vector3f endPosition;
                
                startPosition = new Vector3f(0,app.getContext().getSettings().getHeight()/2,0);
                endPosition = new Vector3f(app.getContext().getSettings().getWidth()/2 - 50, app.getContext().getSettings().getHeight()/2,0);
                
                
                Tween scale = SpatialTweens.scale(target, 0, 1, 2);
                Tween move = SpatialTweens.move(target, startPosition, endPosition, 2);
                return new TweenAnimation(Tweens.smoothStep(Tweens.parallel(move, scale)));
            }
        };
        
        
        
    Effect<Panel> labelOut = new AbstractEffect<Panel>("slideIn/slideOut") {
            @Override
            public Animation create( Panel target, EffectInfo existing ) {
                Vector3f startPosition;
                Vector3f endPosition;
                
                startPosition = new Vector3f(app.getContext().getSettings().getWidth()/2 - 50, app.getContext().getSettings().getHeight()/2,0);
                endPosition = new Vector3f(app.getContext().getSettings().getWidth(), app.getContext().getSettings().getHeight()/2, 0);
                
                Tween scale = SpatialTweens.scale(target, 1, 0, 1);
                Tween move = SpatialTweens.move(target, startPosition, endPosition, 1);
                return new TweenAnimation(Tweens.smoothStep(Tweens.parallel(move,scale)));
            }
        };
    
    
    
    Effect<Panel> fadeIn = new AbstractEffect<Panel>("fadeIn/fadeOut") {
        @Override
        public Animation create( Panel target, EffectInfo existing ) {
            Tween fade = PanelTweens.fade(target, 0f, 1f, 1);
            return new TweenAnimation(fade);
        }
    };
    
    Effect<Panel> fadeOut = new AbstractEffect<Panel>("fadeIn/fadeOut") {
        @Override
        public Animation create( Panel target, EffectInfo existing ) {
            Tween fade = PanelTweens.fade(target, 1f, 0f, 2);
            return new TweenAnimation(fade);
        }
    };
    
}
