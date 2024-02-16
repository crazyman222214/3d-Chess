/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.neumont.csc.controller;
import edu.neumont.csc.view.*;

/**
 *
 * @author Chris & Michael
 */
public class ChessController {
    ChessView view = new ChessView();
    public void run() {
        view.setupProject(); 
    }
    
    public void createModels() {
        view.createModels();
    }
}
