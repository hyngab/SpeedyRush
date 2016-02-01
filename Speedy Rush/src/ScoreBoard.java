import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ScoreBoard {
	Scene scoreBoardScene;
	//Player player = new Player();
	Pane scoreBoardPane;
	//Pane roadPane;

	int highScore[] = {100,1000,2000,3000,5000};
	String highScorePlayer[] = {"Timture","Sam","Toby","Ben","Leo"};
	Text txtScoreList[] = new Text[5] ;
	Text txtNameList[] = new Text[5];
	Text txtRankList[] = new Text[5];
	
	
	ScoreBoard(Game game){
		scoreBoardPane = new Pane();
		//roadPane = new Pane();
		//gameClass = game;
		
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
        	 //txtScoreList[i].setLayoutX(250);
        	 //txtScoreList[i].setLayoutY(160 + i*55);
        	 //txtScoreList[i].setTextAlignment(TextAlignment.RIGHT);;
        	 vboxScore.getChildren().add(txtScoreList[i]);
        	 //scoreBoardPane.getChildren().add(txtScoreList[i]);
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
            	//initGameScene();
            	//titleStage.setScene(gameScene); 
            	//gameWorld.initGame();
            	game.stage.setScene(game.titleScene);
            }
        });
        
        //roadStartAnimation(gameScene);
        //scoreBoardPane.getChildren().add(roadPane);
        scoreBoardPane.getChildren().add(txtTitle);
        scoreBoardPane.getChildren().add(txtScore);
        scoreBoardPane.getChildren().add(txtRank);
        scoreBoardPane.getChildren().add(txtName);
        scoreBoardPane.getChildren().add(vboxScore);
        scoreBoardPane.getChildren().add(btnTitle);
        //scoreBoardPane.getChildren().add(txtArmor);
       
	}
	
	public void updateScoreBoard(){
		for (int i = 0;i<highScore.length;i++){
			txtScoreList[i].setText(Integer.toString(highScore[i]));
		}
		for (int i = 0;i<highScorePlayer.length;i++){
			txtNameList[i].setText(highScorePlayer[i]);
		}

	}
	
	
}
