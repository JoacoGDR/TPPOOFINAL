package game.backend.cell;

import game.backend.Grid;
import game.backend.element.Element;
import game.backend.element.Fruit;
import game.backend.element.Nothing;
import game.backend.element.TimeBomb;
import game.backend.level.Level2;
import game.backend.level.Level3;
import game.backend.move.Direction;

public class Cell {
	
	private Grid grid;
	private Cell[] around = new Cell[Direction.values().length];
	private Element content;
	
	public Cell(Grid grid) {
		this.grid = grid;
		this.content = new Nothing();
	}
	
	public void setAround(Cell up, Cell down, Cell left, Cell right) {
		this.around[Direction.UP.ordinal()] = up;
		this.around[Direction.DOWN.ordinal()] = down;
		this.around[Direction.LEFT.ordinal()] = left;
		this.around[Direction.RIGHT.ordinal()] = right;
	}

	public boolean hasFloor() {
		return !around[Direction.DOWN.ordinal()].isEmpty();
	}
	
	public boolean isMovable(){
		return content.isMovable();
	}
	
	public boolean isEmpty() {
		return !content.isSolid();
	}

	public Element getContent() {
		return content;
	}
	
	/* Modificaciones: (1) Para que no se destruyan las frutas de la misma manera que los demas caramelos
	*	(2) Si se destruye una bomba se descuenta del contador de bombas.
	*/
	
	public void clearContent() {
		if(!(content instanceof Fruit)) {
			if (content.isMovable()) {
				if(content instanceof TimeBomb){
					Level2 mygrid = (Level2) grid;
					mygrid.getBombs().remove(content);
				}
				Direction[] explosionCascade = content.explode();
				grid.cellExplosion(content);
				this.content = new Nothing();
				if (explosionCascade != null) {
					expandExplosion(explosionCascade);
				}
				this.content = new Nothing();
			}
		}
	}
	
	private void expandExplosion(Direction[] explosion) {
		for(Direction d: explosion) {
			this.around[d.ordinal()].explode(d);
		}
	}
	
	private void explode(Direction d) {
		clearContent();
		if (this.around[d.ordinal()] != null)
			this.around[d.ordinal()].explode(d);
	}
	
	public Element getAndClearContent() {
		if (content.isMovable()) {
			Element ret = content;
			this.content = new Nothing();
			return ret;
		}
		return null;
	}

	public boolean fallUpperContent() {
		Cell up = around[Direction.UP.ordinal()];
		if (this.isEmpty() && !up.isEmpty() && up.isMovable()) {
			this.content = up.getAndClearContent();
			grid.wasUpdated();
			if (this.hasFloor()) {
				grid.tryRemove(this);
				return true;
			} else {
				Cell down = around[Direction.DOWN.ordinal()];
				return down.fallUpperContent();
			}
		} 
		return false;
	}
	
	public void setContent(Element content) {
		this.content = content;
	}

}
