package Design;

public class Position {
	public enum PositionType {
		INOUT,LOGIC,EMPTY
	}

private String subblock1; // Element auf erstem Subblock (0)
private String subblock2; // Element auf zweitem Subblock (1)
private PositionType type;

public Position(PositionType type){
	this.type = type;
}
public boolean setSubblock1(String subblock1){
	if(this.subblock1 == null){
		this.subblock1 = subblock1;
		return true; //Setzen hat funktioniert
	}
	else return false; //Subblock schon besetzt
}

public boolean setSubblock2(String subblock2){
	if(this.subblock2 == null){
		this.subblock2 = subblock2;
		return true; //Setzen hat funktioniert
	}
	else return false; //Subblock schon besetzt
}


public String getSubblock1(){
	return subblock1;
}

public String getSubblock2(){
	return subblock2;
}

public PositionType getType(){
	return type;
}
	
	
public String toString(){
	StringBuffer buffer = new StringBuffer();
	buffer.append("Typ: " + type+"\n");
	buffer.append("Sb1: " + subblock1+"\n");
	buffer.append("Sb2: " + subblock2 + "\n");
	
	return buffer.toString();
}

public boolean compare(Position pos){
	if(this.subblock1==null&&pos.getSubblock1()!=null)
		return false;
	if(this.subblock1!=null&&pos.getSubblock1()==null)
		return false;
	if(this.subblock2==null&&pos.getSubblock2()!=null)
		return false;
	if(this.subblock2!=null&&pos.getSubblock2()==null)
		return false;
	if(this.subblock1!=null)
	if(!this.subblock1.equals(pos.getSubblock1()))
		return false;
	if(this.subblock2!=null)
	if(!this.subblock2.equals(pos.getSubblock2()))
		return false;
	if(!(this.type==pos.getType()))
		return false;
	
	return true;
}
}
