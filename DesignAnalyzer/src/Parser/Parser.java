package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Parser {

protected String path;


public Parser(String path){
	this.path = path;
}
	
	
protected String readData(String path){/*Liest Dateien ein, selbsterkl�rend*/
	StringBuffer buffer = new StringBuffer();
	try {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String zeile;
		try {
			zeile = reader.readLine();
			 while (zeile != null)
			    {
			      buffer.append(zeile + "\n");
			      zeile = reader.readLine();
			      
			    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
	  
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return buffer.toString();
	
}
	
protected String deleteComments(String text){								//L�scht eventuelle Kommentare
	StringBuffer buffer = new StringBuffer();								//Ben�tigt, um R�ckgabestring zu erzeugen
	StringTokenizer st = new StringTokenizer(text, "\n");					//Teilt String in Zeilen
	String temp; 															//Tempor�rer String, der die aktuelle Zeile speichert
	
	while ( st.hasMoreTokens() )											//F�r jede Zeile
	{
	temp = st.nextToken();
		if(temp.indexOf("#")!=-1){
		buffer.append(temp.substring(0,temp.indexOf("#")));
		buffer.append("\n"); }												//Entfernt die Kommentare in jeder Zeile 
		else{
		buffer.append(temp);
		buffer.append("\n");}									//Falls keine Kommentare in einer Zeile vorhanden sind, wird diese einfach 1:1 �bernommen
	
	}
	temp = buffer.toString().replaceAll("  [ ]*"," ");  //Ausgabestring aus Buffer erzeugen und �berfl�ssige Leerzeichen entfernen (mithilfe von regul�rem Ausdruck).
	temp = temp.replaceAll("	[	]*"," ");      	//�berfl�ssige Tabulatoren entfernen
	return temp;
}








	
	
}
