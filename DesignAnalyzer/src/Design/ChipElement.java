package Design;

import java.util.LinkedList;

public class ChipElement {
	
	public enum ElementType {
		COMBIATIONAL, SEQUENTIAL, INOUT
		
	}
	
private String name;
private ElementType type;
private int blockNr = 0;//default
private int subblock = 0;//default
private LinkedList<String> pinlist;
private LinkedList<String> subblockList;


public ChipElement(){
	
}

public ChipElement (String name, int blockNr, int subblock){
	this.name = name;
	this.blockNr = blockNr;
	this. subblock = subblock;
}

public ChipElement (String name, ElementType type, int blockNr, int subblock, LinkedList<String> pinlist, LinkedList<String> subblockList){
	this.name = name;
	this.type = type;
	this.blockNr = blockNr;
	this. subblock = subblock;
	this.pinlist = new LinkedList<String>();
	this.pinlist.addAll(pinlist);
	if(subblockList!=null){
	this.subblockList = new LinkedList<String>();
	this.subblockList.addAll(subblockList);}
}


public String getName() {
	return name;
}

public ElementType getType() {
	return type;
}

public int getBlockNr() {
	return blockNr;
}

public int getSubblock() {
	return subblock;
}

public LinkedList<String> getPinlist() {
	return pinlist;
}

public LinkedList<String> getSubblockList() {
	return subblockList;
}


public void setName(String name){
	this.name = name;
}

public void setType(ElementType type){
	this.type = type;
}

public void setBlockNr(int blockNr){
	this.blockNr = blockNr;
}

public void setSubblock(int subblock){
	this.subblock = subblock;
}

public void setPinList(LinkedList<String> pinlist){
	 this.pinlist = new LinkedList<String>();
	this.pinlist.addAll(pinlist);
}

public void setSubblockList(LinkedList<String> subblockList){
	this.subblockList  = new LinkedList<String>();
	this.subblockList.addAll(subblockList);
}



@Override
public String toString(){
	StringBuffer buffer = new StringBuffer();
	buffer.append("Name: " + name + "\n");
	buffer.append("Type: " + type + "\n");
	buffer.append("Blockummer: " + blockNr + "\n");
	buffer.append("Subblock: " + subblock + "\n");
	buffer.append("Pinlist: ");
	if(pinlist!=null)
	buffer.append(pinlist.toString());
	buffer.append("\n");
	buffer.append("Subblocklist: ");
	if(subblockList != null)
	buffer.append(subblockList.toString());
	buffer.append("\n");
	
	return buffer.toString();
	
}


public boolean compare(ChipElement elem){
	if(!elem.getName().equals(this.name))
		return false;
	if(elem.getType() != this.type)
		return false;
	if(elem.getBlockNr()!=this.blockNr) //---->nachfragen, wie man Blocknummer einlesen kann
		return false;
	if(elem.getSubblock()!=this.subblock)
		return false;
	if(this.pinlist!=null)
	if(!elem.getPinlist().equals(this.pinlist))
		return false;
	if(this.subblockList!=null)
	if(this.type != ElementType.INOUT)
	if(!elem.getSubblockList().equals(this.subblockList))
		return false;
	return true;
}

}
