package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import Design.ChipElement;
import Design.Position;
import Design.Position.PositionType;
import Exceptions.IllegalPlacementException;
import Exceptions.IllegalSubblockException;
import Exceptions.OverlapException;
import Exceptions.ParseException;
import Parser.PlacementDataParser;

public class PlacementDataParser_Test {
	PlacementDataParser parser;
	
	
	@Test
	public void Correcttest() throws ParseException {
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\test_correct_placementParser.p");
		
		HashMap<String,ChipElement> expectedElems = new HashMap<String,ChipElement>();
		ArrayList<Position> expectedpos = new ArrayList<Position>(25);
		
		HashMap<String,ChipElement> resultElems;
		ArrayList<Position> resultpos;
		
		for(int i = 0;i<25;i++)
			expectedpos.add(new Position(PositionType.EMPTY));
		
		ChipElement elem1 = new ChipElement("s27_in_2_", 0,1);
		ChipElement elem2 = new ChipElement("s27_in_1_", 1,0);
		ChipElement elem3 = new ChipElement("s27_in_3_", 2,0);
		
		expectedElems.put("s27_in_2_",elem1);
		expectedElems.put("s27_in_1_",elem2);
		expectedElems.put("s27_in_3_",elem3);
		
	
		
	    Position pos1 = new Position(PositionType.INOUT);
	    Position pos2 = new Position(PositionType.LOGIC);
	    
	    pos1.setSubblock2("s27_in_2_");
	    pos1.setSubblock1("s27_in_1_");
	    pos2.setSubblock1("s27_in_3_");
	    
	    
	    expectedpos.set(2+0*5,pos1);
	    expectedpos.set(3+1*5,pos2);
	    
	    resultElems = parser.getChipElements();
	    resultpos = parser.getPositions();
	    
		/*---------------Beide Listen enthalten die gleichen Keys-----------*/
	    
		for(String key : expectedElems.keySet()){
			assertTrue(resultElems.containsKey(key));
		}
		
		for(String key : resultElems.keySet())
			assertTrue(expectedElems.containsKey(key));
			
		/*---------Das Ergbenis enthält die erwarteten Chip-Elements------*/
		for(String key : expectedElems.keySet())
			assertTrue(resultElems.get(key).compare(expectedElems.get(key)));
			
			
		assertTrue(expectedpos.size()==resultpos.size()); // Beide Positionslisten haben die gleiche Länge
		
		/*----------------Positionen sind gleich-------------------*/
		for(int i = 0;i<expectedpos.size();i++)
			assertTrue(expectedpos.get(i).compare(resultpos.get(i)));
	    
	    
	    
			
		}
	    
	    
	@Test(expected = OverlapException.class)
	public void OverlapOnSubblockTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\testOverlap.p");
	}
	
	@Test(expected = OverlapException.class)
	public void ThreeElementsOnOnePositionTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\threeOnOne.p");
	}
	
	@Test(expected = IllegalPlacementException.class)
	public void xNegativeTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\xNegative.p");
	}
	
	@Test(expected = IllegalPlacementException.class)
	public void yNegativeTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\yNegative.p");
	}
	
	@Test(expected = IllegalPlacementException.class)
	public void xOutOfChipTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\xOut.p");
	}
	
	@Test(expected = IllegalPlacementException.class)
	public void yOutOfChipTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\yOut.p");
	}
	
	@Test(expected = IllegalSubblockException.class)
	public void subblockOutOfBoundsTest() throws ParseException{
		parser = new PlacementDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\s27-iobsub.p");
	}
		
	}


