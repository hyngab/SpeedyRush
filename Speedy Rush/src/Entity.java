import javafx.scene.image.Image;

public abstract class Entity {
	double xCoord;
	double yCoord;
	int width;
	int length;
	Image image;
	
	public abstract void move();
	
	public abstract void destroy();
	
}
