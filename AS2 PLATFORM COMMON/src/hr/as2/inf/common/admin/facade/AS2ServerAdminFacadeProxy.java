package hr.as2.inf.common.admin.facade;

import hr.as2.inf.common.admin.dto.AS2ConfigurationVo;
import hr.as2.inf.common.admin.dto.AS2ConnectionJdbcVo;
import hr.as2.inf.common.admin.dto.AS2ConnectionVo;
import hr.as2.inf.common.admin.dto.AS2EmailSetupVo;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.requesthandlers.AS2FacadeProxy;

public class AS2ServerAdminFacadeProxy extends AS2FacadeProxy implements AS2ServerAdminFacade {
    private static AS2ServerAdminFacadeProxy _instance = null;
private AS2ServerAdminFacadeProxy() {
    setRemoteObject("hr.as2.inf.server.admin.facade.AS2ServerAdministrationFacadeServer");
}
public static AS2ServerAdminFacadeProxy getInstance() {
    if (_instance == null)
        _instance = new AS2ServerAdminFacadeProxy();
    return _instance;
}
public AS2Record setConfiguration(AS2ConfigurationVo req) throws AS2Exception {
    return execute(req, "setConfiguration");
}
public AS2EmailSetupVo getMailProperties(AS2Record req) throws AS2Exception {
    return (AS2EmailSetupVo)execute(req, "getMailProperties");
}
public AS2Record setMailProperties(AS2EmailSetupVo req) throws AS2Exception {
    return execute(req, "setMailProperties");
}
public AS2RecordList getStatistics(AS2Record req) throws AS2Exception {
    return executeQuery(req, "getStatistics");
}
public AS2ConfigurationVo getConfiguration(AS2Record req) throws AS2Exception {
    return (AS2ConfigurationVo)execute(req, "getConfiguration");
}
public AS2ConnectionJdbcVo getDefaultJdbcConfiguration(AS2Record req) throws AS2Exception {
    return (AS2ConnectionJdbcVo)execute(req, "getDefaultJdbcConfiguration");
}
public AS2ConnectionVo getDefaultAs400Configuration(AS2Record req) throws AS2Exception {
    return (AS2ConnectionVo)execute(req, "getDefaultAs400Configuration");
}
public AS2RecordList getServerExceptions(AS2Record req) throws AS2Exception {
    return executeQuery(req, "getServerExceptions");
}
public AS2Record setDefaultJdbcConfiguration(AS2ConnectionJdbcVo req) throws AS2Exception {
    return execute(req, "setDefaultJdbcConfiguration");
}
public AS2Record setDefaultAs400Configuration(AS2ConnectionVo req) throws AS2Exception {
    return execute(req, "setDefaultAs400Configuration");
}
public AS2RecordList getExceptionDetailsForId(AS2Record req) throws AS2Exception {
    return executeQuery(req, "getExceptionDetailsForId");
}
public AS2RecordList getExceptionsForId(AS2Record req) throws AS2Exception {
    return executeQuery(req, "getExceptionsForId");
}
public AS2RecordList getSQLQueryResults(AS2Record req) throws AS2Exception {
    return executeQuery(req, "getSQLQueryResults");
}
public AS2Record startSQLJob(AS2Record req) throws AS2Exception {
	return execute(req, "startSQLJob");
}
}
