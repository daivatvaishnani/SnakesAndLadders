package models;

import java.util.List;

public class SnakeAndLadderBoard {
	private List<Snake> snakes;
	private List<Ladder> ladders;
	private List<Player> players;

	public SnakeAndLadderBoard(List<Snake> snakes, List<Ladder> ladders, List<Player> players) {
		this.snakes = snakes;
		this.ladders = ladders;
		this.players = players;
	}

	public List<Snake> getSnakes() {
		return snakes;
	}

	public void setSnakes(List<Snake> snakes) {
		this.snakes = snakes;
	}

	public List<Ladder> getLadders() {
		return ladders;
	}

	public void setLadders(List<Ladder> ladders) {
		this.ladders = ladders;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
