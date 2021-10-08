package model;


import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;



public class ComputerPlayer extends Player {
	
	private static String encodingResource="encoding/makyek";
	private static Handler handler;
	  
    public ComputerPlayer(boolean whiteSide)
    {
    	super();
        this.whiteSide = whiteSide;
        this.humanPlayer = false;
        handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));  
    }
    
   public MyPair<Integer, Point> evaluateNextMove(String inputState) {
	   
	   InputProgram facts = new ASPInputProgram();
		facts.addProgram(inputState);
		handler.addProgram(facts);
		
		InputProgram encoding= new ASPInputProgram();
		encoding.addFilesPath(encodingResource);
		
		
		handler.addProgram(encoding);
		
		
		Output o =  handler.startSync();
		
		
		AnswerSets answersets = (AnswerSets) o;
		
		
		String answer = answersets.getAnswerSetsString();
		Pattern p = Pattern.compile(".*\\{.*move\\((\\d+),(\\d),(\\d)\\),.+\\nCOST.+\\nOPTIMUM");
		Matcher m = p.matcher(answer);
		
		int id, endX, endY;
		MyPair<Integer, Point> move = null;
		
		System.out.println(answer);
		
		if(m.find()) {
			System.out.println("move("+m.group(1)+","+m.group(2)+","+m.group(3)+")");
			id = Integer.valueOf(m.group(1));
			endX = Integer.valueOf(m.group(2));
			endY = Integer.valueOf(m.group(3));	
			
			move = new MyPair<Integer, Point>(id, new Point(endX, endY));
		}
		else {
			 p = Pattern.compile(".*\\{.*move\\((\\d+),(\\d),(\\d)\\),.+}");
			 m = p.matcher(answer);
			
			
			if(m.find()) {
				System.out.println("move("+m.group(1)+","+m.group(2)+","+m.group(3)+")");
				id = Integer.valueOf(m.group(1));
				endX = Integer.valueOf(m.group(2));
				endY = Integer.valueOf(m.group(3));	
				
				move = new MyPair<Integer, Point>(id, new Point(endX, endY));
			}
			
		}
		handler.removeAll();
		return move;
   }
}