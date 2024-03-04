package edu.neumont.csc.model;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Mlopez759
 * @createdOn 3/2/2024 at 2:54 PM
 * @projectName chess
 * @packageName edu.neumont.csc.model;
 */

public class Queen extends Piece {
    public Queen(boolean isWhite, Point position) {
        super(isWhite, position);
        setModelFilePath((isWhite) ? "Models/QueenW.glb" : "Models/QueenB.glb");
        setHasRestrictedMovement(false);
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
    protected void setCaptureMoveSet() {
        this.captureMoveSet = getMoveSet();
    }


}
