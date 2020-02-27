package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import support.Color;

public class Server {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket server = new ServerSocket(1025);
		Socket whitePlayer = server.accept();
		Socket blackPlayer = server.accept();
		ObjectOutputStream whiteOutput = new ObjectOutputStream(whitePlayer.getOutputStream());
		ObjectOutputStream blackOutput = new ObjectOutputStream(blackPlayer.getOutputStream());
		
		whiteOutput.writeObject(Color.WHITE);
		blackOutput.writeObject(Color.BLACK);
		whiteOutput.flush();
		blackOutput.flush();
		ObjectInputStream whiteInput = new ObjectInputStream(whitePlayer.getInputStream());
		Object board = whiteInput.readObject();
		blackOutput.writeObject(board);
		blackOutput.flush();
		ObjectInputStream blackInput = new ObjectInputStream(blackPlayer.getInputStream());
		board = blackInput.readObject();
		whiteOutput.writeObject(board);
		whiteOutput.flush();
		while (true) {
			 board = whiteInput.readObject();
			 blackOutput.writeObject(board);
			 blackOutput.flush();
			 board = blackInput.readObject();
		 	 whiteOutput.writeObject(board);
			 whiteOutput.flush();
		}
	}
}
