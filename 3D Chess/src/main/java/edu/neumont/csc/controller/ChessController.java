/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.controller;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import edu.neumont.csc.model.*;
import edu.neumont.csc.view.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chris & Michael
 */
public class ChessController {
    private static Piece[][] board;
    private static final int BOARD_LENGTH = 8;
    private static ArrayList<Point> validSpots = new ArrayList<>();
    private static ArrayList<Piece> capturedPieces = new ArrayList<>();
    
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
                    board[x][y] = new King(x == 0, new Point(y,x));
                }
                if ((x == 1 || x == 6)) {
                    board[x][y] = new Pawn(x == 1, new Point(y,x));
                }
            }
        }
    }
    
    public static Spatial movePiece(Point oldPoint, Point newPoint) {
        Spatial capturedPiece = null;
        if (board[newPoint.y][newPoint.x] != null) {
            capturePiece(board[newPoint.y][newPoint.x]);
            capturedPiece = board[newPoint.y][newPoint.x].getSpatial();
            capturedPiece.setLocalTranslation(-6.75f ,9.5f, -5.25f + 1.25f * (capturedPieces.size()-1));
            
        }
        board[newPoint.y][newPoint.x] = board[oldPoint.y][oldPoint.x];
        board[oldPoint.y][oldPoint.x] = null;
        board[newPoint.y][newPoint.x].movePiece(newPoint);
        return capturedPiece;
    }

    
    public static ArrayList<Point> checkForValidMoves(Point oldPoint) {
        Piece piece = board[oldPoint.y][oldPoint.x];
        validSpots = new ArrayList<>();
        for (Point moveSet : piece.getMoveSet()) {
           int xDir = moveSet.x;
           int yDir = moveSet.y;

           int xCurr = oldPoint.x;
           int yCurr = oldPoint.y;     
           while(((yCurr+yDir <= 7 && xCurr+xDir <= 7) && (xCurr+xDir >= 0 && yCurr+yDir >= 0)) && (board[yCurr+yDir][xCurr+xDir] == null)) {
                validSpots.add(new Point(xCurr+xDir, yCurr+yDir));
                xDir += (xDir < 0) ? -1 : (xDir == 0) ? 0 : moveSet.x;
                yDir += (yDir < 0) ? -1 : (yDir == 0) ? 0 : moveSet.y;
                if (piece.hasRestrictedMovement()) {
                    break;
                }
                
            }
           ArrayList<Point> validCaptures = checkForCaptureMethod(new Point((piece.hasRestrictedMovement() ? oldPoint.x : xCurr+xDir),(piece.hasRestrictedMovement() ? oldPoint.y : yCurr+yDir)), piece);
            if (!validCaptures.isEmpty()) {
                for (Point point : validCaptures) {
                    validSpots.add(new Point(point.x, point.y));
                }
            }
        }
        
        
        return validSpots;
    }
    
    private static ArrayList<Point> checkForCaptureMethod(Point point, Piece piece) {
        ArrayList<Point> validCaptures = new ArrayList<>();
        for (Point captureMove : piece.getCaptureMoveSet()) {
            Point capturePoint = new Point(point.x + (piece.hasRestrictedMovement() ? captureMove.x : 0), point.y + (piece.hasRestrictedMovement() ? captureMove.y : 0));         
                if ((capturePoint.x >= 0 && capturePoint.y >= 0 && capturePoint.x <= 7 && capturePoint.y <= 7) && board[capturePoint.y][capturePoint.x] != null) {
                    if (board[capturePoint.y][capturePoint.x].isWhite() != piece.isWhite() ) {
                        validCaptures.add(capturePoint);
                    }
                }
        }
        return validCaptures;
    }
    
    private static void capturePiece(Piece capturedPiece) {
        capturedPieces.add(capturedPiece);
    }

    private void checkForCastle(King king, Rook rook) {
        if (!king.hasMoved() && !rook.hasMoved()) {
            boolean canCastle = true;
            boolean isPositive = king.getPosition().x - rook.getPosition().x < 0;
            for (int x = king.getPosition().x; (isPositive)? x >= 0 : x < 8;) {
                if (board[0][x] != null) {
                    canCastle = false;
                }
                
                if (isPositive) x++;
                else x--;
            }
            
            if (canCastle) {
                //king moves two spaces in the direction and the rook moves to the other side
            }
        }
    }
    
    private void checkForCheck() {
        
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
