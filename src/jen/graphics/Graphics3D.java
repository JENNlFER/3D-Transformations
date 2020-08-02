package jen.graphics;

import javafx.scene.paint.Color;
import jen.Composite;
import jen.graphics.line.Edge;
import jen.graphics.line.Line;
import jen.graphics.line.Vertex3D;
import jen.matrix.IMatrix;
import jen.matrix.Matrix;
import jen.matrix.trans.d2.Transformation2D;
import jen.matrix.trans.d3.Transformation3D;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graphics3D implements GraphicsData<Vertex3D> {

    public double Sx = 10.48;// half of viewport size (cm)
    public double Sy = 7.94; // half of viewport size (cm)
    public double D = 50;    // distance from center of viewport to eyes (cm)
    public double Vx = 450;  // half of viewport size
    public double Vy = 350;  // half of viewport size
    public double VCx = 450; // center of viewport
    public double VCy = 350; // center of viewport

    private final List<Vertex3D> vertices3D = new ArrayList<>();
    private final List<Vertex3D> vertices2D = new ArrayList<>();
    private final List<Vertex3D> transVertices3D = new LinkedList<>();
    private final List<Vertex3D> transVertices2D = new LinkedList<>();
    private final List<Edge> edges = new LinkedList<>();
    private final Composite<Transformation2D> composite2D;
    private final Composite<Transformation3D> composite3D;

    @SneakyThrows
    public Graphics3D(Composite<Transformation3D> composite3D, Composite<Transformation2D> composite2D) {
        this.composite3D = composite3D;
        this.composite2D = composite2D;
    }

    @Override
    public void update() {
        transVertices3D.clear();
        vertices2D.clear();
        IMatrix matrix = composite3D.getMatrix();
        for (Vertex3D vertex : vertices3D) {
            IMatrix coords = Matrix.of(vertex.x, vertex.y, vertex.z, 1).multiply(matrix);
            double x = coords.get(0, 0);
            double y = coords.get(0, 1);
            double z = coords.get(0, 2);
            transVertices3D.add(new Vertex3D(x, y, z));
            vertices2D.add(project(x, y, z));
        }

        transVertices2D.clear();
        matrix = composite2D.getMatrix();
        for (Vertex3D vertex : vertices2D) {
            IMatrix coords = Matrix.of(vertex.x, vertex.y, 1).multiply(matrix);
            double x = coords.get(0, 0);
            double y = coords.get(0, 1);
            transVertices2D.add(new Vertex3D(x, y, vertex.z));
        }
    }

    private Vertex3D project(double x, double y, double z) {
        if (z == 0) z = 0.00001;
        double X = ((D * x * Vx) / (Sx * z)) + VCx;
        double Y = ((D * y * Vy) / (Sy * z)) + VCy;
        return new Vertex3D(X, Y, z);
    }

    @Override
    public void apply() {
        vertices3D.clear();
        vertices3D.addAll(transVertices3D);
        vertices2D.clear();
        transVertices2D.clear();
    }

    @Override
    public void clear() {
        vertices3D.clear();
        vertices2D.clear();
        transVertices3D.clear();
        transVertices2D.clear();
        edges.clear();
    }

    @Override
    public void addLine(Line<Vertex3D> line) {
        addLine(line.a.x, line.a.y, line.a.z, line.b.x, line.b.y, line.b.z, line.width, line.color);
    }

    public void addLine(double x0, double y0, double z0, double x1, double y1, double z1, int width, Color color) {
        int indexV1;
        Vertex3D v1 = new Vertex3D(x0, y0, z0);
        if (!vertices3D.contains(v1)) {
            vertices3D.add(v1);
            indexV1 = vertices3D.size() - 1;
        } else {
            indexV1 = vertices3D.indexOf(v1);
        }

        int indexV2;
        Vertex3D v2 = new Vertex3D(x1, y1, z1);
        if (!vertices3D.contains(v2)) {
            vertices3D.add(v2);
            indexV2 = vertices3D.size() - 1;
        } else {
            indexV2 = vertices3D.indexOf(v2);
        }

        edges.add(new Edge(indexV1, indexV2, width, color));
    }

    @Override
    public List<Line<Vertex3D>> getData() {
        List<Line<Vertex3D>> lines = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex3D a = vertices3D.get(edge.end1);
            Vertex3D b = vertices3D.get(edge.end2);
            lines.add(new Line<>(a, b, edge.width, edge.color));
        }
        return lines;
    }

    @Override
    public List<Line<Vertex3D>> getRenderData() {
        List<Line<Vertex3D>> lines = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex3D a = transVertices2D.get(edge.end1);
            Vertex3D b = transVertices2D.get(edge.end2);
            lines.add(new Line<>(a, b, edge.width, edge.color));
        }
        return lines;
    }
}