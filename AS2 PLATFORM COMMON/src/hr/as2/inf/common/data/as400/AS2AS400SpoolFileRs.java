package hr.as2.inf.common.data.as400;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

import java.util.Iterator;

public class AS2AS400SpoolFileRs extends AS2RecordList {
	private static final long serialVersionUID = 1L;
	public AS2AS400SpoolFileRs() {
        super();
    }
    public AS2AS400SpoolFileRs(AS2RecordList set) {
        super(); //rows not set inside super constructor
        setColumnNames(set.getColumnNames());
        setColumnSizes(set.getColumnSizes());
        setMetaData(set.getMetaData());        
        Iterator<AS2Record> it = set.getRows().iterator();
		while (it.hasNext()) {
			AS2Record row = it.next();
			AS2AS400SpoolFileVo vo = new AS2AS400SpoolFileVo(row);
			this.addRow(vo);
		}        
    }
}