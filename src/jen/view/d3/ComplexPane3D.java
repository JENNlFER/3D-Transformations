package jen.view.d3;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import jen.App;
import jen.matrix.trans.d3.Transformation3D;
import jen.util.Format;
import jen.view.TransPane;

import static jen.view.TransPane.Dimension.D3;

public abstract class ComplexPane3D<T extends Transformation3D> extends TransPane<T> {

    @FXML private CheckBox Complex;
    @FXML private TextField Cx;
    @FXML private TextField Cy;
    @FXML private TextField Cz;

    protected ComplexPane3D(String fxml, T transformation) {
        super(fxml, D3, transformation);
    }

    @Override
    protected void init() {
        Cx.setOnKeyReleased(e -> transformation.Cx(Format.strToNum(Cx.getText())));
        Cy.setOnKeyReleased(e -> transformation.Cy(Format.strToNum(Cy.getText())));
        Cz.setOnKeyReleased(e -> transformation.Cz(Format.strToNum(Cz.getText())));
        Complex.setOnAction(a -> {
            transformation.setSimple(!Complex.isSelected());
            if (Complex.isSelected()) {
                Cx.setDisable(false);
                Cy.setDisable(false);
                Cz.setDisable(false);
            } else {
                Cx.setDisable(true);
                Cy.setDisable(true);
                Cz.setDisable(true);
            }
            App.CONTROL.update();
        });
        super.init();
    }

    @Override
    protected void update() {
        super.update();
        Complex.setSelected(!transformation.isSimple());
        if (!transformation.isSimple()) {
            Cx.setText(Format.numToStr(transformation.Cx()));
            Cy.setText(Format.numToStr(transformation.Cy()));
            Cz.setText(Format.numToStr(transformation.Cz()));
        }
    }
}
