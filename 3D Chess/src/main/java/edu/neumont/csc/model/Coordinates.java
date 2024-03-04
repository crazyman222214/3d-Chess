/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package edu.neumont.csc.model;

/**
 *
 * @author cschade
 */
public enum Coordinates {
    A("a"),B("b"),C("c"),D("d"),E("e"),F("f"),G("g"),H("h");
    private String prettyName;
    Coordinates(String prettyName) {
        setPrettyName(prettyName);
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }
}
