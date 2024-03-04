/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.controller;

import edu.neumont.csc.model.*;
import java.awt.Point;

/**
 * This Controller will handle everything with the file management system
 * It will write algebraic notation as the game goes on
 * If the user saves, it will write a FEN (Forsyth–Edwards Notation) file, and will use that to load a new save
 * @author cschade
 */
public class FileController {
    public static String parseMoveToAlgebraic(Piece piece, Point newPoint) {
        String className = piece.getClass().getName();
        return 
            (switch(className) {
                case "edu.neumont.csc.model.Knight" -> "N";
                case "edu.neumont.csc.model.Bishop" -> "B";
                case "edu.neumont.csc.model.Queen" -> "Q";
                case "edu.neumont.csc.model.King" -> "K";
                case "edu.neumont.csc.model.Rook" -> "R";
                default -> "";
            }
            + switch(newPoint.x) {
                case 0 -> Coordinates.A.getPrettyName();
                case 1 -> Coordinates.B.getPrettyName();
                case 2 -> Coordinates.C.getPrettyName();
                case 3 -> Coordinates.D.getPrettyName();
                case 4 -> Coordinates.E.getPrettyName();
                case 5 -> Coordinates.F.getPrettyName();
                case 6 -> Coordinates.G.getPrettyName();
                case 7 -> Coordinates.H.getPrettyName();
                default -> "";
        } + (newPoint.y+1));
        
    }
    
    public static String parseMoveToAlgebraic(Piece piece, boolean castled) {
        String move = "";
        if (castled) {
            if (piece.getPosition().x == 6) {
                move = "O-O";
            } else if (piece.getPosition().x == 2) {
                move = "O-O-O";
            }
        }
        return move;
    }
}
