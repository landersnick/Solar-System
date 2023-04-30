package helloworld;

import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.vecmath.Vector3d;

public class Assignment7 extends Applet {

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        new MainFrame(new Assignment7(), 640, 480);
    }

    public void init() {
        // create canvas
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D cv = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(cv, BorderLayout.CENTER);
        BranchGroup bg = createBranchGraph();
        bg.compile();
        SimpleUniverse su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
    }

    private BranchGroup createBranchGraph() {
        BranchGroup root = new BranchGroup();
        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);
        //object
        Appearance ap = new Appearance();
        ap.setPolygonAttributes(new PolygonAttributes(
                PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0));
        Shape3D shape = new Shape3D(new Octahedron(), ap);
        //transform
        Transform3D tr = new Transform3D();
        tr.setScale(0.25);
        tr.setTranslation(new Vector3d(0, -0.25, 0));
        TransformGroup tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(shape);
        //animation
        Alpha alpha = new Alpha(-1, 4000);
        RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        spin.addChild(rotator);
        return root;
    }
}
