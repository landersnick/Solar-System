package assignment6;





/**
 *
 * @author Hong Zhang
 */
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
//for some reason when I change the name from demo to assignment6, it will no longer run
//I am still very unclear why this is. 
public class Assignment6 extends Applet {

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true"); 
        new MainFrame(new Assignment6(), 640, 480);
    }

    public void init() {
        // set applet background to blue
    	
        setBackground(Color.BLUE);

        // create canvas
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D cv = new Canvas3D(gc);
        cv.setBackground(Color.BLUE); // set canvas background to blue
        setLayout(new BorderLayout());
        add(cv, BorderLayout.CENTER);
        BranchGroup bg = createSceneGraph();
        bg.compile();
        SimpleUniverse su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        // set blue background
        Background bg = new Background(new Color3f(Color.BLUE));
        bg.setCapability(Background.ALLOW_COLOR_WRITE);
        bg.setApplicationBounds(new BoundingSphere());
        root.addChild(bg);

        //object
        Appearance ap = new Appearance();
        ap.setMaterial(new Material());
        Font3D font = new Font3D(new Font("SansSerif", Font.PLAIN, 1), new FontExtrusion());
        
        //there is probably a better way to center this than 4 spaces in front of Nick 
        Text3D text1 = new Text3D(font, "   Nick");
        Text3D text2 = new Text3D(font, "Landers");

        Shape3D shape1 = new Shape3D(text1, ap);
        Shape3D shape2 = new Shape3D(text2, ap);

        //transformation
        Transform3D tr1 = new Transform3D();
        tr1.setScale(0.5);
        tr1.setTranslation(new Vector3f(-0.95f, -0.1f, 0f)); 
        TransformGroup tg1 = new TransformGroup(tr1);

        //transform 2 
        Transform3D tr2 = new Transform3D();
        tr2.setScale(0.5);
        tr2.setTranslation(new Vector3f(-0.95f, -0.6f, 0f));
        TransformGroup tg2 = new TransformGroup(tr2);

        root.addChild(tg1);
        tg1.addChild(shape1);

        root.addChild(tg2);
        tg2.addChild(shape2);

        //light
        PointLight light = new PointLight(new Color3f(Color.white), new Point3f(1f,1f,1f), new Point3f(1f,0.1f,0f));
        BoundingSphere bounds = new BoundingSphere();
        light.setInfluencingBounds(bounds);
        root.addChild(light);

        return root;
    }

}
