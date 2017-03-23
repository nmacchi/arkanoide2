/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 *
 * @author nicolas
 */
public class AudioEffects {
    
    private AssetManager assetManager;
    private Node rootNode;
    private final Node audioNode;
    
    //Audio Effects Nodes
    private AudioNode simpleReboundAudio;
    private AudioNode metallicReboundAudio;
    private AudioNode arkanoidExplosionAudio;
    private AudioNode fireballAudio;
    private AudioNode laserShootAudio;
    private AudioNode brickExplosionAudio;
    private AudioNode lifeWonAudio;
    
    public AudioEffects(){this.audioNode = new Node();
}
    
    public AudioEffects(AssetManager assetManager, Node rootNode){
        this.audioNode = new Node();
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }
    
    public void loadAudioFXs(){
        //Simple ball rebound
        simpleReboundAudio = new AudioNode(assetManager, "Sounds/effects/simple_rebound.wav", false);
        simpleReboundAudio.setName("simpleReboundAudio");
        simpleReboundAudio.setPositional(false);
        simpleReboundAudio.setLooping(false);
        simpleReboundAudio.setVolume(1);
        audioNode.attachChild(simpleReboundAudio);
        //rootNode.attachChild(simpleReboundAudio);
        
        //Brick explosion 
        brickExplosionAudio = new AudioNode(assetManager, "Sounds/effects/small_explosion.wav", false);
        brickExplosionAudio.setName("brickExplosionAudio");
        brickExplosionAudio.setPositional(false);
        brickExplosionAudio.setLooping(false);
        brickExplosionAudio.setVolume(0.5f);
        audioNode.attachChild(brickExplosionAudio);
//        rootNode.attachChild(brickExplosionAudio);
        
        
        //Brick metallic rebound 
        metallicReboundAudio = new AudioNode(assetManager, "Sounds/effects/metal-hammer-hit-01.wav", false);
        metallicReboundAudio.setName("metallicReboundAudio");
        metallicReboundAudio.setPositional(false);
        metallicReboundAudio.setLooping(false);
        metallicReboundAudio.setVolume(1);
        audioNode.attachChild(metallicReboundAudio);
        //rootNode.attachChild(metallicReboundAudio);
        
        //Brick fireball
        fireballAudio = new AudioNode(assetManager, "Sounds/effects/fireball.wav", false);
        fireballAudio.setName("fireballAudio");
        fireballAudio.setPositional(false);
        fireballAudio.setLooping(true);
        fireballAudio.setVolume(4);
        audioNode.attachChild(fireballAudio);
//        rootNode.attachChild(fireballAudio);
        
        //Arkanoid explosion
        arkanoidExplosionAudio = new AudioNode(assetManager, "Sounds/effects/explosion.wav", false);
        arkanoidExplosionAudio.setName("arkanoidExplosionAudio");
        arkanoidExplosionAudio.setPositional(false);
        arkanoidExplosionAudio.setLooping(false);
        arkanoidExplosionAudio.setVolume(4);
        audioNode.attachChild(arkanoidExplosionAudio);
//        rootNode.attachChild(arkanoidExplosionAudio);
        
        //Spaceship shoot audio
        laserShootAudio = new AudioNode(assetManager, "Sounds/effects/laser.wav", false);
        laserShootAudio.setName("laserShootAudio");
        laserShootAudio.setPositional(false);
        laserShootAudio.setLooping(false);
        laserShootAudio.setVolume(2);
        audioNode.attachChild(laserShootAudio);
//        rootNode.attachChild(laserShootAudio);
        
        //Life won audio
        lifeWonAudio = new AudioNode(assetManager, "Sounds/effects/life_won.ogg", false);
        lifeWonAudio.setName("lifeWonAudio");
        lifeWonAudio.setPositional(false);
        lifeWonAudio.setLooping(false);
        lifeWonAudio.setVolume(2);
        audioNode.attachChild(lifeWonAudio);
//        rootNode.attachChild(lifeWonAudio);
    }
    
    public void loadAudioToSceneGraph(){
        rootNode.attachChild(audioNode);
    }
    
    
}
