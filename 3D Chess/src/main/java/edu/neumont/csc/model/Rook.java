package edu.neumont.csc.model;

import edu.neumont.csc.view.ChessView;
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
    
    public Rook(boolean isWhite) {
       setIsWhite(isWhite);
       setMoveSet(); 
       setPosition(new Point(0,0));
       setModelFilePath((isWhite) ? "Models/RookFileW.glb" : "Models/RookFileB.glb");
    }
    
    @Override
    public Point movePiece() {
        return new Point(0,0);
    }
    
    @Override
    public void createModel(ChessView v) {
        v.createModel(modelFilePath, position);
    }

    @Override
    public void setMoveSet() {
       this.moveSet = new ArrayList<>(){};
       this.moveSet.add(new Point(0,1));
       this.moveSet.add(new Point(1,0));
    }
}
