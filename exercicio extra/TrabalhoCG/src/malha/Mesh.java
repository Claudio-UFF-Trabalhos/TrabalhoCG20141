/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package malha;

import com.trabalho.armazenamento.Face;
import com.trabalho.armazenamento.H_Edge;
import com.trabalho.armazenamento.HalfEdge;
import com.trabalho.armazenamento.Vertice;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.math.Vector4f;

/**
 *
 * @author marcoslage
 */
public class Mesh {
    protected ArrayList<Vector4f> normals   = new ArrayList<>();
    protected ArrayList<Vector4f> positions = new ArrayList<>();
    protected ArrayList<Integer>  trigs     = new ArrayList<>();
    
    protected ArrayList<Vertice> verts = new ArrayList<>();
    protected ArrayList<Face> faces = new ArrayList<>();
    protected ArrayList<H_Edge> hedges = new ArrayList<>();
    protected HalfEdge he;
    
    protected int nverts ;
    protected int nfaces ;
    
    private String file;
    
    private boolean Bunny;
    
    public Mesh(String file) {
        this.file = file;
        loadMesh(file);
        computeNormals();
    }
    
    public boolean isBunny()
    {
        return this.Bunny;
    }
    
    
    private void isBunnyModel()
    {
        int indexBarra = file.indexOf("/");
        int indexPonto = file.indexOf(".");
        String aux = file.substring(indexBarra+1, indexPonto);
        if(aux.equalsIgnoreCase("Bunny"))
        {
            Bunny = true;
            System.out.println(aux);
        }else{
            Bunny = false;
        }
    }

    private void loadMesh(String file) {
        try {
            isBunnyModel();
            Scanner scan;

            FileReader read = new FileReader(file);
            BufferedReader buf = new BufferedReader(read);

            //Testa se eé um OFF
            String magic = buf.readLine();
            if (!magic.equalsIgnoreCase("OFF")) 
                return;

            scan = new Scanner(buf.readLine());

            //Leitura do número de vertices
            int nvert = Integer.parseInt(scan.next());
            
            boolean novo = true;

            //Faces
            int tmp0 = Integer.parseInt(scan.next());
            if(novo){
                int tmp1 = Integer.parseInt(scan.next());
            }
            
            for (int i = 0; i < nvert; i++) {
                scan = new Scanner(buf.readLine());

                float x = Float.parseFloat(scan.next());
                float y = Float.parseFloat(scan.next());
                float z = Float.parseFloat(scan.next());
                
                
                if(!novo){
                    float nx = Float.parseFloat(scan.next());
                    float ny = Float.parseFloat(scan.next());
                    float nz = Float.parseFloat(scan.next());
                }
                Vector4f p = new Vector4f(x,y,z,1.0f);
                
                Vertice v = new Vertice(p);
                verts.add(v);
                positions.add(p);
                normals.add(null);
            }
            int contfaces = 0;
            String line;
            while ( (line = buf.readLine()) != null ) {
                scan = new Scanner(line);

                int tORq = Integer.parseInt(scan.next());

                if(tORq == 3) {
                    int i0 = Integer.parseInt(scan.next());
                    int i1 = Integer.parseInt(scan.next());
                    int i2 = Integer.parseInt(scan.next());
                    
                    H_Edge h0 = new H_Edge(verts.get(i0));
                    H_Edge h1 = new H_Edge(verts.get(i1));
                    H_Edge h2 = new H_Edge(verts.get(i2));
                    
                    hedges.add(h0);
                    hedges.add(h1);
                    hedges.add(h2);
                    
                    Face a = new Face(h0);
                    
                    
                    h0.setHeNext(h1);
                    h0.setF(a);
                    verts.get(i0).sethEdge(h0);
                    
                    
                    h1.setHeNext(h2);
                    h1.setF(a);
                    verts.get(i1).sethEdge(h1);
                    
                    
                    h2.setHeNext(h0);
                    h2.setF(a);
                    verts.get(i2).sethEdge(h2);
                    
                    faces.add(a);
                    
                    trigs.add(i0);
                    trigs.add(i1);
                    trigs.add(i2);
                    contfaces ++;
                } 
                else if(tORq == 4) {
                    int i0 = Integer.parseInt(scan.next());
                    int i1 = Integer.parseInt(scan.next());
                    int i2 = Integer.parseInt(scan.next());
                    int i3 = Integer.parseInt(scan.next());
                    
                    H_Edge h0 = new H_Edge(verts.get(i0));
                    H_Edge h1 = new H_Edge(verts.get(i1));
                    H_Edge h2 = new H_Edge(verts.get(i2));
                    
                    Face a = new Face(h0);
                    
                    
                    h0.setHeNext(h1);
                    h0.setF(a);
                    verts.get(i0).sethEdge(h0);
                    
                    
                    h1.setHeNext(h2);
                    h1.setF(a);
                    verts.get(i1).sethEdge(h1);
                    
                    
                    h2.setHeNext(h0);
                    h2.setF(a);
                    verts.get(i2).sethEdge(h2);
                    
                    faces.add(a);
                    
                    H_Edge h3 = new H_Edge(verts.get(i0));
                    H_Edge h4 = new H_Edge(verts.get(i2));
                    H_Edge h5 = new H_Edge(verts.get(i3));
                    
                    Face b = new Face(h3);
                    
                    
                    h3.setHeNext(h4);
                    h3.setF(b);
                    
                    
                    h4.setHeNext(h5);
                    h4.setF(b);
                    
                    
                    h5.setHeNext(h3);
                    h5.setF(b);
                    verts.get(i3).sethEdge(h5);
                    
                    faces.add(b);
                    
                    hedges.add(h0);
                    hedges.add(h1);
                    hedges.add(h2);
                    hedges.add(h3);
                    hedges.add(h4);
                    hedges.add(h5);
                    
                    trigs.add(i0);
                    trigs.add(i1);
                    trigs.add(i2);
                    
                    trigs.add(i0);
                    trigs.add(i2);
                    trigs.add(i3);
                    contfaces +=2;
                }else if (tORq > 4){
                    System.out.println(tORq);
                }
            }
            buf.close();
            he = new HalfEdge(verts,faces);
            he.setHedges(hedges);
            nfaces = faces.size();

        } catch (IOException ex) {
            Logger.getLogger(Mesh.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    private void computeNormals() {
        
        normals.ensureCapacity(nverts);
        
        for (int i = 0; i < faces.size(); i++) {
            Face temp = faces.get(i);
            Vector4f vertex1 = temp.gethEdge().getpOrigem().getP();
            Vector4f vertex2 = temp.gethEdge().getHeNext().getpOrigem().getP();
            Vector4f vertex3 = temp.gethEdge().getHeNext().getHeNext().getpOrigem().getP();
            
            Vector4f u = new Vector4f(vertex2.x-vertex1.x, vertex2.y-vertex1.y, vertex2.z-vertex1.z,0);
            Vector4f v = new Vector4f(vertex3.x-vertex1.x, vertex3.y-vertex1.y, vertex3.z-vertex1.z,0);
            
            Vector4f nm = new Vector4f(u.y*v.z-u.z*v.y, u.z*v.x-u.x*v.z, u.x*v.y-u.y*v.x,0);
            
            temp.gethEdge().getpOrigem().setNormal(nm);
            temp.gethEdge().getHeNext().getpOrigem().setNormal(nm);
            temp.gethEdge().getHeNext().getHeNext().getpOrigem().setNormal(nm);
        }
        /*
        for(int i=0; i<trigs.size(); i+=3){
            Vector4f vertex1 = positions.get(trigs.get( i ));
            Vector4f vertex2 = positions.get(trigs.get(i+1));
            Vector4f vertex3 = positions.get(trigs.get(i+2));
            
            Vector4f u = new Vector4f(vertex2.x-vertex1.x, vertex2.y-vertex1.y, vertex2.z-vertex1.z,0);
            Vector4f v = new Vector4f(vertex3.x-vertex1.x, vertex3.y-vertex1.y, vertex3.z-vertex1.z,0);
            
            Vector4f nm = new Vector4f(u.y*v.z-u.z*v.y, u.z*v.x-u.x*v.z, u.x*v.y-u.y*v.x,0);
            
            normals.set(trigs.get( i ), nm);
            normals.set(trigs.get(i+1), nm);
            normals.set(trigs.get(i+2), nm);
        }  
        */    
    }
    
    private void calculaHEDGEIrma(){
        for(int i = 0 ; i < hedges.size();i++ )
        {
            H_Edge oCara = hedges.get(i);
            Vertice Origem = oCara.getpOrigem();
            Vertice Proximo = oCara.getHeNext().getpOrigem();
        }
    }
}
