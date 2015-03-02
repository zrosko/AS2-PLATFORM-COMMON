/*
 * (c) Adriacom Software d.o.o. 22211 Vodice, Croatia Created by Z.Rosko
 * (zrosko@yahoo.com) Date 2010.04.28 Time: 20:02:26
 */
package hr.as2.inf.common.transaction.compensation;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.transaction.compensation.dto.AS2TransactionVo;
import hr.as2.inf.common.transaction.compensation.facade.AS2TransactionCompensationFacadeProxy;
import hr.as2.inf.common.types.AS2Date;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Stack;

public final class AS2TransactionCompensation {
    private Stack<AS2Record> _stepsTXN = new Stack<AS2Record>();
    //private Stack<AS2Record> _stepsCTS = new Stack<AS2Record>();
    private int _status = AS2TransactionStatus.NoTransaction;
    private long _timeOut = 0;
    private long _startTime = 0;
    private long _endTime = 0;
    private String _ipAddress = null;
    private String _userName = null;
    private String _threadName = null;
    private int _step = 0;
    private String _transactionId = null;
    AS2TransactionCompensation() {
        _timeOut = 0;//J2EEContext.getInstance().TXNTIMEOUT;
        _userName = (String) System.getProperties().get("user.name");
        _threadName = Thread.currentThread().getName();
        try {
            InetAddress thisIp = InetAddress.getLocalHost();
            _ipAddress = thisIp.getHostAddress();
        } catch (Exception e) {
        }
        _transactionId = _userName + "-" + _ipAddress + "-" + System.currentTimeMillis();
    }
    public String getTransactionId() {
        return _transactionId;
    }
    public int getStatus() {
        return _status;
    }
    public static void addStep(AS2Record txn_step) throws AS2Exception {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
        if (tran == null)
            return;
        if (txn_step != null && tran.getStatus() == AS2TransactionStatus.Active) {
            AS2Trace.trace(AS2Trace.I, tran, "New step added to the J2EETransactionCompensation.");
            tran._stepsTXN.push(txn_step);
        }
    }
    public static void begin() throws IllegalStateException {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().getJ2EETransaction();
        if (tran.getStatus() != AS2TransactionStatus.NoTransaction)
            throw new IllegalStateException("J2EETransactionCompensation already in progress.");
        tran._status = AS2TransactionStatus.Active;
        tran._startTime = System.currentTimeMillis();
        try {
            if (AS2ConfigurationCTS.isLONGTransaction()) {
                /* Use server side compensation. */
                if (AS2ConfigurationCTS.isCACHEAtClientLocation()) {
                    //do nothing else
                } else {
                    AS2TransactionVo _vo = new AS2TransactionVo();
                    _vo.setTransactionId(tran._transactionId);
                    _vo.setUserName(tran._userName);
                    _vo.setThreadName(tran._threadName);
                    _vo.setSourceAddress(tran._ipAddress);
                    _vo.setTimestamp(AS2Date.getTodayAsCalendar());
                    _vo.setStatus(tran._status + "");
                    AS2TransactionCompensationFacadeProxy.getInstance().beginTxn(_vo);
                }
            }
        } catch (Exception e) {
            System.out.println("J2EETransactionCompensation.begin problem:" + e);
        }
    }
    public static void begin(long timeOut) throws IllegalStateException {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().getJ2EETransaction();
        if (tran.getStatus() != AS2TransactionStatus.NoTransaction)
            throw new IllegalStateException("J2EETransactionCompensation already in progress.");
        tran._timeOut = timeOut;
        begin();//call up begin
    }
    /**
     * Commit all used steps and notify all threads waiting on current
     * transaction object.
     */
    public static void commit() throws IllegalStateException, AS2Exception {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
        if (tran == null || tran.getStatus() != AS2TransactionStatus.Active)
            throw new IllegalStateException("No J2EETransactionCompensation in progress.");
        tran._endTime = System.currentTimeMillis();
        if (((tran._endTime - tran._startTime) > tran._timeOut) && tran._timeOut != 0)
            throw new AS2Exception("453");
        tran._status = AS2TransactionStatus.Committing;
        int steps_done = 0;
        try {
            if (AS2ConfigurationCTS.isLONGTransaction()) {
                /* Use client side compensation. */
                if (AS2ConfigurationCTS.isCACHEAtClientLocation()) {
                    if (AS2ConfigurationCTS.isCTSMultipleExecution()) {
                        Enumeration<AS2Record> E = tran._stepsTXN.elements();
                        while (E.hasMoreElements()) {
                            AS2Record txn_step = E.nextElement();
                            steps_done = steps_done+1;
                            AS2TransactionCompensationFacadeProxy.getInstance().compensateTxn(txn_step);
                        }
                    } else if (AS2ConfigurationCTS.isCTSSigneExecution()) {
                        //TODO result set
                        // J2EETransactionCompensationFacadeProxy.getInstance().compensateTxn(_stepsTXN);
                    }
                } else {
                    AS2TransactionVo _vo = new AS2TransactionVo();
                    _vo.setTransactionId(tran._transactionId);
                    _vo.setUserName(tran._userName);
                    _vo.setThreadName(tran._threadName);
                    _vo.setSourceAddress(tran._ipAddress);
                    _vo.setEndTimestamp(AS2Date.getTodayAsCalendar());
                    _vo.setStatus(tran._status + "");
                    AS2TransactionCompensationFacadeProxy.getInstance().commitTxn(_vo);
                }
            }
        } catch (Exception e) {
            AS2Trace.trace(AS2Trace.E, e, "Have to rollback J2EETransactionCompensation");
            AS2Exception exc = new AS2Exception("453");
            exc.setTechnicalErrorDescription("Failed to commit compensating transaction at step:" + steps_done + " of:" + tran._step);
            throw new AS2Exception("451");
        } finally {
            tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
            if (tran != null) {
                synchronized (tran) {
                    tran._status = AS2TransactionStatus.Committed;
                    AS2TransactionCompensation.releaseAllSteps();
                    tran.notifyAll();
                }
                AS2TransactionCompensationFactory.getInstance().deleteTransaction();
            }
        }
    }
    public void finalize() {
        try {
            if (_status != AS2TransactionStatus.Committed && _status != AS2TransactionStatus.RolledBack)
                releaseAllSteps();
        } catch (Exception e) {
            AS2Trace.trace(AS2Trace.E, e, "J2EETransactionCompensation.finalize error");
        }
    }
    public static AS2Record getStepByName(String name) {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
        if (tran == null || tran.getStatus() != AS2TransactionStatus.Active) {
            AS2Trace.trace(AS2Trace.E, tran, "No J2EETransactionCompensation in progress.");
            throw new IllegalStateException("No J2EETransactionCompensation in progress.");
        }
        AS2Record txn_step = null;
        if (!tran._stepsTXN.empty()) {
            Enumeration<AS2Record> E = tran._stepsTXN.elements();
            while (E.hasMoreElements()) {
                txn_step = E.nextElement();
                if (txn_step.getComponentAndService().equals(name))
                    return txn_step;
                else
                    txn_step = null;
            }
        }
        return txn_step;
    }
    public static void prepareTxn() throws IllegalStateException {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().getJ2EETransaction();
        if (tran.getStatus() != AS2TransactionStatus.NoTransaction)
            throw new IllegalStateException("J2EETransactionCompensation already in progress.");
        tran._status = AS2TransactionStatus.Active;
    }
    public static void releaseAllSteps() throws AS2Exception {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
        while (tran != null && !tran._stepsTXN.empty()) {
            AS2Record txn_step = (AS2Record) tran._stepsTXN.pop();
            System.out.println(txn_step);
            txn_step = null;
        }
    }
    /**
     * Delete the transaction object.
     */
    public static void releaseTxn() throws IllegalStateException, AS2Exception {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
        if (tran == null || tran.getStatus() != AS2TransactionStatus.Active)
            throw new IllegalStateException("No J2EETransactionCompensation in progress.");
        tran._status = AS2TransactionStatus.Committed;
        AS2TransactionCompensationFactory.getInstance().deleteTransaction();
    }
    /**
     * Rollback all used steps and notify all threads waiting on current
     * transaction object.
     */
    public static void rollback() throws IllegalStateException, AS2Exception {
        AS2TransactionCompensation tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
        if (tran == null)
            throw new IllegalStateException("No J2EETransactionCompensation in progress.");
        tran._endTime = System.currentTimeMillis();
        if (((tran._endTime - tran._startTime) > tran._timeOut) && tran._timeOut != 0)
            throw new AS2Exception("453");
        int status = tran.getStatus();
        int steps_done = 0;
        try {
            if (status != AS2TransactionStatus.Active && status != AS2TransactionStatus.Committing)
                throw new IllegalStateException("No J2EETransactionCompensation in progress.");
            tran._status = AS2TransactionStatus.RollingBack;
            if (AS2ConfigurationCTS.isLONGTransaction()) {
                /* Use client side compensation. */
                if (AS2ConfigurationCTS.isCACHEAtClientLocation()) {
                    if (AS2ConfigurationCTS.isCTSMultipleExecution()) {
                        while (tran != null && !tran._stepsTXN.empty()) {
                            AS2Record txn_step = (AS2Record) tran._stepsTXN.pop();
                            steps_done = steps_done+1;
                            AS2TransactionCompensationFacadeProxy.getInstance().compensateTxn(txn_step);
                            txn_step = null;
                        }
                    }
                } else { //send to server
                    AS2TransactionVo _vo = new AS2TransactionVo();
                    _vo.setTransactionId(tran._transactionId);
                    _vo.setUserName(tran._userName);
                    _vo.setThreadName(tran._threadName);
                    _vo.setSourceAddress(tran._ipAddress);
                    _vo.setEndTimestamp(AS2Date.getTodayAsCalendar());
                    _vo.setStatus(tran._status + "");
                    AS2TransactionCompensationFacadeProxy.getInstance().rollbackTxn(_vo);
                }
            }
        } catch (Exception e) {
            AS2Exception exc = new AS2Exception("453");
            exc.setTechnicalErrorDescription("Failed to rollback compensating transaction at step:" + steps_done + " of:" + tran._step);
            throw exc;
        } finally {
            tran = AS2TransactionCompensationFactory.getInstance().currentTransaction();
            if (tran != null) {
                synchronized (tran) {
                    tran._status = AS2TransactionStatus.RolledBack;
                    AS2TransactionCompensation.releaseAllSteps();
                    tran.notifyAll();
                }
                AS2TransactionCompensationFactory.getInstance().deleteTransaction();
            }
        }
    }
}