package drivers;

import models.Ladder;
import models.Player;
import models.Snake;
import models.SnakeAndLadderBoard;
import services.SnakeAndLadderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameDriver {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numberOfSnakes = sc.nextInt();
		List<Snake> snakes = new ArrayList<>();
		for(int i = 0; i < numberOfSnakes; ++i) {
			int head = sc.nextInt();
			int tail = sc.nextInt();
			snakes.add(new Snake(head, tail));
		}
		int numberOfLadders = sc.nextInt();
		List<Ladder> ladders = new ArrayList<>();
		for(int i = 0; i < numberOfLadders; ++i) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			ladders.add(new Ladder(start, end));
		}
		int numberOfPlayers = sc.nextInt();
		List<Player> players = new ArrayList<>();
		for(int i = 0; i < numberOfPlayers; ++i) {
			String name = sc.next();
			players.add(new Player(name));
		}
		SnakeAndLadderBoard snakeAndLadderBoard = new SnakeAndLadderBoard(snakes, ladders, players);
		SnakeAndLadderService snakeAndLadderService = new SnakeAndLadderService(snakeAndLadderBoard);
		snakeAndLadderService.startGame();
	}
}
