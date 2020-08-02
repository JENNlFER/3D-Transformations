package jen;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import jen.matrix.IMatrix;
import jen.matrix.trans.Transformation;
import jen.util.Format;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class Composite<T extends Transformation> {

    private static int ID = 0;
    private final List<T> transformations = new ArrayList<>();
    private final GridPane display;
    private final T identity;
    private IMatrix matrix;

    @SneakyThrows
    public Composite(GridPane display, T identity) {
        this.display = display;
        this.identity = identity;
        matrix = identity;
    }

    public void add(T transformation) {
        transformations.add(transformation);
    }

    public void delete(T transformation) {
        transformations.remove(transformation);
    }

    public void clear() {
        matrix = identity;
        transformations.clear();
    }

    public void deactivate() {
        transformations.forEach(t -> t.setEnabled(false));
        update();
    }

    public void update() {
        matrix = identity;
        for (Transformation trans : transformations) {
            matrix = matrix.multiply(trans);
        }

        ObservableList<Node> cells = display.getChildren();
        int cell = 0;
        for (int row = 0; row < identity.rows(); ++row) {
            for (int col = 0; col< identity.cols(); ++col) {
                ((Label) cells.get(cell++)).setText(Format.numToStr(matrix.get(row, col)));
            }
        }
    }

    public IMatrix getMatrix() {
        return matrix.copy();
    }

    public List<Transformation> getTransformations() {
        List<Transformation> list = new ArrayList<>(transformations);
        list.remove(0);
        return list;
    }
}
