package Trash;
public class Hexadecimal implements Convertible{
	public String FromString(String input){
		var array = new char[input.length() * 3];
		for(var index = 0;index < input.length();index += 1){
			var character = input.charAt(index);
			var high = '0' + character / 16;
			var low = '0' + character % 16;
			var alphabeticGap = 0x7;
			array[index * 3] = (char)high;
			array[index * 3 + 1] = low > '9' ? (char)(low + alphabeticGap) : (char)low;
			array[index * 3 + 2] = ' ';
		}
		return new String(array).stripTrailing();
	}
	public String ToString(String input){return "";}
}