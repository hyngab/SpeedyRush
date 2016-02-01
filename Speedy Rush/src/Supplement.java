import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Supplement extends Collectible implements Runnable{

		double xCoord;
		double yCoord;
		int width = 20;
		int height = 20;
		Image[] SuppImage = {new Image("/img/bonus.jpg")};
		ImageView SuppView = new ImageView(SuppImage[0]);
		float speed;
		float respawnSpeed;
		int armorSupp = 10;
		static int numberOfSupp = 0;
		Thread SuppThread;

		Supplement(Pane pane){	
			xCoord = (250-width)*Math.random() + 70;
			yCoord = -100;
			SuppView.setImage(SuppImage[0]);
			SuppView.setX(xCoord);
			SuppView.setY(yCoord);
			pane.getChildren().add(SuppView);
			System.out.println("Supp View added");
			if (numberOfSupp >=4){
				numberOfSupp = -1;
			}
			numberOfSupp += 1;		
			SuppThread = new Thread(this);
			SuppThread.start();
		
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (this.SuppThread != null){
				this.move(1);  //moving downward
				this.checktake();	
				if(this.yCoord > 610){
					destroy();
				}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		}

		public void respawn(Pane pane){
			this.xCoord = (250-width)*Math.random() + 70;
			this.yCoord = 40;
			this.SuppView.setImage(SuppImage[0]);
			this.SuppView.setX(xCoord);
			this.SuppView.setY(40);
			pane.getChildren().add(SuppView);
			
		}
		
		void checktake(){
			if((yCoord + height > Player.yCoord) && 
					(yCoord < Player.yCoord + Player.height) &&
					(xCoord < Player.xCoord + Player.width) &&
					(xCoord + width > Player.xCoord))
			{
				//System.out.println("hit");
				Player.armor += 10;
				GameWorld.txtArmor.setText("Armor: " + Player.armor);
				this.SuppView.setVisible(false);
				this.SuppThread = null;

			}
		}
		
		public void stop(){
			this.SuppThread = null;
		}
		
		@Override
		public void move(float multiplier) {
			// TODO Auto-generated method stub
			Platform.runLater(new Runnable() {
				   @Override
				   public void run() {
					   if (SuppThread != null){
					    yCoord += 1;  //moving downward
						SuppView.setY(yCoord);
					   }
				   }
				});
		}
}
