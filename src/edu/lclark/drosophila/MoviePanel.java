package edu.lclark.drosophila;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;

public class MoviePanel extends JPanel{
	
  
  public static void main(String[] args)
  {
    String sourceUrl = "step.mov";
    
//    MoviePanel videoPlayer = new MoviePanel();
//    videoPlayer.play(sourceUrl);
  }
  
  public Dimension getPreferredSize(){	
	  return new Dimension(500, 500);
  }
  public MoviePanel(){

  }

  public void play(String sourceUrl)
  {
    IMediaReader reader = ToolFactory.makeReader(sourceUrl);
    reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
    
    final MyVideoPanel frame = new MyVideoPanel();
    
    
    add(frame);
    
    MediaListenerAdapter adapter = new MediaListenerAdapter()
    {
      @Override
      public void onVideoPicture(IVideoPictureEvent event)
      {
        frame.setImage((BufferedImage) event.getImage());
        try{
        Thread.sleep(1);
        repaint();
        }
        catch(InterruptedException e){
        	e.getStackTrace();
        }
      }
        
    };
    reader.addListener(adapter);
    //frame.setDefaultCloseOperation(JPanel.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
    while (reader.readPacket() == null)
      do {} while(false);
  }
  
  @SuppressWarnings("serial")
  private class MyVideoPanel extends JPanel
  {
    Image image;
    
    public void setImage(final Image image)
    {
      SwingUtilities.invokeLater(new Runnable()
      {
        public void run()
        {
        	
        	System.err.println("being run");
          MyVideoPanel.this.image = image;
          repaint();
          
        }
      });
    }
  
    @Override
    public synchronized void paintComponent(Graphics g)
    {
    	System.err.println(image);
      if (image != null)
      {
        g.drawImage(image, 0, 0, null);
      }
    }
  }
}
