package jen.matrix.trans.d3;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Scale3D extends Transformation3D {

    @Getter private double Sx;
    @Getter private double Sy;
    @Getter private double Sz;

    public Scale3D(){
        this(1, 1, 1);
    }

    public Scale3D(double Sx, double Sy, double Sz) {
        super();
        Sx(Sx).Sy(Sy).Sz(Sz);
    }

    public Scale3D(double Sx, double Sy, double Sz, double Cx, double Cy, double Cz) {
        this(Sx, Sy, Sz);
        setCenter(Cx, Cy, Cz);
    }

    public Scale3D Sx(double Sx) {
        this.Sx = Sx;
        set(0, 0, Sx);
        return this;
    }

    public Scale3D Sy(double Sy) {
        this.Sy = Sy;
        set(1, 1, Sy);
        return this;
    }

    public Scale3D Sz(double Sz) {
        this.Sz = Sz;
        set(2, 2, Sz);
        return this;
    }
}
