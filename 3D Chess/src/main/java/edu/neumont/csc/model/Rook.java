package edu.neumont.csc.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cschade
 */
public class Rook extends Piece implements Drawable {
    
    public Rook(boolean isWhite, Point position) {
       super(isWhite, position);
       setModelFilePath((isWhite) ? "Models/RookFileW.glb" : "Models/RookFileB.glb");
       setHasRestrictedMovement(false);
    }
    
    @Override
    public void movePiece(Point point) {
        
    }
    
    @Override
    public void createModel() {
        
    }
    
    

    @Override
    public void setMoveSet() {
       this.moveSet = new ArrayList<>(){};
       this.moveSet.add(new Point(0,1));
       this.moveSet.add(new Point(1,0));
       this.moveSet.add(new Point(-1,0));
       this.moveSet.add(new Point(0, -1));
       this.moveSet.add(new Point(1,1));
       this.moveSet.add(new Point(-1,-1));
       this.moveSet.add(new Point(-1, 1));
       this.moveSet.add(new Point(1,-1));
    }

    
}
