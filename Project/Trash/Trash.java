package Trash;
import java.util.Collections;
public class Trash {
	public void Trash(){
		//In a certain distant universe, this is totally logical syntax.
		//Edit: resizable storage only usable as fixed-size. Totally logical semantics.
		var digits = Collections.<Character>emptyList();
	}
	//Sad to see this one go.
	public static String StripNonPrintables(String input){
		var builder = new StringBuilder();
		for(var character : input.toCharArray()){
			if(character < 0x20){continue;}
			builder.append(character);
		}
		return builder.toString();
	}
}