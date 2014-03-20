package edu.lclark.drosophila;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

public class VideoThumbnailsExample {

	public static final double SECONDS_BETWEEN_FRAMES = 1 / 10.0;

	public static final long MICRO_SECONDS_BETWEEN_FRAMES = (long) (Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);

	private static final String INPUT_FILE_NAME = "Clip18.mov";

	// Time of last frame write
	// PTS means ... picture time stamp?
	private long mLastPtsWrite;

	private List<BufferedImage> frames;

	public static void main(String[] args) {
		new VideoThumbnailsExample().run();
	}

	public VideoThumbnailsExample() {
		frames = new ArrayList<BufferedImage>();
		mLastPtsWrite = Global.NO_PTS;
	}

	public void run() {
		try {
			IMediaReader mediaReader = ToolFactory.makeReader(INPUT_FILE_NAME);
			// stipulate that we want BufferedImages created in BGR 24bit color
			// space
			mediaReader
					.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
			mediaReader.addListener(new ImageSnapListener());
			// read out the contents of the media file and
			// dispatch events to the attached listener
			while (mediaReader.readPacket() == null) {
				// Wait
			}
			;
			System.out.println(frames);
			JFrame frame = new JFrame();
			ImagePanel panel = new ImagePanel();
			panel.setPreferredSize(new Dimension(200, 300));
			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			System.out.println("There are " + frames.size() + " frames ");
			System.out.println("Sleeping for " + (MICRO_SECONDS_BETWEEN_FRAMES / 1000) + " msec");
			for (BufferedImage b : frames) {
				panel.setImage(b);
				frame.repaint();
				Thread.sleep(MICRO_SECONDS_BETWEEN_FRAMES / 1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ImagePanel extends JPanel {
		private BufferedImage image;

		public void setImage(BufferedImage image) {
			this.image = image;
		}

		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}

	}

	private class ImageSnapListener extends MediaListenerAdapter {

		public void onVideoPicture(IVideoPictureEvent event) {
			// if uninitialized, back date mLastPtsWrite to get the very first
			// frame
			if (mLastPtsWrite == Global.NO_PTS) {
				mLastPtsWrite = event.getTimeStamp()
						- MICRO_SECONDS_BETWEEN_FRAMES;
			}
			// if it's time to write the next frame
			if (event.getTimeStamp() - mLastPtsWrite >= MICRO_SECONDS_BETWEEN_FRAMES) {
				double seconds = ((double) event.getTimeStamp())
						/ Global.DEFAULT_PTS_PER_SECOND;
				frames.add(event.getImage());
				System.out.printf(
						"at elapsed time of %6.3f seconds wrote: %s\n",
						seconds, "<imaginary file>");
				// update last write time
				mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
			}
		}
	}

}
