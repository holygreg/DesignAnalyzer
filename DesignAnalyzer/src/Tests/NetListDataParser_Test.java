package Tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import Design.ChipElement;
import Design.ChipElement.ElementType;
import Parser.NetlistDataParser;

public class NetListDataParser_Test {
	static NetlistDataParser parser;
	@BeforeClass
	public static void setup(){
		parser = new NetlistDataParser("C:\\Users\\Gregor\\test\\oj10cyte-ace-prak1\\DesignAnalyzer\\Testdaten\\test_fuer_NetlistParser.net");
	}
	

	@Test
	public void test() {
	HashMap<String,ChipElement> expected = new HashMap<String, ChipElement>();
	HashMap<String,ChipElement> result;
	
	LinkedList<String>pinlist = new LinkedList<String>();
	LinkedList<String>subblocklist = new LinkedList<String>();
	
	pinlist.add("s27_in_2_");
	ChipElement input1 = new ChipElement("s27_in_2_",ElementType.INOUT,0,0, pinlist, subblocklist);
	pinlist.clear();
	
	pinlist.add("s27_out");
	ChipElement output1 = new ChipElement("out:s27_out",ElementType.INOUT,0,0, pinlist, subblocklist);
	pinlist.clear();
	
	pinlist.add("s27_in_3_");
	pinlist.add("n_n41");
	pinlist.add("n_n42");
	pinlist.add("[13]");
	pinlist.add("s27_out");
	pinlist.add("open");
	subblocklist.add("s27_out");
	subblocklist.add("0");
	subblocklist.add("1");
	subblocklist.add("2");
	subblocklist.add("3");
	subblocklist.add("4");
	subblocklist.add("open");
	ChipElement clb1 = new ChipElement("s27_out",ElementType.COMBIATIONAL,0,0, pinlist, subblocklist);
	pinlist.clear();
	subblocklist.clear();
	
	pinlist.add("s27_in_1_");
	pinlist.add("s27_in_3_");
	pinlist.add("[13]");
	pinlist.add("[11]");
	pinlist.add("n_n40");
	pinlist.add("clock");
	subblocklist.add("n_n40");
	subblocklist.add("0");
	subblocklist.add("1");
	subblocklist.add("2");
	subblocklist.add("3");
	subblocklist.add("4");
	subblocklist.add("5");
	ChipElement clb2 = new ChipElement("n_n40",ElementType.SEQUENTIAL,0,0, pinlist, subblocklist);
	pinlist.clear();
	subblocklist.clear();
	
	expected.put(input1.getName(),input1);
	expected.put(output1.getName(),output1);
	expected.put(clb1.getName(),clb1);
	expected.put(clb2.getName(),clb2);
	
	
	
	
	result=parser.getChipElements();
	/*---------------Beide Listen enthalten die gleichen Keys-----------*/
	for(String key : expected.keySet()){
		assertTrue(result.containsKey(key));
	}
	
	for(String key : result.keySet()){
		assertTrue(expected.containsKey(key));
	}
	
	
	/*---------Das Ergbenis enthält die erwarteten Chip-Elements------*/
	for(String key : expected.keySet())
		assertTrue(result.get(key).compare(expected.get(key)));
	

		
	}

}
