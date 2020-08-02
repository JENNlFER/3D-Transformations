package jen.view.d3;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jen.matrix.trans.d3.Translate3D;
import jen.util.Format;
import jen.view.TransPane;

import static jen.view.TransPane.Dimension.D3;

public class TranslatePane3D extends TransPane<Translate3D> {

    @FXML private TextField Tx;
    @FXML private TextField Ty;
    @FXML private TextField Tz;

    public TranslatePane3D() {
        super("3d/translate", D3, new Translate3D());
    }

    @Override
    protected void init() {
        super.init();
        Tx.setOnKeyReleased(e -> transformation.Tx(Format.strToNum(Tx.getText())));
        Ty.setOnKeyReleased(e -> transformation.Ty(Format.strToNum(Ty.getText())));
        Tz.setOnKeyReleased(e -> transformation.Tz(Format.strToNum(Tz.getText())));
    }

    @Override
    protected void update() {
        super.update();
        Tx.setText(Format.numToStr(transformation.Tx()));
        Ty.setText(Format.numToStr(transformation.Ty()));
        Tz.setText(Format.numToStr(transformation.Tz()));
    }
}
