package jen.graphics.line;

import javafx.scene.paint.Color;

public class Edge {
    public final int end1;
    public final int end2;
    public final int width;
    public final Color color;

    public Edge(int end1, int end2, int width, Color color) {
        this.end1 = end1;
        this.end2 = end2;
        this.width = width;
        this.color = color;
    }
}
