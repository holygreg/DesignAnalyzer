package Parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import Design.ChipElement;
import Design.ChipElement.ElementType;


public class NetlistDataParser extends Parser{
	HashMap<String,ChipElement> ChipElements;
	
	public NetlistDataParser(String path) {
		super(path);
		parseData();
		
	}
	
	private void parseData(){
	String clock = " "; 													   //Name der Clock
	ChipElements = new HashMap<String,ChipElement>(); //speichert ChipElements zwischen, damit später auf gegenseitige Konsistenz mit dem Ergebnis des PlacementParsers geprüft werden kann
	ChipElement tempElement;												   //Wird in die temporäre Map eingefügt
	
	String netlist = readData(this.path);									   //Daten einlesen
	netlist = deleteComments(netlist);					                       //Kommentare entfernen
	StringTokenizer st = new StringTokenizer(netlist, "\n");				   //Eingelesenen String zeilwenweise bearbeiten
	String temp;															   //Speichert Zeile zwischen
	String[] tempArray; 													   //Speichert Wörter einer Zeile zwischen
	
	String Tempname;  														   //Speichert Elementnamen zwischen
	ElementType type;
	LinkedList<String> pinlist = new LinkedList<String>();					   //Speichert Pinliste zwischen
	LinkedList<String> subblocklist = new LinkedList<String>();				   //Speichert Subblockliste zwischen
	
	while(st.hasMoreTokens()){												   //Eingelesenen String zeilenweise durchgehen
		temp = st.nextToken();
		tempArray = temp.split(" ");										   //Zeile in einzelne Wörter auftrennen
		
		/*----------------Uhr einlesen-----------------*/
		if(tempArray[0].equals(".global")){
			clock = tempArray[1];}  										   //Uhr speichern
		
		/*-------------.input/.output einlesen und als ChipElement in temporäre Map speichern-------------------*/
		else if(tempArray[0].equals(".input")||tempArray[0].equals(".output")){			
			Tempname = tempArray[1];
			temp = st.nextToken();
			tempArray = temp.split(" ");
			pinlist= new LinkedList<String>();
			pinlist.add(tempArray[1]);
			type = ElementType.INOUT;
			
			tempElement = new ChipElement();
			tempElement.setName(Tempname);
			tempElement.setType(type);
			tempElement.setPinList(pinlist);
			
			ChipElements.put(Tempname,tempElement);
			pinlist.clear();
		}
		/*-------------.clb einlesen und als ChipElement in temporäre Map speichern-------------------*/
		else if(tempArray[0].equals(".clb")){
			Tempname = tempArray[1];
			temp = st.nextToken();
			tempArray = temp.split(" ");
			
		/*---------------Anhand der clock prüfen, ob Block sequentiell oder kombinatorisch----------*/
			if(tempArray[6].equals(clock))
				type = ElementType.SEQUENTIAL;
			else
				type = ElementType.COMBIATIONAL;
		/*-----------------------------------------------------------------------------------------*/	
			
			
			pinlist.add(tempArray[1]);
			pinlist.add(tempArray[2]);
			pinlist.add(tempArray[3]);
			pinlist.add(tempArray[4]);
			pinlist.add(tempArray[5]);
			pinlist.add(tempArray[6]);
			
			temp = st.nextToken();
			tempArray = temp.split(" ");
			
		
			subblocklist.add(tempArray[1]);
			subblocklist.add(tempArray[2]);
			subblocklist.add(tempArray[3]);
			subblocklist.add(tempArray[4]);
			subblocklist.add(tempArray[5]);
			subblocklist.add(tempArray[6]);
			subblocklist.add(tempArray[7]);
			
			tempElement = new ChipElement();
			tempElement.setName(Tempname);
			tempElement.setType(type);
			tempElement.setPinList(pinlist);
			tempElement.setSubblockList(subblocklist);
			
			ChipElements.put(Tempname,tempElement);
			
			pinlist.clear();
			subblocklist.clear();
		}
		else if(st.hasMoreTokens())
			st.nextToken();
	
	}
	
		

		
	}

	
	
	
	
	
	public HashMap<String,ChipElement> getChipElements(){
		return ChipElements;
	}
	





}
