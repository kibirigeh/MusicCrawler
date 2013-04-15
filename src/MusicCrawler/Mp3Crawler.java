package MusicCrawler;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class Mp3Crawler extends Observable implements Runnable
{
	ItemListener itemlistener;
	String searchTerm="";
	JLabel response;
	List<String>bases= new ArrayList<String>();
	//MusicPlayer player;
	
    Mp3Crawler(String SearchTerm,JLabel response)  
    {
    	searchTerm=SearchTerm;
    	this.response=response;
    	bases.add("http://mp3skull.com/mp3/");
    	bases.add("http://www.downloads.nl/music/");
    	bases.add("http://freemp3x.com/"); 
    	//player = new MusicPlayer();
    	  
    	  itemlistener = new ItemListener() 
    	  {

    		  @Override
    		  public void itemStateChanged(ItemEvent itemE) 
    		  {
    	
    			  AbstractButton absB = (AbstractButton) itemE.getSource();
    	
    			  int st = itemE.getStateChange();
    	 
    			  if (st == ItemEvent.SELECTED) 
    			  {
    				  System.out.println("Link "+absB.getText()+" checked");
    				  setChanged();
    				  notifyObservers("preview/"+absB.getText());
    				 // player.playSong(absB.getText());
    			  }
    			  if (st == ItemEvent.DESELECTED) 
    			  {
    				  System.out.println("Link"+absB.getText()+" unchecked");
    				  setChanged();
    				  notifyObservers("stop/"+absB.getText());
    				 // player.stopSong(absB.getText());
    			  }
    		  }
    	    };
    }
    
    
    public  void extractUrl(String value)
    {
        if (value == null) 
        {
        	return;
       	}
        
        String urlPattern = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(value);
       
        while (m.find())
        {
        	String temp=value.substring(m.start(0),m.end(0));
        	if(temp.endsWith(".mp3"))
        	{
        		JCheckBox tempObj= new JCheckBox(temp);
        		tempObj.addItemListener(itemlistener);
        		tempObj.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 19));
        		tempObj.setForeground(Color.WHITE);
        		setChanged();
        		notifyObservers(tempObj);
        	}
        }
    }

	@Override
	public void run() 
	{
		setChanged();
		notifyObservers("crawling started");
		// TODO Auto-generated method stub
		for(String base:bases)
        {
        	try 
            {
                URL my_url = new URL(base+searchTerm+".html");
                BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
                response.setText("Searching the Web for files related to this topic..........");
                response.revalidate();
                
                while(null != br.readLine())
                {
                	extractUrl(br.readLine());
                }
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        	
        }
		setChanged();
		notifyObservers("crawling done");
	} 
}