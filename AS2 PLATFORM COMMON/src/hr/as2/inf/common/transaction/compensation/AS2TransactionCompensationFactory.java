/*
 * (c) Adriacom Software d.o.o. 22211 Vodice, Croatia Created by Z.Rosko
 * (zrosko@yahoo.com) Date 2010.04.28 Time: 20:02:26
 */
package hr.as2.inf.common.transaction.compensation;

import hr.as2.inf.common.core.AS2Context;

import java.util.Hashtable;

/* Using current user instead of current thread (client side only). */
/* zrosko@gmail.com */
public final class AS2TransactionCompensationFactory {
    private static AS2TransactionCompensationFactory _instance = null;
    private Hashtable _transactions;
    private AS2TransactionCompensationFactory() {
        _transactions = new Hashtable(200);
        AS2Context.setSingletonReference(this);
        try {
            /**/
        } catch (Exception e) {
            System.out.println("Can not initialize Context:" + e);
        }
    }
    public static AS2TransactionCompensationFactory getInstance() {
        if (_instance == null)
            _instance = new AS2TransactionCompensationFactory();
        return _instance;
    }
    public AS2TransactionCompensation currentTransaction() {
        String currentUser = (String) System.getProperties().get("user.name");
        AS2TransactionCompensation ct = (AS2TransactionCompensation) _transactions.get(currentUser);
        return ct;
    }
    public void deleteTransaction() {
        String currentUser = (String) System.getProperties().get("user.name");
        _transactions.remove(currentUser);
    }
    public AS2TransactionCompensation getJ2EETransaction() {
        String currentUser = (String) System.getProperties().get("user.name");
        AS2TransactionCompensation ct = (AS2TransactionCompensation) _transactions.get(currentUser);
        if (ct == null) {
            ct = new AS2TransactionCompensation();
            _transactions.put(currentUser, ct);
        }
        return ct;
    } 
}