package hr.as2.inf.common.transaction.compensation.facade;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.transaction.compensation.dto.AS2CompensationRs;
import hr.as2.inf.common.transaction.compensation.dto.AS2CompensationStepVo;
import hr.as2.inf.common.transaction.compensation.dto.AS2CompensationVo;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionRs;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionStepVo;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionVo;

public interface AS2TransactionCompensationFacade {
	public abstract AS2TransactionVo beginTxn(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionVo commitTxn(AS2TransactionVo value) throws Exception;	
	public abstract AS2TransactionVo rollbackTxn(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionVo rollbackTxnCTS(AS2TransactionVo value) throws Exception;
	public abstract AS2Record compensateTxn(AS2Record value) throws Exception;//proxy only for client version
	//transactions for GUI administration
    public abstract AS2TransactionRs readAllTransactions(AS2TransactionVo value) throws Exception;
    public abstract AS2TransactionRs readAllTransactionSteps(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionRs searchTransactions(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionVo addTransaction(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionStepVo addTransactionStep(AS2TransactionStepVo value) throws Exception;	
	public abstract AS2TransactionVo deleteTransaction(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionStepVo deleteTransactionStep(AS2TransactionStepVo value) throws Exception;
	public abstract AS2TransactionVo updateTransaction(AS2TransactionVo value) throws Exception;
	public abstract AS2TransactionStepVo updateTransactionStep(AS2TransactionStepVo value) throws Exception;	
	//compensations for GUI administration
    public abstract AS2CompensationRs readAllCompensations(AS2CompensationVo value) throws Exception;
    public abstract AS2CompensationRs readAllCompensationSteps(AS2CompensationVo value) throws Exception;
	public abstract AS2CompensationRs searchCompensations(AS2CompensationVo value) throws Exception;
	public abstract AS2CompensationVo addCompensation(AS2CompensationVo value) throws Exception;
	public abstract AS2CompensationStepVo addCompensationStep(AS2CompensationStepVo value) throws Exception;
	public abstract AS2CompensationVo deleteCompensation(AS2CompensationVo value) throws Exception;
	public abstract AS2CompensationStepVo deleteCompensationStep(AS2CompensationStepVo value) throws Exception;
	public abstract AS2CompensationVo updateCompensation(AS2CompensationVo value) throws Exception;
	public abstract AS2CompensationStepVo updateCompensationStep(AS2CompensationStepVo value) throws Exception;	
}
