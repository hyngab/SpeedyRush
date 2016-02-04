import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreBoard {
	Scene scoreBoardScene;
	Pane scoreBoardPane;

	//int highScore[] = {10000,5000,300,100,100};
	//String highScorePlayer[] = {"Timture","Sam","Toby","Ben","Leo"};
	
	int highScore[] = new int[5];
	String highScorePlayer[] = new String[5];
		
	Text txtScoreList[] = new Text[5];
	Text txtNameList[] = new Text[5];
	Text txtRankList[] = new Text[5];
	
	
	ScoreBoard(Game game){
		scoreBoardPane = new Pane();
		scoreBoardScene = new Scene(scoreBoardPane,400,600);
		
		Font gameFont = new Font("Consolas",40);

		Text txtTitle = new Text("Score Board");
		txtTitle.setFont(gameFont);
		txtTitle.setLayoutX(84);
		txtTitle.setLayoutY(70);
		
		gameFont = new Font("Consolas",26);
		
        Text txtScore = new Text("Score");
        txtScore.setFont(gameFont);
        txtScore.setLayoutX(300);
        txtScore.setLayoutY(120);
         
        Text txtRank = new Text("Rank");
        txtRank.setFont(gameFont);
        txtRank.setLayoutX(38);
        txtRank.setLayoutY(120);
		
        Text txtName = new Text("Name");
        txtName.setFont(gameFont);
        txtName.setLayoutX(135);
        txtName.setLayoutY(120);
        
        VBox vboxScore = new VBox();
        vboxScore.setAlignment(Pos.CENTER_RIGHT);
        vboxScore.setLayoutX(300);
        vboxScore.setLayoutY(135);
        vboxScore.setSpacing(25);
        
        for (int i=0; i < 5; i++){
        	txtRankList[i] = new Text(Integer.toString(i+1));
        	txtRankList[i].setFont(gameFont);
        	txtRankList[i].setLayoutX(60);
        	txtRankList[i].setLayoutY(160 + i*55);
       	 	scoreBoardPane.getChildren().add(txtRankList[i]);
        }
        
        for (int i=0; i < txtScoreList.length; i++){
        	 txtScoreList[i] = new Text();
        	 txtScoreList[i].setFont(gameFont);
        	 vboxScore.getChildren().add(txtScoreList[i]);
        }
        
        for (int i=0; i < txtNameList.length; i++){
        	txtNameList[i] = new Text();
        	txtNameList[i].setFont(gameFont);
        	txtNameList[i].setLayoutX(135);
        	txtNameList[i].setLayoutY(160 + i*55);
        	scoreBoardPane.getChildren().add(txtNameList[i]);
        }
           
        Button btnTitle = new Button();
        btnTitle.setFont(gameFont);
        btnTitle.setLayoutX(100);
        btnTitle.setLayoutY(480);
        btnTitle.setText("Back to Menu");
        btnTitle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	game.stage.setScene(game.titleScene);
            }
        });
        
        scoreBoardPane.getChildren().add(txtTitle);
        scoreBoardPane.getChildren().add(txtScore);
        scoreBoardPane.getChildren().add(txtRank);
        scoreBoardPane.getChildren().add(txtName);
        scoreBoardPane.getChildren().add(vboxScore);
        scoreBoardPane.getChildren().add(btnTitle);       
	}
	
	public void updateScoreBoard(){
		for (int i = 0;i<highScore.length;i++){
			txtScoreList[i].setText(Integer.toString(highScore[i]));
		}
		for (int i = 0;i<highScorePlayer.length;i++){
			txtNameList[i].setText(highScorePlayer[i]);
		}

	}
	
	public void insertScoreBoard(String playerName, int playerRank){
		
		System.out.println(playerName);
		if(playerName.length() == 0){
			playerName = "NoName";
		}
		
		System.out.println(playerName);
		
		for(int i = highScore.length -1; i >= playerRank -1 ;i--){
			System.out.println(i);
			System.out.println(playerRank);
			if (i > 0){
				if (highScore[i-1] != 0){
					highScore[i] = highScore[i-1];
					highScorePlayer[i] = highScorePlayer[i-1];
				}
			}
			if (i == playerRank - 1){
				highScore[i] = GameWorld.playerScore;
				highScorePlayer[i] = playerName;
			}
	
		}

	}
	
}
