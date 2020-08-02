package jen.graphics.line;

public class Vertex2D implements Vertex {
    public final double x;
    public final double y;

    public Vertex2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Vertex2D) && (x == ((Vertex2D)o).x) && (y == ((Vertex2D)o).y);
    }
}
