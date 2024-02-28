/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.view;

import com.jme3.system.AppSettings;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.input.*;
import com.jme3.input.controls.*;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.*;
import com.jme3.scene.*;
import com.jme3.scene.shape.Cylinder;
import edu.neumont.csc.controller.ChessController;
import edu.neumont.csc.model.*;
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
    private boolean isWhiteTurn = true;
    private boolean isOverhead = false;
    private boolean selectedPiece = false;
    private Geometry selectedGeometry;
    private ArrayList<Geometry> validSpotMarkers = new ArrayList<>();
    
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
    
    /**
     * This is to setup the Project
     */
    public void setupProject() {
        setupSettings();
        
    }
    
    /**
     * This is to set up the app settings
     */
    public void setupSettings() {
        ChessView app = new ChessView();
        
        AppSettings appSettings = new AppSettings(true);
        
        appSettings.setResolution(1600, 900);
        //appSettings.setFullscreen(true);
        appSettings.setFrameRate(165);
        
        app.setSettings(appSettings);
        app.setShowSettings(false); //Settings dialog not supported on mac
        
        
        
        app.start();
    }
    
    /**
     * This is to Setup the initialCamera (White)
     */
    public void setupCamera() {
        flyCam.onAnalog("FLYCAM_Backward", 11f,200);
        flyCam.onAnalog("FLYCAM_Rise", 25f,200);
        flyCam.onAnalog("FLYCAM_Down", 0.65f,200);
        flyCam.setEnabled(false);
    }
    /**
     * This is to switch to the camera to the other side (White-Black & Black-White)
     */
    public void switchCamera() {
        setIsWhiteTurn(!isWhiteTurn());
        flyCam.setEnabled(true);
        flyCam.onAnalog("FLYCAM_Up", 0.65f,200);
        flyCam.onAnalog("FLYCAM_Forward", 42f,200);
        flyCam.onAnalog("FLYCAM_Left", (float)Math.PI, 200);
        flyCam.onAnalog("FLYCAM_Down", 0.65f,200);
        flyCam.setEnabled(false);
    
    }
    /**
     * This is to switch to the camera to the overhead view
     */
    public void overheadCamera() {
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
    
    /**
     * This is to setup the Board Models
     */
    public void setupBoard() {
        
        createSpatialObject("Models/chessbooard-file.glb");
        
        Piece[][] board = ChessController.getBoard();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == null ) continue;
                createModel(board[x][y].getModelFilePath(), board[x][y].getPosition());
            }
        }
    }

    
    /**
     * This is used to setup the initial components of the app
     */
    @Override
    public void simpleInitApp() {
        setupLight();
        
        //For the board, The bottom right is (5.25, 5.25), The top left is (-5.25, -5.25)
        //Each Space is 1.5 x 1.5
        setupBoard();
  
        addTriggers();
        
        flyCam.setZoomSpeed(1000);
        flyCam.onAnalog("FLYCAM_ZoomIn", 25f,200);
        setupCamera();
        
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
        
        DirectionalLight dl1 = new DirectionalLight();
        dl1.setColor(ColorRGBA.White);
        dl1.setDirection(new Vector3f(-2.8f, 5f, 2.8f).normalizeLocal());
        rootNode.addLight(dl1);
        
        
        DirectionalLight dl2 = new DirectionalLight();
        dl2.setColor(ColorRGBA.White);
        dl2.setDirection(new Vector3f(2.8f, 5f, 2.8f).normalizeLocal());
        rootNode.addLight(dl2);
        
        DirectionalLight dl3 = new DirectionalLight();
        dl3.setColor(ColorRGBA.White);
        dl3.setDirection(new Vector3f(-2.8f, 5f, -2.8f).normalizeLocal());
        rootNode.addLight(dl3);
        
        
        DirectionalLight dl4 = new DirectionalLight();
        dl4.setColor(ColorRGBA.White);
        dl4.setDirection(new Vector3f(2.8f, -5f, -2.8f).normalizeLocal());
        rootNode.addLight(dl4);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }
    
    
    
    /**
     * This is the Variable that listens for each action that will mess with the game<br>
     * Controls:<br>
     * Left Click: Select Piece/Move Piece
     * P : Change to Other Players Perspective
     * O : Change to Overhead View
     */
    final private ActionListener actionLister = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (isPressed) {
                if (name.equals("MouseLeftClick")) {
                    selectPiece();
                    
                }
                if (name.equals("P")) {
                    if (isOverhead()) return;
                    switchCamera();
                    if (!validSpotMarkers.isEmpty()) {
                        for (Geometry markers : validSpotMarkers) {
                            
                            markers.move(0, 0, (isWhiteTurn) ? 14.5f : -14.5f);
                        }
                    }
                }
                if (name.equals("O")) {
                    isOverhead = !isOverhead;
                    overheadCamera();
                }
                
            }
        }
    };
    
    /**
     * This adds the inputTriggers into the inputManager that way they can be monitored
     */
    private void addTriggers() {
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
     * @param filePath the file path to the .jlb file
     */
    private Spatial createSpatialObject(String filePath) {
        assetManager.registerLocator("/assets", FileLocator.class);
        Spatial object = assetManager.loadModel(filePath);
        rootNode.attachChild(object);
        return object;
    }
    
    /**
     * This creates the model of the pieces and sets the position/rotation correctly
     * @param filePath the file path to the .jlb file
     * @param pointOnBoard Point on the board to place the model
     */
    public void createModel(String filePath, Point pointOnBoard) {
        Spatial piece = createSpatialObject(filePath);
        if (filePath.contains("KnightW")) {
            Quaternion quaternion = new Quaternion();
            quaternion.fromAngleAxis( FastMath.PI , new Vector3f(0,1,0) );
            piece.setLocalRotation(quaternion);
        }
        
        piece.setLocalTranslation(-5.25f + (1.5f*(pointOnBoard.x)) , 9.5f, 5.25f - (1.5f*(pointOnBoard.y)));
    }
    
    /**
     * This parses a Spatial Vector3f to the Boards Coordinates
     * @param spatialVector the Vector3f to parse
     * @return Point on the board
     */
    public Point parseSpatialToBoard(Vector3f spatialVector) {
        int boardX = Math.round((spatialVector.x + 5.25f) / 1.5f);
        int boardY = Math.round((-spatialVector.z + 5.25f) / 1.5f);
        return new Point(boardX, boardY);
    }
    
    
    /**
     * This manages all of the click event details
     * @return the Point Selected
     */
    public Point selectPiece() {
        Point point = new Point();
        CollisionResults results = new CollisionResults();
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f);
        
        Vector3f direction = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
        
        //This is the Ray that represents the "ray" from which the mouse and the camera create
        Ray ray = new Ray(click3d, direction);
        
        //This is a built in function to tell if a object has collided within a bound ray, and update it to a CollisionResults
        rootNode.collideWith(ray, results);
        
        System.out.println(results.getCollision(2).getGeometry());
        try {
            if (results.getCollision(0).getGeometry().getName().equals("Cylinder.008")) {
                setSelectedPiece(true);
                point = parseSpatialToBoard(results.getCollision(0).getGeometry().getWorldTranslation());
                ArrayList<Point> validPoints = ChessController.checkForValidMoves(point);
                for (Point selectedPoint : validPoints) {
                    createCircleSpot(selectedPoint);
                }
                if (selectedGeometry != null) {
                    clearCircleSpot();
                    selectedGeometry = null;
                } else {
                    selectedGeometry = results.getCollision(0).getGeometry();
                }
            }
            
            
            
            else if (results.getCollision(2).getGeometry().getName().equals("Plane.003") && selectedPiece) {
                Point pointToMove = parseSpatialToBoard(results.getCollision(2).getContactPoint());
                
                //TODO: Handle captures
                //TODO: Handle Checks
                //TODO: Handle Castling
                //TODO: Handle en passant
                //TODO: Write the file manager for notation
                //TODO SOMETIME: create a Simple AI
                
                if (ChessController.isValidMove(pointToMove)) {
                   point = movePiece(pointToMove, selectedGeometry); 
                   selectedGeometry = null;
                   selectedPiece = false;
                   switchCamera();
                }
                
            }
        } catch(IllegalArgumentException e) {
            
        }
        return point;
    }
    /**
     * This clears all of the markers for valid moves
     */
    private void clearCircleSpot() {
        for (Geometry geom : validSpotMarkers) {
            rootNode.detachChild(geom);
        }
    }
    /**
     * This moves the Piece Model and updates
     * @param pointToMove The Point on the board that the Piece is moving to
     * @param selectedGeometry The Geometry for the model of the Piece that is moving
     * @return The Point that it moved to
     */
    public Point movePiece(Point pointToMove, Geometry selectedGeometry) {
        Point boardPoint = parseSpatialToBoard(selectedGeometry.getWorldTranslation());
        float deltaX = pointToMove.x - boardPoint.x;
        float deltaY = pointToMove.y - boardPoint.y;
        
        selectedGeometry.move((1.5f*deltaX), 0f, -(1.5f*deltaY));
        ChessController.movePiece(boardPoint, pointToMove);
        setSelectedPiece(false);
        clearCircleSpot();
        
        
        return pointToMove;
    }
    
    
    /**
     * Creates a marker for the valid moves that a given piece can make
     * @param spotOnBoard The Point on the board that the circle spot is going to
     */
    private void createCircleSpot(Point spotOnBoard) {
        Cylinder mesh = new Cylinder(30, 30, 0.25f, 0.25f, true);
        Geometry geom = new Geometry("Cylinder", mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  
        mat.setColor("Color", ColorRGBA.DarkGray);
        geom.setMaterial(mat);
        
        Quaternion quaternion = new Quaternion();
        quaternion.fromAngleAxis( FastMath.PI/2 , new Vector3f(1,0,0) );
        geom.setLocalRotation(quaternion);
        
        rootNode.attachChild(geom);
        geom.setLocalTranslation(-3.5f+spotOnBoard.x,15f, ((isWhiteTurn) ? 10.75f-spotOnBoard.y : (7-spotOnBoard.y)-10.75f));
        validSpotMarkers.add(geom);
    }
}