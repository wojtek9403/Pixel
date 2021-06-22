package window;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import pointsAndPanes.PointsNavPane;

public class View extends BorderPane {

	private WorkSpace workSpace;

	private Scene scene;

	private ScrollPane navBar;

	public void setScene(Scene primaryScene) {
		this.scene = primaryScene;
	}

	public void setWorkspace() {
		//workspace is scalling to the size of window so it's nessecery to pass scene to it
		this.workSpace = new WorkSpace(scene);
		this.workSpace.setView(this);
		this.setCenter(workSpace);

	}

	public void setNavBar() {

		VBox bar = new VBox();
		this.navBar = new ScrollPane();
		//navbar is scalling to window width
		navBar.minWidthProperty().bind(scene.widthProperty().divide(7));
		navBar.setContent(bar);

		this.setLeft(navBar);

	}

	public void addNavPane(PointsNavPane np) {

		((VBox) this.navBar.getContent()).getChildren().add(np);

	}

	public void setMenuBar() {

		Menu menu = new Menu("Menu");

		String menu_item_string = "Add / switch image";

		MenuItem menu_item = new MenuItem(menu_item_string);
		menu_item.setOnAction(e -> {

			FileChooser f = new FileChooser();
			File image = f.showOpenDialog(scene.getWindow());

			Thread loadImage = new Thread(new Runnable() {

				@Override
				public void run() {
					FileInputStream fis;

					try {
						if (image != null) {
														
							fis = new FileInputStream(image.toPath().toString());
							if(ImageIO.read(image) == null) {
								throw new IOException("not an image!");
							}

							Platform.runLater(() -> {
								((VBox) navBar.getContent()).getChildren().clear();
							});

							workSpace.initWorkSpace(fis);
							
							fis.close();
						}
					} catch (IOException e1) {

						Platform.runLater(() -> {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Problem with your file");
							alert.setHeaderText(null);
							alert.setContentText(
									"There was a problem with the file, file not found or file is broken !");
							alert.showAndWait();
						});
					}
				}

			});

			loadImage.setName("loadImage thread");
			loadImage.start();

		});

		menu.getItems().add(menu_item);
		MenuBar menu_bar = new MenuBar();
		menu_bar.getMenus().add(menu);

		VBox menu_cont = new VBox(menu_bar);
		this.setTop(menu_cont);

	}

	public WorkSpace getWorkSpace() {
		return workSpace;
	}

	public ScrollPane getNavBar() {
		return navBar;
	}

	public void setNavBar(ScrollPane navBar) {
		this.navBar = navBar;
	}

}
