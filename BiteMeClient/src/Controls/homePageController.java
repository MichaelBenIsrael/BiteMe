package Controls;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Set;

import Entities.ServerResponse;
import Entities.User;
import client.ClientGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Homepage form controller.
 * 
 * @author Shaked
 * @author Natali
 * 
 * @version December 05 2021, v1.0
 */
public class homePageController implements Initializable {

	private Router router;

	private Stage stage;

	private Scene scene = null;

	private AnchorPane mainContainer;

	private Deque<String> favListDisplayed = new LinkedList<>(); // Displayed 3 fav restaurants
	private Deque<String> favListHidden = new LinkedList<>(); // 3 hidden fav restaurants

	@FXML
	private ImageView caruasalLeft;

	@FXML
	private ImageView caruasalRight;

	@FXML
	private Text homePageBtn;

	@FXML
	private Text logOutBtn;

	@FXML
	private Text loginBtn;

	@FXML
	private Text profileBtn;

	@FXML
	private Text restaurantBtn;

	@FXML
	private Text userFirstName;

	@FXML
	private Rectangle avatar;

	@FXML
	private Text ceoBtn;

	@FXML
	private Text managerBtn;

	@FXML
	private Text supplierBtn;

	@FXML
	private Text employerHRBtn;

	@FXML
	private ImageView res1;

	@FXML
	private ImageView res2;

	@FXML
	private ImageView res3;

	/**
	 * Moving the carousel 1 step backward.
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void caruasalLeftClicked(MouseEvent event) {
		String resName = favListHidden.pollLast().toLowerCase();
		favListHidden.addFirst(res1.getId());
		favListDisplayed.addLast(resName);
		res1.setImage(res2.getImage());
		res1.setId(res2.getId());
		res2.setImage(res3.getImage());
		res2.setId(res3.getId());
		res3.setImage(new Image(getClass().getResource("../images/" + resName + "-logo.jpg").toString()));
		res3.setId(resName);
	}

	/**
	 * Moving the carousel 1 step forward
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void caruasalRight(MouseEvent event) {
		String resName = favListHidden.pollFirst().toLowerCase();
		favListHidden.addLast(res3.getId());
		favListDisplayed.addFirst(resName);
		res3.setImage(res2.getImage());
		res3.setId(res2.getId());
		res2.setImage(res1.getImage());
		res2.setId(res1.getId());
		res1.setImage(new Image(getClass().getResource("../images/" + resName + "-logo.jpg").toString()));
		res1.setId(resName);
	}

	@FXML
	void ceoBtnClicked(MouseEvent event) {

	}

	@FXML
	void managerBtnClicked(MouseEvent event) {
		if (router.getRestaurantselectionController() == null) {
			AnchorPane mainContainer;
			managerPanelController controller;
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../gui/bitemeManagerPanelPage.fxml"));
				mainContainer = loader.load();
				controller = loader.getController();
				//controller.setStage(stage);
				controller.setAvatar();
				controller.setStage(stage);
				Scene mainScene = new Scene(mainContainer);
				mainScene.getStylesheets().add(getClass().getResource("../gui/style.css").toExternalForm());
				controller.setScene(mainScene);
				stage.setTitle("BiteMe - Manager Home Page");
				stage.setScene(mainScene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			stage.setTitle("BiteMe - Manager Panel");
			stage.setScene(router.getManagerPanelController().getScene());
			stage.show();
		}
	}

	@FXML
	void supplierBtnClicked(MouseEvent event) {

	}

	@FXML
	void employerHRBtnClicked(MouseEvent event) {

	}

	@FXML
	void profileBtnClicked(MouseEvent event) {

	}

	/**
	 * Onclicked event handler, switching scenes to restaurant selection scene.
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void restaurantBtnClicked(MouseEvent event) {
		if (router.getRestaurantselectionController() == null) {
			AnchorPane mainContainer;
			restaurantSelectionController controller;
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../gui/bitemeRestaurantsPage.fxml"));
				mainContainer = loader.load();
				controller = loader.getController();
				controller.setStage(stage);
				controller.setAvatar();
				controller.setRestaurants();
				Scene mainScene = new Scene(mainContainer);
				mainScene.getStylesheets().add(getClass().getResource("../gui/style.css").toExternalForm());
				controller.setScene(mainScene);
				stage.setTitle("BiteMe - Restaurants");
				stage.setScene(mainScene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			stage.setTitle("BiteMe - Restaurants");
			stage.setScene(router.getRestaurantselectionController().getScene());
			stage.show();
		}
	}

	/**
	 * Switching scenes to login page.
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void displayLoginScreen(MouseEvent event) {
		if (router.getLogincontroller() == null) {
			AnchorPane mainContainer;
			loginController controller;
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../gui/bitemeLoginPage.fxml"));
				mainContainer = loader.load();
				controller = loader.getController();
				controller.setStage(stage);
				controller.setAvatar();
				Scene mainScene = new Scene(mainContainer);
				mainScene.getStylesheets().add(getClass().getResource("../gui/style.css").toExternalForm());
				controller.setScene(mainScene);
				stage.setTitle("BiteMe - Restaurants");
				stage.setScene(mainScene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			stage.setTitle("BiteMe - Login");
			stage.setScene(router.getLogincontroller().getScene());
			stage.show();
		}
	}

	public AnchorPane getMainContainer() {
		return mainContainer;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Setting homePage to match user's permissions.
	 */
	public void setProfile(boolean val) {
		ServerResponse resUser = ClientGUI.client.getUser();
		User user = (User) resUser.getServerResponse();
		if (user != null)
			userFirstName.setText(user.getFirstName());
		setDefaults(user, val);
		logOutBtn.setStyle("-fx-cursor: hand;");
		profileBtn.setStyle("-fx-cursor: hand;");
		restaurantBtn.setStyle("-fx-cursor: hand;");
		homePageBtn.setStyle("-fx-cursor: hand;");
	}

	/**
	 * Onclicked event handler, logout.
	 * 
	 * @param MouseEvent event
	 */
	@FXML
	void logOutBtnClicked(MouseEvent event) {
		ServerResponse resUser = ClientGUI.client.getUser();
		User user = (User) resUser.getServerResponse();
		if (user != null) {
			ClientGUI.client.logout(user.getUserName());
			ClientGUI.client.setUser(null);
			userFirstName.setText("Guest");
			setDefaults(user, false);
		}
	}

	/**
	 * Changing the homepage interface depending on the user permissions.
	 * 
	 * @param boolean val
	 */
	private void setDefaults(boolean val) {
		logOutBtn.setVisible(val);
		profileBtn.setVisible(val);
		homePageBtn.setVisible(val);
		loginBtn.setVisible(!val);

	}

	/**
	 * Changing the homepage interface depending on the user permissions.
	 */
	private void setDefaults(User user, boolean val) { // after logout: null,false, after login: manager,true
		setDefaults(val);
		if (user==null)
		{
			managerBtn.setVisible(val);
			restaurantBtn.setVisible(val);
		}
		else
		{
				switch (user.getUserType()) {
				case Customer:
				case BusinessCustomer:
					restaurantBtn.setVisible(val);
					break;
				case BranchManager:
					managerBtn.setVisible(val);
					break;
				case Supplier:
					supplierBtn.setVisible(val);
					break;
				case CEO:
					ceoBtn.setVisible(val);
					break;
				case EmployerHR:
					employerHRBtn.setVisible(val);
					break;
			}
		}


	}

	/**
	 * Setting the avatar image of the user.
	 */
	public void setAvatar() {
		try {
			avatar.setArcWidth(65);
			avatar.setArcHeight(65);
			ImagePattern pattern = new ImagePattern(
					new Image(getClass().getResource("../images/guest-avatar.png").toString()));
			avatar.setFill(pattern);
			avatar.setEffect(new DropShadow(3, Color.BLACK));
			avatar.setStyle("-fx-border-width: 0");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		router = Router.getInstance();
		router.setHomePageController(this);
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}

	/**
	 * Getting the favourite restaurants in the db.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void setFavRestaurants() {
		ServerResponse ResFavRestaurants = ClientGUI.client.getFavRestaurants();
		HashMap<String, File> favRestaurants;
		if (ResFavRestaurants == null) {
			ClientGUI.client.favRestaurantsRequest();
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while (ClientGUI.client.getFavRestaurants() == null) {
						synchronized (ClientGUI.monitor) {
							try {
								ClientGUI.monitor.wait();
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
						}
					}
				}
			});
			t.start();
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		favRestaurants = (HashMap<String, File>) ClientGUI.client.getFavRestaurants().getServerResponse();
		Set<String> resSet = favRestaurants.keySet();
		String[] res = new String[resSet.size()];
		int j = 0;
		for (String s : resSet) {
			res[j] = s;
			j++;
		}
		setFavDisplayed(res);
	}

	/**
	 * Setting the queue's for the carousel.
	 * 
	 * @param String[] res
	 */
	private void setFavDisplayed(String[] res) {
		for (int i = 0; i < 3; i++) {
			favListDisplayed.addLast(res[i]);
			favListHidden.addLast(res[i + 3]);
		}
		String resName = favListDisplayed.pollFirst().toLowerCase();
		res1.setImage(new Image(getClass().getResource("../images/" + resName + "-logo.jpg").toString()));
		res1.setId(resName);
		favListDisplayed.addLast(resName);
		resName = favListDisplayed.pollFirst().toLowerCase();
		res2.setImage(new Image(getClass().getResource("../images/" + resName + "-logo.jpg").toString()));
		res2.setId(resName);
		favListDisplayed.addLast(resName);
		resName = favListDisplayed.pollFirst().toLowerCase();
		res3.setImage(new Image(getClass().getResource("../images/" + resName + "-logo.jpg").toString()));
		res3.setId(resName);
		favListDisplayed.addLast(resName);
	}

	public void setContainer(AnchorPane mainContainer) {
		this.mainContainer = mainContainer;
	}
}
