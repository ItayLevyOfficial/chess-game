package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import controler.Controler;

public class StoryBoard {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StoryBoard window = new StoryBoard();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws Exception 
	 */
	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.pack();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create the buttons board, and set themselves as listeners.
	 * then new controller createn and the buttons sets him as their controller.
	 * @throws  
	 */
	protected void createContents() throws Exception {
		shell = new Shell();
		shell.setModified(true);
		shell.setImage(SWTResourceManager.getImage("C:\\Users\\Itay\\eclipse-workspace\\Chess4Real\\images\\black_horse.png"));
		shell.setSize(450, 300);
		shell.setText("chess");
		GridLayout gl_shell = new GridLayout(8, true);
		gl_shell.horizontalSpacing = 0;
		gl_shell.verticalSpacing = 0;
		shell.setLayout(gl_shell);
		IndexedButton[][] grid = new IndexedButton[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				IndexedButton button = new IndexedButton(shell, SWT.PUSH, i, j);
				grid[i][j] = button;
				button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			}
		}
		Controler controler = new Controler(grid);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				grid[i][j].addSelectionListener(grid[i][j]);
				grid[i][j].controler = controler;
			}
		}
	}
}
