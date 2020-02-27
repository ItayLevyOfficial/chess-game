package tools;

import java.util.LinkedList;
import java.util.List;

import support.Color;
import support.Point;

public class Horse extends Tool {

	public Horse(Point point, Tool[][] board, Color color) {
		super(point, board, color);
	}

	@Override
	public List<Point> possibleMoves() {
		List<Point> possibleMoves = new LinkedList<>();
		Point[] allMoves = allHorseplaces();
		
		for (int i = 0; i < allMoves.length; i++) {
			addPoint(allMoves[i], possibleMoves);
		}
		return possibleMoves;
	}

	@Override
	public List<Point> aimingplaces(List<Point> enemyMoves) {
		List<Point> possibleMoves = new LinkedList<>();
		if (enemyMoves != null && enemyMoves.contains(current)) {
			return possibleMoves;
		}
		Point[] allMoves = allHorseplaces();
		
		for (int i = 0; i < allMoves.length; i++) {
			Point point = allMoves[i];
			if(inBounds(point)) {
				possibleMoves.add(point);
			}
		}
		return possibleMoves;
	}

	//return all the points that the horse can move to *theoreticly*
	private Point[] allHorseplaces() {
		Point a= new Point(current.y+1, current.x+2);
		Point b = new Point(current.y+2, current.x+1);
		Point c = new Point(current.y-1, current.x+2);
		Point d = new Point(current.y-2, current.x+1);
		Point e = new Point(current.y+1, current.x-2);
		Point f = new Point(current.y+2, current.x-1);
		Point g = new Point(current.y-1, current.x-2);
		Point h = new Point(current.y-2, current.x-1);
		Point[] allMoves = {a,b,c,d,e,f,g,h};
		return allMoves;
	}

	@Override
	public String toString() {
		return color + "_horse";
	}
	
}
