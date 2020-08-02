package jen.view.d2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jen.matrix.trans.d2.Scale2D;
import jen.util.Format;

public class ScalePane2D extends ComplexPane2D<Scale2D> {

    @FXML private TextField Sx;
    @FXML private TextField Sy;

    public ScalePane2D() {
        super("2d/scale", new Scale2D());
    }

    @Override
    protected void init() {
        Sx.setOnKeyReleased(e -> transformation.Sx(Format.strToNum(Sx.getText().isEmpty() ? "1" : Sx.getText())));
        Sy.setOnKeyReleased(e -> transformation.Sy(Format.strToNum(Sy.getText().isEmpty() ? "1" : Sy.getText())));
        super.init();
    }

    @Override
    protected void update() {
        super.update();
        Sx.setText(Format.numToStr(transformation.Sx()));
        Sy.setText(Format.numToStr(transformation.Sy()));
    }
}