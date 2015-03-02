package hr.as2.inf.common.admin.facade;

import hr.as2.inf.common.admin.dto.AS2ConfigurationVo;
import hr.as2.inf.common.admin.dto.AS2ConnectionJdbcVo;
import hr.as2.inf.common.admin.dto.AS2ConnectionVo;
import hr.as2.inf.common.admin.dto.AS2EmailSetupVo;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

public interface AS2ServerAdminFacade {
    public abstract AS2ConfigurationVo getConfiguration(AS2Record  req) throws Exception;
    public abstract AS2Record setConfiguration(AS2ConfigurationVo req) throws Exception;      
    public abstract AS2EmailSetupVo getMailProperties(AS2Record req) throws Exception;
    public abstract AS2Record setMailProperties(AS2EmailSetupVo req) throws Exception; 
    public abstract AS2ConnectionJdbcVo getDefaultJdbcConfiguration(AS2Record req) throws Exception;
    public abstract AS2Record setDefaultJdbcConfiguration(AS2ConnectionJdbcVo req) throws Exception;
    public abstract AS2ConnectionVo getDefaultAs400Configuration(AS2Record req) throws Exception;
    public abstract AS2Record setDefaultAs400Configuration(AS2ConnectionVo req) throws Exception; 
    public abstract AS2RecordList getServerExceptions(AS2Record req) throws Exception;
    public abstract AS2RecordList getStatistics(AS2Record req) throws Exception;
    public abstract AS2RecordList getExceptionDetailsForId(AS2Record req) throws Exception;
    public abstract AS2RecordList getExceptionsForId(AS2Record req) throws Exception;
    public abstract AS2RecordList getSQLQueryResults(AS2Record  req) throws Exception;
    public abstract AS2Record startSQLJob(AS2Record req) throws Exception;
}
