import Log.*;
public class GlobalConverter{
	public static String output = "";
	public static void main(String[] args){
		var options = Options.main;
		options.parse(args);
		var converter = new NumericConverter(options.inputBase);
		if(options.inputBase == options.outputBase){
			GlobalConverter.output = options.message;
			Log.info(GlobalConverter.output);
			return;
		}
		if(options.inputBase > 0){
			if(!converter.validateInput(options.message)){
				Log.error("[GlobalConverter.main] Input format is invalid. It may contain extra digits?",true);
			}
			GlobalConverter.output = converter.toText(options.message);
		}
		if(options.outputBase > 0){
			converter.base = options.outputBase;
			var message = options.inputBase > 0 ? GlobalConverter.output : options.message;
			GlobalConverter.output = converter.fromText(message);
		}
		Log.info(GlobalConverter.output);
	}
}