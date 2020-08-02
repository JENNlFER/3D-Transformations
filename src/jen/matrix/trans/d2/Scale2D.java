package jen.matrix.trans.d2;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Scale2D extends Transformation2D {

    @Getter private double Sx;
    @Getter private double Sy;

    public Scale2D() {
        this(1, 1);
    }

    public Scale2D(double Sx, double Sy) {
        super();
        Sx(Sx).Sy(Sy);
    }

    public Scale2D(double Sx, double Sy, double Cx, double Cy) {
        this(Sx, Sy);
        setCenter(Cx, Cy);
    }

    public Scale2D Sx(double Sx) {
        this.Sx = Sx;
        set(0, 0, Sx);
        return this;
    }

    public Scale2D Sy(double Sy) {
        this.Sy = Sy;
        set(1, 1, Sy);
        return this;
    }
}
