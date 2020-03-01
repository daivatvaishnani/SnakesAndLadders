package services;

import models.*;

import java.util.HashMap;

public class SnakeAndLadderService {
	private static final Integer WINNING_POSITION = 100;
	private static final Integer STARTING_POSITION = 0;

	private SnakeAndLadderBoard snakeAndLadderBoard;

	private HashMap<String, Integer> playerPositions;
	private HashMap<Integer, Integer> snakeWithHeadAtPosition;
	private HashMap<Integer, Integer> ladderStartingAtPosition;
	private HashMap<Integer, Integer> cachedPaths;

	public SnakeAndLadderService(SnakeAndLadderBoard snakeAndLadderBoard) {
		this.snakeAndLadderBoard = snakeAndLadderBoard;
		cachedPaths = new HashMap<>();
		markSnakePositions();
		markLadderPositions();
		markInitialPlayerPositions();
	}

	private void markSnakePositions() {
		if(snakeWithHeadAtPosition == null) {
			snakeWithHeadAtPosition = new HashMap<>();
		}
		for (Snake snake : snakeAndLadderBoard.getSnakes()) {
			snakeWithHeadAtPosition.put(snake.getHead(), snake.getTail());
		}
	}

	private void markLadderPositions() {
		if(ladderStartingAtPosition == null) {
			ladderStartingAtPosition = new HashMap<>();
		}
		for (Ladder ladder : snakeAndLadderBoard.getLadders()) {
			ladderStartingAtPosition.put(ladder.getStart(), ladder.getEnd());
		}
	}

	private void markInitialPlayerPositions() {
		if(playerPositions == null) {
			playerPositions = new HashMap<>();
		}
		for (Player player : snakeAndLadderBoard.getPlayers()) {
			playerPositions.put(player.getName(), STARTING_POSITION);
		}
	}

	private int movePlayer(Player player, int moves) {
		int currentPosition = playerPositions.get(player.getName());
		if (currentPosition + moves > 100) {
			// player cannot make the move
			return currentPosition;
		}
		int finalPosition = move(currentPosition + moves);
		playerPositions.put(player.getName(), finalPosition);
		return finalPosition;
	}

	private int move(int position) {
		if (cachedPaths.containsKey(position)) {
			return cachedPaths.get(position);
		}
		int finalPosition = position;
		if (ladderStartingAtPosition.containsKey(position)) {
			finalPosition = move(ladderStartingAtPosition.get(position));
		}
		if (snakeWithHeadAtPosition.containsKey(position)) {
			finalPosition = move(snakeWithHeadAtPosition.get(position));
		}
		cachedPaths.put(position, finalPosition);
		return finalPosition;
	}

	public void startGame() {
		boolean isCompleted = false;
		int totalPlayers = snakeAndLadderBoard.getPlayers().size();
		int playerTurn = 0;
		Player player;
		int playerPositionBeforeTurn;
		int playerPositionAfterTurn;
		int diceRoll;
		while (!isCompleted) {
			diceRoll = Dice.roll();
			player = snakeAndLadderBoard.getPlayers().get(playerTurn);
			playerPositionBeforeTurn = playerPositions.get(player.getName());
			playerPositionAfterTurn = movePlayer(player, diceRoll);
			System.out.println(player.getName() + " moved from " + playerPositionBeforeTurn + " to " + playerPositionAfterTurn);
			if (playerPositionAfterTurn == WINNING_POSITION) {
				isCompleted = true;
				System.out.println(player.getName() + " won the game!");
			}
			playerTurn = (playerTurn + 1) % totalPlayers;
		}
	}

}
