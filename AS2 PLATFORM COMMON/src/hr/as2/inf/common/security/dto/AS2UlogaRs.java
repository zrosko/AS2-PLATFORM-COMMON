package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

public class AS2UlogaRs extends AS2RecordList {

	private static final long serialVersionUID = 1L;

	public AS2UlogaRs() {
		super();
	}

	/**
	 * @param set
	 */
	/* constructor comment version 0.01 */
	public AS2UlogaRs(AS2RecordList set) {
		super(); // rows not set inside super constructor
		setColumnNames(set.getColumnNames());
		setColumnSizes(set.getColumnSizes());
		setMetaData(set.getMetaData());
		for (AS2Record row : set.getRows()) {
			AS2UlogaVo vo = new AS2UlogaVo(row);
			this.addRow(vo);
		}
	}
}
