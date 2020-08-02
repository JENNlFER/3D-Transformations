package jen.matrix.trans.d3;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Translate3D extends Transformation3D {

    @Getter private double Tx;
    @Getter private double Ty;
    @Getter private double Tz;

    public Translate3D() {
        this(0, 0, 0);
    }

    public Translate3D(double Tx, double Ty, double Tz) {
        super();
        Tx(Tx).Ty(Ty).Tz(Tz);
    }

    public Translate3D Tx(double Tx) {
        this.Tx = Tx;
        set(3, 0, Tx);
        return this;
    }

    public Translate3D Ty(double Ty) {
        this.Ty = Ty;
        set(3, 1, Ty);
        return this;
    }

    public Translate3D Tz(double Tz) {
        this.Tz = Tz;
        set(3, 2, Tz);
        return this;
    }

    @Override
    public void setSimple(boolean b) { }
}
