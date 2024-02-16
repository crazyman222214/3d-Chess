/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.view;

import com.jme3.system.AppSettings;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import edu.neumont.csc.model.Rook;
import java.util.ArrayList;

/**
 * This is the class that deals with everything visual<br>
 * Essentially, this deals with all of the things within the Engine
 * 
 * Simple Application is an abstact class designed to contain everything that has to deal with running a SimpleApplication<br>
 * See {@link com.jme3.app.SimpleApplication} to see the Abstract class this implements <br>
 * @author Chris
 */
public class ChessView extends SimpleApplication {
    private ArrayList<Box> pieces = new ArrayList<>();
    private boolean isWhiteTurn = true;
    private boolean isOverhead = false;

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void setIsWhiteTurn(boolean isWhiteTurn) {
        this.isWhiteTurn = isWhiteTurn;
    }

    public boolean isOverhead() {
        return isOverhead;
    }

    public void setIsOverhead(boolean isOverhead) {
        this.isOverhead = isOverhead;
    }
    
    
    
    public void setupProject() {
        setupSettings();
        
    }
    
    public void setupSettings() {
        ChessView app = new ChessView();
        
        AppSettings appSettings = new AppSettings(true);
        
        appSettings.setResolution(1600, 900);
        //appSettings.setFullscreen(true);
        appSettings.setFrameRate(165);
        
        app.setSettings(appSettings);
        app.setShowSettings(false); //Settings dialog not supported on mac
        app.start();
        flyCam.setMoveSpeed(5.0f);
    }
    
    //This is to setup the Camera Settings For White
    public void setupCamera(FlyByCamera flyCam) {
        flyCam.onAnalog("FLYCAM_Backward", 11f,200);
        flyCam.onAnalog("FLYCAM_Rise", 25f,200);
        flyCam.onAnalog("FLYCAM_Down", 0.65f,200);
        flyCam.setEnabled(false);
    }
    
    public void switchCamera(FlyByCamera flyCam) {
        flyCam.setEnabled(true);
        flyCam.onAnalog("FLYCAM_Up", 0.65f,200);
        flyCam.onAnalog("FLYCAM_Forward", 42f,200);
        flyCam.onAnalog("FLYCAM_Left", (float)Math.PI, 200);
        flyCam.onAnalog("FLYCAM_Down", 0.65f,200);
        //flyCam.setEnabled(false);
    
    }
    
    public void overheadCamera(FlyByCamera flyCam) {
        flyCam.setEnabled(true);
        if (isOverhead()) {
            flyCam.onAnalog("FLYCAM_Up", 1.5f,200);
            flyCam.onAnalog("FLYCAM_Forward", 25f, 200);
            flyCam.onAnalog("FLYCAM_Down", 2.3f,200);
        } else {
            flyCam.onAnalog("FLYCAM_Up", 2.3f,200);
            flyCam.onAnalog("FLYCAM_Backward", 25f, 200);
            flyCam.onAnalog("FLYCAM_Down", 1.5f,200);  
        }
        flyCam.setEnabled(false);
    }
    
    public void setupBoard() {
        
    }

    
    //This is used to setup the initial components of the app
    @Override
    public void simpleInitApp() {
        setupLight();
        
        //For the board, The bottom right is (5.25, 5.25), The top left is (-5.25, -5.25)
        //Each Space is 1.5 x 1.5
        createSpatialObject("Models/chessbooard-file.glb");
        
        
        Spatial blackHRook = createSpatialObject("Models/RookFileB.glb");
        Spatial blackARook = createSpatialObject("Models/RookFileB.glb");
        
        Spatial whiteHRook = createSpatialObject("Models/RookFileW.glb");
        Spatial whiteARook = createSpatialObject("Models/RookFileW.glb");
        
        
        blackARook.setLocalTranslation(-5.25f,9.5f,-5.25f);
        blackHRook.setLocalTranslation(5.25f,9.5f,-5.25f);
        
        whiteARook.setLocalTranslation(-5.25f,9.5f,5.25f);
        whiteHRook.setLocalTranslation(5.25f,9.5f,5.25f);
        
        

        addTriggers();
        
        flyCam.setZoomSpeed(1000);
        flyCam.onAnalog("FLYCAM_ZoomIn", 25f,200);
        setupCamera(flyCam);
        
    }
    
    
    /**
     * This creates a light system to portray the 
     * Works by creating ambient light, and then adding a direction light to do shading <br>
     * {@link com.jme3.light.AmbientLight}
     * {@link com.jme3.light.DirectionalLight}<br>
     * Might come back to this<br>
     * (Chess has no shading needed, but this is still needed to see Spacial Objects)
     */
    private void setupLight() {
        /**
         * This shines from the top left corner of the scene, shining down
         * The normalizeLocal is just normalizing the Vector, thereas creating a unit vector with same direction
         * <a href="https://stackoverflow.com/questions/10002918/what-is-the-need-for-normalizing-a-vector">My research into why it's needed for 3d rendering</a>
         */
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -1f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
        
        
        //We have a second Directional Light that way both sides of the texture loads in
        //(May not be needed for this but we will see)
        DirectionalLight dl2 = new DirectionalLight();
        dl2.setColor(ColorRGBA.White);
        dl2.setDirection(new Vector3f(2.8f, -1f, 2.8f).normalizeLocal());
        rootNode.addLight(dl2);
        
        DirectionalLight dl3 = new DirectionalLight();
        dl3.setColor(ColorRGBA.White);
        dl3.setDirection(new Vector3f(-2.8f, -1f, 2.8f).normalizeLocal());
        rootNode.addLight(dl3);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }
    
    
    
    /**
     * This is the Variable that listens for each action that will mess with the game<br>
     * Controls:<br>
     * Left Click: Select Piece/Move Piece
     */
    final private ActionListener actionLister = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) {
                if (name.equals("MouseLeftClick")) {
                    selectPiece();
                    System.out.println("aya");
                    
                }
                if (name.equals("P")) {
                    if (isOverhead()) return;
                    setIsWhiteTurn(!isWhiteTurn());
                    switchCamera(flyCam);
                }
                if (name.equals("O")) {
                    
                    isOverhead = !isOverhead;
                    overheadCamera(flyCam);
                }
            }
        }
    };
    
    
    public void addTriggers() {
        inputManager.addMapping("MouseLeftClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionLister, "MouseLeftClick");
        
        inputManager.addMapping("P", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(actionLister, "P");
        
        inputManager.addMapping("O", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addListener(actionLister, "O");
    }
    
    /**
     * This method creates a spatial object that holds a .jlb file as a model <br>
     * Uses the .loadModel method of the assetManager built into the SimpleApplication
     * @param filePath 
     */
    private Spatial createSpatialObject(String filePath) {
        assetManager.registerLocator("/assets", FileLocator.class);
        Spatial object = assetManager.loadModel(filePath);
        rootNode.attachChild(object);
        return object;
    }
    
    public void createModels() {
        
    }
    
    public void selectPiece() {
        CollisionResults results = new CollisionResults();
        
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f);
        
        Vector3f direction = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        System.out.println(direction);
        
        Ray ray = new Ray(click3d, direction);
        
        rootNode.collideWith(ray, results);
        
        for (int i = 0; i < results.size(); i++) {
          // (For each "hit", we know distance, impact point, geometry.)
          float dist = results.getCollision(i).getDistance();
          Vector3f pt = results.getCollision(i).getContactPoint();
          String target = results.getCollision(i).getGeometry().getName();
          System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
        }
        
        results.getCollision(0).getGeometry().move(1.5f, 0,0);
        results.getCollision(0).getGeometry().move(0, 0,1.5f);
    }
}
