package support;

import java.io.Serializable;

public class Point implements Serializable{
	public int y;
	public int x;
	
	public Point(int y, int x) {
		this.y = y;
		this.x = x;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Point other = (Point)arg0;
		return other.x == x && other.y == y;
	}
}
