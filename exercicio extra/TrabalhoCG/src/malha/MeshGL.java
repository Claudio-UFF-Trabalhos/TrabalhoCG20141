/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package malha;

// java imports
import com.trabalho.armazenamento.Face;
import com.trabalho.armazenamento.H_Edge;
import com.trabalho.armazenamento.Vertice;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import util.math.Matrix4f;
import util.math.Vector4f;
import util.shader.ObjectGL;
import util.shader.ShaderProgram;

/**
 *
 * @author marcoslage
 */
public class MeshGL extends Mesh implements ObjectGL {
  
    // Vertex Array Object Id
    private int vaoHandle;
    // Shader Program
    private ShaderProgram shader;
    // Buffer with the Positions
    private FloatBuffer positionBuffer;
    // Buffer with the Colors
    private FloatBuffer posiBuffer;
    
    // Vector Id
    private int vectorId = -1;
    // Matrix Id
    private int matrixId = -1;
    // Buffer with the Matrix uniform
    private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
  
    public MeshGL(String name){
        super(name);
    }

    @Override
    public void fillVBOs() {
        // convert vertex array to buffer
        positionBuffer = BufferUtils.createFloatBuffer(4 * 3 * nfaces); //4(coordinates)*3(vertices)*nfaces(triangles)
        // convert color array to buffer
        posiBuffer = BufferUtils.createFloatBuffer(4 * 3 * nfaces); //4(coordinates)*3(vertices)*nfaces(triangles)
        
        for(int i=0; i<faces.size(); i++){
            
            H_Edge h0 = faces.get(i).gethEdge();
            H_Edge h1 = faces.get(i).gethEdge().getHeNext();
            H_Edge h2 = faces.get(i).gethEdge().getHeNext().getHeNext();
            
            Vertice vertex1 = h0.getpOrigem();
            Vertice vertex2 = h1.getpOrigem();
            Vertice vertex3 = h2.getpOrigem();
            
            vertex1.getP().store(positionBuffer);
            vertex2.getP().store(positionBuffer);
            vertex3.getP().store(positionBuffer);
            
            vertex1.getNormal().store(posiBuffer);
            vertex2.getNormal().store(posiBuffer);
            vertex3.getNormal().store(posiBuffer);          
            
            /*
            int a = trigs.get( i );
            int b = trigs.get(i+1);
            int c = trigs.get(i+2);
            
            positions.get(a).store(positionBuffer);
            positions.get(b).store(positionBuffer);
            positions.get(c).store(positionBuffer);
            
            normals.get(a).store(posiBuffer);
            normals.get(b).store(posiBuffer);
            normals.get(c).store(posiBuffer);
            */
        }
        
        positionBuffer.flip();
        posiBuffer.flip();
    }
    
    @Override
    public void fillVAOs() {
        // fills the VBOs
        fillVBOs();

        // create vertex byffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // create VBO for color values
        int normalBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, posiBuffer, GL15.GL_STATIC_DRAW);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // create vertex array object (VAO)
        vaoHandle = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // assign vertex VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, normalBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
 
    @Override
    public void loadShaders() {
        // compile and link vertex and fragment shaders into
        // a "program" that resides in the OpenGL driver
        shader = new ShaderProgram();

        // do the heavy lifting of loading, compiling and linking
        // the two shaders into a usable shader program
        shader.init("shaders/phong.vert", "shaders/phong.frag");
 
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());
    }

    @Override
    public void render() {
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());        

        // bind vertex and color data
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0); // VertexPosition
        GL20.glEnableVertexAttribArray(1); // VertexColor        

        // draw VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3*nfaces);
    }

    public void setMatrix(String nameMatrix, Matrix4f matrix) {
        // converts from matrix to FloatBuffer
        matrixBuffer.clear();
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        
        // defines the uniform variable
        matrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
        GL20.glUniformMatrix4(matrixId, false, matrixBuffer);
    }
    
    public void setVector(String nameVector, Vector4f vector, int tipo) {
        // defines the uniform variable
        vectorId = GL20.glGetUniformLocation(shader.getProgramId(), nameVector);
        if(tipo == 4)
        {
            GL20.glUniform4f(vectorId, vector.x, vector.y, vector.z, vector.w);
        }else if(tipo == 3){
            GL20.glUniform3f(vectorId, vector.x, vector.y, vector.z);
        }
    }    
}
