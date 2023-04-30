
package helloworld;

/**
 *
 * @author Nick Landers, Phil Ware
 */
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.media.j3d.*;
import javax.swing.JButton;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;

public class Demo extends Applet {
	 	private SimpleUniverse su1, su2;
	    private View view1, view2;
	    private Button switchViewButton;
	    private boolean isView1 = true;
	    private View currentView;
	

    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true"); 
        new MainFrame(new Demo(), 640, 480);
    }

    public void init() {
        // create canvas
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D cv = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(cv, BorderLayout.CENTER);
        cv.setBackground(Color.WHITE);
        BranchGroup bg = createSceneGraph();
        bg.compile();
        SimpleUniverse su = new SimpleUniverse(cv);

        // set initial view (angled top-down)
        setAngledTopDownView(su);

        su.addBranchGraph(bg);

        // Declare Buttons
        JButton switchViewButton = new JButton("Switch View");
        JButton toggleOrbitLinesButton = new JButton("Toggle Orbit Lines"); 
        
        add(switchViewButton, BorderLayout.SOUTH);
        add(toggleOrbitLinesButton, BorderLayout.SOUTH);

        // Add action listener to button
        switchViewButton.addActionListener(new ActionListener() {
            private boolean isTopDownView = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isTopDownView) {
                    setAngledTopDownView(su);
                } else {
                    setTopDownView(su);
                } 
                isTopDownView = !isTopDownView;
            }
        });
    
    toggleOrbitLinesButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.println("button clicked");
        }
    }); 
    } 
    private void setAngledTopDownView(SimpleUniverse su) {
        Transform3D viewTransform = new Transform3D();
        viewTransform.lookAt(new Point3d(0, 8, 6), new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
        viewTransform.invert();
        su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTransform);
    }

    private void setTopDownView(SimpleUniverse su) {
        Transform3D viewTransform = new Transform3D();
        viewTransform.lookAt(new Point3d(0, 30, 0), new Point3d(0, 0, 0), new Vector3d(0, 0, -1));
        viewTransform.invert();
        su.getViewingPlatform().getViewPlatformTransform().setTransform(viewTransform);

    }
            

    
    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        // transform to rotate the Sol
        TransformGroup spinSol = new TransformGroup();
        spinSol.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spinSol);
        
        // Sol - sun yellow color sphere
        Appearance solAppearance = new Appearance();
        Material solMaterial = new Material();
        solMaterial.setDiffuseColor(new Color3f(1.0f, 0.9f, 0.0f));
        solAppearance.setMaterial(solMaterial);
        int divisions = 200;
        Sphere sol = new Sphere(0.5f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, solAppearance);
        TransformGroup tgSol = new TransformGroup();
        spinSol.addChild(tgSol);
        tgSol.addChild(sol);
        
     // Mercury - smaller brown sphere
        Appearance mercuryAppearance = new Appearance();
        Material mercuryMaterial = new Material();
        mercuryMaterial.setDiffuseColor(new Color3f(0.6f, 0.4f, 0.2f)); // brown color
        mercuryAppearance.setMaterial(mercuryMaterial);
        Sphere mercury = new Sphere(0.1f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, mercuryAppearance); 
        
        
        TransformGroup orbitMercury = new TransformGroup();
        orbitMercury.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitMercury);
        
        Transform3D mercuryTranslation = new Transform3D();
        mercuryTranslation.setTranslation(new Vector3f(1.5f, 0.0f, 0.0f));
        TransformGroup tgMercury = new TransformGroup(mercuryTranslation);
        orbitMercury.addChild(tgMercury);
        tgMercury.addChild(mercury);
        
        
        
     // Venus - smaller sphere
        Appearance venusAppearance = new Appearance();
        Material venusMaterial = new Material();
        venusMaterial.setDiffuseColor(new Color3f(0.8f, 0.6f, 0.1f)); // color
        venusAppearance.setMaterial(venusMaterial);
        Sphere venus = new Sphere(0.12f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, venusAppearance);

        TransformGroup orbitVenus = new TransformGroup();
        orbitVenus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitVenus);

        Transform3D venusTranslation = new Transform3D();
        venusTranslation.setTranslation(new Vector3f(2.2f, 0.0f, 0.0f));
        TransformGroup tgVenus = new TransformGroup(venusTranslation);
        orbitVenus.addChild(tgVenus);
        tgVenus.addChild(venus);

        
        // Earth - slightly bigger sphere
        Appearance earthAppearance = new Appearance();
        Material earthMaterial = new Material();
        earthMaterial.setDiffuseColor(new Color3f(0.1f, 0.5f, 0.1f)); // color
        earthAppearance.setMaterial(earthMaterial);
        Sphere earth = new Sphere(0.15f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, earthAppearance);

        TransformGroup orbitEarth = new TransformGroup();
        orbitEarth.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitEarth);

        Transform3D earthTranslation = new Transform3D();
        earthTranslation.setTranslation(new Vector3f(2.5f, 0.0f, 0.0f));
        TransformGroup tgEarth = new TransformGroup(earthTranslation);
        orbitEarth.addChild(tgEarth);
        tgEarth.addChild(earth);
        
        
        // Mars - smaller sphere
        Appearance marsAppearance = new Appearance();
        Material marsMaterial = new Material();
        marsMaterial.setDiffuseColor(new Color3f(0.8f, 0.0f, 0.0f)); // color
        marsAppearance.setMaterial(marsMaterial);
        Sphere mars = new Sphere(0.1f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, marsAppearance);

        TransformGroup orbitMars = new TransformGroup();
        orbitMars.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitMars);

        Transform3D marsTranslation = new Transform3D();
        marsTranslation.setTranslation(new Vector3f(3.0f, 0.0f, 0.0f));
        TransformGroup tgMars = new TransformGroup(marsTranslation);
        orbitMars.addChild(tgMars);
        tgMars.addChild(mars);

       
     // Jupiter - larger sphere
        Appearance jupiterAppearance = new Appearance();
        Material jupiterMaterial = new Material();
        jupiterMaterial.setDiffuseColor(new Color3f(0.5f, 0.4f, 0.0f)); // brown color
        jupiterAppearance.setMaterial(jupiterMaterial);
        Sphere jupiter = new Sphere(0.5f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, jupiterAppearance); // Jupiter is the largest planet

        TransformGroup orbitJupiter = new TransformGroup();
        orbitJupiter.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitJupiter);

        Transform3D jupiterTranslation = new Transform3D();
        jupiterTranslation.setTranslation(new Vector3f(5.0f, 0.0f, 0.0f)); // Jupiter is much further away from the Sun than Mars
        TransformGroup tgJupiter = new TransformGroup(jupiterTranslation);
        orbitJupiter.addChild(tgJupiter);
        tgJupiter.addChild(jupiter);

     // Saturn - large sphere
        Appearance saturnAppearance = new Appearance();
        Material saturnMaterial = new Material();
        saturnMaterial.setDiffuseColor(new Color3f(0.9f, 0.7f, 0.4f)); // light brown color
        saturnAppearance.setMaterial(saturnMaterial);
        Sphere saturn = new Sphere(0.65f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, saturnAppearance); // Saturn is smaller than Jupiter

        TransformGroup orbitSaturn = new TransformGroup();
        orbitSaturn.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitSaturn);

        Transform3D saturnTranslation = new Transform3D();
        saturnTranslation.setTranslation(new Vector3f(6.0f, 0.0f, 0.0f)); // Saturn is further away from the Sun than Jupiter
        TransformGroup tgSaturn = new TransformGroup(saturnTranslation);
        orbitSaturn.addChild(tgSaturn);
        tgSaturn.addChild(saturn);

        // Uranus - smaller sphere
        Appearance uranusAppearance = new Appearance();
        Material uranusMaterial = new Material();
        uranusMaterial.setDiffuseColor(new Color3f(0.2f, 0.6f, 0.8f)); // color
        uranusAppearance.setMaterial(uranusMaterial);
        Sphere uranus = new Sphere(0.22f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, uranusAppearance);

        TransformGroup orbitUranus = new TransformGroup();
        orbitUranus.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitUranus);

        Transform3D uranusTranslation = new Transform3D();
        uranusTranslation.setTranslation(new Vector3f(7f, 0.0f, 0.0f));
        TransformGroup tgUranus = new TransformGroup(uranusTranslation);
        orbitUranus.addChild(tgUranus);
        tgUranus.addChild(uranus);

     // Neptune - smaller sphere
        Appearance neptuneAppearance = new Appearance();
        Material neptuneMaterial = new Material();
        neptuneMaterial.setDiffuseColor(new Color3f(0.0f, 0.0f, 0.7f)); // blue color
        neptuneAppearance.setMaterial(neptuneMaterial);
        Sphere neptune = new Sphere(0.1f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, divisions, neptuneAppearance);

        TransformGroup orbitNeptune = new TransformGroup();
        orbitNeptune.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(orbitNeptune);

        Transform3D neptuneTranslation = new Transform3D();
        neptuneTranslation.setTranslation(new Vector3f(8.0f, 0.0f, 0.0f)); // adjust the distance from the sun
        TransformGroup tgNeptune = new TransformGroup(neptuneTranslation);
        orbitNeptune.addChild(tgNeptune);
        tgNeptune.addChild(neptune);
       


        
        
        
        
        // animation - Sol rotation
        Alpha solAlpha = new Alpha(-1, 4000);
        RotationInterpolator solRotator = new RotationInterpolator(solAlpha, spinSol);
        BoundingSphere bounds = new BoundingSphere();
        solRotator.setSchedulingBounds(bounds);
        spinSol.addChild(solRotator);

     // animation - Mercury orbiting Sol
        Alpha mercuryAlpha = new Alpha(-1, 5000); // 5 seconds orbit time
        RotationInterpolator mercuryRotator = new RotationInterpolator(mercuryAlpha, orbitMercury);
        mercuryRotator.setSchedulingBounds(bounds);
        orbitMercury.addChild(mercuryRotator);

        // animation - Venus orbiting Sol
        Alpha venusAlpha = new Alpha(-1, 10000); // 10 seconds orbit time
        RotationInterpolator venusRotator = new RotationInterpolator(venusAlpha, orbitVenus);
        venusRotator.setSchedulingBounds(bounds);
        orbitVenus.addChild(venusRotator);

        // animation - Earth orbiting Sol
        Alpha earthAlpha = new Alpha(-1, 15000); // 15 seconds orbit time
        RotationInterpolator earthRotator = new RotationInterpolator(earthAlpha, orbitEarth);
        earthRotator.setSchedulingBounds(bounds);
        orbitEarth.addChild(earthRotator);

        // animation - Mars orbiting Sol
        Alpha marsAlpha = new Alpha(-1, 20000); // 20 seconds orbit time
        RotationInterpolator marsRotator = new RotationInterpolator(marsAlpha, orbitMars);
        marsRotator.setSchedulingBounds(bounds);
        orbitMars.addChild(marsRotator);
        root.addChild(createPointLight(new Point3f(3.0f, 0.0f, 0.0f))); // Light for Mars
        
        Alpha jupiterAlpha = new Alpha(-1, 12000); // Jupiter's orbit takes about 12 years, so let's represent that as 12 seconds for simplicity
        RotationInterpolator jupiterRotator = new RotationInterpolator(jupiterAlpha, orbitJupiter);
        jupiterRotator.setSchedulingBounds(bounds);
        orbitJupiter.addChild(jupiterRotator);

        Alpha saturnAlpha = new Alpha(-1, 29000); // Saturn's orbit takes about 29 years, so let's represent that as 29 seconds for simplicity
        RotationInterpolator saturnRotator = new RotationInterpolator(saturnAlpha, orbitSaturn);
        saturnRotator.setSchedulingBounds(bounds);
        orbitSaturn.addChild(saturnRotator);

        Alpha uranusAlpha = new Alpha(-1, 21000); // 21 seconds orbit time
        RotationInterpolator uranusRotator = new RotationInterpolator(uranusAlpha, orbitUranus);
        uranusRotator.setSchedulingBounds(bounds);
        orbitUranus.addChild(uranusRotator);
        
        Alpha neptuneAlpha = new Alpha(-1, 164400); // adjust rotation speed
        RotationInterpolator neptuneRotator = new RotationInterpolator(neptuneAlpha, orbitNeptune);
        neptuneRotator.setSchedulingBounds(bounds);
        orbitNeptune.addChild(neptuneRotator);

      
        
      //add orbit lines 
        root.addChild(createOrbitLine(1.5f)); // Orbit line for Mercury
        root.addChild(createOrbitLine(2.2f)); // Orbit line for Venus
        root.addChild(createOrbitLine(2.5f)); // Orbit line for Earth
        root.addChild(createOrbitLine(3.0f)); // Orbit line for Mars
        root.addChild(createOrbitLine(5.0f));  // Create orbit for Jupiter
        root.addChild(createOrbitLine(6.0f)); // Create orbit line for Saturn
        root.addChild(createOrbitLine(7.0f)); // Create orbit line for Uranus 
        root.addChild(createOrbitLine(8.0f)); // Create orbit line for Neptune 

     // create directional light source
        DirectionalLight directionalLight = new DirectionalLight(new Color3f(1.0f, 1.0f, 1.0f), new Vector3f(-1.0f, -1.0f, -1.0f));
        directionalLight.setInfluencingBounds(bounds);
        root.addChild(directionalLight);
        PointLight p1 = new PointLight();
        root.addChild(p1);
        DirectionalLight directionalLight2 = new DirectionalLight(new Color3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f));
        directionalLight2.setInfluencingBounds(bounds);
        root.addChild(directionalLight2);
        
        
        
        
        	return root;
    }
 // Method to create a PointLight
    private PointLight createPointLight(Point3f position) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        PointLight light = new PointLight(new Color3f(1.0f, 1.0f, 1.0f), position, new Point3f(1.0f, 0.0f, 0.0f));
        light.setInfluencingBounds(bounds);
        return light;
    }

    private Shape3D createOrbitLine(float radius) {
        int numVertices = 101; // Increased by one
        Point3f[] orbitVertices = new Point3f[numVertices];
        Color3f[] colors = new Color3f[numVertices];

        for(int i = 0; i < numVertices - 1; i++) { // Loop until numVertices - 1
            double angle = 2.0 * Math.PI * i / (numVertices - 1); // Divided by (numVertices - 1)
            float x = radius * (float)Math.cos(angle);
            float z = radius * (float)Math.sin(angle);
            orbitVertices[i] = new Point3f(x, 0.0f, z);
            colors[i] = new Color3f(1.0f, 1.0f, 1.0f); // White color for the orbit line
        }

        // Add the closing point (same as the first point)
        orbitVertices[numVertices - 1] = orbitVertices[0];
        colors[numVertices - 1] = colors[0];

        LineStripArray orbitLine = new LineStripArray(numVertices, LineStripArray.COORDINATES | LineStripArray.COLOR_3, new int[] {numVertices});
        orbitLine.setCoordinates(0, orbitVertices);
        orbitLine.setColors(0, colors);

        return new Shape3D(orbitLine);
    }

}
