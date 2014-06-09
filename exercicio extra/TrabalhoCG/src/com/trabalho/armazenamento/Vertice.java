/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trabalho.armazenamento;

import util.math.Vector4f;

/**
 *
 * @author claudio
 */
public class Vertice {
    private Vector4f p;
    private Vector4f normal;
    private H_Edge hEdge;

    public Vertice(Vector4f p, H_Edge hEdge) {
        this.p = p;
        this.hEdge = hEdge;
        normal = new Vector4f();
        normal.x = 0.0f;
        normal.y = 0.0f;
        normal.z = 0.0f;
        normal.w = 0.0f;
    }
    
    public Vertice(Vector4f copy)
    {
        this.p = copy;
        this.hEdge = null;
        normal = new Vector4f();
        normal.x = 0.0f;
        normal.y = 0.0f;
        normal.z = 0.0f;
        normal.w = 0.0f;
    }
    
    public Vertice(Vertice copy){
        this.p = copy.getP();
        this.hEdge = copy.gethEdge();
        normal = new Vector4f();
        normal.x = 0.0f;
        normal.y = 0.0f;
        normal.z = 0.0f;
        normal.w = 0.0f;
    }

    public Vector4f getP() {
        return p;
    }

    public void setP(Vector4f p) {
        this.p = p;
    }

    public H_Edge gethEdge() {
        return hEdge;
    }

    public void sethEdge(H_Edge hEdge) {
        this.hEdge = hEdge;
    }

    public Vector4f getNormal() {
        return normal;
    }

    public void setNormal(Vector4f normal) {
        this.normal.x+=normal.x;
        this.normal.y+=normal.y;
        this.normal.z+=normal.z;
    }

}
