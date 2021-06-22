package pointsAndPanes;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import window.View;
// provides point representative panel
public class PointsNavPane extends BorderPane {

	private Label name;
	private HBox panel;
	private Label x;
	private Label y;
	private TextField xField;
	private TextField yField;
	private View view;

	private List<MyPoint> menagedPoints;

	public PointsNavPane() {

		this.menagedPoints = new ArrayList<>();

		name = new Label("Point");
		x = new Label("X = ");
		y = new Label("  Y = ");
		xField = new TextField();
		xField.setPrefWidth(60);
		xField.setPadding(new Insets(2));

		yField = new TextField();
		yField.setPrefWidth(60);
		yField.setPadding(new Insets(2));

		panel = new HBox();
		panel.getChildren().add(x);
		panel.getChildren().add(xField);

		panel.getChildren().add(y);
		panel.getChildren().add(yField);
		this.setTop(name);
		this.setCenter(panel);
		this.navPaneEnableListener();

	}

	public void addMenagedPoint(MyPoint p) {
		this.menagedPoints.add(p);
		this.bindNavPane();
	}

	public void bindNavPane() {

		this.getName().setTextFill(this.menagedPoints.get(0).getFill());
		this.getXField().setText(this.menagedPoints.get(0).getLayoutX() + "");
		this.getYField().setText(this.menagedPoints.get(0).getLayoutY() + "");

	}

	public void navPaneEnableListener() {

		this.getXField().setOnKeyPressed(k -> {
			this.calculateCoordinates(k);
		});

		this.getYField().setOnKeyPressed(n -> {
			this.calculateCoordinates(n);
		});

	}

	public void calculateCoordinates(javafx.scene.input.KeyEvent n) {
		// set new point xy while wee etdit it on point panel
		if (n.getCode() == KeyCode.ENTER) {

			String texX = this.getXField().getText();
			String texY = this.getYField().getText();

			double x = this.menagedPoints.get(0).getLayoutX();
			if ((!texX.isBlank()) && texX.matches("([0-9]*\\.[0-9]*)|[0-9]*"))
				x = Double.parseDouble(texX);

			double y = this.menagedPoints.get(0).getLayoutY();
			if ((!texY.isBlank()) && texY.matches("([0-9]*\\.[0-9]*)|[0-9]*"))
				y = Double.parseDouble(texY);
			
			//from view
			
			double YsizeOfPic = this.getView().getWorkSpace().getHeightofPicture().get();
			double XsizeOfPic = this.getView().getWorkSpace().getWidthofPicture().get();

			if (y < YsizeOfPic && x < XsizeOfPic) {
				for (MyPoint p : this.menagedPoints) {
					p.setLayoutX(x);
					p.setLayoutY(y);
				}
			}

		}
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Label getName() {
		return name;
	}

	public void setName(Label name) {
		this.name = name;
	}

	public HBox getPanel() {
		return panel;
	}

	public Label getX() {
		return x;
	}

	public void setX(Label x) {
		this.x = x;
	}

	public Label getY() {
		return y;
	}

	public void setY(Label y) {
		this.y = y;
	}

	public TextField getXField() {
		return xField;
	}

	public void setXf(TextField xField) {
		this.xField = xField;
	}

	public TextField getYField() {
		return yField;
	}

	public void setYf(TextField yField) {
		this.yField = yField;
	}
	
	public List<MyPoint> getMenagedPoints() {
		return menagedPoints;
	}

	public void setMenagedPoints(List<MyPoint> menagedPoints) {
		this.menagedPoints = menagedPoints;
	}

}
