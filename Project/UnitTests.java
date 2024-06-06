import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Log.Log;
public class UnitTests{
	public static List<Test> all = Arrays.asList(
		//Conversion
		new Test("[Convert] Text -> Hexadecimal",new String[]{"hexadecimal","\"Hello World\""},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Convert] Text -> Decimal",new String[]{"decimal","\"Hello World\""},"072 101 108 108 111 032 087 111 114 108 100"),
		new Test("[Convert] Text -> Octal",new String[]{"octal","\"Hello World\""},"110 145 154 154 157 040 127 157 162 154 144"),
		new Test("[Convert] Text -> Binary",new String[]{"binary","\"Hello World\""},"01001000 01100101 01101100 01101100 01101111 00100000 01010111 01101111 01110010 01101100 01100100"),
		new Test("[Convert] Text -> Text",new String[]{"text","\"Hello World\""},"Hello World"),
		new Test("[Convert] Hexadecimal -> Text",new String[]{"text","\"48 65 6C 6C 6F 20 57 6F 72 6C 64\""},"Hello World"),
		new Test("[Convert] Hexadecimal -> Decimal",new String[]{"decimal","\"48 65 6C 6C 6F 20 57 6F 72 6C 64\""},"072 101 108 108 111 032 087 111 114 108 100"),
		new Test("[Convert] Base detection evasion",new String[]{"text","65 65 65 65"},"A A A A"),
		//Command-line
		new Test("[Command-line] Case sensitivity",new String[]{"Hexadecimal","\"Hello World\""},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Command-line] Extra parameters",new String[]{"weewoo","hexadecimal","\"Hello World\""},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		//Unit Tests
		new Test("[Unit Tests] Recursion prevention",new String[]{"Hexadecimal","\"Hello World\"","test"},"48 65 6C 6C 6F 20 57 6F 72 6C 64"),
		new Test("[Unit Tests] Error report",new String[]{"Hexadecimal","\"Hello World\""},"Intentionally wrong.")
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
			Log.error(error);
		}
		UnitTests.running = false;
	}
}