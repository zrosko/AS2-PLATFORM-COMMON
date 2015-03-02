package hr.as2.inf.common.transaction.compensation;

/**
 * The class Status defines the values of a transaction status.
 */
public class AS2TransactionAction {
    public final static String _BEGIN = "begin";
    public final static String _STEP = "step";
    public final static String _COMMIT = "commit";
    public final static String _ROLLBACK = "rollback";
    public final static String _ROLLBACK_CTS = "rollback_cts";
}
