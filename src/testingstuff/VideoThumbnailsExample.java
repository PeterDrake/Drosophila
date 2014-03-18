package testingstuff;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;

public class VideoThumbnailsExample {
    
    public static final double SECONDS_BETWEEN_FRAMES = 5;

    public static final long MICRO_SECONDS_BETWEEN_FRAMES = 
    		(long)(Global.DEFAULT_PTS_PER_SECOND * SECONDS_BETWEEN_FRAMES);
    
    private static final String INPUT_FILE_NAME = "step.mov";
    
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
        IMediaReader mediaReader = ToolFactory.makeReader(INPUT_FILE_NAME);
        // stipulate that we want BufferedImages created in BGR 24bit color space
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        mediaReader.addListener(new ImageSnapListener());
        // read out the contents of the media file and
        // dispatch events to the attached listener
        while (mediaReader.readPacket() == null) {
        	  	// Wait
        };
        System.out.println(frames);
    }

    private class ImageSnapListener extends MediaListenerAdapter {

        public void onVideoPicture(IVideoPictureEvent event) {
            // if uninitialized, back date mLastPtsWrite to get the very first frame
            if (mLastPtsWrite == Global.NO_PTS) {
                mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
            }
            // if it's time to write the next frame
            if (event.getTimeStamp() - mLastPtsWrite >= 
                    MICRO_SECONDS_BETWEEN_FRAMES) {
                double seconds = ((double) event.getTimeStamp()) / 
                    Global.DEFAULT_PTS_PER_SECOND;
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
