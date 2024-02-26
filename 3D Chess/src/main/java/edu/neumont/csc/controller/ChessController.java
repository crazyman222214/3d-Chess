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
    
    public static ChessView view = new ChessView();
    public static void run() {
        view.setupProject();
        setupBoard(view);
    }
    
    
    
    private static void setupBoard(ChessView view) {
        board = new Piece[8][8];
        for(int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if ((x == 0 || x == 7) && (y == 0 || y == 7)) { //Rooks (a1/8 && h1/8) 
                    board[x][y] = new Rook((x==0)? true : false);
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
                    board[x][y] = new Pawn();
                }
            }
        }
    }
    
    private void movePiece(Piece piece) {
        
        //Check if the move is valid
        //Move It if empty
        //Capture if it's not your own piece
    }
    
    public static ArrayList<Point> checkForValidMoves(Point oldPoint) {
        //TODO: Figure out why this doesn't run for Black Or Pawns
        //TODO: Make this work for pieces with limited range of motion (I.E Pawns and Kings and Knights)
        Piece piece = board[oldPoint.x][oldPoint.y];
        ArrayList<Point> validSpots = new ArrayList<>();
        for (Point point : piece.getMoveSet()) {
           int xDir = point.x;
           int yDir = point.y;

           int xCurr = piece.getPosition().x;
           int yCurr = piece.getPosition().y;
           String filePath = piece.getModelFilePath();

           Piece filePathOtherPiece = board[xCurr+xDir][yCurr+yDir];
           System.out.println(filePathOtherPiece);
           
            if (xCurr+xDir < 0 || yCurr+yDir < 0) {
                continue;
            }
            while (board[yCurr+yDir][xCurr+xDir] == null) {
                validSpots.add(new Point(xCurr+xDir, yCurr+yDir));
                xDir += (xDir < 0) ? -1 : (xDir == 0) ? 0 : 1;
                yDir += (yDir < 0) ? -1 : (yDir == 0) ? 0 : 1;
            }
           
        }

        return validSpots;
    }
    
    private void checkForCaptureMethod(Point point) {
        //valid moves = checkForValidMoves();
        //if point is in moves
        //  "takes Piece"
        //  captured piece array gets added to to display
    }

    private void checkForCheck() {
        //valid moves = checkForValidMoves();
        //if valid moves has king in vision and no others in its path
        //  otherKing.isChecked = true
        //
        //if valid moves has king in vision but piece in the way
        //  piece.isChecked = true
    }
    
}
