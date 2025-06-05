package Chess;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import Chess.Pieces.PieceImages;

/**
 * JavaFX App
 */
public class Main extends Application {

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
                    board.squareClicked(square);

                });

                grid.add(square, col, row);
            }
        }


        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close();
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