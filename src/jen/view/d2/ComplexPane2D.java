package jen.view.d2;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import jen.App;
import jen.matrix.trans.d2.Transformation2D;
import jen.util.Format;
import jen.view.TransPane;

import static jen.view.TransPane.Dimension.D2;

public abstract class ComplexPane2D<T extends Transformation2D> extends TransPane<T> {

    @FXML private CheckBox Complex;
    @FXML private TextField Cx;
    @FXML private TextField Cy;

    protected ComplexPane2D(String fxml, T transformation) {
        super(fxml, D2, transformation);
    }

    @Override
    protected void init() {
        Cx.setOnKeyReleased(e -> transformation.Cx(Format.strToNum(Cx.getText())));
        Cy.setOnKeyReleased(e -> transformation.Cy(Format.strToNum(Cy.getText())));
        Complex.setOnAction(a -> {
            transformation.setSimple(!Complex.isSelected());
            if (Complex.isSelected()) {
                Cx.setDisable(false);
                Cy.setDisable(false);
            } else {
                Cx.setDisable(true);
                Cy.setDisable(true);
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
        }
    }
}
