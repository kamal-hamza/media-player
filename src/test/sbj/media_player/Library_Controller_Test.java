package test.sbj.media_player;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;

import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sbj.media_player.Library_Controller;

import org.testfx.util.WaitForAsyncUtils;
import org.testfx.service.query.NodeQuery;
import org.testfx.api.FxRobot;


public class Library_Controller_Test extends ApplicationTest {
    
    private Library_Controller controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sbj/media_player/Library.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        controller = fxmlLoader.getController();
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setUp() {

    }

    // Tests the open folder feature
    @Test
    public void openFolderTest() throws Exception {
        // I JUST CANT FIGURE OUT HOW WE WOULD TEST THIS
        // Problem is using directory chooser
    }

    // Tests the play feature
    @Test
    public void testPlay() throws Exception {
        File sampleFile = new File("src/test/assets/better-day-186374.mp3");
        Method addFile = Library_Controller.class.getDeclaredMethod("addFile", File.class);
        Field MPField = Library_Controller.class.getDeclaredField("MP");

        MPField.setAccessible(true);
        addFile.setAccessible(true);

        addFile.invoke(controller, sampleFile);

        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#playButton");

        MediaPlayer MP = (MediaPlayer) MPField.get(controller);
        assertNotNull(MP);
        assertEquals(MediaPlayer.Status.PLAYING, MP.getStatus());
    }

    // Tests the shuffle feature
    @Test
    public void testShuffle() throws Exception {
        File sampleFile1 = new File("src/tests/assets/better-day-186374.mp3");
        File sampleFile2 = new File("src/tests/assets/ethereal-vistas-191254.mp3");
        File sampleFile3 = new File("src/tests/assets/groovy-ambient-funk-201745.mp3");
        File sampleFile4 = new File("src/tests/assets/movement-200697.mp3");
        Method addFile = Library_Controller.class.getDeclaredMethod("addFile", File.class);
        Field MPField = Library_Controller.class.getDeclaredField("MP");

        MPField.setAccessible(true);
        addFile.setAccessible(true);

        addFile.invoke(controller, sampleFile1);
        addFile.invoke(controller, sampleFile2);
        addFile.invoke(controller, sampleFile3);
        addFile.invoke(controller, sampleFile4);

        Field orignalLibraryField = Library_Controller.class.getDeclaredField("library");
        orignalLibraryField.setAccessible(true);
        List<File> orignalLibrary = (ArrayList<File>) orignalLibraryField.get(controller);

        Field shuffledLibraryField = Library_Controller.class.getDeclaredField("shuffledLibrary");
        shuffledLibraryField.setAccessible(true);
        List<File> shuffledLibrary = (ArrayList<File>) shuffledLibraryField.get(controller);
        
        assertNotEquals(shuffledLibrary, orignalLibrary);
    }

    @Test
    public void testRepeat() throws Exception {
        
    }

}
