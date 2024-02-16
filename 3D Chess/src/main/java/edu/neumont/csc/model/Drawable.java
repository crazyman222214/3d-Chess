/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.neumont.csc.model;

/**
 * This is going to be the interface that deals with everything that is drawable in the scene<br>
 * Basically Pieces and the Board
 * @author Chris & Michael
 */
public interface Drawable {
    
    /**
     * This forces the concrete class to create it's model, as each different model will have a different file to import from
     */
    void createModel();
    
    
}