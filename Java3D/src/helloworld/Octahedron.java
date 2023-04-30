package helloworld;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedTriangleArray;
import javax.vecmath.Point3f;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Octahedron extends IndexedTriangleArray {

    public Octahedron() {
        super(6, GeometryArray.COORDINATES, 24);

        // Vertices of the octahedron
        Point3f v1 = new Point3f(0, 0, 1);
        Point3f v2 = new Point3f(-1, 0, 0);
        Point3f v3 = new Point3f(0, -1, 0);
        Point3f v4 = new Point3f(1, 0, 0);
        Point3f v5 = new Point3f(0, 1, 0);
        Point3f v6 = new Point3f(0, 0, -1);
        
        setCoordinate(0, v1);
        setCoordinate(1, v2);
        setCoordinate(2, v3);
        setCoordinate(3, v4);
        setCoordinate(4, v5);
        setCoordinate(5, v6);

        // Indices for the triangles
        int[] indices = {
                0, 1, 2,
                0, 2, 3,
                0, 3, 4,
                0, 4, 1,
                5, 2, 1,
                5, 3, 2,
                5, 4, 3,
                5, 1, 4
        };

        setCoordinateIndices(0, indices);
    }
}
