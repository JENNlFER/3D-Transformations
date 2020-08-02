package jen.matrix;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import static java.lang.String.format;

@Accessors(fluent=true)
public class Matrix implements IMatrix {

    final double[] data;
    @Getter private final int rows;
    @Getter private final int cols;

    public Matrix(int rows, int cols) {
        this.data = new double[rows * cols];
        this.rows = rows;
        this.cols = cols;
    }

    private Matrix(int rows, int cols, double[] data) {
        this(rows, cols);
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    public IMatrix multiply(IMatrix matrix) {
        if (cols != matrix.rows())
            throw new ArithmeticException(format("Matrix dimension mismatch: [%d x %d] × [%d x %d]",
                    rows, cols, matrix.rows(), matrix.cols()));

        Matrix m = new Matrix(rows, matrix.cols());
        for (int i = 0; i < m.rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (data[i * rows + j] != 0) {
                    for (int k = 0; k < m.cols; ++k) {
                        m.set(i, k, m.get(i, k) + get(i, j) * matrix.get(j, k));
                    }
                }
            }
        }
        return m;
    }
    public IMatrix set(int row, int col, double value) {
        data[row * cols + col] = value;
        return this;
    }

    public double get(int row, int col) {
        return data[row * cols + col];
    }

    @SneakyThrows
    public IMatrix copy() {
        return new Matrix(rows, cols, data.clone());
    }

    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        int bound = size * size++;
        for (int i = 0; i < bound ; i += size) matrix.data[i] = 1;
        return matrix;
    }

    public static Matrix zero(int rows, int cols) {
        return new Matrix(rows, cols);
    }

    public static Matrix of(double[][] data) {
        Matrix matrix = new Matrix(data.length, data[0].length);
        int destination = 0;
        for (double[] datum : data) {
            System.arraycopy(datum, 0, matrix.data, destination, datum.length);
            destination += datum.length;
        }
        return matrix;
    }

    public static Matrix of(double... vector) {
        Matrix matrix = new Matrix(1, vector.length);
        System.arraycopy(vector, 0, matrix.data, 0, vector.length);
        return matrix;
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                string += get(i, j) + " ";
            }
            string += "\n";
        }
        return string;
    }
}
