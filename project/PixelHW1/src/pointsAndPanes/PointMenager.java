package pointsAndPanes;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import window.View;
// this component decides of points behavior
public class PointMenager {

	private View view;

	private List<Pane> photoContainers;

	public PointMenager() {

		this.photoContainers = new ArrayList<>();

	}

	public void addObserver(Pane p) {
		this.photoContainers.add(p);
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public void addToAllPanes(MyPoint p) {
		//set points behavior and relation to its navPane
		PointsNavPane np = new PointsNavPane();
		np.setView(view);

		for (int i = 0; i < 4; i++) {

			MyPoint point = new MyPoint(p.getFill(), p.getLayoutX(), p.getLayoutY());
			np.addMenagedPoint(point);
			
				point.setOnMousePressed(x -> {
				np.getMenagedPoints().stream().forEach(q ->{
					
					q.setFill(Color.TRANSPARENT);
					q.setStroke(Color.TRANSPARENT);
					q.setCursor(Cursor.CLOSED_HAND);
					
				});
				
				np.getName().setStyle("-fx-background-color:silver;");

			});

			point.setOnMouseEntered(cx -> {

				point.setCursor(Cursor.CLOSED_HAND);
				np.getName().setStyle("-fx-background-color:silver;");

			});

			point.setOnMouseReleased(c -> {
				
				this.removeFromAllPanes(point);
				((VBox) this.view.getNavBar().getContent()).getChildren().remove(np);

			});

			point.setOnMouseExited(o ->{
				np.getName().setStyle("-fx-background-color:transparent;");

			});
			
			photoContainers.get(i).getChildren().add(point);
		}

		((VBox) this.view.getNavBar().getContent()).getChildren().add(np);

	}

	public void removeFromAllPanes(MyPoint p) {

		for (int i = 0; i < 4; i++) {

			Object[] o = photoContainers.get(i).getChildren().stream().filter(e -> e instanceof MyPoint)
					.filter(e1 -> e1.getLayoutX() == p.getLayoutX()).filter(e2 -> e2.getLayoutY() == p.getLayoutY())
					.toArray();

			photoContainers.get(i).getChildren().remove((MyPoint) o[0]);

		}

	}

}
