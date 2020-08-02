package jen.graphics;

import jen.graphics.line.Line;
import jen.graphics.line.Vertex;
import jen.graphics.line.Vertex2D;
import java.util.List;

public interface GraphicsData <T extends Vertex> {

    void update();
    void apply();
    void clear();
    void addLine(Line<T> line);
    List<Line<T>> getData(); // raw data
    List<Line<T>> getRenderData(); // transformed data
}