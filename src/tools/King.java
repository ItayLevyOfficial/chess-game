package tools;

import java.util.LinkedList;
import java.util.List;

import support.Color;
import support.Point;

public class King extends Tool {
	public King(Point point, Tool[][] board, Color color) {
		super(point, board, color);
	}

	@Override
	public List<Point> possibleMoves() {
		List<Point> possibleMoves = new LinkedList<>();
		Point[] allMoves = allMoves(current);
		for (int i = 0; i < allMoves.length; i++) {
			Point point = allMoves[i];
			if (!isAimed(point) && inBounds(point) &&
					!(board[point.y][point.x] != null && board[point.y][point.x].color.equals(color))) {
				possibleMoves.add(point);
			}
		}
		return possibleMoves;
	}

	//return all the points around the king
	private Point[] allMoves(Point point) {
		Point[] allMoves = {new Point(point.y-1, point.x-1), new Point(point.y+1, point.x-1),
				new Point(point.y-1, point.x+1), new Point(point.y+1, point.x+1),
				new Point(point.y-1, point.x), new Point(point.y+1, point.x),
				new Point(point.y, point.x-1), new Point(point.y, point.x+1)};
		return allMoves;
	}

	@Override
	public List<Point> aimingplaces(List<Point> enemyMoves) {
		Point[] allMoves = allMoves(current);
		List<Point> aimingMoves = new LinkedList<>();
		for (int i = 0; i < allMoves.length; i++) {
			if (inBounds(allMoves[i])) {
				aimingMoves.add(allMoves[i]);
			}
		}
		return aimingMoves;
	}
	
	//check if the king is aimed. return true if he did, false either.
	public boolean isAimed(Point point) {
		if (!inBounds(point)) {
			return true;
		}
		if(enemyAims().contains(point)) {
			return true;
		} else {
			return false;
		}
	}

	//returns all the points the enemy tools aims.
	private List<Point> enemyAims() {
		List<Point> enemyAims = new LinkedList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Tool tool = board[i][j];
				if(tool != null && !tool.color.equals(color)) {
					enemyAims.addAll(tool.aimingplaces(null));
				}
			}
		}
		return enemyAims;
	}

	@Override
	public String toString() {
		return color + "_king";
	}
	
}
















