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
                    board[x][y] = new Knight(x==0, new Point(y,x));
                } 
                if ((x == 0 || x == 7) && (y == 2 || y == 5)) { //Bishops (a3/6 && h3/6)
                    board[x][y] = new Bishop(x == 0, new Point(y,x));
                }
                if ((x == 0 || x == 7) && y == 3) { //Queen (a4 && h4)
                    board[x][y] = new Queen(x == 0, new Point(y,x));
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
        Piece movingPiece = board[oldPoint.y][oldPoint.x];
        Spatial rook = null;
        boolean castling = movingPiece instanceof King && (oldPoint.x+2 == newPoint.x || oldPoint.x-2 == newPoint.x);
        if (castling) {
            if (oldPoint.x+2 == newPoint.x) {
                board[oldPoint.y][5] = board[oldPoint.y][7]; //Rook
                board[oldPoint.y][7] = null;
                board[oldPoint.y][5].movePiece(newPoint);
                rook = board[oldPoint.y][5].getSpatial();
            }
            if (oldPoint.x-2 == newPoint.x) {
                board[oldPoint.y][3] = board[oldPoint.y][0]; //Rook
                board[oldPoint.y][0] = null;
                board[oldPoint.y][3].movePiece(newPoint);
                rook = board[oldPoint.y][3].getSpatial();
            }
        }
        if (board[newPoint.y][newPoint.x] != null) {
            capturePiece(board[newPoint.y][newPoint.x]);
            Spatial capturedPiece = board[newPoint.y][newPoint.x].getSpatial();
            capturedPiece.setLocalTranslation(-6.75f ,9.5f, -5.25f + 1.25f * (capturedPieces.size()-1));
        }
        board[newPoint.y][newPoint.x] = board[oldPoint.y][oldPoint.x];
        board[oldPoint.y][oldPoint.x] = null;
        board[newPoint.y][newPoint.x].movePiece(newPoint);
        FileController.parseMoveToAlgebraic(movingPiece, newPoint);
        return rook;
    }

    
    public static ArrayList<Point> checkForValidMoves(Point oldPoint) {
        Piece piece = board[oldPoint.y][oldPoint.x];
        if (piece instanceof King) {
            if (board[oldPoint.y][0] instanceof Rook) {
                checkForCastle((King)board[oldPoint.y][oldPoint.x], (Rook)board[oldPoint.y][0]);
            }
            if (board[oldPoint.y][7] instanceof Rook) {
                checkForCastle((King)board[oldPoint.y][oldPoint.x], (Rook)board[oldPoint.y][7]);
            }
        }
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

    public static void checkForCastle(King king, Rook rook) {
        boolean canCastle = true;
        boolean isPositive = false;
        if (!king.hasMoved() && !rook.hasMoved()) {
            isPositive = king.getPosition().x - rook.getPosition().x < 0;
            for (int x = king.getPosition().x + (isPositive ? 1: -1); (isPositive)? x < 7 : x > 0;) {
                if (board[king.getPosition().y][x] != null) {
                    canCastle = false;
                }

                
                if (isPositive) x++;
                else x--;
            }
            
        }
        if (canCastle) {
            king.castleMoveSet(isPositive);
        }
    }
    
    private void checkForCheck(King king, ArrayList<Point> attackingMoves) {
        //If a piece moves and the King is in the valid moves, the king is in check
        //The king can ONLY move out of check or another piece needs to move in the way
        //Meaning that the king must know if it's in check and what piece is putting it in check
        //All other pieces CANNOT move until king is out of check
        //If king cannot move and no piece can block, this is checkmate
        
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
