package project;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import project.ObjLoader;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_BACK;
import static javax.media.opengl.GL.GL_CLAMP_TO_EDGE;
import static javax.media.opengl.GL.GL_COLOR_BUFFER_BIT;
import static javax.media.opengl.GL.GL_DEPTH_TEST;
import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_FRONT_AND_BACK;
import static javax.media.opengl.GL.GL_LINEAR;
import static javax.media.opengl.GL.GL_LINEAR_MIPMAP_LINEAR;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL.GL_TEXTURE_MAG_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_MIN_FILTER;
import static javax.media.opengl.GL.GL_TEXTURE_WRAP_S;
import static javax.media.opengl.GL.GL_TRIANGLES;
import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.GL2GL3.GL_LINE;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT1;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT2;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHTING;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_NORMALIZE;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_CUTOFF;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SPOT_DIRECTION;
import javax.media.opengl.glu.GLU;

/**
 * Control: W, S, A, D, scrolling wheel
 *
 * @author Marketa Hanusova
 */
public class Scene implements GLEventListener {

    private GLU glu = new GLU();
    private GLUT glut = new GLUT();

    // angle of rotation for the camera direction
    private float angle = 0.0f;
    // actual vector representing the camera's direction
    private float lx = 0.0f, ly = 70.0f, lz = -1.0f;
    // XZ position of the camera
    private float x = -100f, y = 70.0f, z = 50f;

    private float fraction = 1f;

    private float height = 10; //height of teacup
    private boolean goDown = false;

    private int handAngle = 0; //height of teacup
    private boolean goLeft = false;
    
    private float[] diffuseLight0;
    private float[] positionLight0;
    private float[] specularLight0;

    private float[] diffuseLight1;
    private float[] positionLight1;
    private float[] specularLight1;

    private ObjLoader model1;
    private ObjLoader model2;
    private ObjLoader model3;
    private ObjLoader model4;
    private ObjLoader model5;
    private ObjLoader model6;
    private ObjLoader model7;
    private ObjLoader model8;
    private ObjLoader model9;

    private Texture wood;
    private Texture carpet;
    private Texture flowers;
    private Texture geometry;
    private Texture organic;
    private Texture painting;

    public Scene() {
        model1 = new ObjLoader("/resources/lamp.obj");
        model2 = new ObjLoader("/resources/FLoor.obj");
        model3 = new ObjLoader("/resources/table.obj");
        model4 = new ObjLoader("/resources/metronome.obj");
        model5 = new ObjLoader("/resources/notebook.obj");
        model6 = new ObjLoader("/resources/notebook-display.obj");
        model7 = new ObjLoader("/resources/cabinet.obj");
        model8 = new ObjLoader("/resources/cabinet-leftdoor.obj");
        model9 = new ObjLoader("/resources/metronome-hand.obj");

        model1.load();
        model2.load();
        model3.load();
        model4.load();
        model5.load();
        model6.load();
        model7.load();
        model8.load();
        model9.load();
        
        diffuseLight0 = new float[]{1f, 1f, 1f};
        positionLight0 = new float[]{-10, 100, 20, 0};
        specularLight0 = new float[]{1, 1, 1};

        diffuseLight1 = new float[]{1f, 0f, 1f};
        positionLight1 = new float[]{0, 0, 4, 0};
        specularLight1 = new float[]{0, 1, 0};
    }

    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();

        // BACKGROUND
        gl.glClearColor(0f, 0.4f, 0.7f, 0.0f);

        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_LIGHT0);
        //gl.glEnable(GL_LIGHT1);
        //gl.glEnable(GL_LIGHT2);
        gl.glCullFace(GL_BACK);
        gl.glEnable(GL_NORMALIZE);
        gl.glEnable(GL_TEXTURE_2D);

        gl.glEnable(GL_DEPTH_TEST);

        // to c chceme emsnit / jakou slozku / pole floutou/ a 0 
        gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLight0, 0);
    //    gl.glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, new float[]{0, -1, 0}, 0);
        gl.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 20.0f);
        gl.glLightfv(GL_LIGHT0, GL_SPECULAR, specularLight0, 0);
      //  gl.glLightfv(GL_LIGHT0, GL_POSITION, positionLight0, 0);
        
        /*gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, diffuseLight1, 1);
        gl.glLightfv(GL_LIGHT1, GL_SPECULAR, specularLight1, 1);
        // gl.glLightfv(GL_LIGHT1, GL_POSITION, positionLight1, 1);*/
        

        

        // TEXTURES
        String name = "";
        try {
            name = "/resources/wood.jpg";
            wood = loadTexture(gl, name, null);
            name = "/resources/carpet.jpg";
            carpet = loadTexture(gl, name, null);
            name = "/resources/flowers.jpg";
            flowers = loadTexture(gl, name, null);
            name = "/resources/geometry.jpg";
            geometry = loadTexture(gl, name, null);
            name = "/resources/organic.jpg";
            organic = loadTexture(gl, name, null);
            name = "/resources/painting.jpg";
            painting = loadTexture(gl, name, null);
        } catch (IOException ex) {
            System.err.println("File not found:" + name);
        }

    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void display(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();

        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        gl.glColor3f(0.5f, 0f, 0.4f);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45, glad.getSurfaceWidth() / glad.getSurfaceHeight(), 1, 1000);

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        glu.gluLookAt(x, y, z,
                x + lx, ly, z + lz,
                0.0f, 1.0f, 0.0f);
        
        // LIGHTS SOURCES
        gl.glPushMatrix();
        gl.glTranslatef(positionLight0[0], positionLight0[1], positionLight0[2]);
        glut.glutSolidSphere(20, 5, 5);
        gl.glPopMatrix();

        //FLOOR
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.25f, 0.25f, 0.25f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.4f, 0.4f, 0.4f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0.774597f, 0.774597f, 0.774597f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 76.8f);

        // FLOOR       
        gl.glPushMatrix();

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.18f, 0.18f, 0.36f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.18f, 0.18f, 0.11f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0.50f, 0.66f, 0.89f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 121.8f);

        gl.glTranslatef(-10f, 0, 20);
        drawObj(gl, model2, carpet);
        gl.glPopMatrix();

        // TABLE WITH STUFF
        gl.glPushMatrix();

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.25f, 0.25f, 0.25f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.4f, 0.4f, 0.4f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0.774597f, 0.774597f, 0.774597f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 76.8f);

        gl.glTranslatef(-50.0f, 0, -100);
        drawObj(gl, model3, wood);                    //table

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.00f, 0.30f, 0.30f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.00f, 0.25f, 0.42f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        gl.glTranslatef(40f, 71f, -10);
        drawObj(gl, model1);                    //lamp
        
        
        

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.00f, 0.30f, 0.30f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.00f, 0.52f, 0.39f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        gl.glTranslatef(-70f, -5f, 0);
        drawObj(gl, model4, organic);                    //metronom

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.00f, 0.20f, 0.20f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.00f, 0.10f, 0.10f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0.1f, 0.2f, 0.3f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        gl.glPushMatrix();
        gl.glRotatef(handAngle, 0, 0, 1);
        drawObj(gl, model9);

        gl.glTranslatef(0f, height, 0);

        if (handAngle > 6) {
            goLeft = true;
        }
        if (handAngle < -6) {
            goLeft = false;
        }

        if (goLeft == false) {
            handAngle++;
        } else {
            handAngle--;
        }
        gl.glPopMatrix();

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.25f, 0.25f, 0.25f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.4f, 0.4f, 0.4f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{0.774597f, 0.774597f, 0.774597f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 76.8f);

        gl.glTranslatef(30f, 5f, 15);
        drawObj(gl, model5);                    //notebook
        drawObj(gl, model6);
        gl.glPopMatrix();

        gl.glPushMatrix();

        gl.glTranslatef(80f, 0f, 150);
        gl.glRotatef(180, 0, 1, 0);

        drawObj(gl, model7, wood);                    //cabinet
        drawObj(gl, model8, wood);

        gl.glTranslatef(0f, height, 0);
        if (height > 170) {
            goDown = true;
        }
        if (height <= 10) {
            goDown = false;
        }

        if (goDown == false) {
            height++;
        } else {
            height--;
        }

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.00f, 0.52f, 0.64f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.18f, 0.36f, 0.42f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        glut.glutSolidTeapot(12);
        gl.glPopMatrix();

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.40f, 0.10f, 0.64f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.30f, 0.10f, 0.42f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        gl.glPushMatrix();
        gl.glTranslatef(-100, 10, 150);
        drawCube(gl, flowers);

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.20f, 0.70f, 0.20f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.10f, 0.60f, 0.30f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        gl.glRotatef(30, 0, 1, 0);
        gl.glTranslatef(0, 20, 0);
        drawCube(gl, geometry);

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.10f, 0.10f, 0.30f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.10f, 0.10f, 0.30f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 128f);

        gl.glRotatef(-20, 0, 1, 0);
        gl.glTranslatef(0, 20, 0);
        drawCube(gl, organic);

        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{0.40f, 0.40f, 0.10f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{0.30f, 0.30f, 0.10f}, 0);
        gl.glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{1f, 1f, 1f}, 0);
        gl.glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 20f);

        gl.glRotatef(-30, 0, 1, 0);
        gl.glTranslatef(0, 20, 0);
        drawCube(gl, painting);

        gl.glPopMatrix();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL_SMOOTH);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(55, (float) width / height, 1, 1000);

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL_MODELVIEW);

    }

    private void drawObj(GL2 gl, ObjLoader model) {
        gl.glShadeModel(GL_SMOOTH);
        for (int i = 0; i < model.getVertexIndices().size(); i++) {

            int[] index = model.getVertexIndices().get(i);
            int[] normalIndex = model.getNormalIndices().get(i);

            gl.glBegin(GL_TRIANGLES);
            float[] vertex = model.getVertices().get(index[0]);
            float[] normal = model.getNormals().get(normalIndex[0]);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glVertex3f(vertex[0], vertex[1], vertex[2]);

            vertex = model.getVertices().get(index[1]);
            normal = model.getNormals().get(normalIndex[1]);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glVertex3f(vertex[0], vertex[1], vertex[2]);

            vertex = model.getVertices().get(index[2]);
            normal = model.getNormals().get(normalIndex[2]);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glVertex3f(vertex[0], vertex[1], vertex[2]);

            gl.glEnd();
        }
    }
    
    private void drawObj(GL2 gl, ObjLoader model, Texture textureName) {
        textureName.bind(gl);
        gl.glShadeModel(GL_SMOOTH);
        TextureCoords tc = textureName.getImageTexCoords();
        for (int i = 0; i < model.getVertexIndices().size(); i++) {

            int[] index = model.getVertexIndices().get(i);
            int[] textureIndex = model.getTextureIndices().get(i);
            int[] normalIndex = model.getNormalIndices().get(i);

            gl.glBegin(GL_TRIANGLES);
            float[] vertex = model.getVertices().get(index[0]);
            float[] texture = model.getTextures().get(textureIndex[0]);
            float[] normal = model.getNormals().get(normalIndex[0]);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glTexCoord2f(texture[0], texture[1]);
            gl.glVertex3f(vertex[0], vertex[1], vertex[2]);

            vertex = model.getVertices().get(index[1]);
            texture = model.getTextures().get(textureIndex[1]);
            normal = model.getNormals().get(normalIndex[1]);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glTexCoord2f(texture[0], texture[1]);
            gl.glVertex3f(vertex[0], vertex[1], vertex[2]);

            vertex = model.getVertices().get(index[2]);
            texture = model.getTextures().get(textureIndex[2]);
            normal = model.getNormals().get(normalIndex[2]);
            gl.glNormal3f(normal[0], normal[1], normal[2]);
            gl.glTexCoord2f(texture[0], texture[1]);
            gl.glVertex3f(vertex[0], vertex[1], vertex[2]);

            gl.glEnd();
        }
    }
    
    private Texture loadTexture(GL2 gl, String filename, String suffix) throws IOException {
        try {
            InputStream is = Scene.class.getResourceAsStream(filename);
            if (is != null) {
                Texture tex = TextureIO.newTexture(is, true, suffix);

                tex.setTexParameteri(gl, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
                tex.setTexParameteri(gl, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
                //tex.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
                //tex.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
                //tex.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
                //tex.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
                tex.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
                //tex.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

                return tex;
            } else {
                throw new IOException("File not found!");
            }

        } catch (NullPointerException ex) {
            System.err.println("filename was null!");
        }
        return null;
    }

    private void drawCube(GL2 gl, Texture textureName) {

        //gl.glActiveTexture(GL_TEXTURE0);
        //dice1.bind(gl);
        //dice1.enable(gl);
        textureName.bind(gl);

        gl.glBegin(GL_QUADS);

        TextureCoords tc = textureName.getImageTexCoords();

        //predna stena
        gl.glNormal3f(0, 0, 1f);
        //gl.glTexCoord2f(0.0f, 0.0f);
        gl.glTexCoord2f(tc.left(), tc.bottom());
        gl.glVertex3f(-10f, -10f, 10f);

//        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glTexCoord2f(tc.right(), tc.bottom());
        gl.glVertex3f(10f, -10f, 10f);

//        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glTexCoord2f(tc.right(), tc.top());
        gl.glVertex3f(10f, 10f, 10f);

//        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glTexCoord2f(tc.left(), tc.top());
        gl.glVertex3f(-10f, 10f, 10f);

        //zadna stena
        gl.glNormal3f(0, 0, -1f);
        //gl.glTexCoord2f(0.0f, 0.0f);
        gl.glTexCoord2f(tc.left(), tc.bottom());
        gl.glVertex3f(-10f, 10f, -10f);

        //gl.glTexCoord2f(1.0f, 0.0f);
        gl.glTexCoord2f(tc.right(), tc.bottom());
        gl.glVertex3f(10f, 10f, -10f);

        //gl.glTexCoord2f(1.0f, 1.0f);
        gl.glTexCoord2f(tc.right(), tc.top());
        gl.glVertex3f(10f, -10f, -10f);

        //gl.glTexCoord2f(0.0f, 1.0f);
        gl.glTexCoord2f(tc.left(), tc.top());
        gl.glVertex3f(-10f, -10f, -10f);

        //prava stena
        gl.glNormal3f(1, 0, 0);
        gl.glTexCoord2f(tc.left(), tc.bottom());
        gl.glVertex3f(10f, -10f, -10f);
        gl.glTexCoord2f(tc.right(), tc.bottom());
        gl.glVertex3f(10f, 10f, -10f);
        gl.glTexCoord2f(tc.right(), tc.top());
        gl.glVertex3f(10f, 10f, 10f);
        gl.glTexCoord2f(tc.left(), tc.top());
        gl.glVertex3f(10f, -10f, 10f);

        //lava stena
        gl.glNormal3f(-1, 0, 0);
        gl.glTexCoord2f(tc.left(), tc.bottom());
        gl.glVertex3f(-10f, -10f, 10f);
        gl.glTexCoord2f(tc.right(), tc.bottom());
        gl.glVertex3f(-10f, 10f, 10f);
        gl.glTexCoord2f(tc.right(), tc.top());
        gl.glVertex3f(-10f, 10f, -10f);
        gl.glTexCoord2f(tc.left(), tc.top());
        gl.glVertex3f(-10f, -10f, -10f);

        //horna stena
        gl.glNormal3f(0, 1, 0);
        gl.glVertex3f(-10f, 10f, 10f);
        gl.glVertex3f(10f, 10f, 10f);
        gl.glVertex3f(10f, 10f, -10f);
        gl.glVertex3f(-10f, 10f, -10f);

        //dolna stena
        gl.glNormal3f(0, -1, 0);
        gl.glVertex3f(-10f, -10f, -10f);
        gl.glVertex3f(10f, -10f, -10f);
        gl.glVertex3f(10f, -10f, 10f);
        gl.glVertex3f(-10f, -10f, 10f);

        gl.glEnd();
    }
    
    void goBack() {
        x -= lx * fraction;
        z -= lz * fraction;
    }

    void goStraight() {
        x += lx * fraction;
        z += lz * fraction;
    }

    void turnRight() {
        angle += 0.05f;
        lx = (float) sin(angle);
        lz = (float) -cos(angle);
    }

    void turnLeft() {
        angle -= 0.05f;
        lx = (float) sin(angle);
        lz = (float) -cos(angle);
    }

    void goUp() {
        ly++;
        y++;
    }

    void goDown() {
        ly--;
        y--;
    }

    void goRight() {
        x -= lz * fraction;
        z -= lz * fraction;
    }

    void goLeft() {
        x += lx * fraction;
        z += ly * fraction;
    }

}
