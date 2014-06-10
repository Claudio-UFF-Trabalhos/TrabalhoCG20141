/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.projection;

import util.math.FastMath;
import util.math.Matrix4f;

/**
 *
 * @author claudio
 */
public class ProjectionOrtho implements project{
    
    private float fovY   = 0.0f;
    private float aspect = 0.0f;
    private float near  = 0.0f;
    private float far   = 0.0f;

    public ProjectionOrtho(float fovY, float aspect, float zNear, float zFar) {
        this.fovY   = fovY;
        this.aspect = aspect;
        this.near  = zNear;
        this.far   = zFar;
    }
    
    @Override
    public Matrix4f perspective()
    {
        Matrix4f aux = new Matrix4f();
        
        float angle = fovY * FastMath.DEG_TO_RAD;
        float tangent = FastMath.sin(angle) / FastMath.cos(angle);
        
        float top    = near * tangent; 
        float right  = top * aspect;  
        
        float left = -right;
        float bottom = -top;
        
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
