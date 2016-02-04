import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {
	Stage stage;
	Player player = new Player();
	Scene titleScene;
	GameOver gameOver;
	ScoreBoard scoreBoard;
	static Pane titleScreenPane;
	
	static Timeline tl;
	static KeyValue[] linesY;
	static KeyValue[] linesNewY;
	static KeyFrame kf1;
	static KeyFrame kf2;
	Pane root;
	
	
	
	
	public void start(Stage titleStage){
		GameWorld gameWorld = new GameWorld(this);
		gameOver = new GameOver(this);
		scoreBoard = new ScoreBoard(this);
		
		
		stage = titleStage;	
		stage.setTitle("Speedy Rush");
		stage.setResizable(false);
		titleScreenPane = new Pane();
		titleScene = new Scene(titleScreenPane,400,600);	
		Font gameFont = new Font("Consolas",18);
		
		

		
		Button btnStart = new Button();
		btnStart.setFont(gameFont);
		btnStart.setText("Start Game");
		
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	gameWorld.initGame();
	            	titleScreenPane.getChildren().remove(Player.playerView);
	            	stage.setScene(gameWorld.gameScene);
	            }
	        });
		
		
		Button btnScoreBoard = new Button();
		btnScoreBoard.setFont(gameFont);
		btnScoreBoard.setText("Highscore");
		btnScoreBoard.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	scoreBoard.updateScoreBoard();
	            	stage.setScene(scoreBoard.scoreBoardScene);
	      	
	            }
	        });
		
		btnStart.setMaxWidth(Double.MAX_VALUE);
		btnScoreBoard.setMaxWidth(Double.MAX_VALUE);
		
		VBox menuButtons = new VBox(2);
		menuButtons.setPrefHeight(150);
		menuButtons.setPrefWidth(200);
		menuButtons.setSpacing(30);
		menuButtons.setLayoutX(100);
		menuButtons.setLayoutY(320);
        menuButtons.getChildren().add(btnStart);
        menuButtons.getChildren().add(btnScoreBoard);
         
        Text gameTitle = new Text("Speedy Rush");
        gameTitle.setFont(new Font("Consolas",46));
        gameTitle.setLayoutX(65);
        gameTitle.setLayoutY(80);
        
        Text gameCredit = new Text("Created by ISOM3320 Group 2 (2015-16 Winter)");
        gameCredit.setFont(new Font("Consolas",13));
        gameCredit.setLayoutX(45);
        gameCredit.setLayoutY(580);
        
        Rectangle BackgroundTitle = new Rectangle(400,80);
        BackgroundTitle.setFill(Color.web("#ffffff",0.9));
        BackgroundTitle.setLayoutX(0);
        BackgroundTitle.setLayoutY(27);
        
        Rectangle BackgroundCredit = new Rectangle(400,30);
        BackgroundCredit.setFill(Color.web("#ffffff",0.9));
        BackgroundCredit.setLayoutX(0);
        BackgroundCredit.setLayoutY(560);
        
        Rectangle backgroundRoad = new Rectangle(250,600);
        backgroundRoad.setStroke(Color.BLACK);
        backgroundRoad.setFill(Color.web("#f2f2f2"));
        backgroundRoad.setLayoutX(75);
        backgroundRoad.setLayoutY(0);
        
        //gameWorld.roadStartAnimation(titleScene);
        roadStartAnimation(titleScreenPane); 
        
        
        Player.create(titleScreenPane);
        
        ImageView arrowLeft = new ImageView(new Image("/icon/arrowLeft.png"));
        ImageView arrowRight = new ImageView(new Image("/icon/arrowRight.png"));
		
        arrowLeft.setX(100);
        arrowLeft.setY(490);
        arrowRight.setX(250);
        arrowRight.setY(490);
        
        
        arrowLeft.setOnMouseClicked(
				new EventHandler<MouseEvent>(){
					public void handle(MouseEvent e){
						Player.carIndex += 1;
						player.changeCar();
					}
				});
        
        arrowRight.setOnMouseClicked(
				new EventHandler<MouseEvent>(){
					public void handle(MouseEvent e){
						Player.carIndex -= 1;
						player.changeCar();
					}
				});
        
        
        titleScreenPane.getChildren().add(arrowLeft);
        titleScreenPane.getChildren().add(arrowRight);
        
        titleScreenPane.getChildren().add(BackgroundTitle);
        titleScreenPane.getChildren().add(BackgroundCredit);
        
        
        titleScreenPane.getChildren().add(gameTitle);
        titleScreenPane.getChildren().add(menuButtons);
        titleScreenPane.getChildren().add(gameCredit);
        


        stage.setScene(titleScene);
        
        stage.show();
		
	}
	public static void main (String[] args){
		launch(args);
	}
	
	public void roadStartAnimation(Pane pane){
		 //root = (Pane) scene.getRoot();
		 
		 Rectangle backgroundRoad = new Rectangle(250,600);
	     backgroundRoad.setStroke(Color.BLACK);
	     backgroundRoad.setFill(Color.web("#f2f2f2"));
	     backgroundRoad.setLayoutX(75);
         backgroundRoad.setLayoutY(0);
		 
         pane.getChildren().add(backgroundRoad);
        
		 Rectangle[] yellowLines = new Rectangle[8];
		 

		for (int i = 0; i<8; i++){
			yellowLines[i] = new Rectangle(10,75);
			yellowLines[i].setStroke(Color.BLACK);
	        yellowLines[i].setArcHeight(5);
	        yellowLines[i].setArcWidth(5);
	        yellowLines[i].setFill(Color.web("#ffff00"));
	        yellowLines[i].setLayoutX(195);
	        yellowLines[i].setLayoutY(-90 + i*100);
	        pane.getChildren().add(yellowLines[i]);
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
		
		
		kf1 = new KeyFrame(Duration.millis(0),linesY);
		kf2 = new KeyFrame(Duration.millis(2000),linesNewY);
		
		tl.getKeyFrames().addAll(kf1,kf2);
		tl.setCycleCount(Animation.INDEFINITE);

		tl.play();	
		System.out.println(tl);
	}
	
	public void refreshPlayer(){
		 player.create(titleScreenPane);
	}
	
	public void roadAnimationchange(Pane pane, int currentLevel){

		 pane.getChildren().clear();
		 Rectangle backgroundRoad = new Rectangle(250,600);
	     backgroundRoad.setStroke(Color.BLACK);
	     backgroundRoad.setFill(Color.web("#f2f2f2"));
	     backgroundRoad.setLayoutX(75);
         backgroundRoad.setLayoutY(0);
		 
         pane.getChildren().add(backgroundRoad);
       
		 Rectangle[] yellowLines = new Rectangle[8];
		 

		for (int i = 0; i<8; i++){
			yellowLines[i] = new Rectangle(10,75);
			yellowLines[i].setStroke(Color.BLACK);
	        yellowLines[i].setArcHeight(5);
	        yellowLines[i].setArcWidth(5);
	        yellowLines[i].setFill(Color.web("#ffff00"));
	        yellowLines[i].setLayoutX(195);
	        yellowLines[i].setLayoutY(-90 + i*100);
	        pane.getChildren().add(yellowLines[i]);
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
		
		
		kf1 = new KeyFrame(Duration.millis(0),linesY);
		kf2 = new KeyFrame(Duration.millis(2000-currentLevel*350),linesNewY);
		
		tl.getKeyFrames().addAll(kf1,kf2);
		tl.setCycleCount(Animation.INDEFINITE);

		tl.play();	
		System.out.println(tl);
	
		 /*
		 root = (Pane) scene.getRoot();
		 
		 System.out.println(tl);
		 //root.getChildren().clear();
		 /*
		 Rectangle backgroundRoad = new Rectangle(250,600);
	     backgroundRoad.setStroke(Color.BLACK);
	     backgroundRoad.setFill(Color.web("#f2f2f2"));
	     backgroundRoad.setLayoutX(75);
         backgroundRoad.setLayoutY(0);
		 
        //root.getChildren().add(backgroundRoad);
       
		 Rectangle[] yellowLines = new Rectangle[8];
		 

		for (int i = 0; i<8; i++){
	        root.getChildren().add(yellowLines[i]);
		}
		
		/*
		for (int i = 0; i<8; i++){
			linesY[i] = new KeyValue(yellowLines[i].layoutYProperty(), yellowLines[i].getLayoutY());
		}
		for (int i = 0; i<8; i++){
			linesNewY[i] = new KeyValue(yellowLines[i].layoutYProperty(), yellowLines[i].getLayoutY()+100);
		}
		*/
		/*
		System.out.println(currentLevel);
		tl.stop();
		tl = new Timeline();

		kf1 = new KeyFrame(Duration.millis(0),linesY);
		kf2 = new KeyFrame(Duration.millis(2000-350*currentLevel),linesNewY);
		
		
		tl.getKeyFrames().addAll(kf1,kf2);
		tl.setCycleCount(Animation.INDEFINITE);
		
		//tl.stop();
		//tl.play();
		
		*/
		
		
		
		
	}
	
}
