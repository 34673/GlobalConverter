import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Log.Log;
public class UnitTests{
	public static List<Test> all = Arrays.asList(
		//Conversion
		new Test("[Convert] Text -> Hexadecimal",new String[]{"input","text","\"Hello World\"","output","hexadecimal"},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Convert] Text -> Decimal",new String[]{"input","text","\"Hello World\"","output","decimal"},"072 101 108 108 111 032 087 111 114 108 100"),
		new Test("[Convert] Text -> Octal",new String[]{"input","text","\"Hello World\"","output","octal"},"110 145 154 154 157 040 127 157 162 154 144"),
		new Test("[Convert] Text -> Binary",new String[]{"input","text","\"Hello World\"","output","binary"},"01001000 01100101 01101100 01101100 01101111 00100000 01010111 01101111 01110010 01101100 01100100"),
		new Test("[Convert] Text -> Text",new String[]{"input","text","\"Hello World\"","output","text"},"Hello World"),
		new Test("[Convert] Text -> Arbitrary",new String[]{"input","text","\"Hello World\"","output","3"},"002200 010202 011000 011000 011010 001012 010020 011010 011020 011000 010201"),
		new Test("[Convert] Hexadecimal -> Decimal",new String[]{"input","hexadecimal","\"48 65 6C 6C 6F 20 57 6F 72 6C 64\"","output","decimal"},"072 101 108 108 111 032 087 111 114 108 100"),
		new Test("[Convert] Hexadecimal -> Text",new String[]{"input","hexadecimal","\"48 65 6C 6C 6F 20 57 6F 72 6C 64\"","output","text"},"Hello World"),
		new Test("[Convert] Arbitrary -> Text",new String[]{"input","3","\"002200 010202 011000 011000 011010 001012 010020 011010 011020 011000 010201\"","output","text"},"Hello World"),
		new Test("[Convert] Base detection evasion",new String[]{"input","decimal","\"65 32 65 32 65 32 65\"","output","text"},"A A A A"),
		//Encryption
		new Test("[Encryption] Caesar Cipher encryption",new String[]{"input","text","\"Hello World\"","output","text","encrypt","caesar","2"},"Jgnnq Yqtnf"),
		new Test("[Encryption] Caesar Cipher decryption",new String[]{"input","text","\"Jgnnq Yqtnf\"","output","text","decrypt","caesar","2"},"Hello World"),
		new Test("[Encryption] Unknown algorithm",new String[]{"input","text","\"Hello World\"","output","text","encrypt","ceasar","2"},"Hello World"),
		new Test("[Encryption] Non-text encryption prevention",new String[]{"input","hexadecimal","\"48 65 6C 6C 6F 20 57 6F 72 6C 64\"","output","hexadecimal","encrypt","caesar","2"},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		//Command-line
		new Test("[Command-line] Case sensitivity",new String[]{"iNpUt","TeXt","\"Hello World\"","OuTpUt","HeXaDeCiMal"},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Command-line] Out of order",new String[]{"output","hexadecimal","input","text","\"Hello World\""},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Command-line] Extra parameters",new String[]{"input","text","\"Hello World\"","wee","output","hexadecimal","woo"},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		//Unit Tests
		new Test("[Unit Tests] Recursion prevention",new String[]{"input","text","\"Hello World\"","output","hexadecimal","test"},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Unit Tests] Error report",new String[]{"input","text","\"Jgnnq Yqtnf\"","output","text","decrypt","caesar","2"},"Intentionally wrong.")
	);
	public static boolean running;
	public static class Test{
		public String name;
		public String[] arguments;
		public String expectedResult;
		public Test(String name,String[] arguments,String expectedResult){
			this.name = name;
			this.arguments = arguments;
			this.expectedResult = expectedResult;
		}
		public boolean run(){
			Log.info("Beginning test '"+this.name+"'");
			if(UnitTests.running){
				for(var index = 0;index < this.arguments.length;index += 1){
					if(!this.arguments[index].equals("test")){continue;}
					Log.warning("[Test.run()] Ignoring 'test' argument to prevent recursion.");
					this.arguments[index] = "";
				}
			}
			GlobalConverter.main(this.arguments);
			return GlobalConverter.output.equals(this.expectedResult);
		}
	}
	public static void runAll(){
		UnitTests.running = true;
		for(var test : UnitTests.all){
			Options.main.clear();
			var success = test.run();
			if(success){
				Log.success("'"+test.name+"' ran successfully.");
				continue;
			}
			var error = new ArrayList<String>();
			error.add("'"+test.name+"' failed.\n");
			error.add("Arguments: '"+String.join(" ",test.arguments).trim()+"'\n");
			error.add("Result: '"+GlobalConverter.output+"'\n");
			error.add("Expected: '"+test.expectedResult+"'\n");
			error.add("Options: \n");
			error.add("    Input Base: "+Options.main.inputBase+"\n");
			error.add("    Output Base: "+Options.main.outputBase+"\n");
			error.add("    Key: '"+Options.main.key+"'");
			Log.error(error);
		}
		UnitTests.running = false;
	}
}