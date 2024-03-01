/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.model;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author cschade
 */
public class King extends Piece {
    private boolean hasMoved;
    
    public King(boolean isWhite, Point position) {
        super(isWhite, position);
        setModelFilePath((isWhite) ? "Models/KingW.glb" : "Models/KingB.glb");
        setHasRestrictedMovement(true);
        setHasMoved(false);
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    
    
    @Override
    protected void setMoveSet() {
        this.moveSet = new ArrayList<>();
        this.moveSet.add(new Point(0,1));
        this.moveSet.add(new Point(1,0));
        this.moveSet.add(new Point(-1,0));
        this.moveSet.add(new Point(0,-1));
    }

    @Override
    protected void setCaptureMoveSet() {
        this.captureMoveSet = getMoveSet();
    }

    @Override
    public void movePiece(Point point) {
        
    }
    
    public void checkForCheckAvailable(Point direction) {
        
    }
    
}
