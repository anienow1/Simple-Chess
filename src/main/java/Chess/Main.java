package Chess;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    private Modes currentMode = Modes.moveMode;

    private enum Modes {
        moveMode,
        deleteMode;
    }

    @Override
    public void start(Stage stage) throws IOException {
        StackPane root = new StackPane();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); // Find the size of the viewing screen
        double screenHeight = screenBounds.getHeight();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenHeight, Color.BLACK);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        ChessBoard board = new ChessBoard(screenHeight);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GameSquare square = board.getBoard()[row][col];

                square.setOnMouseClicked(event -> {
                    if (currentMode == Modes.moveMode) {
                        GameSquare selectedSquare = board.getSelectedSquare();

                        if (selectedSquare == null) {
                            if (!square.isEmpty() && board.colorMatches(square.getPieceColor(), board.isWhiteTurn())) {
                                board.setSelectedSquare(square);
                                board.squareClicked(square);
                            }
                        }

                        else {
                            if (!square.isEmpty() && board.colorMatches(square.getPieceColor(), board.isWhiteTurn())) {
                                board.setSelectedSquare(square);
                                board.squareClicked(square);
                            }

                            else {
                                board.makeMove(selectedSquare, square);
                                board.setSelectedSquare(null);
                                board.squareClicked(square);
                            }
                        }

                        board.setSelectedSquare(square);
                    } else if (currentMode == Modes.deleteMode) {
                        if (!square.isEmpty()) {
                            square.removePiece();
                        }
                    }
                });

                grid.add(square, col, row);
            }
        }

        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                } else if (event.getCode() == KeyCode.Q) {
                    currentMode = Modes.moveMode;
                } else if (event.getCode() == KeyCode.W) {
                    currentMode = Modes.deleteMode;
                }
            }
        });
        root.getChildren().addAll(grid);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}