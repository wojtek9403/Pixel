package window;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import pointsAndPanes.MyPoint;
import pointsAndPanes.PointMenager;

public class WorkSpace extends VBox {
	// col0 and 1 are rows in wich each of 2 boxes with points and image are placed
	private HBox row0;
	private HBox row1;

	private Paint[] colors;

	private Scene scene;

	private View view;

//	private DoubleProperty heightofPicture;
//	private DoubleProperty widthofPicture;

	public ReadOnlyDoubleProperty heightofContainers;
	public ReadOnlyDoubleProperty widthofContainers;

	public WorkSpace(Scene primaryScene) {
		this.setStyle("-fx-background-color: black;");
		this.scene = primaryScene;
		this.row0 = new HBox();
		this.row1 = new HBox();
		this.initColors();
		this.initView();
	}

	public void initColors() {
		// for each image diffrent color of point will be added
		this.colors = new Paint[4];
		colors[0] = Color.RED;
		colors[1] = Color.BLUE;
		colors[2] = Color.GREEN;
		colors[3] = Color.DARKVIOLET;
	}

	public void initView() {
		this.getChildren().add(row0);
		this.getChildren().add(row1);

	}

	public void initWorkSpace(InputStream is) {

		PointMenager thisViewPointMenager = new PointMenager();
		thisViewPointMenager.setView(view);

		Image image = new Image(is);

		double op = image.getWidth();
		double opp = image.getHeight();

		double ratio = 0;

		if (op > opp) {
			ratio = op / opp;
		} else {
			ratio = opp / op;
		}
		
		
		
		List<HBox> listOfPanels = new ArrayList<>();

		for (Paint color : colors) {
			// container for pannel
			HBox panel = new HBox();
			panel.setStyle("-fx-background-color: black;");
			panel.prefHeightProperty().bind(scene.heightProperty().divide(2));
			panel.prefWidthProperty().bind(scene.widthProperty().divide(2));
			panel.setFillHeight(false);

			ImageView picture = new ImageView(image);

			picture.setPreserveRatio(true);
			picture.fitHeightProperty()
					.bind(scene.heightProperty().divide(scene.widthProperty().divide(scene.heightProperty())));
			picture.fitWidthProperty().bind(scene.widthProperty()
					.divide((scene.widthProperty().divide(scene.heightProperty())).multiply(ratio * ratio)));

			// this values will be passed to PointNavPane for calaculation of possible point
			// movement in calculateCoordinates();
//			this.heightofPicture = picture.fitHeightProperty();
//			this.widthofPicture = picture.fitWidthProperty();

			panel.setAlignment(Pos.CENTER);

			// container in wich image and points are placed
			Pane photoContainer = new Pane();
			photoContainer.setPickOnBounds(false); // bardzo ważne uniemożliwia rozszerzanie pane na krawedziach pod
													// wplywem rosnacych dzieci

			this.heightofContainers = photoContainer.heightProperty();
			this.widthofContainers = photoContainer.widthProperty();
			

			thisViewPointMenager.addObserver(photoContainer);
			photoContainer.setCursor(getCursor().HAND);

			photoContainer.setOnMouseClicked(e -> {
				// point is added to Pane so wee see it

				MyPoint circle = new MyPoint(color, e.getX(), e.getY());
				thisViewPointMenager.addToAllPanes(circle);

			});

			photoContainer.getChildren().add(picture);
			panel.getChildren().add(photoContainer);
			listOfPanels.add(panel);

		}

		Platform.runLater(() -> {
			((Stage)scene.getWindow()).setMinHeight(heightofContainers.get()*10);
			((Stage)scene.getWindow()).setMinWidth(widthofContainers.get()*10);
			this.row0.getChildren().clear();
			this.row0.getChildren().add(listOfPanels.get(0));
			this.row0.getChildren().add(listOfPanels.get(1));

			this.row1.getChildren().clear();
			this.row1.getChildren().add(listOfPanels.get(2));
			this.row1.getChildren().add(listOfPanels.get(3));

		});

	}


	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

}
