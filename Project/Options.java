import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import Encryption.*;
import Log.*;
public class Options{
	public static Options main = new Options();
	public int inputBase;
	public int outputBase;
	public String message;
	public BiFunction<String,String,String> encryptionStep;
	public String key;
	public HashMap<String,BiFunction<String[],Integer,Integer>> parseTable = new HashMap<String,BiFunction<String[],Integer,Integer>>();
	public HashMap<String,Integer> baseTable = new HashMap<String,Integer>();
	public Options(){
		this.parseTable.put("input",this::parseInput);
		this.parseTable.put("output",this::parseOutput);
		this.parseTable.put("encrypt",this::parseEncryption);
		this.parseTable.put("decrypt",this::parseEncryption);
		this.parseTable.put("i",this::parseInput);
		this.parseTable.put("o",this::parseOutput);
		this.parseTable.put("e",this::parseEncryption);
		this.parseTable.put("d",this::parseEncryption);
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
		this.encryptionStep = null;
		this.key = "";
	}
	public void parse(String[] arguments){
		var cursor = 0;
		if(arguments.length < 1 || arguments.length < 2 && !arguments[0].equals("test")){
			Log.error("[Options.parse] Insufficient parameters. Usage: GlobalConverter input <encoding> <message> output <encoding> [encrypt|decrypt] <algorithm> <key> [test]",true);
		}
		while(cursor < arguments.length){
			var argument = arguments[cursor].toLowerCase().replace("-","").trim();
			var method = this.parseTable.get(argument);
			if(method == null){Log.warning("[Options.parse] Skipping unknown argument '"+argument+"'.");}
			cursor = method == null ? cursor + 1 : method.apply(arguments,cursor+1);
		}
	}
	public int parseInput(String[] arguments,int offset){
		if(arguments.length - offset < 2){
			Log.error("[Options.parseInput] Insufficient parameters. Usage: input <encoding> <message>",true);
		}
		var encoding = arguments[offset].toLowerCase().trim();
		var isNumber = StringUtilities.isNumeric(encoding.replace("-",""));
		var base = isNumber ? (Integer)Integer.parseInt(encoding) : (Integer)baseTable.get(encoding);
		if(base == null){
			var error = new ArrayList<String>();
			error.add("[Options.parseInput] Invalid encoding mode'"+encoding+"'. The following modes are supported:");
			for(var encodingMode : this.baseTable.keySet()){
				if(encodingMode.startsWith("-")){continue;}
				error.add("\n"+encodingMode);
			}
			Log.error(error,true);
		}
		this.inputBase = base;
		var message = arguments[++offset].replace("\"","").trim();
		if(!StringUtilities.isAlphanumeric(message) && !message.contains(" ")){
			Log.error("[Options.parseOutput] Input contains invalid characters. Only alphanumeric (ASCII) characters are supported.",true);
		}
		this.message = message;
		return offset + 1;
	}
	public int parseOutput(String[] arguments,int offset){
		if(arguments.length - offset < 1){
			Log.error("[Options.parseOutput] Insufficient parameters. Usage: output <encoding>",true);
		}
		var decoding = arguments[offset].toLowerCase().trim();
		var isNumber = StringUtilities.isNumeric(decoding.replace("-",""));
		if(isNumber){
			this.outputBase = Integer.parseInt(decoding);
			return offset + 1;
		}
		var base = this.baseTable.get(decoding);
		if(base == null) {
			var error = new ArrayList<String>();
			error.add("[Options.parseOutput] Invalid decoding mode'"+decoding+"'. The following modes are supported:");
			for(var decodingMode : this.baseTable.keySet()){
				if(decodingMode.startsWith("-")){continue;}
				error.add("\n"+decodingMode);
			}
			Log.error(error,true);
		}
		this.outputBase = base;
		return offset + 1;
	}
	public int parseEncryption(String[] arguments,int offset){
		var algorithms = new HashMap<String,Encryption>();
		algorithms.put("caesar",new CaesarCipher());
		var encrypting = arguments[offset-1].equals("encrypt");
		if(arguments.length - offset < 2){
			Log.error("[Options.parseEncryption] Insufficient parameters. Usage: [encrypt|decrypt] <algorithm> <key>",true);
		}
		var algorithmName = arguments[offset].replace("--","").toLowerCase().trim();
		var algorithm = algorithms.get(algorithmName);
		if(algorithm == null){
			Log.warning("[Options.parseEncryption] Specified encryption could not be found. Ignoring parameter.");
			return offset + 1;
		}
		this.encryptionStep = encrypting ? algorithm::encrypt : algorithm::decrypt;
		this.key = arguments[++offset];
		return offset + 1;
	}
}