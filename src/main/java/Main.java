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
    }


    public static void main(String[] args) {
        launch(args);
    }
}

