import javax.imageio.ImageIO;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameWorld implements Runnable{
	Scene gameScene;
	Player player = new Player();
	Pane gameWorldPane;
	Pane roadPane;
	static int currentLevel = 1;
	static float levelMultiplier = 1;
	static int playerScore = 0;
	long timeInterval = 8000;
	boolean isGameOver = false;
	boolean isGameStarted = false;
	//Enemy enemy = new Enemy();
	Enemy[] enemyList = new Enemy[10];
	Supplement [] SuppList = new Supplement [5];
	//int nextLevelScore[] = {1000,2000,3000,4500,6000,10000};
	int nextLevelScore[] = {200,400,600,800};
	
	//Thread enemythread;
	Thread checkGameThread;
	Thread mainGame = new Thread(this);
	
	static Text txtLevel;
	static Text txtScore;
	static Text txtArmor;
	
	Game gameClass;
	
	
	GameWorld(Game game){
		gameWorldPane = new Pane();
		roadPane = new Pane();
		gameClass = game;
		
		gameScene = new Scene(gameWorldPane,400,600);
		Font gameFont = new Font("Consolas",21);
		
		Rectangle levelBox = new Rectangle(125,40);
		levelBox.setFill(Color.web("#ffffff",0.9));
		levelBox.setLayoutX(0);
		levelBox.setLayoutY(0);

		Rectangle playerInfoBox = new Rectangle(190,70);
		playerInfoBox.setFill(Color.web("#ffffff",0.9));
		playerInfoBox.setLayoutX(210);
		playerInfoBox.setLayoutY(0);
		
		txtLevel = new Text("Level " + currentLevel);
		txtLevel.setFont(gameFont);
		txtLevel.setLayoutX(10);
		txtLevel.setLayoutY(25);
        
        txtScore = new Text("Score: " + playerScore);
        txtScore.setFont(gameFont);
        txtScore.setLayoutX(220);
        txtScore.setLayoutY(30);
         
        txtArmor = new Text("Armor:" + player.armor);
        txtArmor.setFont(gameFont);
        txtArmor.setLayoutX(220);
        txtArmor.setLayoutY(60);
		
        roadStartAnimation(gameScene);
        gameWorldPane.getChildren().add(roadPane);
		gameWorldPane.getChildren().add(levelBox);
		gameWorldPane.getChildren().add(playerInfoBox);
		gameWorldPane.getChildren().add(txtLevel);
		gameWorldPane.getChildren().add(txtScore);
		gameWorldPane.getChildren().add(txtArmor);
		
		
		
	}

	public void roadStartAnimation(Scene scene){
		 final Pane root = (Pane) scene.getRoot();
		 
		 Rectangle backgroundRoad = new Rectangle(250,600);
	     backgroundRoad.setStroke(Color.BLACK);
	     backgroundRoad.setFill(Color.web("#f2f2f2"));
	     backgroundRoad.setLayoutX(75);
         backgroundRoad.setLayoutY(0);
		 
         root.getChildren().add(backgroundRoad);
         
		 Rectangle[] yellowLines = new Rectangle[8];
		 

		for (int i = 0; i<8; i++){
			yellowLines[i] = new Rectangle(10,75);
			yellowLines[i].setStroke(Color.BLACK);
	        yellowLines[i].setArcHeight(5);
	        yellowLines[i].setArcWidth(5);
	        yellowLines[i].setFill(Color.web("#ffff00"));
	        yellowLines[i].setLayoutX(195);
	        yellowLines[i].setLayoutY(-90 + i*100);
	        root.getChildren().add(yellowLines[i]);
		}
		
		Timeline tl = new Timeline();
		KeyValue[] linesY = new KeyValue[8];
		KeyValue[] linesNewY = new KeyValue[8];
		
		for (int i = 0; i<8; i++){
			linesY[i] = new KeyValue(yellowLines[i].layoutYProperty(), yellowLines[i].getLayoutY());
		}
		for (int i = 0; i<8; i++){
			linesNewY[i] = new KeyValue(yellowLines[i].layoutYProperty(), yellowLines[i].getLayoutY()+100);
		}
		
		
		KeyFrame kf1 = new KeyFrame(Duration.millis(0),linesY);
		KeyFrame kf2 = new KeyFrame(Duration.millis(2000),linesNewY);
		
		tl.getKeyFrames().addAll(kf1,kf2);
		tl.setCycleCount(Animation.INDEFINITE);

		tl.play();	
	}
	
	public void initGame(){	
	     try {
				mainGame.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     //Thread mainGame = new Thread(this);
	     mainGame = new Thread(this);
	     checkGameThread = new Thread( new Runnable(){
	    	 public void run(){
	    		 while (isGameStarted == true && isGameOver == false){
	    			 checkGame();
	    			 try {
	    	                Thread.sleep(1);
	    	            } catch (InterruptedException e) {
	    	                Thread.currentThread().interrupt();
	    	            }
	    		 }
	    		 System.out.println("game loop end");
	    	 }
	     });
	     
	     player.create(roadPane);
	     	currentLevel = 1;
	     	playerScore = 0;
	     	txtLevel.setText("Level " + currentLevel);        
	        txtScore.setText("Score: " + playerScore);
	        txtArmor.setText("Armor:" + player.armor);
			
	        //roadStartAnimation(gameScene);
	        
	     gameScene.setOnMouseMoved(
					new EventHandler<MouseEvent>(){
						public void handle(MouseEvent e){
							player.move(e);	
						}
					});
	     isGameStarted = true;
	     
	     mainGame.start();
	     checkGameThread.start();
	     
	}
	public void gameOver(){
		//mainGame = null;
		//checkGameThread = null;
		
		for(int i =0; i<enemyList.length;i++){
				//roadPane.getChildren().remove(enemyList[Enemy.numberOfEnemy].EnemyView);
				if(enemyList[i] != null){
				   enemyList[i].destroy();
				   enemyList[i]=null;
				}
		}
		
		for(int i =0; i<SuppList.length;i++){
			//roadPane.getChildren().remove(enemyList[Enemy.numberOfEnemy].EnemyView);
			if(SuppList[i] != null){
				SuppList[i].destroy();
				SuppList[i]=null;
			}
	}
		
		
		//enemyList = null;
		isGameStarted = false;
		roadPane.getChildren().clear();
		
	}
	
	public static void levelUp(){
		
	}
	
	public void checkGame(){
		txtScore.setText("Score: " + playerScore);
		txtArmor.setText("Armor: " + Player.armor);
		if(Player.armor <= 0){
			
			Platform.runLater(new Runnable() {
                @Override public void run() {
                	gameOver();
    
                	gameClass.gameOver.setScore(playerScore);
                	gameClass.stage.setScene(gameClass.gameOver.gameOverScene);
                }
            });
			
		}
		if(playerScore >= nextLevelScore[currentLevel-1]){
			if (currentLevel < nextLevelScore.length){
			currentLevel += 1;
			txtLevel.setText("Level " + currentLevel);
			levelMultiplier -= 0.08;
			timeInterval = (long) (timeInterval*levelMultiplier);
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//new Thread(enemy).start();
		while (isGameStarted == true && isGameOver == false){
			//checkGame();
			EnemySpawn();
			SuppSpawn();
			//new Thread(enemyList[Enemy.numberOfEnemy]).start();
			System.out.println(Enemy.numberOfEnemy);
			try {
				//Thread.sleep(timeInterval);
				Thread.sleep(timeInterval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
		System.out.println(Thread.currentThread());
		
	}
	
	public void EnemySpawn(){
		Platform.runLater(new Runnable() {
			   @Override
			   public void run() {
				   if (enemyList[Enemy.numberOfEnemy] != null){
					   roadPane.getChildren().remove(enemyList[Enemy.numberOfEnemy].EnemyView);
					   enemyList[Enemy.numberOfEnemy].EnemyThread = null;
					   //enemyList[Enemy.numberOfEnemy].EnemyView = null;
					   enemyList[Enemy.numberOfEnemy] = null;
				   }
				   enemyList[Enemy.numberOfEnemy] = new Enemy(roadPane);  
				   System.out.println(enemyList[Enemy.numberOfEnemy]);
			   }
			});
	}
	
	public static void addScore(){
		System.out.println("addScore()");
		playerScore += 100;
		//txtScore.setText("Score: " + playerScore);
	}
	
	public void SuppSpawn(){
		Platform.runLater(new Runnable() {
			   @Override
			   public void run() {
				   if (SuppList[Supplement.numberOfSupp] != null){
					   roadPane.getChildren().remove(SuppList[Supplement.numberOfSupp].SuppView);
					   SuppList[Supplement.numberOfSupp].SuppThread = null;
					   SuppList[Supplement.numberOfSupp] = null;
				   }
				   SuppList[Supplement.numberOfSupp] = new Supplement(roadPane);  
				   System.out.println(SuppList[Supplement.numberOfSupp]);
			   }
			});
	}
	
	//
	class checkCollision extends Thread{
		String entityType;
		checkCollision(Entity entity){
			if(entity instanceof Enemy){
				entityType  = "Enemy";
			}
			
		}		
	}
	
	//
}
