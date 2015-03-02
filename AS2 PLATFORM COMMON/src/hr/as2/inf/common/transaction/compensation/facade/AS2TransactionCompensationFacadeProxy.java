package hr.as2.inf.common.transaction.compensation.facade;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.requesthandlers.AS2FacadeProxy;
import hr.as2.inf.common.transaction.compensation.AS2TransactionAction;
import hr.as2.inf.common.transaction.compensation.dto.AS2CompensationRs;
import hr.as2.inf.common.transaction.compensation.dto.AS2CompensationStepVo;
import hr.as2.inf.common.transaction.compensation.dto.AS2CompensationVo;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionRs;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionStepVo;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionVo;

public class AS2TransactionCompensationFacadeProxy extends AS2FacadeProxy implements AS2TransactionCompensationFacade {
    private static AS2TransactionCompensationFacadeProxy _instance = null;
    private String _component; 
	private AS2TransactionCompensationFacadeProxy(){
		_component = "hr.as2.inf.server.transaction.compensation.facade.AS2TransactionCompensationFacadeServer";
		setRemoteObject(_component); 
	}

	public static AS2TransactionCompensationFacadeProxy getInstance()	{
		if (_instance == null)
			_instance = new AS2TransactionCompensationFacadeProxy();
		return _instance;
	}
	public AS2TransactionVo beginTxn(AS2TransactionVo value) throws AS2Exception	{
	    value.set(AS2Record._TRANSACTION_ACTION, AS2TransactionAction._BEGIN);
		return (AS2TransactionVo) executeCTS(value); 
	}
	public AS2TransactionVo commitTxn(AS2TransactionVo value) throws AS2Exception	{
	    value.set(AS2Record._TRANSACTION_ACTION, AS2TransactionAction._COMMIT);
		return (AS2TransactionVo) executeCTS(value); 
	}
	public AS2TransactionVo rollbackTxn(AS2TransactionVo value) throws AS2Exception	{
	    value.set(AS2Record._TRANSACTION_ACTION, AS2TransactionAction._ROLLBACK);
		return (AS2TransactionVo) executeCTS(value);
	}
	//can be called for completed transaction later
	public AS2TransactionVo rollbackTxnCTS(AS2TransactionVo value) throws AS2Exception	{
	    value.set(AS2Record._TRANSACTION_ACTION, AS2TransactionAction._ROLLBACK);
		return (AS2TransactionVo) executeCTS(value);
	}
	public AS2Record compensateTxn(AS2Record value) throws AS2Exception	{
	    setRemoteObject(null);//call the original facade (component)
		return (AS2Record) execute(value); 
	}
	/** STANDAR METHODS  **/
    public AS2TransactionRs readAllTransactions(AS2TransactionVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionRs) executeQuery(value, "readAllTransactions");
    }

    public AS2TransactionRs readAllTransactionSteps(AS2TransactionVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionRs) executeQuery(value, "readAllTransactionSteps"); 
    }

    public AS2TransactionRs searchTransactions(AS2TransactionVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionRs) executeQuery(value, "searchTransactions"); 
    }

    public AS2TransactionVo addTransaction(AS2TransactionVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionVo) execute(value, "addTransaction");
    }

    public AS2TransactionStepVo addTransactionStep(AS2TransactionStepVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return new AS2TransactionStepVo(execute(value, "addTransactionStep")); 
    }

    public AS2TransactionVo deleteTransaction(AS2TransactionVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionVo) execute(value, "deleteTransaction"); 
    }

    public AS2TransactionStepVo deleteTransactionStep(AS2TransactionStepVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionStepVo) execute(value, "deleteTransactionStep"); 
    }

    public AS2TransactionVo updateTransaction(AS2TransactionVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionVo) execute(value, "updateTransaction");
    }

    public AS2TransactionStepVo updateTransactionStep(AS2TransactionStepVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2TransactionStepVo) execute(value, "updateTransactionStep"); 
    }

    public AS2CompensationRs readAllCompensations(AS2CompensationVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationRs) executeQuery(value, "readAllCompensations");
    }

    public AS2CompensationRs readAllCompensationSteps(AS2CompensationVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationRs) executeQuery(value, "readAllCompensationSteps"); 
    }

    public AS2CompensationRs searchCompensations(AS2CompensationVo value) throws AS2Exception {
        setRemoteObject(_component);
		return (AS2CompensationRs) executeQuery(value, "searchCompensations"); 
    }

    public AS2CompensationVo addCompensation(AS2CompensationVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationVo) execute(value, "addCompensation"); 
    }

    public AS2CompensationStepVo addCompensationStep(AS2CompensationStepVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationStepVo) execute(value, "addCompensationStep"); 
    }

    public AS2CompensationVo deleteCompensation(AS2CompensationVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationVo) execute(value, "deleteCompensation");
    }

    public AS2CompensationStepVo deleteCompensationStep(AS2CompensationStepVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationStepVo) execute(value, "deleteCompensationStep");
    }

    public AS2CompensationVo updateCompensation(AS2CompensationVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationVo) execute(value, "updateCompensation"); 
    }

    public AS2CompensationStepVo updateCompensationStep(AS2CompensationStepVo value) throws AS2Exception {
	    setRemoteObject(_component);
		return (AS2CompensationStepVo) execute(value, "updateCompensationStep");
    }
}
