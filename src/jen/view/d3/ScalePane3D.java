package jen.view.d3;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jen.matrix.trans.d3.Scale3D;
import jen.util.Format;

public class ScalePane3D extends ComplexPane3D<Scale3D> {

    @FXML private TextField Sx;
    @FXML private TextField Sy;
    @FXML private TextField Sz;

    public ScalePane3D() {
        super("3d/scale", new Scale3D());
    }

    @Override
    protected void init() {
        Sx.setOnKeyReleased(e -> transformation.Sx(Format.strToNum(Sx.getText().isEmpty() ? "1" : Sx.getText())));
        Sy.setOnKeyReleased(e -> transformation.Sy(Format.strToNum(Sy.getText().isEmpty() ? "1" : Sy.getText())));
        Sz.setOnKeyReleased(e -> transformation.Sz(Format.strToNum(Sz.getText().isEmpty() ? "1" : Sz.getText())));
        super.init();
    }

    @Override
    protected void update() {
        super.update();
        Sx.setText(Format.numToStr(transformation.Sx()));
        Sy.setText(Format.numToStr(transformation.Sy()));
        Sz.setText(Format.numToStr(transformation.Sz()));
    }
}
