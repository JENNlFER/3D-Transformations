package jen.matrix.trans.d2;

import jen.matrix.IMatrix;
import jen.matrix.Matrix;
import jen.matrix.trans.Transformation;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Transformation2D extends Transformation {

    @Getter private final IMatrix before = Matrix.identity(3);
    @Getter private final IMatrix after = Matrix.identity(3);
    @Getter private double Cx = 450;
    @Getter private double Cy = 350;

    public Transformation2D() {
        super(Matrix.identity(3));
        setCenter(Cx, Cy);
    }

    private Transformation2D(Transformation2D trans) {
        super(trans);
        setCenter(trans.Cx, trans.Cy);
    }

    public void setCenter(double Cx, double Cy) {
        this.Cx(Cx).Cy(Cy);
    }

    public Transformation2D Cx(double Cx) {
        this.Cx = Cx;
        before.set(2, 0, -Cx);
        after.set(2, 0, +Cx);
        return this;
    }

    public Transformation2D Cy(double Cy) {
        this.Cy = Cy;
        before.set(2, 1, -Cy);
        after.set(2, 1, +Cy);
        return this;
    }

    @Override
    public IMatrix copy() {
        return new Transformation2D(this);
    }
}
