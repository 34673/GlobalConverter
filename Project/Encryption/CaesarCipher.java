package Encryption;
public class CaesarCipher extends Encryption{
	public String name = "Caesar";
	public String longName = "Caesar Cipher";
	@Override public String encrypt(String message,String key){
		var characters = message.toCharArray();
		for(var index = 0;index < characters.length;index += 1){
			var alphabetic = characters[index] > 'A' && characters[index] < 'Z' || characters[index] > 'a' && characters[index] < 'z';
			var numeric = characters[index] > '0' && characters[index] < '9';
			if(!alphabetic && !numeric){continue;}
			characters[index] += Integer.parseInt(key);
		}
		return new String(characters);
	}
	@Override public String decrypt(String message,String key){
		key = key.startsWith("-") ? key.replace("-","") : "-"+key;
		return this.encrypt(message,key);
	}
}