package Controls;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientGUI;
import client.ClientUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controller for the landing page of the application. user needs to provide the
 * server ip.
 * 
 * @author Shaked
 * @author Natali
 * 
 * @version December 05 2021, v1.0
 *
 */
public class EnterGUIController implements Initializable {

	private Router router;
	
	private Stage stage;

	@FXML
	private Label enterBtn;

	@FXML
	private Label errorMsg;

	@FXML
	private Label exitBtn;

	@FXML
	private TextField ipTxt;

	/**
	 * Setting the stage instance.
	 * 
	 * @param Stage stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Onclick event handler, closing the application.
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void closeApplication(MouseEvent event) {
		System.exit(0);
	}

	/**
	 * Onclick event handler, checking if the ip is a valid string and switch scenes
	 * to the homepage scene.
	 * 
	 * @param MouseEvent event.
	 */
	@FXML
	void enterApplication(MouseEvent event) {
		String ip = ipTxt.getText();
		if (ip == null || ip.equals("")) {
			errorMsg.setTextFill(Color.web("red"));
			errorMsg.setText("Must fill ip");
			return;
		}
		ClientGUI.client = new ClientUI(ip, 5555);
//		AnchorPane mainContainer;
//		homePageController controller;
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("../gui/bitemeHomePage.fxml"));
//			mainContainer = loader.load();
//			controller = loader.getController();
//			controller.setStage(stage);
//			controller.setAvatar();
//			controller.setFavRestaurants();
//			Scene mainScene = new Scene(mainContainer);
//			mainScene.getStylesheets().add(getClass().getResource("../gui/style.css").toExternalForm());
//			stage.setTitle("BiteMe - HomePage");
//			stage.setScene(mainScene);
//			stage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
		(new homePageController()).setScene(stage);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		router=Router.getInstance();
		router.setEnterguicontroller(this);
		
	}

}
