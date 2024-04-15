/*

Author: Multiple
Creation Date: 4/10/2024

Music_Player: Music player built for CSS 360 final project
Features:	Play, Stop, Pause, Skip, Repeat songs


*/

package sbj.media_player;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Music_Player {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onPlayButtonClick() {
        welcomeText.setText("Test Button Functionality - Play");
    }

    @FXML
    protected void onStopButtonClick() {
        welcomeText.setText("Test Button Functionality - Stop");
    }

    @FXML
    protected void onPauseButtonClick() {
        welcomeText.setText("Test Button Functionality - Pause");
    }

    @FXML
    protected void onBackButtonClick() {
        welcomeText.setText("Test Button Functionality - Back");
    }

    @FXML
    protected void onForwardButtonClick() {
        welcomeText.setText("Test Button Functionality - Forward");
    }

    @FXML
    protected void onRepeatButtonClick() {
        welcomeText.setText("Test Button Functionality - Repeat");
    }

    @FXML
    protected void onOpenButtonClick() {
        welcomeText.setText("Test Button Functionality - Open");
    }
}