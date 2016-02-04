import java.nio.file.Paths;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class GameWorld implements Runnable{
	Scene gameScene;
	//Player player = new Player();
	Pane gameWorldPane;
	Pane roadPane;
	Pane animationPane;
	
	static int currentLevel = 1;
	static float levelMultiplier = 1;
	static int playerScore = 0;
	long timeInterval;
	boolean isGameOver = false;
	boolean isGameStarted = false;
	Enemy[] enemyList = new Enemy[15];
	Supplement [] SuppList = new Supplement [5];
	Invincible[] Inv = new Invincible [2];
	
	//int nextLevelScore[] = {1000,2000,3000,4500,6000,10000};
	int nextLevelScore[] = {200,400,600,800};
	//MediaPlayer mediaPlayer = new MediaPlayer();
	//java.net.URL bgMusicLink = getClass().getResource("Space_Boy.mp3");
	Media bgMusicFile = new Media(getClass().getResource("/audio/SpaceBoy.mp3").toString());
	MediaPlayer bgMusic = new MediaPlayer (bgMusicFile);
	
	AudioClip crashSound = new AudioClip(Paths.get("res/audio/carCrash.mp3").toUri().toString());
	AudioClip gameOverSound = new AudioClip(Paths.get("res/audio/gameOver.mp3").toUri().toString());

	Timeline tl;
	KeyValue[] linesY;
	KeyValue[] linesNewY;
	
	Thread checkGameThread;
	Thread mainGame = new Thread();
	Thread suppThread = new Thread();
	Thread invThread = new Thread();
	
	static Text txtLevel;
	static Text txtScore;
	static Text txtArmor;
	
	Game gameClass;
	
	
	GameWorld(Game game){
		gameWorldPane = new Pane();
		roadPane = new Pane();
		gameClass = game;
		animationPane = new Pane();
		
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
         
        txtArmor = new Text("Armor:" + Player.armor);
        txtArmor.setFont(gameFont);
        txtArmor.setLayoutX(220);
        txtArmor.setLayoutY(60);
		
        gameClass.roadStartAnimation(animationPane);
        gameWorldPane.getChildren().add(animationPane);
        gameWorldPane.getChildren().add(roadPane);
		gameWorldPane.getChildren().add(levelBox);
		gameWorldPane.getChildren().add(playerInfoBox);
		gameWorldPane.getChildren().add(txtLevel);
		gameWorldPane.getChildren().add(txtScore);
		gameWorldPane.getChildren().add(txtArmor);		
	}

	
	public void roadStartAnimation(Scene scene){
		 Pane root = (Pane) scene.getRoot();
		 
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
		
		tl = new Timeline();
		linesY = new KeyValue[8];
		linesNewY = new KeyValue[8];
		
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
	    	 	invThread.join();
	    	 	suppThread.join();
				mainGame.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     //Thread mainGame = new Thread(this);
	    
	     roadPane.getChildren().clear();
	     bgMusic.setCycleCount(AudioClip.INDEFINITE);

	     bgMusic.play();
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
	     
	     invThread = new Thread( new Runnable(){
	    	 public void run(){
	    		 while (isGameStarted == true && isGameOver == false){
	    			 InvSpawn();
	    			 try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 }
	    		 System.out.println("invThread end");
	    	 }
	     });
	     

			suppThread = new Thread( new Runnable(){
		    	 public void run(){
		    		 while (isGameStarted == true && isGameOver == false){
		    			 try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			 SuppSpawn();
		    		 }
		    		 System.out.println("suppThread end");
		    	 }
		     });
			
	     
	     
	     Player.create(roadPane);
	     levelMultiplier = 1;
	     	currentLevel = 1;
	     	playerScore = 0;
	     	txtLevel.setText("Level " + currentLevel);        
	        txtScore.setText("Score: " + playerScore);
	        txtArmor.setText("Armor:" + Player.armor);
			
	        //roadStartAnimation(gameScene);
	        
	     gameScene.setOnMouseMoved(
					new EventHandler<MouseEvent>(){
						public void handle(MouseEvent e){
							Player.move(e);	
						}
					});
	     isGameStarted = true;
	     timeInterval = 8000;
	     
	     mainGame.start();
	     checkGameThread.start();
	     invThread.start();
	     suppThread.start();
	     
	     
	}
	public void gameOver(){
		//mainGame = null;
		//checkGameThread = null;
		
		roadPane.getChildren().clear();
			
		
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
		
		bgMusic.stop();
		gameOverSound.play();
		int rank = getRank();
		
        //gameClass.gameOver.setScore(playerScore);
        //System.out.println(gameClass.scoreBoard.highScore[4]);
		isGameStarted = false;
		//TODO roadPane.getChildren().clear();
		//gameClass.titleScreenPane.getChildren().add(Player.playerView);
		//player.create(gameClass.titleScreenPane);
		
		if(rank>0){
			gameClass.gameOver.setScene(playerScore,rank);
		}else
		{
			gameClass.gameOver.setScene(playerScore,rank);
		}
		
	}
	
	public int getRank(){
	 for (int i = 0; i<gameClass.scoreBoard.highScore.length;i++){
		if(playerScore > gameClass.scoreBoard.highScore[i]){
			return i+1;
		} 
		
	 }
		return 0;
	}
	
	public void checkGame(){
		txtScore.setText("Score: " + playerScore);
		txtArmor.setText("Armor: " + Player.armor);
		if(Player.armor <= 0){
			
			Platform.runLater(new Runnable() {
                @Override public void run() {
                	gameOver();
                		
                	gameClass.stage.setScene(gameClass.gameOver.gameOverScene);
                }
            });
			
		}
		if (currentLevel <= nextLevelScore.length){
		if(playerScore >= nextLevelScore[currentLevel-1]){
			currentLevel += 1;
			txtLevel.setText("Level " + currentLevel);
			levelMultiplier -= 0.19;
			timeInterval = (long) (timeInterval*levelMultiplier);
			Platform.runLater(new Runnable() {
                @Override public void run() {
                	System.out.println("speed up");
                	gameClass.roadAnimationchange(animationPane, currentLevel);
                }
            });
			
	
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
			//SuppSpawn();
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
		//System.out.println("addScore()");
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
	
	public void InvSpawn(){
		Platform.runLater(new Runnable() {
			   @Override
			   public void run() {
				   if (Inv[Invincible.numberOfinv] != null){
					   roadPane.getChildren().remove(Inv[Invincible.numberOfinv].InvView);
					   Inv[Invincible.numberOfinv].InvThread = null;
					   Inv[Invincible.numberOfinv] = null;
				   }
				   Inv[Invincible.numberOfinv] = new Invincible(roadPane);  
				   //System.out.println(SuppList[Supplement.numberOfSupp]);
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
