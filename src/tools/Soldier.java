package tools;

import java.util.LinkedList;
import java.util.List;

import support.Color;
import support.Direction;
import support.Point;

public class Soldier extends Tool {
	public Soldier(Point point, Tool[][] board, Color color) {
		super(point, board, color);
	}

	@Override
	public List<Point> possibleMoves() {
		Direction direction;
		if(color.equals(Color.BLACK)) {
			direction = Direction.DOWN;
		} else {
			direction = Direction.UP;
		}
		List<Point> possibleMoves = new LinkedList<>();
		addFowardPoints(direction, possibleMoves);
		addSlants(direction, possibleMoves);
		return possibleMoves;
	}

	@Override
	public List<Point> aimingplaces(List<Point> enemyMoves) {
		List<Point> aimingPlaces = new LinkedList<>();
		if (enemyMoves != null && enemyMoves.contains(current)) {
			return aimingPlaces;
		}
		Direction direction;
		if(color.equals(Color.BLACK)) {
			direction = Direction.DOWN;
		} else {
			direction = Direction.UP;
		}
		Point point = new Point(current.y, current.x);
		addAllSlants(aimingPlaces, direction, point);
		return aimingPlaces;
	}

	//add the slants points in the given direction to the list.
	private void addAllSlants(List<Point> aimingPlaces, Direction direction, Point point) {
		direction.initial(point);
		point.x--;
		if(inBounds(point)) {
			aimingPlaces.add(new Point(point.y, point.x));
		}
		point.x += 2;
		if(inBounds(point)) {
			aimingPlaces.add(point);
		}
	}

	//add the slants points in the given direction to the list if they eatable(theres 
	//enemy tool in it).
	private void addSlants(Direction direction, List<Point> possibleMoves) {
		Point point = new Point(current.y, current.x);
		direction.initial(point);
		point.x--;
		addIfEatable(point, possibleMoves);
		point.x += 2;
		addIfEatable(point, possibleMoves);
	}

	//add the point to the list if it is eatable(theres enemy tool in it).
	private void addIfEatable(Point point, List<Point> possibleMoves) {
		Tool tool = null;
		try {
			tool = board[point.y][point.x];
		}catch (ArrayIndexOutOfBoundsException e) {return;}
		if (tool != null && !tool.color.equals(color)) {
			possibleMoves.add(new Point(point.y, point.x));
		}
	}

	//add two points in the given direction to the list if there's no tool in them
	private void addFowardPoints(Direction direction, List<Point> possibleMoves) {
		Point point = new Point(current.y, current.x);
		direction.initial(point);
		Tool tool = board[point.y][point.x];
		if(tool == null) {
			possibleMoves.add(new Point(point.y, point.x));
			direction.initial(point);
			try {
				tool = board[point.y][point.x];
			}catch (ArrayIndexOutOfBoundsException e) { return; }
			if(tool == null && current.y == 1 || tool == null && current.y == 6 ) {
				possibleMoves.add(point);
			}
		}
	}
	@Override
	public String toString() {
		return color + "_soldier";
	}
}