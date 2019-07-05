package game.frontend;

import game.backend.Grid;
import game.backend.element.TimeBomb;
import game.backend.level.Level2;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BoardPanel extends TilePane {

	private StackPane[][] cells;

	public BoardPanel(final int rows, final int columns, final int cellSize) {
		setPrefRows(rows);
		setPrefColumns(columns);
		setPrefTileHeight(cellSize);
		setPrefTileWidth(cellSize);
		this.cells = new StackPane[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new StackPane();
				getChildren().add(cells[i][j]);
			}
		}
	}

	public void setImage(int row, int column, Image image, Grid level) {
		cells[row][column].getChildren().add(new ImageView(image));
		if(level.get(row,column) instanceof TimeBomb) {
			DropShadow dropShadow = new DropShadow();
			dropShadow.setRadius(3.0);
			dropShadow.setOffsetX(3.0);
			dropShadow.setOffsetY(3.0);

			dropShadow.setColor(Color.ORANGERED);


			TimeBomb curr = (TimeBomb) level.get(row,column);
			Text text = new Text(String.format("%d",curr.getCounter()));
			text.setFont(Font.font("Impact", FontWeight.BOLD, 40));
			text.setFill(Color.BLACK);
			text.setEffect(dropShadow);
			cells[row][column].getChildren().add(text);
		}


	}

}