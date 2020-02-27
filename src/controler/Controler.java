package controler;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import model.Chess;
import support.Point;
import view.IndexedButton;

public class Controler {
	public IndexedButton[][] buttonsGrid;
	public Chess chess;
	public Controler(IndexedButton[][] buttonsGrid) throws Exception {
		this.buttonsGrid = buttonsGrid;
		chess = new Chess();
		updateViewFromModel();
	}
	
	//updates the visual board from the game model in the memory
	private void updateViewFromModel() throws Exception {
		updateImages();
		updateColors();
		displayChess();
		if (!chess.ourTurn) {
			chess.switchOurColor();
			displayChess();
			chess.switchOurColor();
			updateButtons();
			chess.organizeNetworkConf();
			updateViewFromModel();
		}
	}

	//just redraw the buttons according to the changes in their attributes
	private void updateButtons() {
		for (int i = 0; i < buttonsGrid.length; i++) {
			for (int j = 0; j < buttonsGrid[0].length; j++) {
				buttonsGrid[i][j].update();
			}
		}
	}

	//display the chess by coloring the aimed king in red(chess)/purple(chessMate)
	private void displayChess() {
		if(chess.isChess()) {
			Point kingPoint = chess.kingPoint();
			buttonsGrid[kingPoint.y][kingPoint.x].setBackground(new Color(
					buttonsGrid[0][0].getDisplay(), new RGB(200, 0, 0)));
			if (chess.isChessMate()) {
				buttonsGrid[kingPoint.y][kingPoint.x].setBackground(new Color(
						buttonsGrid[0][0].getDisplay(), new RGB(200, 0, 200)));
			}
		}
	}

	//update the board colors according to the chess model
	private void updateColors() {
		colorBoardDefault();
		if(chess.possibleMoves == null) { return; }
		colorPossibleMoves();
	}

	//color the board in black and white
	private void colorBoardDefault() {
		Color white = new Color(buttonsGrid[0][0].getDisplay(), new RGB(230, 230, 230));
		Color black = new Color(buttonsGrid[0][0].getDisplay(), new RGB(40, 40, 40));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if((i+j)%2 == 0) {
					buttonsGrid[i][j].setBackground(white);
				} else {
					buttonsGrid[i][j].setBackground(black);
				}
			}
		}
	}

	//color the possible moves in blue
	private void colorPossibleMoves() {
		Color blue = new Color(buttonsGrid[0][0].getDisplay(), new RGB(0, 0, 200));
		for(Point point: chess.possibleMoves) {
			buttonsGrid[point.y][point.x].setBackground(blue);
		}
	}

	//update the tools images according the chess model
	private void updateImages() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chess.board[i][j] == null) {
					buttonsGrid[i][j].setImage(new Image(buttonsGrid[i][j].getDisplay(), "images\\empty.png"));
				} else {
					buttonsGrid[i][j].setImage(new Image(buttonsGrid[i][j].getDisplay(), "images\\"
					+ chess.board[i][j] + ".png"));
				}
			}
		}
	}

	//called when any button selected. say to the model that this square selected and 
	//updates the view from the model
	public void buttonSelected(Point point) throws Exception {
		chess.squareSelected(point);
		updateViewFromModel();
	}
}












