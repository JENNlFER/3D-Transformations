package jen.view.d2;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jen.matrix.Matrix;
import jen.matrix.trans.d2.Translate2D;
import jen.util.Format;
import jen.view.TransPane;

import static jen.view.TransPane.Dimension.D2;

public class TranslatePane2D extends TransPane<Translate2D> {

    @FXML private TextField Tx;
    @FXML private TextField Ty;

    public TranslatePane2D() {
        super("2d/translate", D2, new Translate2D());
    }

    @Override
    protected void init() {
        super.init();
        Tx.setOnKeyReleased(e -> transformation.Tx(Format.strToNum(Tx.getText())));
        Ty.setOnKeyReleased(e -> transformation.Ty(Format.strToNum(Ty.getText())));
    }

    @Override
    protected void update() {
        super.update();
        Tx.setText(Format.numToStr(transformation.Tx()));
        Ty.setText(Format.numToStr(transformation.Ty()));
    }
}
