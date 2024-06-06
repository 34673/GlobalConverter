import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import Log.*;
public class Options{
	public static Options main = new Options();
	public int inputBase;
	public int outputBase;
	public String message;
	public HashMap<String,BiFunction<String[],Integer,Integer>> parseTable = new HashMap<String,BiFunction<String[],Integer,Integer>>();
	public HashMap<String,Integer> baseTable = new HashMap<String,Integer>();
	public Options(){
		this.parseTable.put("hexadecimal",this::parseBase);
		this.parseTable.put("octal",this::parseBase);
		this.parseTable.put("decimal",this::parseBase);
		this.parseTable.put("binary",this::parseBase);
		this.parseTable.put("text",this::parseBase);
		this.parseTable.put("h",this::parseBase);
		this.parseTable.put("o",this::parseBase);
		this.parseTable.put("d",this::parseBase);
		this.parseTable.put("b",this::parseBase);
		this.parseTable.put("t",this::parseBase);
		this.baseTable.put("hexadecimal",16);
		this.baseTable.put("octal",8);
		this.baseTable.put("decimal",10);
		this.baseTable.put("binary",2);
		this.baseTable.put("text",0);
		this.baseTable.put("h",16);
		this.baseTable.put("o",8);
		this.baseTable.put("d",10);
		this.baseTable.put("b",2);
		this.baseTable.put("t",0);
		this.parseTable.put("test",(a,b)->{
			UnitTests.runAll();
			System.exit(0);
			return Integer.MAX_VALUE;
		});
	}
	public void clear(){
		this.inputBase = 0;
		this.outputBase = 0;
		this.message = "";
	}
	public void parse(String[] arguments){
		var cursor = 0;
		if(arguments.length < 1 || arguments.length < 2 && !arguments[0].equals("test")){
			Log.error("[Options.parse] Insufficient parameters. Usage: GlobalConverter <outputBase> <message> [test]",true);
		}
		while(cursor < arguments.length){
			var argument = arguments[cursor].toLowerCase().replace("-","").trim();
			var method = this.parseTable.get(argument);
			if(method == null){Log.warning("[Options.parse] Skipping unknown argument '"+argument+"'.");}
			cursor = method == null ? cursor + 1 : method.apply(arguments,cursor);
		}
	}
	public int parseBase(String[] arguments,int offset){
		if(arguments.length - offset < 2){
			Log.error("[Options.parseBase] Insufficient parameters. Usage: input <encoding> <message>",true);
		}
		var encoding = arguments[offset].toLowerCase().trim();
		var isNumber = StringUtilities.isNumeric(encoding.replace("-",""));
		var base = isNumber ? (Integer)Integer.parseInt(encoding) : (Integer)baseTable.get(encoding);
		if(base == null){
			var error = new ArrayList<String>();
			error.add("[Options.parseBase] Invalid encoding mode'"+encoding+"'. The following modes are supported:");
			for(var encodingMode : this.baseTable.keySet()){
				if(encodingMode.startsWith("-")){continue;}
				error.add("\n"+encodingMode);
			}
			Log.error(error,true);
		}
		this.outputBase = base;
		var message = arguments[++offset].replace("\"","").trim();
		if(!StringUtilities.isAlphanumeric(message) && !message.contains(" ")){
			Log.error("[Options.parseBase] Input contains invalid characters. Only alphanumeric (ASCII) characters are supported.",true);
		}
		this.inputBase = NumericConverter.detectBase(message);
		this.message = message;
		return offset + 1;
	}
}