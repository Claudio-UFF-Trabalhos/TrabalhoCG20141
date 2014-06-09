/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trabalho.armazenamento;

import java.util.ArrayList;

/**
 *
 * @author claudio
 */
public class HalfEdge {
    
    private ArrayList<Vertice> verts;
    private ArrayList<Face> faces;
    private ArrayList<H_Edge> hedges;

    public HalfEdge(ArrayList<Vertice> verts, ArrayList<Face> faces) {
        this.verts = verts;
        this.faces = faces;
        this.hedges = null;
    }

    public HalfEdge(ArrayList<Vertice> verts, ArrayList<Face> faces, ArrayList<H_Edge> hedges) {
        this.verts = verts;
        this.faces = faces;
        this.hedges = hedges;
    }
    
    
    
    public HalfEdge(HalfEdge copy){
        this.verts = copy.getVerts();
        this.faces = copy.getFaces();
        this.hedges = copy.getHedges();
    }

    public ArrayList<H_Edge> getHedges() {
        return hedges;
    }

    public void setHedges(ArrayList<H_Edge> hedges) {
        this.hedges = hedges;
    }
        
    public ArrayList<Vertice> getVerts() {
        return verts;
    }

    public void setVerts(ArrayList<Vertice> verts) {
        this.verts = verts;
    }

    public ArrayList<Face> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<Face> faces) {
        this.faces = faces;
    }
}
