package jen.matrix.trans;

import jen.matrix.IMatrix;
import lombok.Getter;
import lombok.Setter;

public abstract class Transformation implements IMatrix, Cloneable {

    @Getter @Setter boolean enabled;
    @Getter @Setter boolean simple;
    public final IMatrix matrix;

    protected Transformation(IMatrix matrix) {
        this(matrix, true, true);
    }

    Transformation(IMatrix matrix, boolean enabled, boolean simple) {
        this.matrix = matrix;
        this.enabled = enabled;
        this.simple = simple;
    }

    private Transformation(Transformation trans) {
        this.matrix = trans.matrix.copy();
        this.enabled = trans.enabled;
        this.simple = trans.simple;
    }

    @Override
    public IMatrix multiply(IMatrix matrix) {
        if (!enabled) return matrix.copy();
        if (matrix instanceof Transformation) {
            Transformation trans = (Transformation) matrix;
            if (!trans.enabled) return this.copy();
            if (trans.simple) {
                return new Transformation(this.matrix.multiply(matrix)) {
                    protected IMatrix before() { return null; }
                    protected IMatrix after() { return null; }};
            }
            return new Transformation(this.matrix.multiply(trans.before()).multiply(matrix).multiply(trans.after())) {
                protected IMatrix before() { return null; }
                protected IMatrix after() { return null; }};
        }
        return this.matrix.multiply(matrix);
    }

    protected abstract IMatrix before();
    protected abstract IMatrix after();

    @Override
    public IMatrix set(int row, int col, double value) {
        matrix.set(row, col, value);
        return this;
    }

    @Override
    public double get(int row, int col) {
        return matrix.get(row, col);
    }

    @Override
    public int rows() {
        return matrix.rows();
    }

    @Override
    public int cols() {
        return matrix.cols();
    }

    @Override
    public IMatrix copy() {
        return new Transformation(this) {
            protected IMatrix before() { return null; }
            protected IMatrix after() { return null; }};
    }

    @Override
    public String toString() {
        return matrix.toString();
    }
}
