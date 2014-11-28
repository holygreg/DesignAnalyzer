package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import Design.ChipElement;
import Design.Position;
import Design.Position.PositionType;
import Exceptions.IllegalPlacementException;
import Exceptions.IllegalSubblockException;
import Exceptions.OverlapException;
import Exceptions.ParseException;

public class PlacementDataParser extends Parser {

	ArrayList<Position> Positions; //Hier werden die Subblock-Informationen einer bestimmten Position gespeichert. Mit den setSubblock-Methoden der Position-Klasse kann geprüft werden, ob Subblock bereits besetzt ist, falls es bereits ein Position-Element an der entsprechenden Position gibt.
	HashMap<String, ChipElement> ChipElements; // Key= Name des Elements
	String netFile; // Zugehöriges Netzlistenfile
	String archFile; // Zugehöriges Architekturfile
	int xsize; // Länge des Chips
	int ysize; // Breite des Chips
                                  
	/*
	 * Folgende Liste / Strukturen brauchen wir noch
	 * 
	 * Neue Klasse ArrayListe mit allen ausgelesen Informationen der
	 * Platzierungsdatei ArrayList placementPosition Jede Position besitzt 2
	 * ChipElemente alle nicht gesetzten sind null
	 */

	public PlacementDataParser(String path) throws ParseException  {
		super(path);
		parseData();

	}

	private void parseData() throws ParseException {
		int block = 0;
		String[] tempArray;// Speichert Wörter einer Zeile zwischen
		String temp; // Speichert Zeile zwischen
		String placement = readData(this.path);
		placement = deleteComments(placement);
		StringTokenizer st = new StringTokenizer(placement, "\n"); // Text in Zeilen spalten

		/*------------Allgemeine Informationen der Platzierungsdatei speichern------------*/
		temp = st.nextToken();
		tempArray = temp.split(" ");
		netFile = tempArray[2];
		archFile = tempArray[5];
		temp = st.nextToken();
		tempArray = temp.split(" ");
		xsize = Integer.parseInt(tempArray[2]);
		ysize = Integer.parseInt(tempArray[4]);
		Positions = new ArrayList<Position>((xsize+2)*(ysize+2));
		
		for(int i = 0;i<(xsize+2)*(ysize+2);i++)
			Positions.add(new Position(PositionType.EMPTY));
		ChipElements = new HashMap<String,ChipElement>();
		/*------------------------Eigentliche Platzierungsinformationen auslesen-----------------*/
		while (st.hasMoreTokens()) {
			temp = st.nextToken();
			tempArray = temp.split(" ");
			checkForOverlap(tempArray[0], Integer.parseInt(tempArray[1]),
					Integer.parseInt(tempArray[2]),
					Integer.parseInt(tempArray[3]), block); // Platzierugsliste füllen, ChipElement-Liste füllen, auf korrekte Platzierung prüfen
			block++;
		}

	}

	private void checkForOverlap(String name, int x, int y, int subblock,
			int blockNr) throws ParseException {
		Position temp;
		ChipElement elem = new ChipElement(name,blockNr,subblock);
		
	
			if(subblock<0||subblock>1)
				throw new IllegalSubblockException("Der angegebene Subblock für das Element "+ name +" liegt außerhalb der Menge {0,1}!");
		
		
		
	
	if((x==0&&y==0)||(x==xsize+1&&y==0)||(x==0&&y==ysize+1)||(x==xsize+1&&y==ysize+1)||x>=xsize+2||y>=ysize+2||x<0||y<0)
		throw new IllegalPlacementException("Das Element "+ name +" ist auf einer illegalen Position des Chips platziert worden. \nDas Programm wird daher beendet");
	
		
	
	
		temp = Positions.get(x+y*(xsize+2));
		//System.out.println(temp.toString());
		if(temp.getType()!=PositionType.EMPTY){
	
			if(subblock==0)
				if(!temp.setSubblock1(name)){
					//System.out.println(temp.toString());
					throw new OverlapException("An der Stelle "+"("+x+","+y+")"+" tritt eine Überlappung auf.\nDas Programm wird beendet");}
				else {
					Positions.set(x+y*(xsize+2),temp);
					ChipElements.put(name,elem);}
			else if(subblock==1)
				if(!temp.setSubblock2(name)){
					//System.out.println(temp.toString());
					throw new OverlapException("An der Stelle "+"("+x+","+y+")"+" tritt eine Überlappung auf.\nDas Programm wird beendet");}
				else {
					Positions.set(x+y*(xsize+2),temp);
					ChipElements.put(name,elem);}}
				
		
	else{
		if(x ==0||x==xsize+1||y==0||y==ysize+1)
		temp = new Position(PositionType.INOUT);
		else
			temp = new Position(PositionType.LOGIC);
		if(subblock == 0)
			temp.setSubblock1(name);
		else if(subblock == 1)
			temp.setSubblock2(name);
	
		Positions.set(x+y*(xsize+2),temp);
			ChipElements.put(name,elem);
			
			
	}
		}
		
	
	
	
	public ArrayList<Position> getPositions(){
		return Positions;
	}
	
	public HashMap<String, ChipElement> getChipElements(){
		return ChipElements;
	}

	


}
