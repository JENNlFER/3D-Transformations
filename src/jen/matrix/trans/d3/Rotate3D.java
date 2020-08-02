package jen.matrix.trans.d3;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent=true)
public class Rotate3D extends Transformation3D {

    @Getter
    private double radians;
    private Axis axis = Axis.Z;
    private int cosA = 0;
    private int cosB = 0;
    private int[] sinP = new int[2];
    private int[] sinN = new int[2];

    public Rotate3D() {
        this(0, Axis.Z);
    }

    public Rotate3D(double radians, Axis axis) {
        super();
        changeAxis(axis);
        radians(radians);
    }

    public Rotate3D(double radians, Axis axis, double Cx, double Cy, double Cz) {
        this(radians, axis);
        setCenter(Cx, Cy, Cz);
    }

    public void radians(double radians) {
        this.radians = radians;
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        set(cosA, cosA, cos);
        set(cosB, cosB, cos);
        set(sinP[0], sinP[1], sin);
        set(sinN[0], sinN[1], -sin);
    }

    private void changeAxis(Axis axis) {
        switch(this.axis) {
            case X:
                set(2, 1, 0);
                set(1, 2, 0);
                break;
            case Y:
                set(2, 0, 0);
                set(0, 2, 0);
                break;
            case Z:
                set(1, 0, 0);
                set(0, 1, 0);
        }
        this.axis = axis;
        switch(axis) {
            case X:
                set(0, 0, 1);
                cosA = 1;
                cosB = 2;
                sinP[0] = 1;
                sinP[1] = 2;
                sinN[0] = 2;
                sinN[1] = 1;
                break;
            case Y:
                set(1, 1, 1);
                cosA = 0;
                cosB = 2;
                sinP[0] = 2;
                sinP[1] = 0;
                sinN[0] = 0;
                sinN[1] = 2;
                break;
            case Z:
                set(2, 2, 1);
                cosA = 0;
                cosB = 1;
                sinP[0] = 0;
                sinP[1] = 1;
                sinN[0] = 1;
                sinN[1] = 0;
        }
    }

    public void degrees(double degrees) {
        radians(Math.toRadians(degrees));
    }

    public double degrees() {
        return Math.toDegrees(radians);
    }

    public void setAxis(Axis axis) {
        changeAxis(axis);
        radians(radians);
    }

    public Axis getAxis() {
        return this.axis;
    }

    public enum Axis {
        X,Y,Z;
    }
}
