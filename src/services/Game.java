package services;

import models.Dice;
import models.Ladder;
import models.Player;
import models.Snake;

import java.util.HashMap;
import java.util.List;

public class Game {
	private static final int WINNING_POSITION = 100;
	private List<Snake> snakes;
	private List<Ladder> ladders;
	private List<Player> players;
	private HashMap<String, Integer> playerPositions;
	private HashMap<Integer, Integer> cachedPaths;

	public Game(List<Snake> snakes,
				List<Ladder> ladders,
				List<Player> players) {
		this.snakes = snakes;
		this.ladders = ladders;
		this.players = players;
		playerPositions = new HashMap<>();
		cachedPaths = new HashMap<>();
		initPlayerPositions();
	}

	private void initPlayerPositions() {
		for(Player player : players) {
			playerPositions.put(player.getName(), 0);
		}
	}

	private int movePlayer(Player player, int moves) {
		int currentPosition = playerPositions.get(player.getName());
		int finalPosition = move(currentPosition + moves);
		playerPositions.put(player.getName(), finalPosition);
		return finalPosition;
	}

	private int move(int position) {
		if(cachedPaths.containsKey(position)) {
			return cachedPaths.get(position);
		}
		int finalPosition = position;
		Ladder ladder = getLadderStartingAtPosition(position);
		if(ladder != null) {
			finalPosition = move(ladder.getEnd());
		}
		Snake snake = getSnakeWithHeadAtPosition(position);
		if(snake != null) {
			finalPosition = move(snake.getTail());
		}
		cachedPaths.put(position, finalPosition);
		return finalPosition;
	}

	private Snake getSnakeWithHeadAtPosition(int position) {
		for(Snake snake : snakes) {
			if(snake.getHead() == position) {
				return snake;
			}
		}
		return null;
	}

	private Ladder getLadderStartingAtPosition(int position) {
		for(Ladder ladder : ladders) {
			if(ladder.getStart() == position) {
				return ladder;
			}
		}
		return null;
	}

	public void startGame() {
		boolean isCompleted = false;
		int totalPlayers = players.size();
		int playerTurn = 0;
		Player player;
		int playerPositionBeforeTurn;
		int playerPositionAfterTurn;
		int diceRoll;
		while(!isCompleted) {
			diceRoll = Dice.roll();
			player = players.get(playerTurn);
			playerPositionBeforeTurn = playerPositions.get(player.getName());
			playerPositionAfterTurn = movePlayer(player, diceRoll);
			System.out.println(player.getName() + " moved from " + playerPositionBeforeTurn + " to " + playerPositionAfterTurn);
			if(playerPositionAfterTurn == WINNING_POSITION) {
				isCompleted = true;
				System.out.println(player.getName() + " won the game!");
			}
			playerTurn = (playerTurn + 1) % totalPlayers;
		}
	}

}
