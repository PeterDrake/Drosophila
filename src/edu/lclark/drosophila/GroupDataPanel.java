package edu.lclark.drosophila;

public class GroupDataPanel extends DataPanel{
	public GroupDataPanel(AnalyzerPanel analyzerpanel) {
		super(analyzerpanel);
	}
	public String createLabels(){
		String Labels=String.format("%-15s %-25s %-5s \n", "group", "flyID", "Velocity");
		return Labels;
	}
	/**
	 * Creates the text block that contains the data for all the flies in the fly list
	 * @param start
	 * @param end
	 * @return String Data
	 */
	public String createData(int start, int end){
		String Data= "";
		if(analyzerPanel.getFlyList()!=null){
		for(Fly fly : analyzerPanel.getFlyList()){
			Data+=createFlyLine(fly,start,end);
			Data+="\n";
		}
	}
		return Data;
	}
	/**
	 * takes a single fly and creates a string to display its avg velocity and total distance"
	 * @param fly
	 * @param start
	 * @param end
	 * @return String FlyLine
	 */
	public String createFlyLine(Fly fly, int start, int end){
		String Data =String.format("%-15s %-25s %-5f", fly.getArena(), fly, fly.totalDistance(start, end));
		return Data;
	}

}
