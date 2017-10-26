import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane layout = FXMLLoader.load(getClass().getResource("gui.fxml"));
        stage.setTitle("TicTacToe");
        stage.setScene(new Scene(layout, 800, 768));
        stage.show();

        // Turn off resizing by setting explicite max and min dimensions
        stage.setMaxWidth(800);
        stage.setMaxHeight(768);
        stage.setMinWidth(800);
        stage.setMinHeight(768);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

