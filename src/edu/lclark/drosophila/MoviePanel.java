package edu.lclark.drosophila;
import com.xuggle.*;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;
public class MoviePanel {
	public static void main(String[] args) {

		        IContainer container = IContainer.make();
		        int result = container.open("step.mov", IContainer.Type.READ, null);
		        long duration = container.getDuration();
		        System.out.println(duration);
		        IMediaReader reader = ToolFactory.makeReader(container);
		        reader.addListener(ToolFactory.makeViewer(false));
		        while(reader.readPacket()==null);
		        }
	}

