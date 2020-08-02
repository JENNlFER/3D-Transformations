package jen;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import jen.graphics.Graphics2D;
import jen.graphics.Graphics3D;
import jen.matrix.trans.d2.Transformation2D;
import jen.matrix.trans.d3.Transformation3D;
import jen.util.FileHandler;
import jen.util.Format;
import jen.util.GraphicsPresets;
import jen.util.Popup;
import jen.view.TransPane;
import jen.view.d2.RotatePane2D;
import jen.view.d2.ScalePane2D;
import jen.view.d2.TranslatePane2D;
import jen.view.d3.RotatePane3D;
import jen.view.d3.ScalePane3D;
import jen.view.d3.TranslatePane3D;
import lombok.Getter;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

import static javafx.scene.control.Alert.AlertType.*;
import static jen.view.TransPane.Dimension.*;

public class Controller {

    @Getter private Composite<Transformation2D> composite2D;
    @Getter private Composite<Transformation3D> composite3D;
    @Getter private Renderer render;
    @Getter private Graphics2D graphics2D;
    @Getter private Graphics3D graphics3D;

    @FXML private GridPane Matrix2D;
    @FXML private GridPane Matrix3D;
    @FXML private VBox Transformations;
    @FXML private Canvas Canvas;
    @FXML private AnchorPane Background;
    @FXML private RadioMenuItem White;
    @FXML private RadioMenuItem Black;
    @FXML private Slider SliderSx;
    @FXML private Slider SliderSy;
    @FXML private Slider SliderD;
    @FXML private Slider SliderVx;
    @FXML private Slider SliderVy;
    @FXML private Slider SliderCx;
    @FXML private Slider SliderCy;
    @FXML private TextField ValueSx;
    @FXML private TextField ValueSy;
    @FXML private TextField ValueD;
    @FXML private TextField ValueVx;
    @FXML private TextField ValueVy;
    @FXML private TextField ValueCx;
    @FXML private TextField ValueCy;

    private int divider = -1; // set at the last instance of a 3D transformation

    @FXML
    private void initialize() {
        App.CONTROL = this;
        composite2D = new Composite<>(Matrix2D, new Transformation2D());
        composite3D = new Composite<>(Matrix3D, new Transformation3D());
        graphics2D = new Graphics2D(composite2D);
        graphics3D = new Graphics3D(composite3D, composite2D);
        render = new Renderer(graphics2D, graphics3D, Canvas);
        composite2D.update();
        composite3D.update();
        initSetting(SliderSx, ValueSx, v -> graphics3D.Sx = v);
        initSetting(SliderSy, ValueSy, v -> graphics3D.Sy = v);
        initSetting(SliderD,  ValueD,  v -> graphics3D.D = v);
        initSetting(SliderVx, ValueVx, v -> graphics3D.Vx = v);
        initSetting(SliderVy, ValueVy, v -> graphics3D.Vy = v);
        initSetting(SliderCx, ValueCx, v -> graphics3D.VCx = v);
        initSetting(SliderCy, ValueCy, v -> graphics3D.VCy = v);
    }

    @FXML
    private void Import() {
        FileChooser fc = new FileChooser();
        File file;
        FileHandler.File2D data;
        if ((file = fc.showOpenDialog(App.STAGE)) != null &&(data = FileHandler.read(file)) != null) {
            if (data.errors != 0) Popup.show(WARNING, "There were some issues importing the file",
                    data.errors + " line(s) failed to import.");
            if (data.black) Black(); else White();

            graphics2D.clear();
            graphics3D.clear();
            composite2D.clear();
            composite3D.clear();
            render.clear();

            data.lines.forEach(line -> graphics2D.addLine(line));
            data.transformations.forEach(trans -> addTransformation(TransPane.of(trans)));
            update();
        }
    }

    @FXML
    private void Export() {
        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(App.STAGE);
        if (file != null &&!FileHandler.write(file, Black.isSelected(), graphics2D.getData(), composite2D.getTransformations())) {
            Popup.show(ERROR, "Export unsuccessful", "File was not able to be created or already exists.");
        }
    }

    @FXML
    private void Translation2D() {
        addTransformation(new TranslatePane2D());
        update();
    }

    @FXML
    private void Translation3D() {
        addTransformation(new TranslatePane3D());
        update();
    }

    @FXML
    private void Rotation2D() {
        addTransformation(new RotatePane2D());
        update();
    }

    @FXML
    private void Rotation3D() {
        addTransformation(new RotatePane3D());
        update();
    }

    @FXML
    private void Scale2D() {
        addTransformation(new ScalePane2D());
        update();
    }

    @FXML
    private void Scale3D() {
        addTransformation(new ScalePane3D());
        update();
    }

    @FXML
    private void Delete2D() {
        Optional<ButtonType> result = Popup.show(CONFIRMATION, "Delete all 2D Transformations?");
        if (result.get() == ButtonType.OK) clearTransformations2D();
    }

    @FXML
    private void Delete3D() {
        Optional<ButtonType> result = Popup.show(CONFIRMATION, "Delete all 3D Transformations?");
        if (result.get() == ButtonType.OK) clearTransformations3D();
    }

    @FXML
    private void Apply2D() {
        Optional<ButtonType> result = Popup.show(CONFIRMATION, "Apply all 2D Transformations?");
        if (result.get() == ButtonType.OK) {
            graphics2D.apply();
            composite2D.deactivate();
            graphics2D.update();
            render.update();
            Transformations.getChildren().forEach(c -> {
                if (((TransPane)c).dimension == D2) ((TransPane)c).disable();
            });
        }
    }


    @FXML
    private void Apply3D() {
        Optional<ButtonType> result = Popup.show(CONFIRMATION, "Apply all 3D Transformations?");
        if (result.get() == ButtonType.OK) {
            graphics3D.apply();
            composite3D.deactivate();
            graphics3D.update();
            render.update();
            Transformations.getChildren().forEach(c -> {
                if (((TransPane)c).dimension == D3) ((TransPane)c).disable();
            });
        }
    }

    @FXML
    private void Random2D() {
        GraphicsPresets.random2D(graphics2D);
        render.update();
    }

    @FXML
    private void Random3D() {
        GraphicsPresets.random3D(graphics3D);
        SliderCx.setValue(400);
        SliderCy.setValue(100);
        SliderD.setValue(12);
        render.update();
    }

    @FXML
    private void Spectrum2D() {
        GraphicsPresets.spectrum2D(graphics2D);
        render.update();
    }

    @FXML
    private void Cone3D() {
        GraphicsPresets.cone3D(graphics3D);
        SliderCx.setValue(275);
        SliderCy.setValue(245);
        SliderD.setValue(25);
        render.update();
    }

    @FXML
    private void HouseClose3D() {
        GraphicsPresets.HouseClose3D(graphics3D);
        SliderCx.setValue(125);
        SliderCy.setValue(675);
        SliderD.setValue(1);
        render.update();
    }

    @FXML
    private void HouseFar3D() {
        GraphicsPresets.HouseFar3D(graphics3D);
        SliderCx.setValue(450);
        SliderCy.setValue(350);
        SliderD.setValue(50);
        render.update();
    }

    @FXML
    private void Bresenham() {
        render.mode = Renderer.Mode.BRESENHAM;
    }

    @FXML
    private void Simple() {
        render.mode = Renderer.Mode.SIMPLE;
    }

    @FXML
    private void White() {
        Background.setStyle("-fx-background-color: white");
        Black.setSelected(false);
        White.setSelected(true);
    }

    @FXML
    private void Black() {
        Background.setStyle("-fx-background-color: black");
        Black.setSelected(true);
        White.setSelected(false);
    }

    @FXML
    private void Clear() {
        graphics3D.clear();
        graphics2D.clear();
        render.clear();
    }

    public void update() {
        composite3D.update();
        composite2D.update();
        graphics3D.update();
        graphics2D.update();
        render.update();
    }

    public void clearTransformations2D() {
        Transformations.getChildren().removeIf(n -> ((TransPane)n).dimension == D2);
        composite2D.clear();
        composite2D.update();
        graphics2D.update();
        render.update();
    }

    public void clearTransformations3D() {
        Transformations.getChildren().removeIf(n ->
                ((TransPane)n).dimension == D3 && (divider = Math.max(-1, divider--)) != -1);
        composite3D.clear();
        composite3D.update();
        graphics3D.update();
        render.update();
    }

    public void removeTransformation(TransPane pane) {
        Transformations.getChildren().remove(pane);
        if (pane.dimension == D2) {
            composite2D.delete((Transformation2D) pane.getTransformation());
        } else if (pane.dimension == D3) {
            composite3D.delete((Transformation3D) pane.getTransformation());
            divider--;
        }
        update();
    }

    public void addTransformation(TransPane pane) {
        if (pane.dimension == D3) {
            Transformations.getChildren().add(Math.max(++divider, 0), pane);
            composite3D.add((Transformation3D) pane.getTransformation());
        } else if (pane.dimension == D2) {
            Transformations.getChildren().add(Transformations.getChildren().size(), pane);
            composite2D.add((Transformation2D) pane.getTransformation());
        }
    }

    private void initSetting(Slider slider, TextField text, Consumer<Double> func) {
        text.textProperty().bind(Bindings.format("%.2f", slider.valueProperty()));
        text.textProperty().addListener(((observable, oldValue, newValue) -> {
            func.accept(Format.strToNum(newValue));
            update();
        }));
    }
}
