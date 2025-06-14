package Chess;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

import Chess.Pieces.Bishop;
import Chess.Pieces.Knight;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;
import Chess.Pieces.Rook;

/**
 * JavaFX App
 */
public class Main extends Application {

    private Modes currentMode = Modes.moveMode;
    private ChessBoard board;
    private Pane root;

    private enum Modes {
        moveMode,
        deleteMode;
    }

    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); // Find the size of the viewing screen
        double screenHeight = screenBounds.getHeight();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenHeight, Color.BLACK);

        board = new ChessBoard(screenHeight, this);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setLayoutX((scene.getWidth() - 8 * board.getSquareSize()) /2);
        grid.setLayoutY((scene.getHeight() - 8 * board.getSquareSize()) /2);


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
        // showPromotionChoices(board.getBoard()[0][0], false);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void showPromotionChoices(GameSquare square, boolean isWhite) {
        VBox options = new VBox();
        options.setAlignment(Pos.CENTER);
        options.setSpacing(4);
        options.setStyle("-fx-background-color: rgba(255,255,255,1);" +
                "-fx-border-color: black;" +
                "-fx-border-width: 2;");
        double boxSize = board.getSquareSize();
        options.setPrefWidth(boxSize);
        options.setPrefHeight(boxSize * 2);

        Piece[] choices = {
                new Queen(isWhite, square.getRow(), square.getCol()),
                new Rook(isWhite, square.getRow(), square.getCol()),
                new Bishop(isWhite, square.getRow(), square.getCol()),
                new Knight(isWhite, square.getRow(), square.getCol())
        };

        for (Piece piece : choices) {
            ImageView image = piece.getImage();
            image.setFitHeight(59);
            image.setPreserveRatio(true);

            StackPane pane = new StackPane(image);
            pane.setOnMouseClicked(e -> {
                root.getChildren().remove(options);
                board.finalizePromotion(piece);
                board.getHistory().peek().setPromotedPiece(piece);
            });

            options.getChildren().add(pane);
        }
        
        Bounds bounds = square.localToScene(square.getBoundsInLocal());
        double posX = bounds.getMinX();
        double posY = bounds.getMinY();

        options.setLayoutX(posX);
        if (isWhite) {
            options.setLayoutY(posY + boxSize);
        } else {
            options.setLayoutY(posY - boxSize * 2);
        }

        root.getChildren().add(options);
    }

    public void displayEndOfGame(int whoWins) {

    }
    public static void main(String[] args) {
        launch();
    }

}