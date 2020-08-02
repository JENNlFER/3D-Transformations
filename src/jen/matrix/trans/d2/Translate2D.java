package jen.matrix.trans.d2;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Translate2D extends Transformation2D {

    @Getter private double Tx;
    @Getter private double Ty;

    public Translate2D() {
        this(0, 0);
    }

    public Translate2D(double Tx, double Ty) {
        super();
        Tx(Tx).Ty(Ty);
    }

    public Translate2D Tx(double Tx) {
        this.Tx = Tx;
        set(2, 0, Tx);
        return this;
    }

    public Translate2D Ty(double Ty) {
        this.Ty = Ty;
        set(2, 1, Ty);
        return this;
    }

    @Override
    public void setSimple(boolean b) { }
}
