/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.controller;
import edu.neumont.csc.model.*;
import edu.neumont.csc.view.*;
import java.awt.Point;

/**
 *
 * @author Chris & Michael
 */
public class ChessController {
    Piece[][] board = new Piece[8][8];
    
    ChessView view = new ChessView();
    public void run() {
        view.setupProject();
    }
}
