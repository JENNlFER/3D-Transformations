package jen.view.d2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jen.matrix.trans.d2.Rotate2D;
import jen.util.Format;

public class RotatePane2D extends ComplexPane2D<Rotate2D> {

    @FXML private TextField Degrees;
    @FXML private TextField Radians;

    public RotatePane2D() {
        super("2d/rotate", new Rotate2D());
    }

    @Override
    protected void init() {
        super.init();
        Degrees.setOnKeyReleased(e -> {
            transformation.degrees(Format.strToNum(Degrees.getText()));
            Radians.setText(Format.numToStr(transformation.radians()));
        });
        Radians.setOnKeyReleased(e -> {
            transformation.radians(Format.strToNum(Radians.getText()));
            Degrees.setText(Format.numToStr(transformation.degrees()));
        });
    }

    @Override
    protected void update() {
        super.update();
        Degrees.setText(Format.numToStr(transformation.degrees()));
        Radians.setText(Format.numToStr(transformation.radians()));
    }
}
