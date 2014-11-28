package Design;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import Design.ChipElement.ElementType;
import Design.Position.PositionType;
import Exceptions.IllegalSubblockForLogicBlockException;
import Exceptions.InputElementOnLogicPositionException;
import Exceptions.LogicElementOnInputPositionException;
import Exceptions.PositionException;
import Exceptions.ElementNotPlacedException;
import Exceptions.PlacedElementNotDefinedException;

public class ChipPlacementStructure {

	HashMap<String,ChipElement> ChipElements;
	ArrayList<Position> Positions;
	
	HashMap<String,ChipElement> NetListParserResult;
	HashMap<String,ChipElement> PlacementParserResult;
	
	public ChipPlacementStructure(ArrayList<Position> Positions,HashMap<String,ChipElement> NetListParserResult,HashMap<String,ChipElement> PlacementParserResult) throws PositionException{
		this.ChipElements = checkConsistency(NetListParserResult,PlacementParserResult);
		
			checkLegalSubblocks(this.ChipElements);
		
			this.Positions = new ArrayList<Position>(Positions);
		
			checkCorrectPlacement(this.ChipElements, this.Positions);
		
		
		
		
	}

	
	private void checkLegalSubblocks(HashMap<String, ChipElement> ChipElements) throws IllegalSubblockForLogicBlockException {
		for(String key : ChipElements.keySet())
			if(ChipElements.get(key).getType()!=ElementType.INOUT && ChipElements.get(key).getSubblock()!=0)
				throw new IllegalSubblockForLogicBlockException("Das Element "+ChipElements.get(key).getName() + " ist ein logischer Block. Der Subblock muss 0 sein.\nDasProgramm wird beendet.");
		
	}


	private HashMap<String, ChipElement> checkConsistency(HashMap<String, ChipElement> netListParserResult,HashMap<String, ChipElement> placementParserResult) {
		ElementType type;
		LinkedList<String> pinlist = new LinkedList<String>();
		LinkedList<String> subblocklist = new LinkedList<String>();
		/*----------------Prüfe, ob alle in der Netzlistendatei definierten Elemente auch platziert wurden----------*/
		try {
			for(String key : netListParserResult.keySet())
				/*Wenn Element nicht platziert---->Fehler*/
				if(!placementParserResult.containsKey(key))
					throw new ElementNotPlacedException("Das Chip-Element " + key + " wurde nicht in der Platzierungsdatei platziert. \nDas Programm wird daher beendet.");
				/*Ansonsten update die Daten in der Chip-Elements-Map-------*/
				else {
					type = netListParserResult.get(key).getType();
					placementParserResult.get(key).setType(type);
					
					pinlist = netListParserResult.get(key).getPinlist();
					placementParserResult.get(key).setPinList(pinlist);
					
					
					if(type != ElementType.INOUT){
						subblocklist = netListParserResult.get(key).getSubblockList();
						placementParserResult.get(key).setSubblockList(subblocklist);
						
					}
						
				}
		}catch (ElementNotPlacedException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		
		/*--------------------Prüfe, ob alle in der Platzierungsdatei platzierten Elemente auch in der Netzliste definiert worden sind-------------------*/
		
		try {
			for(String key: placementParserResult.keySet())
				if(!netListParserResult.containsKey(key))
					throw new PlacedElementNotDefinedException("Das in der Platzierungsdatei platzierte Element "+ key + " ist nicht in der Netzliste definiert worden. \nDas Programm wird daher beendet.");
		} catch(PlacedElementNotDefinedException e){
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		return placementParserResult;
		
	}

	
	
	
	private void checkCorrectPlacement(HashMap<String,ChipElement> ChipElements,ArrayList<Position> Positions) throws LogicElementOnInputPositionException, InputElementOnLogicPositionException{
		PositionType pos;
		ChipElement elem;
		for(Position p : Positions){
			pos=p.getType();
			
			
			elem = ChipElements.get(p.getSubblock1());
			if(elem!=null){
			if(elem.getType()==ElementType.INOUT && pos == PositionType.LOGIC){
						throw new InputElementOnLogicPositionException("Das Element "+elem.getName()+" vom Typ "+elem.getType()+" ist auf einer Logic-Position platziert worden.\nDas Programm wird daher beendet.");
				}
			
			else if((elem.getType()==ElementType.SEQUENTIAL || elem.getType()==ElementType.COMBIATIONAL) && pos == PositionType.INOUT){
					throw new InputElementOnLogicPositionException("Das Element "+elem.getName()+" vom Typ " +elem.getType()+" ist auf einer INOUT-Position platziert worden.\nDas Programm wird daher beendet.");
			}
			}
			
			elem = ChipElements.get(p.getSubblock2());
			if(elem!=null){
				if(elem.getType()==ElementType.INOUT && pos == PositionType.LOGIC){
						throw new InputElementOnLogicPositionException("Das Element "+elem.getName()+" vom Typ "+elem.getType()+" ist auf einer Logic-Position platziert worden.\nDas Programm wird daher beendet.");
				}
				
			else if((elem.getType()==ElementType.SEQUENTIAL || elem.getType()==ElementType.COMBIATIONAL) && pos == PositionType.INOUT){
					throw new LogicElementOnInputPositionException("Das Element "+elem.getName()+" vom Typ " +elem.getType()+" ist auf einer INOUT-Position platziert worden.\nDas Programm wird daher beendet.");
			}
			}
			
			
			}
			
			
			}
		
		
		
	
	
	
	
	
	public HashMap<String,ChipElement> getChipElements(){
		return ChipElements;
	}
	
	public ArrayList<Position> getPositions(){
		return Positions;
	}
	
	public static void main (String[] args){
		System.out.println("Hallo");
	}
	

}
