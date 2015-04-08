package knowledgedesigner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class KnowledgeDesignerDriver extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(KnowledgeDesignerDriver.class.getResource("KnowledgeDesignerGUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root,655,512);
			//scene.getStylesheets().add(getClass().getResource("KDStyleSheet.css").toExternalForm());
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
