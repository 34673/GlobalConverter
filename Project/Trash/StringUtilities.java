package Trash;
public class StringUtilities{
	public static boolean isAlphabetic(Character input){return input >= 'a' && input <= 'z' || input >= 'A' && input <= 'Z';}
	public static boolean isAlphabetic(String input){
		for(var character : input.toCharArray()){
			var alphabetic = StringUtilities.isAlphabetic(character);
			if(!alphabetic){return false;}
		}
		return true;
	}
	public static boolean isNumeric(Character input){return input >= '0' && input <= '9';}
	public static boolean isNumeric(String input){
		for(var character : input.toCharArray()){
			var numeric = StringUtilities.isNumeric(character);
			if(!numeric){return false;}
		}
		return true;
	}
	public static boolean isAlphanumeric(String input){return StringUtilities.isAlphabetic(input) || StringUtilities.isNumeric(input);}
	public static boolean isAlphanumeric(Character input){return StringUtilities.isAlphabetic(input) || StringUtilities.isNumeric(input);}
}