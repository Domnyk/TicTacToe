import helpers.EndGameHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import models.*;

import java.util.Random;

public class Controller {
    private static int NUM_OF_PLAYERS = 2;

    @FXML
    private RadioButton aiVsHumanRadioButton;

    @FXML
    private RadioButton aiVsAiRadioButton;

    @FXML
    private Label firstAiTreeDepthLabel;

    @FXML
    private TextField firstAiTreeDepthTextField;

    @FXML
    private Label secondAiTreeDepthLabel;

    @FXML
    private TextField secondAiTreeDepthTextField;

    @FXML
    private Button startGameButton;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label statusLabel;

    private Player[] players;

    private Player currentPlayer;

    private int currentPlayerNumber;

    private Grid grid;

    private GameState gameState;

    private GameType gameType;

    private boolean isHumanVsAIGameType;

    private StringProperty statusLabelProperty = new SimpleStringProperty();

    private void setStatusLabelProperty(String newStr) {
        statusLabelProperty.set(newStr);
    }

    // When FXML is done loading document (gui.fxml) it automatically calls initialize()
    @FXML
    private void initialize() {
        statusLabel.textProperty().bind(statusLabelProperty);
        addBtns();

        updateGameState(GameState.WAITING_FOR_GAME_START);
    }

    private void updateGameState(GameState newGameState) {
        gameState = newGameState;
        setStatusLabelProperty(newGameState.toString());
    }

    // Add button to each cell in gridPane
    private void addBtns() {
        for(int row = 0; row < 5; ++row) {
            for(int col = 0; col < 5; ++col) {
                Button btn = createGridBtn();
                gameGrid.add(btn, col, row);
            }
        }
    }

    private void handleGridButtonClicked(Event event) {
        Button source = (Button) event.getSource();

        int col = GridPane.getColumnIndex(source);
        int row = GridPane.getRowIndex(source);
        Coordinates coordinates = new Coordinates(row, col);

        // Update GUI - set mark on proper field
        source.setText(currentPlayer.getPlayersMark().toString());

        // Update GUI - disable clicked button
        source.setDisable(true);

        // Update model - set mark on proper position
        FieldState newFieldState = FieldState.valueOf(currentPlayer.getPlayersMark().toString());
        grid.setFieldState(coordinates, newFieldState);

        // Check for win
        if (EndGameHelper.hasCurrentPlayerWon(grid, coordinates, currentPlayer)) {

            // Update game status
            if (gameState == GameState.X_IS_MAKING_MOVE) {
                updateGameState(GameState.X_WIN);
            } else {
                updateGameState(GameState.O_WIN);
            }

            // Disable grid
            gameGrid.setDisable(true);

            return;
        }

        // If not win - maybe draw
        if (EndGameHelper.isDraw(grid)) {
            updateGameState(GameState.DRAW);
            return;
        }

        // If not win or draw - set newGameStatus
        if (gameState == GameState.X_IS_MAKING_MOVE) {
            updateGameState(GameState.O_IS_MAKING_MOVE);
        } else {
            updateGameState(GameState.X_IS_MAKING_MOVE);
        }

        // If not win - change currentPlayer
        currentPlayerNumber = (currentPlayerNumber + 1) % 2;
        currentPlayer = players[currentPlayerNumber];

        // If new current player is AI - make his move
        if (!currentPlayer.isHuman()) {
            makeAIMove();
        }

    }

    private Button createGridBtn() {
        Button btn = new Button();

        btn.setOpacity(0.4);
        btn.setPrefWidth(90);
        btn.setPrefHeight(90);
        btn.setOpacity(0.5);
        btn.setStyle("-fx-font: 40 arial; -fx-stroke-width: 5;");

        btn.setOnAction(this::handleGridButtonClicked);

        return btn;
    }

    // When SI vs Human radio button is selected then there is no need for second text field to be active
    @FXML
    private void handleAiVsHumanRadioButtonClicked() {
        secondAiTreeDepthLabel.setDisable(true);
        secondAiTreeDepthTextField.setDisable(true);
    }

    // When SI vs SI radio button is selected then second text field is active
    @FXML
    private void handleAiVsAiRadioButtonClicked() {
        secondAiTreeDepthLabel.setDisable(false);
        secondAiTreeDepthTextField.setDisable(false);
    }

    // Actually start the game but also disables all controls
    @FXML
    private void handleStartGameButtonClicked() {
        disableAllControls();

        // Enable grid -> enable buttons
        gameGrid.setDisable(false);

        // Set proper flag for game type
        // At least one radio button has to be always selected
        // So, if not AI vs Human then AI vs AI
        isHumanVsAIGameType = aiVsHumanRadioButton.isSelected();

        startGame();
    }

    private void disableAllControls() {
        aiVsHumanRadioButton.setDisable(true);
        aiVsAiRadioButton.setDisable(true);
        startGameButton.setDisable(true);
        firstAiTreeDepthLabel.setDisable(true);
        firstAiTreeDepthTextField.setDisable(true);
        secondAiTreeDepthLabel.setDisable(true);
        secondAiTreeDepthTextField.setDisable(true);
    }

    private void makeAIMove() {
        Coordinates aiCoordinates = ((ArtificialPlayer) currentPlayer).makeMove(grid);
        int row = aiCoordinates.getRow();
        int col = aiCoordinates.getCol();

        // For some reason getChildren().get(0) is a group element. That's why +1 is needed
        ((Button)gameGrid.getChildren().get(row*5+col+1)).fire();
    }

    private void startGame() {
       // Create grid
       grid = new Grid();

       // Create players' models
       players = new Player[NUM_OF_PLAYERS];

       if ( isHumanVsAIGameType ) {
           int firstAiTreeDepth = Integer.parseInt(firstAiTreeDepthTextField.getText());

           players[0] = new HumanPlayer(Mark.O);
           players[1] = new ArtificialPlayer(Mark.X, firstAiTreeDepth);
       } else {
           int firstAiTreeDepth = Integer.parseInt(firstAiTreeDepthTextField.getText());
           int secondAiTreeDepth = Integer.parseInt(secondAiTreeDepthTextField.getText());

           players[0] = new ArtificialPlayer(Mark.O, firstAiTreeDepth);
           players[1] = new ArtificialPlayer(Mark.X, secondAiTreeDepth);
       }

       // Choose starting player
       GameState newGameState;
       currentPlayerNumber = new Random().nextInt(2);
       currentPlayer = players[currentPlayerNumber];
       if (currentPlayer.getPlayersMark() == Mark.X) {
           newGameState = GameState.X_IS_MAKING_MOVE;
       } else {
           newGameState = GameState.O_IS_MAKING_MOVE;
       }

       updateGameState(newGameState);

       // If AI moves first we need to make it to move
       if (!currentPlayer.isHuman()) {
           makeAIMove();
       }
    }
}