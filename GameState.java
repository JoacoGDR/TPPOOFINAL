package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	
	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public abstract boolean gameOver();
	
	public abstract boolean playerWon();

	//El "valor default" es el score
	//Se hizo este metodo para simplificar la forma de obtener y mostrar en pantalla lo pedido en cada nivel
	//(puntaje, cantidad de movimientos, frutas, etc)
	@Override
	public String toString() {
		return String.format("%d", getScore());
	}
}
