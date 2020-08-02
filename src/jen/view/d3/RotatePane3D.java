package jen.view.d3;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import jen.App;
import jen.matrix.trans.d3.Rotate3D;
import jen.util.Format;

public class RotatePane3D extends ComplexPane3D<Rotate3D> {

    @FXML private TextField Degrees;
    @FXML private TextField Radians;
    @FXML private MenuButton AxisSelect;
    @FXML private MenuItem SelectX;
    @FXML private MenuItem SelectY;
    @FXML private MenuItem SelectZ;


    public RotatePane3D() {
        super("3d/rotate", new Rotate3D());
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
        SelectX.setOnAction(e -> {
            AxisSelect.setText("X ");
            transformation.setAxis(Rotate3D.Axis.X);
            App.CONTROL.update();
        });
        SelectY.setOnAction(e -> {
            AxisSelect.setText("Y ");
            transformation.setAxis(Rotate3D.Axis.Y);
            App.CONTROL.update();
        });
        SelectZ.setOnAction(e -> {
            AxisSelect.setText("Z ");
            transformation.setAxis(Rotate3D.Axis.Z);
            App.CONTROL.update();
        });
    }

    @Override
    protected void update() {
        super.update();
        Degrees.setText(Format.numToStr(transformation.degrees()));
        Radians.setText(Format.numToStr(transformation.radians()));
    }
}
