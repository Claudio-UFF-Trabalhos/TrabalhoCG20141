/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.projection;

import util.math.Matrix4f;

/**
 *
 * @author claudio
 */
public class ProjectionOrtho implements project{
    private float left;
    private float right;
    private float top;
    private float bottom;
    private float near;
    private float far;

    public ProjectionOrtho(float left, float right, float top, float bottom, float near, float far) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.near = near;
        this.far = far;
    }
    
    @Override
    public Matrix4f perspective()
    {
        Matrix4f aux = new Matrix4f();
        
        /* Primeira Linha */
        aux.m11 = 2/(right-left);
        aux.m12 = 0;
        aux.m13 = 0;
        aux.m14 = -(left+right)/(right-left);
        
        /* Segunda Linha */
        aux.m21 = 0;
        aux.m22 = 2/(top-bottom);
        aux.m23 = 0;
        aux.m24 = -(top+bottom)/(top-bottom);
        
        /* Terceira Linha */
        aux.m31 = 0;
        aux.m32 = 0;
        aux.m33 = -2/(far-near);
        aux.m34 = -(far+near)/(far-near);
        
        /* Quarta Linha */
        aux.m41 = 0;
        aux.m42 = 0;
        aux.m43 = 0;
        aux.m44 = 1;
        
        return aux;
    }

}
