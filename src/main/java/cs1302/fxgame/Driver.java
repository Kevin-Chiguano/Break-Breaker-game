package cs1302.fxgame;
import com.michaelcotterell.game.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Driver extends Application {
    public void start(Stage primaryStage) throws Exception { 
        
        Game game = new ladrilloRoto(primaryStage);
        primaryStage.setTitle(game.getTitle());
        primaryStage.setScene(game.getScene());
        primaryStage.show();
        game.run();
    }
    public static void main(String[] args) {
        launch(args);
    } 
}
