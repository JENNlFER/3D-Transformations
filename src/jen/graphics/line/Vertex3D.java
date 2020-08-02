package jen.graphics.line;

public class Vertex3D implements Vertex {

    public final double x;
    public final double y;
    public final double z;

    public Vertex3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Vertex3D) &&
                (x == ((Vertex3D)o).x) && (y == ((Vertex3D)o).y) && (z == ((Vertex3D)o).z);
    }
}
