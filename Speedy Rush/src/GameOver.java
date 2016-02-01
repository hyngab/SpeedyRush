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

public class GameOver {
		Stage stage;
		Scene gameOverScene;
		//Player player = new Player();
		Pane gameOverPane;
		Pane roadPane;
		Text txtScore;
		int highScore[] = {100,1000,2000,3000,5000};
		String highScorePlayer[] = {"Timture","Sam","Toby","Ben","Leo"};
		//int highScore[5];
		//String highScorePlayer[5];
		
		
		GameOver(Game game){
		gameOverPane = new Pane();
		//roadPane = new Pane();
		
		gameOverScene = new Scene(gameOverPane,400,600);
		Font gameOverFont = new Font("Consolas",40);
			
			Text txtGameOver = new Text("Game Over");
			txtGameOver.setFont(gameOverFont);
			txtGameOver.setLayoutX(94);
			txtGameOver.setLayoutY(100);
			
			
			gameOverFont = new Font("Consolas",26);
	        txtScore = new Text("Your Score: ");
	        txtScore.setFont(gameOverFont);
	        txtScore.setLayoutX(45);
	        txtScore.setLayoutY(215);
	         
	        Text txtRank = new Text("Rank: ");
	        txtRank.setFont(gameOverFont);
	        txtRank.setLayoutX(120);
	        txtRank.setLayoutY(255);
	        
	        Button btnTitle = new Button();
	        btnTitle.setFont(gameOverFont);
	        btnTitle.setLayoutX(100);
	        btnTitle.setLayoutY(380);
	        btnTitle.setText("Back to Menu");
	        btnTitle.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	//initGameScene();
	            	//titleStage.setScene(gameScene); 
	            	//gameWorld.initGame();
	            	game.stage.setScene(game.titleScene);
	            }
	        });
	        
			
	        //roadStartAnimation(gameOverScene);
	        //gameOverPane.getChildren().add(roadPane);
	        
			gameOverPane.getChildren().add(txtGameOver);
			gameOverPane.getChildren().add(txtScore);
			gameOverPane.getChildren().add(txtRank);
			gameOverPane.getChildren().add(btnTitle);
			
		}
		
		public void setScore(int score){
			txtScore.setText("Your Score: " + score);
		}
		
		
}
