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
public class H_Edge {
    private Vertice pOrigem;
    private H_Edge heTwin;
    private H_Edge heNext;
    private Face f;

    public H_Edge(Vertice pOrigem, H_Edge heTwin, H_Edge heNext, Face f) {
        this.pOrigem = pOrigem;
        this.heTwin = heTwin;
        this.heNext = heNext;
        this.f = f;
    }
    
    public H_Edge(Vertice pOrigem){
        this.pOrigem = pOrigem;
        this.heTwin = null;
        this.heNext = null;
        this.f = null;
    }
    
    public H_Edge(H_Edge copy){
        this.pOrigem = copy.getpOrigem();
        this.heTwin = copy.getHeTwin();
        this.heNext = copy.getHeNext();
        this.f = copy.getF();
    }

    public Vertice getpOrigem() {
        return pOrigem;
    }

    public void setpOrigem(Vertice pOrigem) {
        this.pOrigem = pOrigem;
    }

    public H_Edge getHeTwin() {
        return heTwin;
    }

    public void setHeTwin(H_Edge heTwin) {
        this.heTwin = heTwin;
    }

    public H_Edge getHeNext() {
        return heNext;
    }

    public void setHeNext(H_Edge heNext) {
        this.heNext = heNext;
    }

    public Face getF() {
        return f;
    }

    public void setF(Face f) {
        this.f = f;
    }
    
    public boolean isTwin(H_Edge comparada)
    {
       Vertice pOri = heNext.getpOrigem();
       Vertice pNext = getpOrigem();
       
       Vertice CompOri = comparada.getpOrigem();
       Vertice CompNext = comparada.getHeNext().getpOrigem();
       
       return (pOri.isEqual(CompOri) && pNext.isEqual(CompNext));
    }
}
