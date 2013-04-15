package MusicCrawler;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class JPanelUpdatable extends JPanel implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void update(Observable o, Object arg) 
	{
		// TODO Auto-generated method stub
		if(arg instanceof JCheckBox)
		{
			this.add((JCheckBox) arg);
		}
	}

}
