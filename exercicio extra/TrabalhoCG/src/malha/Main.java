package malha;

/**
 *
 * @author marcoslage
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector4f;
import util.projection.Projection;
import util.projection.ProjectionOrtho;
import util.projection.project;

public class Main{

    // Creates a new cube
    private final MeshGL mesh = new MeshGL("malhas/bunny.off");

    // Animation:
    private float currentAngle = 0.0f;
    private float currentScale = 1.0f;
    private float factor = 0.005f;
    
    // Projection Matrix
    private project proj = new Projection(45, 1.3333f, 1.0f, 100.0f);
    //private final project proj = new ProjectionOrtho(-4, 4, 4, -4, 4, -4);
    
    // View Matrix
    private final Vector4f eye = new Vector4f( 0.0f, 0.0f, 3.0f, 1.0f);
    private final Vector4f at  = new Vector4f( 0.0f, 0.0f, 0.0f, 1.0f);
    private final Vector4f up  = new Vector4f( 0.0f, 1.0f, 0.0f, 1.0f);
    
    
    // View Matrix
    private final Vector4f eye1 = new Vector4f( 0.0f, 3.0f, 0.0f, 1.0f);
    private final Vector4f at1  = new Vector4f( 0.0f, 0.0f, 0.0f, 1.0f);
    private final Vector4f up1  = new Vector4f( 0.0f, 0.0f, 1.0f, 1.0f);
    
    // Camera
    private Camera cam = new Camera(eye, at, up);
    //private final Camera cam = new Camera(eye1, at1, up1);

    // Rotation Matrix:
    private final Matrix4f rotationMatrixZ = new Matrix4f();
    private final Matrix4f rotationMatrixY = new Matrix4f();
    // Translation Matrix:
    private final Matrix4f translationMatrix = new Matrix4f();
    private final Matrix4f translationMatrix1 = new Matrix4f();
    // Scale Matrix:
    private final Matrix4f scaleMatrix = new Matrix4f();
    
    // Model Matrix
    private final Matrix4f modelViewMatrix  = new Matrix4f();
    // NormalMatrix Matrix
    private final Matrix4f normalMatrix     = new Matrix4f();
    // Projection Matrix
    private final Matrix4f projectionMatrix = new Matrix4f();
       
    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width = 640;
        int height = 480;

        // set up window and display
        DisplayMode display = new DisplayMode(width, height);
        Display.setDisplayMode(display);
        
        
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
                
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
    }

    public void run() throws InterruptedException {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        mesh.fillVAOs();
        mesh.loadShaders();
       
        // Model Matrix setup
        translationMatrix.m42 = -1.0f;
        translationMatrix1.m43 = -10.0f;
        
        if(mesh.isBunny())
        {
            currentScale = 0.1f;
        }
        
        boolean typeCam = true;
        boolean typeProjection = true;
        
        while (Display.isCloseRequested() == false) {
            
            currentAngle += 0.01f;
            float c = FastMath.cos(currentAngle);
            float s = FastMath.sin(currentAngle);

            // first rotation
            rotationMatrixZ.m11 = c; rotationMatrixZ.m21 = s;
            rotationMatrixZ.m12 =-s; rotationMatrixZ.m22 = c;
            // second rotation
            rotationMatrixY.m11 = c; rotationMatrixY.m31 = s;
            rotationMatrixY.m13 =-s; rotationMatrixY.m33 = c;
            
            if( typeCam )
            {
                eye.x = 3*c;
                eye.z = 3*s;
            }
            
            if(Keyboard.isKeyDown(Keyboard.KEY_C)){
                typeCam = !typeCam;
                Thread.sleep(160);
            }
            
            if(!typeCam){
                cam = new Camera(eye1, at1, up1);
                mesh.setVector("eyePosition", eye1,4);
            }else{
                cam = new Camera(eye, at, up);
                mesh.setVector("eyePosition", eye,4);
            }


            if(Keyboard.isKeyDown(Keyboard.KEY_P)){
                typeProjection = !typeProjection;
                Thread.sleep(160);
            }
            
            if(!typeProjection){
                proj = new ProjectionOrtho(45, 1.3333f, 1.0f, 100.0f);
            }else{
                proj = new Projection(45, 1.3333f, 1.0f, 100.0f);
            }
            

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            
            projectionMatrix.setTo(proj.perspective());            
            modelViewMatrix.setTo(cam.viewMatrix());
            
            
            if(!typeCam){
                //modelViewMatrix.multiply(rotationMatrixY);
                System.out.println("");
            }else{
                if(mesh.isBunny()){
                    modelViewMatrix.multiply(translationMatrix);
                }
                //modelViewMatrix.multiply(translationMatrix1);   
            }
                     
            // scale
            scaleMatrix.m11 = currentScale;
            scaleMatrix.m22 = currentScale;
            scaleMatrix.m33 = currentScale;
 
            //matrix stack
            //modelViewMatrix.multiply(rotationMatrixY);
            modelViewMatrix.multiply(scaleMatrix);

            normalMatrix.setTo(modelViewMatrix);
            normalMatrix.inverse();
            normalMatrix.transpose();
            
            mesh.setMatrix("NormalMatrix", normalMatrix);
            mesh.setMatrix("ModelViewMatrix", modelViewMatrix);
            mesh.setMatrix("ProjectionMatrix", projectionMatrix);
                       
            
            mesh.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * main method to run the example
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException, InterruptedException {
        Main example = new Main();
        example.initGl();
        example.run();
    }    
}
