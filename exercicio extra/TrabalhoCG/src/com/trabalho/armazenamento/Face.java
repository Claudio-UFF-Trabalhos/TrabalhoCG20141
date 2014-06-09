/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trabalho.armazenamento;

/**
 *
 * @author claudio
 */
public class Face {
    private H_Edge hEdge;

    public Face(H_Edge hEdge) {
        this.hEdge = hEdge;
    }
    
    public Face(Face copy){
        this.hEdge = copy.gethEdge();
    }

    public H_Edge gethEdge() {
        return hEdge;
    }

    public void sethEdge(H_Edge hEdge) {
        this.hEdge = hEdge;
    }
    
    
}
