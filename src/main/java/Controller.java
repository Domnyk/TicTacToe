import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.*;

import java.util.Random;

public class Controller {
    private static int NUM_OF_PLAYERS = 2;
    private static int NUM_OF_COLS = 5;
    private static int NUM_OF_ROWS = 5;
    private static int NUM_OF_MILLISECONDS_IN_SECOND = 1000;

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

    @FXML
    private TextField delayTextField;

    private Player[] players;

    private Player currentPlayer;

    private int currentPlayerNumber;

    private Board board;

    private boolean isHumanVsAIGameType;

    private int sleepAmount;

    private StringProperty statusLabelProperty = new SimpleStringProperty();

    private void setStatusLabelProperty(String newStr) {
        statusLabelProperty.set(newStr);
    }

    // When FXML is done loading document (gui.fxml) it automatically calls initialize()
    @FXML
    private void initialize() {
        statusLabel.textProperty().bind(statusLabelProperty);
        delayTextField.setText("0");
        addBtns();

        updateGameState(GameState.WAITING_FOR_GAME_START);
    }

    private void updateGameState(GameState newGameState) {
        setStatusLabelProperty(newGameState.toString());
    }

    // Add button to each cell in gridPane
    private void addBtns() {
        for(int row = 0; row < NUM_OF_ROWS; ++row) {
            for(int col = 0; col < NUM_OF_COLS; ++col) {
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
        GameState newGameState;

        // Update GUI - set mark on proper field
        source.setText(currentPlayer.getPlayersMark().toString());

        // Update GUI - disable clicked button
        source.setDisable(true);

        // Update model - set mark on proper position
        FieldState newFieldState = FieldState.valueOf(currentPlayer.getPlayersMark().toString());
        board.setFieldState(coordinates, newFieldState);

        // Get new GameState
        newGameState = board.evaluateGameState(currentPlayer);

        switch (newGameState) {
            case X_WIN:
                updateGameState(GameState.X_WIN);
                gameGrid.setDisable(true);
                return;
            case O_WIN:
                updateGameState(GameState.O_WIN);
                gameGrid.setDisable(true);
                return;
            case DRAW:
                updateGameState(GameState.DRAW);
                return;
            case X_IS_MAKING_MOVE:
                updateGameState(GameState.X_IS_MAKING_MOVE);
                break;
            case O_IS_MAKING_MOVE:
                updateGameState(GameState.O_IS_MAKING_MOVE);
                break;


        }
        // If not end of game - change currentPlayer
        currentPlayerNumber = (currentPlayerNumber + 1) % NUM_OF_PLAYERS;
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

        // Enable board -> enable buttons
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
        delayTextField.setDisable(true);
    }

    private void makeAIMove() {
        Runnable makeAIMoveTask = () -> {
            Platform.runLater(() -> gameGrid.setDisable(true));

            try {
                Thread.sleep(sleepAmount * NUM_OF_MILLISECONDS_IN_SECOND);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            ArtificialPlayer artificialPlayer = ((ArtificialPlayer ) currentPlayer);
            Coordinates aiCoordinates = artificialPlayer.makeMove(board);
            int row = aiCoordinates.getRow();
            int col = aiCoordinates.getCol();

            Platform.runLater(() -> gameGrid.setDisable(false));

            // For some reason getChildren().get(0) is a group element. That's why +1 is needed
            Platform.runLater(() -> {
                int buttonNumber = row * NUM_OF_ROWS + col + 1;
                ((Button)gameGrid.getChildren().get(buttonNumber)).fire();
            });
        };

        Thread aiThread = new Thread(makeAIMoveTask);
        aiThread.setDaemon(true);
        aiThread.start();
    }

    private void startGame() {
       // Create board
       board = new Board();

       // Create players' models
       players = new Player[NUM_OF_PLAYERS];

       // Get sleepAmount value
       sleepAmount = Integer.parseInt(delayTextField.getText());

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