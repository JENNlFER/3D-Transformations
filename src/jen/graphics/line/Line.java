package jen.graphics.line;

import javafx.scene.paint.Color;

public class Line <T extends Vertex> {
    public final T a;
    public final T b;
    public final int width;
    public final Color color;

    public Line(T a, T b, int width, Color color) {
        this.a = a;
        this.b = b;
        this.width = width;
        this.color = color;
    }
}
