package tools;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import support.Color;
import support.Direction;
import support.Point;

public class Tool implements Serializable{
	public Point current; 
	public Tool[][] board;
	public Color color;
	
	public Tool(Point point, Tool[][] board, Color color) {
		this.current = point;
		this.board = board;
		this.color = color;
	}
	
	//return a list of all the tool possible moves points.
	public List<Point> possibleMoves() {return null;}
	
	//return a list of all the places the tool is aiming.
	public List<Point> aimingplaces(List<Point> enemyMoves) {return null;}
	
	//return the possible row moves points in the given direction
	public List<Point> rowMoves(Direction direction) {
		Point point = new Point(current.y, current.x);
		direction.initial(point);
		Tool tool;
		List<Point> possibleMoves = new LinkedList<>();
		while(inBounds(point)){
			tool = board[point.y][point.x];
			if (tool != null) {
				if(tool.color.equals(color)) {
					break;
				} else {
					possibleMoves.add(new Point(point.y, point.x));
					break;
				}
			} else {
				possibleMoves.add(new Point(point.y, point.x));
			}
			direction.initial(point);
		}
		return possibleMoves;
	}
	
	//return the row points that aimed by our tool in the given direction
	public List<Point> aimingRowMoves(Direction direction, List<Point> enemyMoves) {
		if (enemyMoves == null) {
			enemyMoves = new LinkedList<>();
		}
		Point point = new Point(current.y, current.x);
		direction.initial(point);
		Tool tool;
		List<Point> possibleMoves = new LinkedList<>();
		while(inBounds(point) && !enemyMoves.contains(point)){
			tool = board[point.y][point.x];
			if (tool != null && !(tool.getClass().equals(King.class) && !tool.color.equals(color))) {
				possibleMoves.add(new Point(point.y, point.x));
				break;
			} else {
				possibleMoves.add(new Point(point.y, point.x));
			}
			direction.initial(point);
		}
		return possibleMoves;
	}
	
	//adds the given point to the given list if the tool can move to this point.
	public void addPoint(Point toAdd, List<Point> possibleMoves) {
		if(!inBounds(toAdd)) { return; }
		Tool tool = board[toAdd.y][toAdd.x];
		if (tool != null ) {
			if(!tool.color.equals(color)) {
				possibleMoves.add(new Point(toAdd.y, toAdd.x));
			}
		} else {
			possibleMoves.add(toAdd);
		}
	}
	
	//check if the point in the bounds of the board(8*8)
	public boolean inBounds(Point point) {
		return point.x > -1 && point.y > -1 && point.x < 8 && point.y < 8;
	}
}








