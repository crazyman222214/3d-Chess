/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.model;

import java.awt.Point;

/**
 * This is the abstract class that represents a piece
 * Assuming we want to make this as broad as possible, This shouldn't even just be for a chess piece.
 * This could be any given game piece
 * <br>
 * Properties
 * <ul>
 *  <li>Position: Point</li>
 *  <li></li>
 * </ul>
 * @author Chris & Michael
 */
public abstract class Piece {
    protected String modelFilePath;
    protected Point position;

    
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
    
    
    
    /**
     * This is the abstract method to move a piece. <br>
     * All pieces will move their own way<br>
     * @return Point newPoint - The point that the piece moves to.
     */
    public abstract Point movePiece();
    
    
    /**
     * This will check if there is a piece at that spot (Might need to change spots depending on how the check works)
     * @param cordinatesToCheck The Point (x,y) that this will check for a collision
     * @return boolean true if there is a collision, else false
     */
    //public abstract boolean checkForCollision(Point cordinatesToCheck);
    
}
