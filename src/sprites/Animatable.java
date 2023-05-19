package sprites;

import javafx.scene.canvas.GraphicsContext;

public interface Animatable {
	public void animate(int x, int y, GraphicsContext gc, long time);
}
