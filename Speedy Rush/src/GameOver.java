import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class GameOver {
		Stage stage;
		Scene gameOverScene;
		//Player player = new Player();
		Pane gameOverPane;
		Pane roadPane;
		Text txtGameOver;
		Text txtScore;
		Text txtRank;
		Text txtName;
		TextField txtNameInput;
		Button btnTitle;
		Button btnSubmit;
		int playerRank;
		int highScore[] = {10000,1000,200,100,100};
		String highScorePlayer[] = {"Timture","Sam","Toby","Ben","Leo"};
		//int highScore[5];
		//String highScorePlayer[5];
		
		
		GameOver(Game game){
		gameOverPane = new Pane();
		//roadPane = new Pane();
		
		gameOverScene = new Scene(gameOverPane,400,600);
		Font gameOverFont = new Font("Consolas",40);
			
			txtGameOver = new Text("Game Over");
			txtGameOver.setFont(gameOverFont);
			txtGameOver.setLayoutX(94);
			txtGameOver.setLayoutY(100);
			
			gameOverFont = new Font("Consolas",26);
	        txtScore = new Text("Your Score: ");
	        txtScore.setFont(gameOverFont);
	        txtScore.setLayoutX(45);
	        txtScore.setLayoutY(215);
	         
	        txtRank = new Text("Rank: ");
	        txtRank.setFont(gameOverFont);
	        txtRank.setLayoutX(120);
	        txtRank.setLayoutY(255);
	        
	        txtName = new Text("Your Name:");
	        txtName.setFont(gameOverFont);
	        txtName.setLayoutX(35);
	        txtName.setLayoutY(355);
	        
	        gameOverFont = new Font("Consolas",21);
	        txtNameInput = new TextField();
	        txtNameInput.setFont(gameOverFont);
	        txtNameInput.setLayoutX(192);
	        txtNameInput.setLayoutY(325);
	        txtNameInput.setPrefWidth(180);
	        txtNameInput.setPrefHeight(40);
	        
	        gameOverFont = new Font("Consolas",18);
	        
	        btnTitle = new Button();
	        btnTitle.setFont(gameOverFont);
	        btnTitle.setLayoutX(100);
	        btnTitle.setLayoutY(420);
	        btnTitle.setText("Back to Menu");
	        btnTitle.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	//initGameScene();
	            	//titleStage.setScene(gameScene); 
	            	//gameWorld.initGame();
	            	game.stage.setScene(game.titleScene);
	            	//gameOverPane.getChildren().clear();
	            }
	        });
	        
	        
	        
	        btnSubmit = new Button();
	        btnSubmit.setFont(gameOverFont);
	        btnSubmit.setLayoutX(80);
	        btnSubmit.setLayoutY(420);
	        btnSubmit.setText("Submit and Back to Menu");
	        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	//initGameScene();
	            	//titleStage.setScene(gameScene); 
	            	
	            	game.scoreBoard.insertScoreBoard(txtNameInput.getText().toString().trim(),playerRank);
	            	//addScore()
	            	
	            	
	            	
	            	game.stage.setScene(game.titleScene);
	            	//gameOverPane.getChildren().clear();
	            }
	        });
	        
			
	        //roadStartAnimation(gameOverScene);
	        //gameOverPane.getChildren().add(roadPane);
	        
			
			//gameOverPane.getChildren().add(btnTitle);
	        gameOverPane.getChildren().add(txtGameOver);
			gameOverPane.getChildren().add(txtScore);
			gameOverPane.getChildren().add(txtRank);
			gameOverPane.getChildren().add(txtName);
			gameOverPane.getChildren().add(txtNameInput);
			gameOverPane.getChildren().add(btnSubmit);
			gameOverPane.getChildren().add(btnTitle);
			
		}
		
		public void setScene(int score, int rank){
			txtName.setVisible(false);
			txtNameInput.setVisible(false);
			btnSubmit.setVisible(false);
			btnTitle.setVisible(false);
			txtScore.setText("Your Score: " + score);
			Platform.runLater(() -> {
				Player.create(Game.titleScreenPane);
		    });
			
			if(rank>0){
				txtRank.setText("Rank: " + rank);
				txtName.setVisible(true);
				txtNameInput.setVisible(true);
				btnSubmit.setVisible(true);
				playerRank = rank;
			}else
			{
				txtRank.setText("Rank: Out of Rank");
				btnTitle.setVisible(true);
			}
		}
		
		public void setScore(int score){
			txtScore.setText("Your Score: " + score);
		}
		
		
}
