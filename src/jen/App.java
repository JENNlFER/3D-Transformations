package jen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class App extends Application {

    private static final int width = 1119;
    private static final int height = 751;
    public static Stage STAGE;
    public static Controller CONTROL;

    @Override
    @SneakyThrows
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new FXMLLoader(App.class.getResource("interface.fxml")).load()));
        primaryStage.setHeight(height);
        primaryStage.setWidth(width);
        primaryStage.setMinWidth(width);
        primaryStage.setMinHeight(height);
        primaryStage.setMaxWidth(width);
        primaryStage.setMaxHeight(height);
        primaryStage.setTitle("3D Transformations - Jennifer Teissler");
        primaryStage.show();
        STAGE = primaryStage;
    }
}