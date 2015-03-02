package hr.as2.inf.common.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AS2List {
	protected List<String> _list = new ArrayList<String>();

	public int size(){
		return _list.size();
	}
	public int frequency(Object value){
		return Collections.frequency(_list, value);
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (String temp : _list) {
			sb.append(temp);
			sb.append(",");
		}
		return sb.toString();
	}
}
