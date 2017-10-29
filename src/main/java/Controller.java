import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    RadioButton siVsHumanRadioButton;

    @FXML
    RadioButton siVsSiRadioButton;

    @FXML
    Label firstSiTreeDepthLabel;

    @FXML
    TextField firstSiTreeDepthTextField;

    @FXML
    Label secondSITreeDepthLabel;

    @FXML
    TextField secondSITreeDepthTextField;

    @FXML
    Button startGameButton;

    // When FXML is done loading document (gui.fxml) it automatically calls initialize()
    @FXML
    private void initialize() {

    }

    // When SI vs Human radio button is selected then there is no need for second text field to be active
    @FXML
    private void handleSiVsHumanRadioButtonClicked() {
        secondSITreeDepthLabel.setDisable(true);
        secondSITreeDepthTextField.setDisable(true);
    }

    // When SI vs SI radio button is selected then second text field is active
    @FXML
    private void handleSiVsSiRadioButtonClicked() {
        secondSITreeDepthLabel.setDisable(false);
        secondSITreeDepthTextField.setDisable(false);
    }

    // Actually start the game but also disables all controls
    @FXML
    private void handleStartGameButtonClicked() {
        siVsHumanRadioButton.setDisable(true);
        siVsSiRadioButton.setDisable(true);

        startGameButton.setDisable(true);

        firstSiTreeDepthLabel.setDisable(true);
        firstSiTreeDepthTextField.setDisable(true);
        secondSITreeDepthLabel.setDisable(true);
        secondSITreeDepthTextField.setDisable(true);
    }
}
