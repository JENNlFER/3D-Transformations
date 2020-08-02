package jen.matrix;

public interface IMatrix {

    IMatrix multiply(IMatrix matrix);
    IMatrix set(int row, int col, double value);
    double get(int row, int col);
    int rows();
    int cols();
    IMatrix copy();
}

