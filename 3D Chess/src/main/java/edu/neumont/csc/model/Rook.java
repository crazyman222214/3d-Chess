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
public class Rook extends Piece {
    private boolean hasMoved;
    
    public Rook(boolean isWhite, Point position) {
       super(isWhite, position);
       setModelFilePath((isWhite) ? "Models/RookFileW.glb" : "Models/RookFileB.glb");
       setHasRestrictedMovement(false);
       setHasMoved(false);
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }

    private void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    @Override
    public void movePiece(Point point) {
        if (!hasMoved) {
            setHasMoved(true);
        }
        this.setPosition(point);
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

    @Override
    public void setCaptureMoveSet() {
        this.captureMoveSet = getMoveSet();
        
    }
    
}
