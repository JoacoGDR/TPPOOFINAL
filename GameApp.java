package game.frontend;

import game.backend.level.Level1;
import game.backend.level.Level2;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import game.backend.CandyGame;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;



public class GameApp extends Application {


	public static void main(String[] args) {
		launch(args);
	}

	//Cada boton posee las instrucciones para ejecutar el nivel deseado.
	@Override
	public void start(Stage primaryStage) {
		Button level1 = new Button("Level 1");
		level1.setOnAction(event -> {CandyGame game = new CandyGame(Level1.class);
			CandyFrame frame = new CandyFrame(game);
			primaryStage.setResizable(false);
			Scene scene = new Scene(frame);
			primaryStage.setScene(scene);

		});
		Button level2 = new Button("Level 2");
		level2.setOnAction(event -> {CandyGame game = new CandyGame(Level2.class);
			CandyFrame frame = new CandyFrame(game);
			primaryStage.setResizable(false);
			Scene scene = new Scene(frame);
			primaryStage.setScene(scene);

		});
		Button level3 = new Button("Level 3");
		level3.setOnAction(event -> {CandyGame game = new CandyGame(Level3.class);
			CandyFrame frame = new CandyFrame(game);
			primaryStage.setResizable(false);
			Scene scene = new Scene(frame);
			primaryStage.setScene(scene);
		});

		
		//Menu para elegir el nivel
		VBox menu = new VBox(45);
		level1.setPrefSize(100,25);
		level2.setPrefSize(100,25);
		level3.setPrefSize(100,25);
		level1.setTranslateY(225);
		level2.setTranslateY(225);
		level3.setTranslateY(225);

		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(level1,level2,level3);
		
		//imagen de fondo del menu
		Image image = new Image("images/titleScreen.png");
		Scene menuScene = new Scene(menu,image.getWidth(),image.getHeight());
		BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		Background background = new Background(backgroundImage);
		menu.setBackground(background);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(menuScene);
		primaryStage.show();

	}


}
