package hr.as2.inf.common.data.valuelist;

import hr.as2.inf.common.data.AS2Record;

public class AS2ValueListInfo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public final static String HAS_MORE_RESULTS = "@@has_more_results";
	public final static String FORWARD_ACTION = "@@forward_action";
	public final static String BACKWARD_ACTION = "@@backward_action";
	public final static String REFRESH_ACTION = "@@refresh_action";
	public final static String POSITION = "@@POSITION";
	public final static String SIZE = "@@size";
	public final static String MAX = "@@max";
	public final static String TOTAL_ROWS = "@@total_rows";

	public boolean hasMoreResults() {
		return getAsBooleanOrFalse(HAS_MORE_RESULTS);
	}

	public boolean forwardAction() {
		return getAsBooleanOrFalse(FORWARD_ACTION);
	}

	public boolean backwardAction() {
		return getAsBooleanOrFalse(BACKWARD_ACTION);
	}

	public boolean refreshAction() {
		return getAsBooleanOrFalse(REFRESH_ACTION);
	}

	public int getPosition() {
		int position = getAsInt(POSITION);
		if (position == 0)
			return 1;
		else
			return position;
	}

	public int getSize() {
		return getAsInt(SIZE);
	}

	public int getMax() {
		return getAsInt(MAX);
	}
	
	public int getTotalRows() {
		return getAsInt(TOTAL_ROWS);
	}
	
	// setters
	public void setHasMoreResults(boolean value) {
		set(HAS_MORE_RESULTS, value);
	}

	public void setForwardAction(boolean value) {
		set(FORWARD_ACTION, value);
	}

	public void setBackwardAction(boolean value) {
		set(BACKWARD_ACTION, value);
	}

	public void setRefreshAction(boolean value) {
		set(REFRESH_ACTION, value);
	}

	public void setPosition(int value) {
		set(POSITION, value);
	}

	public void setSize(int value) {
		set(SIZE, value);
	}

	public void setMax(int value) {
		set(MAX, value);
	}
	
	public void setTotalRows(int value) {
		set(TOTAL_ROWS, value);
	}

	public void reset() {
		setPosition(0);
		setSize(0);
		setMax(0);
		setTotalRows(0);
		set(HAS_MORE_RESULTS, "false");
		resetActions();
	}

	public void resetActions() {
		set(FORWARD_ACTION, "false");
		set(BACKWARD_ACTION, "false");
		set(REFRESH_ACTION, "false");
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Position=");
		sb.append(getPosition());
		sb.append("Size=");
		sb.append(getSize());
		sb.append("Max=");
		sb.append(getMax());
		sb.append("Total=");
		sb.append(getTotalRows());
		return sb.toString();
	}
}
