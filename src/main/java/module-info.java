module Chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.media;

    exports Chess;
    exports Chess.Pieces;
}
