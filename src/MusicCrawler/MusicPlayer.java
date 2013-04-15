package MusicCrawler;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

public class MusicPlayer 
{
	Music musicPlayer;
	private Thread playerThread;  
	String url;
	boolean started=false;;
	void playSong(Object arg1) 
	{
		// TODO Auto-generated method stub
		if(arg1 instanceof String)
		{
			url=(String)arg1;
			if(!((String) arg1).isEmpty())
			{
					musicPlayer = new Music((String)arg1);
			        this.playerThread = new Thread(musicPlayer, "AudioPlayerThread");
		            this.playerThread.start();
		            started=true;
			}	
		}
	}
	
	void stopSong()
	{
		if(started)
		{
			try
			{
				if(!url.isEmpty())
				{
					musicPlayer.stop();
					playerThread.stop();
					url="";
					started=false;
				}	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	class Music extends PlaybackListener implements Runnable
	{
	    private String filePath;
	    private AdvancedPlayer player;
	      

	    public Music(String filePath)
	    {
	        this.filePath = filePath;
	        play();
	    }
	    
	    public void stop()
	    {
	    	try
	    	{
	    		player.stop();
		    	//playerThread.stop();
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    
	    public void play()
	    {
	        try
	        {	        	
	        	String urlAsString = filePath;

	            this.player = new AdvancedPlayer
	            (
	                new java.net.URL(urlAsString).openStream(),
	                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
	            );

	            this.player.setPlayBackListener(this);
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
	    }

	    // PlaybackListener members
	    public void playbackStarted(PlaybackEvent playbackEvent)
	    {
	        System.out.println("playbackStarted()");
	    }

	    public void playbackFinished(PlaybackEvent playbackEvent)
	    {
	        System.out.println("playbackEnded()");
	    }    

	    // Runnable members
	    public void run()
	    {
	        try
	        {
	            this.player.play();
	        }
	        catch (javazoom.jl.decoder.JavaLayerException ex)
	        {
	            ex.printStackTrace();
	        }
	    }
	}

}
