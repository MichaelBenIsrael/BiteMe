package Controls;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import Entities.Order;
import Entities.ServerResponse;
import Entities.Supplier;
import Enums.RestaurantType;
import Enums.UserType;
import client.ClientGUI;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class restaurantSelectionController implements Initializable {

	private ServerResponse resRestaurants = null;
	public final UserType type = UserType.Customer;
	private Router router;

	private Stage stage;

	private Scene scene;

	private List<ImageView> resImages;

	private List<Text> resNameTexts;

	private List<Label> resOrders;

	private List<Rectangle> borders;

	private ArrayList<Supplier> restaurants;
	private IntegerProperty page = new SimpleIntegerProperty(0);

	@FXML
	private Rectangle avatar;

	@FXML
	private Text homePageBtn;

	@FXML
	private ImageView leftArrowBtn;

	@FXML
	private Text logoutBtn;

	@FXML
	private Text profileBtn;

	@FXML
	private ImageView resImage1;

	@FXML
	private ImageView resImage2;

	@FXML
	private ImageView resImage3;

	@FXML
	private ImageView resImage4;

	@FXML
	private ImageView resImage5;

	@FXML
	private ImageView resImage6;

	@FXML
	private Text resText1;

	@FXML
	private Text resText2;

	@FXML
	private Text resText3;

	@FXML
	private Text resText4;

	@FXML
	private Text resText5;

	@FXML
	private Text resText6;

	@FXML
	private Label resOrder1;

	@FXML
	private Label resOrder2;

	@FXML
	private Label resOrder3;

	@FXML
	private Label resOrder4;

	@FXML
	private Label resOrder5;

	@FXML
	private Label resOrder6;

	@FXML
	private Rectangle border1;

	@FXML
	private Rectangle border2;

	@FXML
	private Rectangle border3;

	@FXML
	private Rectangle border4;

	@FXML
	private Rectangle border5;

	@FXML
	private Rectangle border6;

	@FXML
	private Text restaurantsBtn;

	@FXML
	private ImageView rightArrowBtn;

	@FXML
	private TextField searchRestaurantFieldTxt;

	@FXML
	private Text itemsCounter;

	@FXML
	private AnchorPane root;

	/**
	 * Search functionality, filtering the restaurants based on value in search
	 * field.
	 * 
	 * @param event
	 */
	@FXML
	void filterRestaurants(KeyEvent event) {
		String text = searchRestaurantFieldTxt.getText();
		List<Supplier> searchResults = restaurants.stream()
				.filter(r -> r.getRestaurantName().toLowerCase().contains(text.toLowerCase()))
				.collect(Collectors.toList());
		ArrayList<Supplier> newRestaurants = new ArrayList<>();
		for (Supplier suplier : searchResults) {
			newRestaurants.add(suplier);
		}
		createRestaurants(newRestaurants);
	}

	@FXML
	public void changeToCart(MouseEvent event) {
		router.changeToMyCart("Restaurants");
	}

	@FXML
	void logoutClicked(MouseEvent event) {
		router.logOut();
	}

	/**
	 * Method to display the previous page of restaurants list.
	 * 
	 * @param event
	 */
	@FXML
	void moveLeftClicked(MouseEvent event) {
		ArrayList<Supplier> prevPage = new ArrayList<>();
		if (page.get() == 0) {
			return;
		}
		int counter = 0;
		for (int i = (page.get() - 1) * 6; i < restaurants.size() && counter < 6; i++, counter++) {
			prevPage.add(restaurants.get(i));
		}
		page.set(page.get() - 1);
		createRestaurants(prevPage);
	}

	/**
	 * Method to display the next page of restaurants list.
	 * 
	 * @param event
	 */
	@FXML
	void moveRightClicked(MouseEvent event) {
		ArrayList<Supplier> nextPage = new ArrayList<>();
		int counter = 0;
		for (int i = (page.get() + 1) * 6; i < restaurants.size() && counter < 6; i++, counter++) {
			nextPage.add(restaurants.get(i));
		}
		page.set(page.get() + 1);
		if (nextPage.size() != 0)
			createRestaurants(nextPage);
	}

	@FXML
	void returnToHomePage(MouseEvent event) {
		router.changeSceneToHomePage();
	}

	/**
	 * Setting the stage instance.
	 * 
	 * @param Stage stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Setting the avatar image of the user.
	 */
	public void setAvatar() {
		router.setAvatar(avatar);
	}

	/**
	 * Getting restaurants from the db.
	 */
	@SuppressWarnings("unchecked")
	public void setRestaurants() {
		if (resRestaurants == null) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized (ClientGUI.monitor) {
						ClientGUI.client.restaurantsRequest();
						try {
							ClientGUI.monitor.wait();
						} catch (Exception e) {
							e.printStackTrace();
							return;
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
			resRestaurants = ClientGUI.client.getLastResponse();
		}
		restaurants = ((ArrayList<Supplier>) resRestaurants.getServerResponse());
		createRestaurants(restaurants);
	}

	/**
	 * Displaying restaurants base on filtered list.
	 * 
	 * @param amountToHide
	 */
	private void hideRestaurants(int amountToHide) {
		for (int i = 0; i < amountToHide; i++) {
			resImages.get(resImages.size() - 1 - i).setVisible(false);
			resNameTexts.get(resNameTexts.size() - 1 - i).setVisible(false);
			resOrders.get(resOrders.size() - 1 - i).setVisible(false);
			if (borders.get(borders.size() - 1 - i) != null) {
				borders.get(borders.size() - 1 - i).setVisible(false);
			}
		}
	}

	/**
	 * Private method filtering the restaurants by type.
	 * 
	 * @param type
	 */
	private void filterByType(RestaurantType type) {
		List<Supplier> filteredList = restaurants.stream().filter(r -> r.getRestaurantType().equals(type))
				.collect(Collectors.toList());
		createRestaurants((ArrayList<Supplier>) filteredList);
	}

	/**
	 * Setting buttons for restaurants filtering.
	 */
	public void setButtons() {
		Button showAll = new Button("Show All");
		showAll.setOnAction(e -> createRestaurants(restaurants));
		showAll.setLayoutX(44);
		showAll.setLayoutY(237);
		Button type1 = new Button(RestaurantType.Asian.toString());
		type1.setOnAction(e -> filterByType(RestaurantType.Asian));
		type1.setLayoutX(44);
		type1.setLayoutY(279);
		Button type2 = new Button(RestaurantType.Fastfood.toString());
		type2.setOnAction(e -> filterByType(RestaurantType.Fastfood));
		type2.setLayoutX(44);
		type2.setLayoutY(321);
		Button type3 = new Button(RestaurantType.Italian.toString());
		type3.setOnAction(e -> filterByType(RestaurantType.Italian));
		type3.setLayoutX(44);
		type3.setLayoutY(363);
		Button type4 = new Button(RestaurantType.Other.toString());
		type4.setOnAction(e -> filterByType(RestaurantType.Other));
		type4.setLayoutX(44);
		type4.setLayoutY(405);
		root.getChildren().addAll(showAll, type1, type2, type3, type4);
	}

	/**
	 * Creating restaurants options selection. Setting onclick event on every Order
	 * Now label affiliate with the restaurant.
	 * 
	 * @param restaurants
	 */
	private void createRestaurants(ArrayList<Supplier> restaurants) {
		if (resImages == null) {
			resImages = Arrays.asList(resImage1, resImage2, resImage3, resImage4, resImage5, resImage6);
		}
		if (resNameTexts == null) {
			resNameTexts = Arrays.asList(resText1, resText2, resText3, resText4, resText5, resText6);
		}
		if (resOrders == null) {
			resOrders = Arrays.asList(resOrder1, resOrder2, resOrder3, resOrder4, resOrder5, resOrder6);
		}
		if (borders == null) {
			borders = Arrays.asList(border1, border2, border3, border4, border5, border6);
		}
		for (int i = 0; i < 6; i++) {
			resImages.get(i).setVisible(true);
			resNameTexts.get(i).setVisible(true);
			resOrders.get(i).setVisible(true);
			if (borders.get(i) != null) {
				borders.get(i).setVisible(true);
			}
		}
//		List<Supplier> resNames = new ArrayList<>();
//		resNames.addAll(restaurants.keySet());
		int amount = restaurants.size();
		switch (amount) {
		case 0:
			hideRestaurants(6);
			break;
		case 1:
			hideRestaurants(5);
			break;
		case 2:
			hideRestaurants(4);
			break;
		case 3:
			hideRestaurants(3);
			break;
		case 4:
			hideRestaurants(2);
			break;
		case 5:
			hideRestaurants(1);
			break;
		}
		/** At all time display up to 6 restaurants */
		for (int i = 0; i < restaurants.size() && i < 6; i++) {
			String resName = restaurants.get(i).getRestaurantName();
			resImages.get(i).setImage(
					new Image(getClass().getResource("../images/" + resName.toLowerCase() + "-logo.jpg").toString()));
			resNameTexts.get(i).setText(resName);
			Label resOrder = resOrders.get(i);
			/**
			 * Setting event listener on every order now button affiliate with each
			 * restaurant.
			 */
			resOrder.setOnMouseClicked((MouseEvent e) -> {
				if (router.getOrder().getRestaurantName() != null
						&& !router.getOrder().getRestaurantName().equals(resName)) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Switch restaurants");
					alert.setHeaderText("You got an order in restaurant " + router.getOrder().getRestaurantName()
							+ "\nChoosing a different restaurant will reset your last order.");
					alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
						Order newOrder = new Order();
						newOrder.setRestaurantName(resName);
						router.setOrder(new Order());
						changeToIdentify(resName);
					});
				} else {
					changeToIdentify(resName);
				}

			});
		}
	}

	private void changeToIdentify(String resName) {
		if (router.getIdentifyController() == null) {
			AnchorPane mainContainer;
			identifyController controller;
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../gui/bitemeIdentifyBeforeOrderPage.fxml"));
				mainContainer = loader.load();
				controller = loader.getController();
				controller.setAvatar();
				controller.setRestaurantToOrder(resName);
				controller.setItemsCounter();
				Scene mainScene = new Scene(mainContainer);
				mainScene.getStylesheets().add(getClass().getResource("../gui/style.css").toExternalForm());
				controller.setScene(mainScene);
				stage.setTitle("BiteMe - Identification Page");
				stage.setScene(mainScene);
				stage.show();
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}
		} else {
			router.getIdentifyController().setRestaurantToOrder(resName);
			router.getIdentifyController().setItemsCounter();
			stage.setTitle("BiteMe - Identification page");
			stage.setScene(router.getIdentifyController().getScene());
			stage.show();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		router = Router.getInstance();
		router.setRestaurantselectionController(this);
		setStage(router.getStage());
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		return scene;
	}

	@FXML
	void profileBtnClicked(MouseEvent event) {
		router.showProfile();
	}

	public void setItemsCounter() {
		itemsCounter.setText(router.getBagItems().size() + "");
	}

	/**
	 * @return the resRestaurants
	 */
	public ServerResponse getResRestaurants() {
		return resRestaurants;
	}

	/**
	 * @param resRestaurants the resRestaurants to set
	 */
	public void setResRestaurants(ServerResponse resRestaurants) {
		this.resRestaurants = resRestaurants;
	}

}
