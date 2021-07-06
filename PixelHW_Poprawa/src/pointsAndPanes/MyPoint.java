package pointsAndPanes;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class MyPoint extends Circle {
	

	public MyPoint(Paint paint, double x, double y) {
		this.setFill(paint);
		this.setStroke(Color.WHITE);
		this.setRadius(3);
		this.setLayoutX(x);
		this.setLayoutY(y);
	}

	
	

}
