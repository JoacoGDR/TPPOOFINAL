package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.Optional;

public class GameApp extends Application {

	Button b1 = new Button("Nivel 1");
	Button b2 = new Button("Nivel 2");
	Button b3 = new Button("Nivel 3");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		TilePane r = new TilePane();


		b1.setOnAction(e -> {CandyGame game = new CandyGame(Level1.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene = new Scene(frame);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();});

		b2.setOnAction(e -> {CandyGame game = new CandyGame(Level2.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene = new Scene(frame);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();});

		b3.setOnAction(e -> {CandyGame game = new CandyGame(Level3.class);
			CandyFrame frame = new CandyFrame(game);
			Scene scene = new Scene(frame);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();});

		r.getChildren().addAll(b1,b2,b3);



		//
		Scene sc = new Scene(r, 200, 200);

		// set the scene
		primaryStage.setScene(sc);

		primaryStage.show();


	}




}