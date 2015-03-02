package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

public class AS2KomponentaRs extends AS2RecordList {
	private static final long serialVersionUID = 1L;

	public AS2KomponentaRs() {
		super();
	}

	public AS2KomponentaRs(AS2RecordList set) {
		super(); // rows not set inside super constructor
		setColumnNames(set.getColumnNames());
		setColumnSizes(set.getColumnSizes());
		setMetaData(set.getMetaData());
		for (AS2Record row : set.getRows()) {
			AS2KomponentaVo vo = new AS2KomponentaVo(row);
			this.addRow(vo);
		}
	}
}
