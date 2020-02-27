package view;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import controler.Controler;
import support.Point;

public class IndexedButton extends Button implements SelectionListener {

	int y;
	int x;
	Controler controler;
	
	public IndexedButton(Composite arg0, int arg1, int y, int x) {
		super(arg0, arg1);
		this.y = y;
		this.x = x;
	}

	//never called.
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	//called when the button selected. lets the controller handle the selection by
	//telling him that the button in the given index selected.
	@Override
	public void widgetSelected(SelectionEvent arg0) {
		try {
			controler.buttonSelected(new Point(y, x));
		} catch (Exception e) {}
	}

	@Override
	public void checkSubclass() {}
}
