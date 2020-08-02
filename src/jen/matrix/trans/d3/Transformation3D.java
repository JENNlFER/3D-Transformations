package jen.matrix.trans.d3;

import jen.matrix.IMatrix;
import jen.matrix.Matrix;
import jen.matrix.trans.Transformation;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Transformation3D extends Transformation {

    @Getter private final IMatrix before = Matrix.identity(4);
    @Getter private final IMatrix after = Matrix.identity(4);
    @Getter private double Cx = 450;
    @Getter private double Cy = 350;
    @Getter private double Cz = 2000;

    public Transformation3D() {
        super(Matrix.identity(4));
        setCenter(Cx, Cy, Cz);
    }

    private Transformation3D(Transformation3D trans) {
        super(trans);
        setCenter(trans.Cx, trans.Cy, trans.Cz);
    }

    public void setCenter(double Cx, double Cy, double Cz) {
        Cx(Cx).Cy(Cy).Cz(Cz);
    }

    public Transformation3D Cx(double Cx) {
        this.Cx = Cx;
        before.set(3, 0, -Cx);
        after.set(3, 0, +Cx);
        return this;
    }

    public Transformation3D Cy(double Cy) {
        this.Cy = Cy;
        before.set(3, 1, -Cy);
        after.set(3, 1, +Cy);
        return this;
    }

    public Transformation3D Cz(double Cz) {
        this.Cz = Cz;
        before.set(3, 2, -Cz);
        after.set(3, 2, +Cz);
        return this;
    }

    @Override
    public IMatrix copy() {
        return new Transformation3D(this);
    }
}
