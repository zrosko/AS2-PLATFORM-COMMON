package hr.as2.inf.common.transaction.compensation;

/**
 * (C) 2010, Adriacom Software d.o.o. zrosko@gmail.com
 */
public final class AS2ConfigurationCTS {
    /* Position 0 use compensation service (1). 
     * Position 1 transaction type: (1) _SHORT_TRANSACTION, (2) _LONG_TRANSACTION
     * Position 2 cache what: (1) _CACHE_TRANSACTION, (2) _CACHE_COMPENSATION,(3) _CACHE_TRANSACTION_AND_COMPENSATION
     * Position 3 cache where: (1) _CLIENT_LOCATION, (2) _SERVER_LOCACTION, (3)_SERVER_LOCACTION_RDBMS
     * Position 4 client mode for txn: (1) _SINGLE_EXECUTION, (2) _MULTIPLE_EXECUTION
     * Position 5 client mode for cts: (1) _SINGLE_EXECUTION, (2)_MULTIPLE_EXECUTION
     */
    public static String CTS_OPTIONS = "000000"; //DEFAULT do server side
                                                       // short transactions, no
                                                       // cache, no CTS, single
                                                       // execution
    public static boolean useSERVICE() {
        if (CTS_OPTIONS.charAt(0) == '1')
            return true;
        else
            return false;
    }
    public static boolean isSHORTTransaction() {
        if (CTS_OPTIONS.charAt(1) == '1')
            return true;
        else
            return false;
    }
    public static boolean isLONGTransaction() {
        if (CTS_OPTIONS.charAt(1) == '2')
            return true;
        else
            return false;
    }
    public static boolean isCACHETransaction() {
        if (CTS_OPTIONS.charAt(2) == '1')
            return true;
        else {
            if (CTS_OPTIONS.charAt(2) == '3')
                return true;
            else
                return false;
        }
    }
    public static boolean isCACHECompensation() {
        if (CTS_OPTIONS.charAt(2) == '2')
            return true;
        else {
            if (CTS_OPTIONS.charAt(2) == '3')
                return true;
            else
                return false;
        }
    }
    public static boolean isCACHEAtClientLocation() {
        if (CTS_OPTIONS.charAt(3) == '1')
            return true;
        else
            return false;
    }
    public static boolean isCACHEAtServerLocation() {
        if (CTS_OPTIONS.charAt(3) == '2')
            return true;
        else
            return false;
    }
    public static boolean isCACHEAtServerLocationRDBMS() {
        if (CTS_OPTIONS.charAt(3) == '3')
            return true;
        else
            return false;
    }
    public static boolean isTXNxnSigneExecution() {
        if (CTS_OPTIONS.charAt(4) == '1')
            return true;
        else
            return false;
    }
    public static boolean isTXNMultipleExecution() {
        if (CTS_OPTIONS.charAt(4) == '2')
            return true;
        else
            return false;
    }
    public static boolean isCTSSigneExecution() {
        if (CTS_OPTIONS.charAt(5) == '1')
            return true;
        else
            return false;
    }
    public static boolean isCTSMultipleExecution() {
        if (CTS_OPTIONS.charAt(5) == '2')
            return true;
        else
            return false;
    }
}