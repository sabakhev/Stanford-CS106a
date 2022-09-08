import java.util.ArrayList;

public class stack {
	private ArrayList <String> list = new ArrayList <String>();
	public void push(String s) {
		list.add(s);
	}

	public String pop() {
		String s = list.get(list.size()-1);
		list.remove(list.size()-1);
		return s;
	}

	public int size() {
		return list.size();
	}
}
