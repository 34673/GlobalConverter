package Log;
import java.io.IOException;
import java.util.List;
public class Log{
	public static String defaultColor = "\033[0m";
	public static String successColor = "\u001B[32m";
	public static String warningColor = "\u001B[33m";
	public static String errorColor = "\u001B[31m";
	public static void info(String message){
		System.out.println(Log.defaultColor+message);
	}
	public static void info(List<String> message){
		var joined = String.join("",message);
		Log.info(joined);
	}
	public static void success(String message){
		System.out.println(Log.successColor+message);
	}
	public static void success(List<String> message){
		var joined = String.join("",message);
		Log.success(joined);
	}
	public static void warning(String message){
		System.out.println(Log.warningColor+message+Log.defaultColor);
	}
	public static void warning(List<String> message){
		var joined = String.join("",message);
		Log.warning(joined);
	}
	public static void error(String message){
		System.out.println(Log.errorColor+message+Log.defaultColor);
	}
	public static void error(String message,boolean isFatal){
		Log.error(message);
		if(!isFatal){return;}
		//Doesn't compile without a try-catch? What the f***?
		try{System.in.read();}
		catch(IOException exception){exception.printStackTrace();}
		System.exit(0);
	}
	public static void error(List<String> message){
		var joined = String.join("",message);
		Log.error(joined);
	}
	public static void error(List<String> message,boolean isFatal){
		var joined = String.join("",message);
		Log.error(joined,isFatal);
	}
}