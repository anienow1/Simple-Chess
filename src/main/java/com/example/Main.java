package com.example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 1000, Color.CORNSILK);
        Image icon = new Image("IMG_6333.jpg");
        ImageView image = new ImageView(icon);

        image.setScaleX(.35);
        image.setScaleY(.35);
        image.setY(-500);
        
        Rectangle rect = new Rectangle(100, 100, new Color(117, 148, 91));

        stage.getIcons().add(icon);
        stage.setFullScreen(true);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                }
            }
        });
        root.getChildren().addAll(image, rect);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Chess");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}