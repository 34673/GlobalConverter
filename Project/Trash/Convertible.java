package Trash;
public interface Convertible{
	public String FromString(String input);
	public default String FromString(String input,String key){return "";}
	public String ToString(String input);
	public default String ToString(String input, String key){return "";}
}