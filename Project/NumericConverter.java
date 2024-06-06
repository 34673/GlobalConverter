public class NumericConverter{
	public int base = 10;
	public NumericConverter(int base){
		this.base = base;
	}
	public Boolean validateInput(String input){
		var split = input.split(" ");
		var maxDigits = (int)Math.ceil(Math.log(256) / Math.log(this.base));
		for(var item : split){
			if(item.length() > maxDigits){return false;}
		}
		return true;
	}
	public String fromText(String input){
		var builder = new StringBuilder();
		var digits = new StringBuilder();
		var maxDigits = (int)Math.ceil(Math.log(256) / Math.log(this.base));
		for(var character : input.toCharArray()){
			for(;character > 0;character /= this.base){
				var converted = (char)('0' + character % this.base);
				digits.insert(0,converted);
			}
			if(maxDigits - digits.length() > 0){
				digits.insert(0,"0".repeat(maxDigits - digits.length()));
			}
			builder.append(digits.append(' '));
			digits.setLength(0);
		}
		for(var index = 0;index < builder.length();index += 1){
			var character = builder.charAt(index);
			var alphabeticGap = 0x7;
			if(character <= '9'){continue;}
			builder.setCharAt(index,(char)(character + alphabeticGap));
		}
		return builder.toString().trim();
	}
	public String toText(String input){
		var split = input.split(" ");
		var builder = new StringBuilder();
		for(var item : split){
			var value = 0;
			for(var character : item.toCharArray()){
				value += character > '9' ? character - 'A' + 10 : character - '0';
				value *= this.base;
			}
			if(value != 0){value /= this.base;}
			builder.append((char)value);
		}
		return builder.toString().trim();
	}
}