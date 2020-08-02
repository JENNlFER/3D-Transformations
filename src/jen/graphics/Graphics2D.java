package jen.graphics;

import javafx.scene.paint.Color;
import jen.Composite;
import jen.graphics.line.Edge;
import jen.graphics.line.Line;
import jen.graphics.line.Vertex2D;
import jen.matrix.IMatrix;
import jen.matrix.Matrix;
import jen.matrix.trans.d2.Transformation2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graphics2D implements GraphicsData<Vertex2D> {

    private final List<Vertex2D> vertices = new ArrayList<>();
    private final List<Edge> edges = new LinkedList<>();
    private final List<Vertex2D> transformedVertices = new LinkedList<>();
    private final Composite<Transformation2D> composite;

    public Graphics2D(Composite<Transformation2D> composite) {
        this.composite = composite;
    }

    @Override
    public void update() {
        transformedVertices.clear();
        IMatrix matrix = composite.getMatrix();
        for (Vertex2D vertex : vertices) {
            IMatrix coords = Matrix.of(vertex.x, vertex.y, 1).multiply(matrix);
            double x = coords.get(0, 0);
            double y = coords.get(0, 1);
            transformedVertices.add(new Vertex2D(x, y));
        }
    }

    @Override
    public void apply() {
        vertices.clear();
        vertices.addAll(transformedVertices);
    }

    @Override
    public void clear() {
        vertices.clear();
        transformedVertices.clear();
        edges.clear();
    }

    @Override
    public void addLine(Line<Vertex2D> line) {
        addLine(line.a.x, line.a.y, line.b.x, line.b.y, line.width, line.color);
    }

    @Override
    public List<Line<Vertex2D>> getData() {
        List<Line<Vertex2D>> lines = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex2D a = vertices.get(edge.end1);
            Vertex2D b = vertices.get(edge.end2);
            lines.add(new Line<>(a, b, edge.width, edge.color));
        }
        return lines;
    }

    public void addLine(double x0, double y0, double x1, double y1, int width, Color color) {
        int indexV1;
        Vertex2D v1 = new Vertex2D(x0, y0);
        if (!vertices.contains(v1)) {
            vertices.add(v1);
            indexV1 = vertices.size() - 1;
        } else {
            indexV1 = vertices.indexOf(v1);
        }

        int indexV2;
        Vertex2D v2 = new Vertex2D(x1, y1);
        if (!vertices.contains(v2)) {
            vertices.add(v2);
            indexV2 = vertices.size() - 1;
        } else {
            indexV2 = vertices.indexOf(v2);
        }

        edges.add(new Edge(indexV1, indexV2, width, color));
    }

    @Override
    public List<Line<Vertex2D>> getRenderData() {
        List<Line<Vertex2D>> lines = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex2D a = transformedVertices.get(edge.end1);
            Vertex2D b = transformedVertices.get(edge.end2);
            lines.add(new Line<>(a, b, edge.width, edge.color));
        }
        return lines;
    }
}
