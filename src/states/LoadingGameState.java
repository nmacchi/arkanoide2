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
import com.jme3.scene.Node;
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
   
    //Elements to be loaded
    private CommonModels models;
    private CommonTextures textures; 
    private VisualEffects visualFX; 
    private AudioEffects audioFX;
    
    private boolean load = false;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
    private Future loadFuture = null;
    
    SimpleApplication app;
    AppStateManager stateManager;
    AssetManager assetManager;

    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = ((SimpleApplication)app);
        this.assetManager = this.app.getAssetManager();
        this.stateManager = stateManager;
      
        load = true;
        
        stateManager.getState(GameGuiAppState.class).initProgressBar();
    }
    
    @Override
    public void update(float tpf) {
        if(load){
            if(loadFuture == null){
                loadFuture = executor.submit(callable);
                
            }
            
            if(loadFuture.isDone()){
                audioFX.loadAudioToSceneGraph(); //Attach audios to rootNode once loading is finished
                stopThreadExecution(); //Stop loading thread
                
                stateManager.getState(GameGuiAppState.class).detachChildrenFromPanel(); //Remove progressBar from GUI
                
                setEnabled(false); //Disable state
                
                load = false;
            }
            
        }
        
    }
    
    
    Callable<Void> callable = new Callable<Void>(){
        
        public Void call() throws Exception {
            
            models = new CommonModels(assetManager);    
            updateProgressBar(15, "Cargando Modelos...");
            models.loadModels();
            Thread.sleep(500);
            
            textures = new CommonTextures(assetManager);        
            updateProgressBar(30, "Cargando Texturas...");
            textures.loadTextures();
            Thread.sleep(500);
            
            visualFX = new VisualEffects();
            updateProgressBar(45, "Cargando Efectos...");
            visualFX.initVisualEffect(assetManager, app.getRootNode());
            Thread.sleep(500);
            
            audioFX = new AudioEffects(assetManager, app.getRootNode());
            updateProgressBar(60, "Cargando Sonidos...");
            audioFX.loadAudioFXs();
            Thread.sleep(500);
            //
            updateProgressBar(100, "Completo");
            Thread.sleep(500);
            
            return null;
            
        }
        
    };
    
    
    public void updateProgressBar(final double progress, final String progressMessage){
        app.enqueue(new Callable(){
            public Object call() throws Exception {
                
                stateManager.getState(GameGuiAppState.class).setProgressBarValues(progress, progressMessage);

                return null;
            }
            
        });
    }
    
    /**
     *
     */
    public void stopThreadExecution() {
        //the pool executor needs to be shut down so the application properly exits.
        executor.shutdown();
    }

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }
    
    
}
