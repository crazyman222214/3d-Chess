/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.controller;
import edu.neumont.csc.model.*;
import edu.neumont.csc.view.*;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Chris & Michael
 */
public class ChessController {
    private static Piece[][] board;
    private static final int BOARD_LENGTH = 8;
    private static ArrayList<Point> validSpots = new ArrayList<>();
    
    public static ChessView view = new ChessView();
    public static void run() {
        setupBoard();
        view.setupProject();
    }

    public static Piece[][] getBoard() {
        return board.clone();
    }

    private static void setupBoard() {
        //TODO: Finish creating concrete classes for each piece type
        
        board = new Piece[8][8];
        for(int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if ((x == 0 || x == 7) && (y == 0 || y == 7)) { //Rooks (a1/8 && h1/8) 
                    board[x][y] = new Rook(x == 0, new Point(y,x));
                }
                if ((x == 0 || x == 7) && (y == 1 || y == 6)) { //Knights (a2/7 && h2/7)
                    //this.board[x][y] = new Knight();
                } 
                if ((x == 0 || x == 7) && (y == 2 || y == 5)) { //Bishops (a3/6 && h3/6)
                    //this.board[x][y] = new Bishop();
                }
                if ((x == 0 || x == 7) && y == 3) { //Queen (a4 && h4)
                    //this.board[x][y] = new Queen();
                }
                if ((x == 0 || x == 7) && y == 4) { //King (a5 && h5)
                    //this.board[x][y] = new King();
                }
                if ((x == 1 || x == 6)) {
                    board[x][y] = new Pawn(x == 1, new Point(y,x));
                }
            }
        }
    }
    
    public static void movePiece(Point oldPoint, Point newPoint) {
        board[newPoint.y][newPoint.x] = board[oldPoint.y][oldPoint.x];
        board[oldPoint.y][oldPoint.x] = null;
        board[newPoint.y][newPoint.x].movePiece(newPoint);
        int z = 0;
    }

    
    public static ArrayList<Point> checkForValidMoves(Point oldPoint) {
        //TODO: Figure out why this doesn't run for Black Or Pawns
        //TODO: Make this work for pieces with limited range of motion (I.E Pawns and Kings and Knights)

        Piece piece = board[oldPoint.y][oldPoint.x];
        validSpots = new ArrayList<>();
        for (Point moveSet : piece.getMoveSet()) {
           int xDir = moveSet.x;
           int yDir = moveSet.y;

           int xCurr = oldPoint.x;
           int yCurr = oldPoint.y;     
           while(((yCurr+yDir <= 7 && xCurr+xDir <= 7) && (xCurr+xDir >= 0 && yCurr+yDir >= 0)) &&(board[yCurr+yDir][xCurr+xDir] == null)) {
                validSpots.add(new Point(xCurr+xDir, yCurr+yDir));
                xDir += (xDir < 0) ? -1 : (xDir == 0) ? 0 : moveSet.x;
                yDir += (yDir < 0) ? -1 : (yDir == 0) ? 0 : moveSet.y;
                if (piece.hasRestrictedMovement()) {
                    break;
                }
                
           }
            if ((xCurr+xDir >= 0 && yCurr+yDir >= 0 && xCurr+xDir <= 7 && yCurr+yDir <= 7) && board[yCurr+yDir][xCurr+xDir] != null) {
                if (board[yCurr+yDir][xCurr+xDir].isWhite() != piece.isWhite()) {
                    validSpots.add(new Point(xCurr+xDir, yCurr+yDir));
                }
            }
           
        }

        return validSpots;
    }
    
    private void checkForCaptureMethod(Point point) {
        if (board[point.y][point.x] != null) {
            
        }
    }

    private void checkForCheck() {
        //valid moves = checkForValidMoves();
        //if valid moves has king in vision and no others in its path
        //  otherKing.isChecked = true
        //
        //if valid moves has king in vision but piece in the way
        //  piece.isChecked = true
    }
    
    public static boolean isValidMove(Point point) {
        for (Point move : validSpots) {
            if (point.equals(move)) {   
                return true;
            }
        }
        return false;
    }
    
}
