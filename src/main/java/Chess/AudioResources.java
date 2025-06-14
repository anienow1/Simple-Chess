package Chess;

import javafx.scene.media.AudioClip;

public class AudioResources {
    private static final AudioClip CAPTURE;
    private static final AudioClip CASTLE;
    private static final AudioClip CHECK;
    private static final AudioClip MOVE;

    static {
        MOVE = new AudioClip(
                AudioResources.class.getResource("/sounds/sfx-normal-move.wav").toExternalForm());
        CAPTURE = new AudioClip(
                AudioResources.class.getResource("/sounds/sfx-capture.wav").toExternalForm());
        CASTLE = new AudioClip(
                AudioResources.class.getResource("/sounds/sfx-castle.wav").toExternalForm());
        CHECK = new AudioClip(
                AudioResources.class.getResource("/sounds/sfx-check-move.wav").toExternalForm());
    }

    private static void playCapture() {
        CAPTURE.play();
    }

    private static void playCheck() {
        CHECK.play();
    }

    private static void playCastle() {
        CASTLE.play();
    }

    private static void playMove() {
        MOVE.play();
    }

    public static void playAudioOnce(int clip, boolean playedAudio) {
        if (!playedAudio) {
            switch (clip) {
                case (1): {
                    playMove();
                    break;
                }
                case (2): {
                    playCapture();
                    break;
                }
                case (3): {
                    playCheck();
                    break;
                }
                case (4): {
                    playCastle();
                    break;
                }
            }
        }
    }
}
