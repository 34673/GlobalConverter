public class StringUtilities{
	public static boolean isAlphabetic(String input){
		for(var character : input.toCharArray()){
			var alphabetic = character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z';
			if(!alphabetic){return false;}
		}
		return true;
	}
	public static boolean isNumeric(String input){
		for(var character : input.toCharArray()){
			var numeric = character >= '0' && character <= '9';
			if(!numeric){return false;}
		}
		return true;
	}
	public static boolean isAlphanumeric(String input){
		return isAlphabetic(input) || isNumeric(input);
	}
}