/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.model;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This is the abstract class that represents a piece
 * Assuming we want to make this as broad as possible, This shouldn't even just be for a chess piece.
 * This could be any given game piece
 * <br>
 * Properties
 * <ul>
 *  <li>Position: Point</li>
 *  <li>MoveSet: ArrayList-Point</li>
 *  <li>ModelFilePath: String</li>
 * </ul>
 * @author Chris & Michael
 */
public abstract class Piece {

    public Piece(boolean isWhite, Point position) {
        setIsWhite(isWhite);
        setPosition(position);
        setMoveSet();
        setCaptureMoveSet();
    }
    
    
    protected boolean hasRestrictedMovement;
    protected ArrayList<Point> moveSet;
    protected String modelFilePath;
    protected Point position;
    protected boolean isWhite;
    protected ArrayList<Point> captureMoveSet;
    protected Spatial spatial;
    
    protected abstract void setMoveSet();
    protected abstract void setCaptureMoveSet();

    public boolean isWhite() {
        return isWhite;
    }

    protected void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }
    
    
    
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        if (position == null) throw new IllegalArgumentException("Position must not be null");
        this.position = position;
    }

    public String getModelFilePath() {
        return modelFilePath;
    }

    public void setModelFilePath(String modelFilePath) {
        this.modelFilePath = modelFilePath;
    }
    
    public ArrayList<Point> getMoveSet() {
        return this.moveSet;
    }
    
    public ArrayList<Point> getCaptureMoveSet() {
        return this.captureMoveSet;
    }

    
    protected void setHasRestrictedMovement(boolean hasRestrictedMovement) {
        
        this.hasRestrictedMovement = hasRestrictedMovement;
    }
    
    public boolean hasRestrictedMovement() {
        return this.hasRestrictedMovement;
    }
    
    
    /**
     * This is the abstract method to move a piece. <br>
     * All pieces will move their own way<br>
     * @param point Point that the piece is moving to
     */
    public abstract void movePiece(Point point);
    
    
}