package edu.neumont.csc.model;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Mlopez759
 * @createdOn 3/2/2024 at 2:26 PM
 * @projectName chess
 * @packageName PACKAGE_NAME;
 */

public class Knight extends Piece{
    public Knight(boolean isWhite, Point position) {
        super(isWhite, position);
        setModelFilePath((isWhite) ? "Models/KnightW2.glb" : "Models/KnightB.glb");
        setHasRestrictedMovement(true);
        setMoveSet();
        setCaptureMoveSet();
        
    }

    @Override
    public void movePiece(Point point) {
        this.setPosition(point);
    }



    @Override
    public void setMoveSet() {
        this.moveSet = new ArrayList<>(){};
        this.moveSet.add(new Point(2,1));
        this.moveSet.add(new Point(2,-1));
        this.moveSet.add(new Point(-2,1));
        this.moveSet.add(new Point(-2, -1));
        this.moveSet.add(new Point(1,2));
        this.moveSet.add(new Point(-1,2));
        this.moveSet.add(new Point(1,-2));
        this.moveSet.add(new Point(-1, -2));
    }

    @Override
    protected void setCaptureMoveSet() {
        this.captureMoveSet = getMoveSet();
    }
}
