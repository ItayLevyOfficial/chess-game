package tools;

import java.util.LinkedList;
import java.util.List;

import support.Color;
import support.Direction;
import support.Point;

public class Tower extends Tool {

	public Tower(Point point, Tool[][] board, Color color) {
		super(point, board, color);
	}

	@Override
	public List<Point> possibleMoves() {
		List<Point> possibleMoves = new LinkedList<>();
		possibleMoves.addAll(rowMoves(Direction.DOWN));
		possibleMoves.addAll(rowMoves(Direction.LEFT ));
		possibleMoves.addAll(rowMoves(Direction.UP));
		possibleMoves.addAll(rowMoves(Direction.RIGHT));
		return possibleMoves;
	}

	@Override
	public List<Point> aimingplaces(List<Point> enemyMoves) {
		List<Point> aimingPlaces = new LinkedList<>();
		if (enemyMoves != null && enemyMoves.contains(current)) {
			return aimingPlaces;
		}
		aimingPlaces.addAll(aimingRowMoves(Direction.DOWN, enemyMoves));
		aimingPlaces.addAll(aimingRowMoves(Direction.LEFT, enemyMoves ));
		aimingPlaces.addAll(aimingRowMoves(Direction.UP, enemyMoves));
		aimingPlaces.addAll(aimingRowMoves(Direction.RIGHT, enemyMoves));
		return aimingPlaces;
	}
	@Override
	public String toString() {
		return color + "_tower";
	}
	
}
