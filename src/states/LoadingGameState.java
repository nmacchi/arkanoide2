/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.ProgressBar;
import effects.AudioEffects;
import effects.VisualEffects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import mygame.commons.CommonModels;
import mygame.commons.CommonTextures;

/**
 *
 * @author nicolas
 */
public class LoadingGameState extends AbstractAppState {
    
    //private static final String PROGRESS_BAR_MESSAGE = "Loading...";
    
    //Elements to be loaded
    private CommonModels models;
    private CommonTextures textures; 
    private VisualEffects visualFX; 
    private AudioEffects audioFX;
    
    private boolean load = false;
    private ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);
    private Future loadFuture = null;
    
    SimpleApplication app;
    AssetManager assetManager;
    Node rootNode;
    Node guiNode;
    ProgressBar progressBar;
    
    
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = ((SimpleApplication)app);
        this.assetManager = this.app.getAssetManager();
        this.rootNode = this.app.getRootNode();
        this.guiNode = this.app.getGuiNode();
        
        
        initProgressBar();
    }
    
    private void initProgressBar(){
        Container panel = new Container();
        progressBar = new ProgressBar();
        progressBar.setPreferredSize(new Vector3f(300,20,1));
        //progressBar.setMessage(PROGRESS_BAR_MESSAGE);

        panel.addChild(progressBar);
        
        guiNode.attachChild(panel);
        panel.setLocalTranslation(this.app.getContext().getSettings().getWidth()/2 - progressBar.getPreferredSize().x / 2, this.app.getContext().getSettings().getHeight()/2,0);
        
    }
    
    public void update(float tpf) {
    }
    
    
    Callable<Void> callable = new Callable<Void>(){
        
        public Void call() throws Exception {
            
            models.loadModels();
            updateProgressBar(15, "Cargando Modelos...");
            
            textures.loadTextures();
            updateProgressBar(30, "Cargando Texturas...");
            
            visualFX.initVisualEffect(assetManager, rootNode);
            updateProgressBar(45, "Cargando Efectos...");
            
            audioFX.loadAudioFXs();
            updateProgressBar(60, "Cargando Sonidos...");
            
            
            return null;
            
        }
        
    };
    
    
    public void updateProgressBar(final double progress, final String progressMessage){
        app.enqueue(new Callable(){
            public Object call() throws Exception {
                
            }
            
        });
    }
    
}
