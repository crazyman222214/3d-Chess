/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.model;

import edu.neumont.csc.view.ChessView;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author cschade
 */
public class Pawn extends Piece{
    private boolean hasMoved;

    public Pawn(boolean isWhite, Point point) {
        super(isWhite, point);
        setModelFilePath((isWhite) ? "Models/PawnW.glb" : "Models/PawnB.glb");
        setHasRestrictedMovement(true);
        setHasMoved(false);
    }
    

    
    
    @Override
    public void setMoveSet() {
        this.moveSet = new ArrayList<>();
        this.moveSet.add(new Point(0, (isWhite) ? 1 : -1));
        this.moveSet.add(new Point(0, (isWhite) ? 2 : -2));
        
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
            this.moveSet.clear();
            this.moveSet.add(new Point(0,(isWhite) ? 1 : -1));
            setHasMoved(true);
        }
        this.setPosition(point);
    }

    @Override
    protected void setCaptureMoveSet() {
        this.captureMoveSet = new ArrayList<>();
        this.captureMoveSet.add(new Point(1,1));
        this.captureMoveSet.add(new Point(-1,1));
    }
}
