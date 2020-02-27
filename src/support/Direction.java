package support;

public enum Direction {
	RIGHT(){
		@Override
		public void initial(Point point) {
			point.x++;
		}
	}
	, LEFT(){
		@Override
		public void initial(Point point) {
			point.x--;
		}
	}, UP(){
		@Override
		public void initial(Point point) {
			point.y--;
		}
	}, DOWN(){
		@Override
		public void initial(Point point) {
			point.y++;
		}
	}, UPRIGHT(){
		@Override
		public void initial(Point point) {
			point.y--;
			point.x++;
		}
	}, UPLEFT(){
		@Override
		public void initial(Point point) {
			point.y--;
			point.x--;
		}
	}, DOWNRIGHT(){
		@Override
		public void initial(Point point) {
			point.x++;
			point.y++;
		}
	}, DOWNLEFT(){
		@Override
		public void initial(Point point) {
			point.y++;
			point.x--;
		}
	};
	
	//initial the given point according to the direction, by 1.
	public void initial(Point point) {}
}
