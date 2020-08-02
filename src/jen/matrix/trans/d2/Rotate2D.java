package jen.matrix.trans.d2;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Rotate2D extends Transformation2D {

    @Getter private double radians;

    public Rotate2D() {
        this(0);
    }

    public Rotate2D(double radians) {
        super();
        radians(radians);
    }

    public Rotate2D(double radians, double Cx, double Cy) {
        this(radians);
        setCenter(Cx, Cy);
    }

    public void radians(double radians) {
        this.radians = radians;
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        set(0, 0, +cos);
        set(0, 1, -sin);
        set(1, 0, +sin);
        set(1, 1, +cos);
    }

    public void degrees(double degrees) {
        radians(Math.toRadians(degrees));
    }

    public double degrees() {
        return Math.toDegrees(radians);
    }
}
