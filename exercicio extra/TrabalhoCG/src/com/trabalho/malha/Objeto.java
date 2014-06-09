/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trabalho.malha;

import com.trabalho.Util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.math.Vector4f;

/**
 *
 * @author claudio
 */
public class Objeto {
    private final String dirArquivo;
    protected ArrayList<Vector4f> normals;
    protected ArrayList<Vector4f> positions;
    protected ArrayList<Integer> trigs;
    
    protected int nverts;
    protected int nfaces;
    
    protected Scanner in;
    
    public Objeto(String dirArquivo)
    {
        this.dirArquivo = dirArquivo;
    }
    
    public void carregaArquivo()
    {
        try {
            in = new Scanner(new File(dirArquivo));
            
            System.out.println(in.nextLine()); 
            this.nverts = in.nextInt();
            this.nfaces = in.nextInt();
        
            positions = new ArrayList<>(nverts);
            normals = new ArrayList<>(nverts);
            trigs = new ArrayList<>();
            
            for (int i = 0; i < nverts; i++) 
            {
                float x = Float.parseFloat(in.next());
                float y = Float.parseFloat(in.next());
                float z = Float.parseFloat(in.next());
                positions.add( new Vector4f(x,y,z,1.0f) );
            }
            
            for (int i = 0; i < nfaces; i++) 
            {
                int j = Integer.parseInt( in.next() );
                
                if( j == 3)
                {
                    int a = Integer.parseInt( in.next() );
                    int b = Integer.parseInt( in.next() );
                    int c = Integer.parseInt( in.next() );
                    
                    trigs.add(a);
                    trigs.add(b);
                    trigs.add(c);
                    
                }else if( j == 4)
                {
                    int a = Integer.parseInt( in.next() );
                    int b = Integer.parseInt( in.next() );
                    int c = Integer.parseInt( in.next() );
                    int d = Integer.parseInt( in.next() );
                    
                    trigs.add(a);
                    trigs.add(b);
                    trigs.add(c);
                    
                    trigs.add(b);
                    trigs.add(c);
                    trigs.add(d);
                }
            }
            
            in.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Objeto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void calculateNormal()
    {        
        for (int i = 0; i < trigs.size(); i+=3 ) {
            int a = trigs.get(i);
            int b = trigs.get(i+1);
            int c = trigs.get(i+2);
        
            Vector4f normal = getNormalVector(a, b, c);

            normals.add(a, normal);
            normals.add(b, normal);
            normals.add(c, normal);
        }
        
    }
    
    private Vector4f getNormalVector(int verticeA, int verticeB, int verticeC)
    {
        int vetAx = (int) (positions.get(verticeB).x - positions.get(verticeA).x);
        int vetAy = (int) (positions.get(verticeB).y - positions.get(verticeA).y);
        int vetAz = (int) (positions.get(verticeB).z - positions.get(verticeA).z);
        Vector4f vectorA = new Vector4f(vetAx, vetAy, vetAz, 0.0f);
        
        int vetBx = (int) (positions.get(verticeC).x - positions.get(verticeA).x);
        int vetBy = (int) (positions.get(verticeC).y - positions.get(verticeA).y);
        int vetBz = (int) (positions.get(verticeC).z - positions.get(verticeA).z);
        Vector4f vectorB = new Vector4f(vetBx, vetBy, vetBz, 0.0f);
        
        Vector4f normal = Util.vectorialProduct(vectorB, vectorA);
        
        return normal;
    }
}
