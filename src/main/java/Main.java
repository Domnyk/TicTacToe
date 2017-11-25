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
        stage.setScene(new Scene(layout, 810, 810));
        stage.show();

        // Fix window size - no resizing possible
        stage.setMinHeight(810);
        stage.setMaxHeight(810);
        stage.setMinWidth(810);
        stage.setMaxWidth(810);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

