import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

public class Player extends Entity{
	static double xCoord = 180;
	static double yCoord = 470;
	static int width = 59;
	static int height = 60;
	static int carIndex = 0;
	
	/*
	static Image[] playerImage = {new Image(Paths.get("/img/playerCar01.png").toUri().toString()), 
			new Image(Paths.get("/img/playerCar02.png").toUri().toString()),
			new Image(Paths.get("/img/playerCar03.png").toUri().toString())};
	*/
	
	static Image[] playerImage = {new Image("/img/playerCar01.png"), new Image("/img/playerCar02.png"),new Image("/img/playerCar03.png")};
	static ImageView playerView = new ImageView(playerImage[carIndex]);
	static int armor = 20;  //TODO 200 default
	
	
	public static void move(MouseEvent e) {
			if ((e.getY() + height > 600)&&(e.getX() < 75)){
				playerView.setX(75);
				playerView.setY(600-height);
				xCoord = 75;
				yCoord = 600-height;
			}
			
			else if ((e.getY() + 80 > 600)&&(e.getX() + width > 325)){
				playerView.setX(325-width);
				playerView.setY(600-height);
				xCoord = 325-width;
				yCoord = 600-height;
			}
			
			else if (e.getX() < 75){
				playerView.setX(75);
				playerView.setY(e.getY());
				xCoord = 75;
				yCoord = e.getY();
				
			}
			else if (e.getX() + width > 325){
				playerView.setX(325-width);
				playerView.setY(e.getY());
				xCoord = 325-width;
				yCoord = e.getY();
				
			}	
			else if (e.getY() < 0){
				playerView.setX(e.getX());
				playerView.setY(0);
				xCoord = e.getX();
				yCoord = 0;
			}
			else if (e.getY() + height > 600){
				playerView.setX(e.getX());
				playerView.setY(600-height);
				xCoord = e.getX();
				yCoord = 600-height;
			}
			
			else {
				playerView.setX(e.getX());
				playerView.setY(e.getY());
				xCoord = e.getX();
				yCoord = e.getY();
			}
			
			
		}

	

	@Override
	public void destroy() {
		
	}
	
	public static void create(Pane pane){
		armor = 20;
		playerView.setImage(playerImage[carIndex]);
		playerView.setX(170);
		playerView.setY(470);
		System.out.println(pane);
		System.out.println(playerView);
		pane.getChildren().add(playerView);
	}

	public void changeCar(){
		if(carIndex > playerImage.length - 1){
			carIndex = 0;
		}
		else if (carIndex < 0){
			carIndex = playerImage.length - 1;
		}
		playerView.setImage(playerImage[carIndex]);
	}


	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	

}
