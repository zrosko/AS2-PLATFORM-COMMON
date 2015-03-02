package hr.as2.inf.common.transaction.compensation.dto;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

public class AS2CompensationStepRs extends AS2RecordList {
	private static final long serialVersionUID = 1L;
	public AS2CompensationStepRs() {
        super();
    }
    public AS2CompensationStepRs(AS2RecordList set) {
        super(); //rows not set inside super constructor
        setColumnNames(set.getColumnNames());
        setColumnSizes(set.getColumnSizes());
        setMetaData(set.getMetaData());
        for(AS2Record row : set.getRows()){
        	AS2CompensationStepVo vo = new AS2CompensationStepVo(row);
            this.addRow(vo);
        }
    }
}