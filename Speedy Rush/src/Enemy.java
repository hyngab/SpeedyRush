import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

public class Enemy extends Entity implements Runnable{
	double xCoord;
	double yCoord;
	int width = 50;
	int height = 100;
	Image[] EnemyImage = {new Image("/img/EnemyCar01.png")};
	ImageView EnemyView = new ImageView(EnemyImage[0]);
	float speed;
	float respawnSpeed;
	int score;
	boolean passed;
	static int numberOfEnemy = 0;
	Thread EnemyThread;
	static AudioClip crashSound = new AudioClip(Paths.get("res/audio/carCrash.mp3").toUri().toString());

	Enemy(Pane pane){	
		xCoord = (250-width)*Math.random() + 70;
		yCoord = -100;
		EnemyView.setImage(EnemyImage[0]);
		EnemyView.setX(xCoord);
		EnemyView.setY(yCoord);
		pane.getChildren().add(EnemyView);
		System.out.println("View added");
		if (numberOfEnemy >=9){
			numberOfEnemy = -1;
			
		}
		numberOfEnemy += 1;		
		EnemyThread = new Thread(this);
		EnemyThread.start();

		
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {
			   @Override
			   public void run() {
				   if (EnemyThread != null){
				    yCoord += 1;  //moving downward
					EnemyView.setY(yCoord);
				   }
			   }
			});
	}

	@Override
	public void destroy() {
		if (this.EnemyThread != null){
			this.EnemyThread = null;
		}
	}
	
	
	public void respawn(Pane pane){
		//numberOfEnemy += 1;
		this.xCoord = (250-width)*Math.random() + 70;
		this.yCoord = 40;
		this.EnemyView.setImage(EnemyImage[0]);
		this.EnemyView.setX(xCoord);
		this.EnemyView.setY(40);
		pane.getChildren().add(EnemyView);
		
	}

	@Override
	public void run() {
		while (this.EnemyThread != null){ 
				this.move();  //moving downward
				this.checkCollision();
				this.checkPass();	
				if(this.yCoord > 610){
					destroy();
				}
			try {
				Thread.sleep((long)(21-(4*GameWorld.currentLevel)));
				//Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void stop(){
		this.EnemyThread = null;
	}

	void checkPass(){
		//System.out.println("passed"); //not run if without this?
			if (passed == false){
				//System.out.println("why");
				if (yCoord > Player.yCoord) {
					passed = true;
					GameWorld.addScore();
					//Thread.interrupted();
				}
			}
			
			
	}
	void checkCollision(){
		//System.out.println("collision"); //not run if without this?
		if (Invincible.InvState == false && Player.armor > 0){
		if((yCoord + height > Player.yCoord) && 
				(yCoord < Player.yCoord + Player.height) &&
				(xCoord < Player.xCoord + Player.width) &&
				(xCoord + width > Player.xCoord))
		{
			//System.out.println("hit");
			//crashSound.play();
			Player.armor -= 10;
			//GameWorld.txtArmor.setText("Armor: " + Player.armor);
			this.EnemyView.setVisible(false);
			this.EnemyThread = null;
			if (Player.armor > 0){
				crashSound.play();
			}
			/*
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			*/
		}
	}
	}
}