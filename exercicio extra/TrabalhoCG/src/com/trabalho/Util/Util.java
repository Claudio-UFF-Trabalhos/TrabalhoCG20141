/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trabalho.Util;

import util.math.Vector4f;

/**
 *
 * @author claudio
 */
public final class Util {
    
    public static Vector4f vectorialProduct(Vector4f vectorA, Vector4f vectorB)
    {
        Vector4f aux = new Vector4f();
        aux.x = vectorA.y*vectorB.z - vectorA.z*vectorB.y;
        aux.y = vectorA.z*vectorB.x - vectorA.x*vectorB.z;
        aux.z = vectorA.x*vectorB.y - vectorA.y*vectorB.x;
        aux.w = 0;
        
        return aux;
    }
}
