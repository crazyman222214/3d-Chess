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
    private boolean hasCastled;
    
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

    public boolean hasCastled() {
        return hasCastled;
    }

    public void setHasCastled(boolean hasCastled) {
        this.hasCastled = hasCastled;
    }

    
    
    @Override
    protected void setMoveSet() {
       this.moveSet = new ArrayList<>();
       this.moveSet.add(new Point(0,1));
       this.moveSet.add(new Point(1,0));
       this.moveSet.add(new Point(-1,0));
       this.moveSet.add(new Point(0, -1));
       this.moveSet.add(new Point(1,1));
       this.moveSet.add(new Point(-1,-1));
       this.moveSet.add(new Point(-1, 1));
       this.moveSet.add(new Point(1,-1));
        
    }
    
    public void castleMoveSet(boolean isKingSide) {
        this.moveSet.add(isKingSide ? new Point(2,0) : new Point(-2,0));
    }

    @Override
    protected void setCaptureMoveSet() {
        this.captureMoveSet = getMoveSet();
    }

    @Override
    public void movePiece(Point point) {
        if (this.position.x == point.x-2 || this.position.x == point.x+2) {
            setHasCastled(true);
            setMoveSet();
        }
        this.position = point;
        
    }
    
    public void checkForCheckAvailable(Point direction) {
        
    }
    
}
