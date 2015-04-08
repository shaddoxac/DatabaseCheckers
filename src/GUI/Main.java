package GUI;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("CheckerGUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,800,512);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
//TODO jumps are not being detected correctly
//TODO fix square 9 to square 1, 1 oc, 5 oc, piece gone
//TODO if currentMoves.size()==0, endGame instead of passing to AI
//TODO fix jump square 17 -> 8