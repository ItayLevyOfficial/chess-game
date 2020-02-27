package model;


import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import support.Color;
import support.Point;
import tools.Horse;
import tools.King;
import tools.Queen;
import tools.Runner;
import tools.Soldier;
import tools.Tool;
import tools.Tower;

public class Chess {
	public Tool[][] board;
	public Point lastToolSelectedPoint;
	public Color ourColor;
	public boolean ourTurn;
	public List<Point> possibleMoves;
	public ObjectInputStream in;
	public ObjectOutputStream out;
	
	public Chess() throws Exception {
		board = new Tool[8][8];
		organizeBoardBeggining();
		possibleMoves = new LinkedList<>();
		Socket client = new Socket("127.0.0.1", 1025);
		in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
		ourColor = (Color) in.readObject();
		if (ourColor.equals(Color.BLACK)) {
			board = (Tool[][]) in.readObject();
		}
		out = new ObjectOutputStream(client.getOutputStream());
		ourTurn = true;
	}
	
	//do what should be done when any square in the board selected.
	public void squareSelected(Point point) {
		Tool tool = board[point.y][point.x];
		if(possibleMoves.contains(point)) {
			Tool eaten = board[point.y][point.x];
			moveToThePoint(point);
			possibleMoves = new LinkedList<>();
			if (isChess()) {
				undo(point, eaten);
			}else {
				lastToolSelectedPoint = null;
				ourTurn = false;
				soldiersToQueensIfNeeded();
			}
		}
		else if (tool != null && tool.color.equals(ourColor)) {
			lastToolSelectedPoint = point;
			possibleMoves = tool.possibleMoves();
		} 
	}
	
	//switch every soldier in the last line into queen
	private void soldiersToQueensIfNeeded() {
		for (int i = 0; i < 8; i++) {
			Tool tool = board[0][i];
			changeToQueenIfSoldier(i, tool, 0);
			tool = board[7][i];
			changeToQueenIfSoldier(i, tool, 7);
		}
	}

	//change the tool in the given point into queen if it's soldier
	private void changeToQueenIfSoldier(int x, Tool tool, int y) {
		if (tool != null && tool.getClass().equals(Soldier.class)) {
			board[y][x] = new Queen(tool.current, board, tool.color);
		}
	}

	//send the current board and wait until response
	public void organizeNetworkConf() throws Exception {
		out.writeObject(board);
		out.flush();
		board = (Tool[][]) in.readObject();
		ourTurn = true;
	}

	//undo the last move
	private void undo(Point point, Tool eaten) {
		Point previous = lastToolSelectedPoint;
		lastToolSelectedPoint = point;
		moveToThePoint(previous);
		board[lastToolSelectedPoint.y][lastToolSelectedPoint.x] = eaten;
	}

	//move the lastSelectedTool to the given point
	private void moveToThePoint(Point point) {
		Tool tool;
		tool = board[lastToolSelectedPoint.y][lastToolSelectedPoint.x];
		board[point.y][point.x] = tool;
		tool.current = point;
		board[lastToolSelectedPoint.y][lastToolSelectedPoint.x] = null;
	}
	
	//organize the tool to the beggining of a chess match
	private void organizeBoardBeggining() {
		putTheFuckingSoldiers();
		putTheTools(0, Color.BLACK);
		putTheTools(7, Color.WHITE);
	}

	//put the regular tools in the given raw number
	private void putTheTools(int i, Color color) {
		board[i][0] = new Tower(new Point(i, 0), board, color);
		board[i][7] = new Tower(new Point(i, 7), board, color);
		board[i][6] = new Horse(new Point(i, 6), board, color);
		board[i][1] = new Horse(new Point(i, 1), board, color);
		board[i][5] = new Runner(new Point(i, 5), board, color);
		board[i][2] = new Runner(new Point(i, 2), board, color);
		board[i][4] = new King(new Point(i, 4), board, color);
		board[i][3] = new Queen(new Point(i, 3), board, color);
	}

	//puts the soldiers as should be in the beggining of the match
	private void putTheFuckingSoldiers() {
		for (int i = 0; i < board[0].length; i++) {
			board[1][i] = new Soldier(new Point(1, i), board, Color.BLACK);
			board[6][i] = new Soldier(new Point(6, i), board, Color.WHITE);
		}
	}
	
	//return the point of ourKing
	public Point kingPoint() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Tool tool = board[i][j];
				if (tool != null && tool.getClass().equals(King.class) && tool.color.equals(ourColor)) {
					return new Point(i, j);
				}
			}
		}
		return null;
	}
	
	//checks if our king is aimed. return true if it did, false either.
	public boolean isChess() {
		Point kingPoint = kingPoint();
		King king = (King)board[kingPoint.y][kingPoint.x];
		if(king.isAimed(king.current)) {
			return true;
		} else {
			return false;
		}
	}

	//checks if our king is chessMated. return true if it did, false either.
	public boolean isChessMate() {
		Point kingPoint = kingPoint();
		King king = (King)board[kingPoint.y][kingPoint.x];
		if (king.possibleMoves().isEmpty() && noOneCanDefend()) {
			return true;
		} else {
			return false;
		}
	}

	//checks if any tool can defend our king from the chess. return true if someone can,
	//false either.
	private boolean noOneCanDefend() {
		List<Point> allOurMoves = allOurMoves();
		List<Point> allenemyAims = allEnemyAims(allOurMoves);
		if (allenemyAims.contains(kingPoint())) {
			return true;
		} else {
			return false;
		}
	}

	//return a list of all the points that aimed by the enemy tools.
	private List<Point> allEnemyAims(List<Point> allOurMoves) {
		List<Point> allEnemyAims = new LinkedList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Tool tool = board[i][j];
				if (tool != null && !tool.color.equals(ourColor)) {
					allEnemyAims.addAll(tool.aimingplaces(allOurMoves));
				}
			}
		}
		return allEnemyAims;
	}

	//return a list of all the possible points that one of our soldier can move to.
	private List<Point> allOurMoves() {
		List<Point> allOurMoves = new LinkedList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Tool tool = board[i][j];
				if (tool != null && tool.color.equals(ourColor)) {
					allOurMoves.addAll(tool.possibleMoves());
				}
			}
		}
		return allOurMoves;
	}
	
	//switches our color from black to white and vice versa
	public void switchOurColor() {
		switch (ourColor) {
		case BLACK:
			ourColor = support.Color.WHITE;
			break;
		case WHITE:
			ourColor = support.Color.BLACK;
			break;
		}
	}
}