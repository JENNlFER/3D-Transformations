package jen.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import jen.App;
import jen.matrix.trans.Transformation;
import jen.matrix.trans.d2.Rotate2D;
import jen.matrix.trans.d2.Scale2D;
import jen.matrix.trans.d2.Translate2D;
import jen.view.d2.RotatePane2D;
import jen.view.d2.ScalePane2D;
import jen.view.d2.TranslatePane2D;
import lombok.Getter;
import lombok.SneakyThrows;

public abstract class TransPane<T extends Transformation> extends AnchorPane {

    @Getter protected T transformation;
    public final Dimension dimension;

    @FXML protected TitledPane Title;
    @FXML protected CheckBox Active;
    @FXML protected Button Delete;

    @SneakyThrows
    protected TransPane(String fxml, Dimension dimension, T transformation) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.dimension = dimension;
        this.transformation = transformation;
        String[] name = transformation.getClass().getName().split("\\.");
        Title.setText(name[name.length-1]);
        init();
    }

    public void disable() {
        Active.setSelected(false);
        transformation.setEnabled(false);
    }

    protected void init() {
        setOnKeyReleased(a -> App.CONTROL.update());
        Delete.setOnAction(a -> App.CONTROL.removeTransformation(this));
        Active.setOnAction(a -> {
            transformation.setEnabled(Active.isSelected());
            App.CONTROL.update();
        });
    }

    protected void update() {
        Active.setSelected(transformation.isEnabled());
        Title.setText(transformation.getClass().getSimpleName());
    }

    public enum Dimension {
        D2, D3
    }

    public static TransPane of(Transformation transformation) {
        TransPane pane = null;
        if (transformation instanceof Translate2D) pane = new TranslatePane2D();
        else if (transformation instanceof Scale2D) pane = new ScalePane2D();
        else if (transformation instanceof Rotate2D) pane = new RotatePane2D();
        pane.transformation = transformation;
        pane.update();
        return pane;
    }

}
