package tests.sbj.media_player;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import org.junit.Before;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sbj.media_player.Library_Controller;

import org.testfx.util.WaitForAsyncUtils;


public class Library_Controller_Test extends ApplicationTest {
    private Library_Controller controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sbj/media_player/Library.fxml"));
        controller = fxmlLoader.getController();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testPlay() throws Exception {
        File sampleFile = new File("C:/Users/kamal/Music/spotifydown.com - Nobody - from Kaiju No. 8.mp3");
        Method addFile = Library_Controller.class.getDeclaredMethod("addFile", File.class);
        Field MPField = Library_Controller.class.getDeclaredField("MP");

        MPField.setAccessible(true);
        addFile.setAccessible(true);

        addFile.invoke(controller, sampleFile);

        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#play");

        MediaPlayer MP = (MediaPlayer) MPField.get(controller);
        assertNotNull(MP);
        assertEquals(MediaPlayer.Status.PLAYING, MP.getStatus());
    }
}
