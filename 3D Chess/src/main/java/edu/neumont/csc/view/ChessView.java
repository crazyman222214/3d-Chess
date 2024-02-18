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
import java.awt.Point;
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
    private boolean selectedPiece = false;
    private Geometry selectedGeometry;
    
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

    public boolean isSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(boolean selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Geometry getSelectedGeometry() {
        return selectedGeometry;
    }

    public void setSelectedGeometry(Geometry selectedGeometry) {
        this.selectedGeometry = selectedGeometry;
    }
    
    
    public void setupProject() {
        setupSettings();
        
    }
    
    public void setupSettings() {
        ChessView app = new ChessView();
        
        AppSettings appSettings = new AppSettings(true);
        
        appSettings.setResolution(1600, 900);
        appSettings.setFullscreen(true);
        appSettings.setFrameRate(165);
        
        app.setSettings(appSettings);
        app.setShowSettings(false); //Settings dialog not supported on mac
                
        app.start();
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
        flyCam.setEnabled(false);
    
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
        createSpatialObject("Models/chessbooard-file.glb");
        createModel("Models/RookFileB.glb", new Point(1,8));
        createModel("Models/RookFileB.glb", new Point(8,8));
        createModel("Models/RookFileW.glb", new Point(1,1));
        createModel("Models/RookFileW.glb", new Point(8,1));
        
    }

    
    //This is used to setup the initial components of the app
    @Override
    public void simpleInitApp() {
        setupLight();
        
        //For the board, The bottom right is (5.25, 5.25), The top left is (-5.25, -5.25)
        //Each Space is 1.5 x 1.5
        setupBoard();
  
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
        DirectionalLight dl2 = new DirectionalLight();
        dl2.setColor(ColorRGBA.White);
        dl2.setDirection(new Vector3f(2.8f, -1f, 2.8f).normalizeLocal());
        rootNode.addLight(dl2);
        
        
        //We have a third Directional Light to clean up the shading
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
    
    public void createModel(String filePath, Point pointOnBoard) {
        Spatial piece = createSpatialObject(filePath);
        piece.setLocalTranslation(-5.25f + (1.5f*(pointOnBoard.x-1)) , 9.5f, 5.25f - (1.5f*(pointOnBoard.y-1)));
    }
    
    public void selectPiece() {
        CollisionResults results = new CollisionResults();
        //This gets the Cursors position on the window
        Vector2f click2d = inputManager.getCursorPosition();
        
        //This gets the world coordinates based on the cursors position
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f);
        
        //This gets the direction from the camera to the click3d
        Vector3f direction = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        
        //This is the Ray that represents the "ray" from which the mouse and the camera create
        Ray ray = new Ray(click3d, direction);
        
        //This is a built in function to tell if a object has collided within a bound ray, and update it to a CollisionResults
        rootNode.collideWith(ray, results);
        try {
            if (results.getCollision(0).getGeometry().getName().equals("Cylinder.008")) {
                setSelectedPiece(true);
                setSelectedGeometry(results.getCollision(0).getGeometry());
            }
            
            if (results.getCollision(0).getGeometry().getName().equals("Plane.003") && selectedPiece) {

                //I am very proud of the math I have done here
                float newX = (float) Math.floor(results.getCollision(0).getContactPoint().x/12 *8);
                float newZ = (float) Math.floor(results.getCollision(0).getContactPoint().z/12 *8);
                movePiece(newX, newZ);
    
            }
        } catch(IndexOutOfBoundsException e) {
            
        }
    }
    
    public void movePiece(float newX, float newZ) {
        float oldX = (float) Math.floor(selectedGeometry.getWorldTranslation().x/12 *8);
        float oldZ = (float) Math.floor(selectedGeometry.getWorldTranslation().z/12 *8);

        float deltaX = newX - oldX;
        float deltaZ = newZ - oldZ;
        selectedGeometry.move((1.5f*deltaX), 0f, (1.5f*deltaZ));
        setSelectedPiece(false);
    }
}