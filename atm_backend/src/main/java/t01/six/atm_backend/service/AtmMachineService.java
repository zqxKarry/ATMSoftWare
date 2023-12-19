package t01.six.atm_backend.service;

import t01.six.atm_backend.entity.AtmRecord;
import t01.six.atm_backend.utils.Result;

public interface AtmMachineService {
    public Result<?> checkAtmBalance(String atmId);
    public Result<?> checkAtmPaperCount(String atmId);
    public Result<?> adminAddRMB(Integer addCount,String atmId, String adminId);
    public Result<?> adminTakeRMB(Integer takeCount,String atmId, String adminId);
    public Result<?> adminAddPaper(Integer addPaperCount,String atmId);
    public Result<?> printRecudePaper(String atmId);
    /*增加atm交易记录:随机生成recordid
     *cardid：交易卡号
     *optype: "add" or "reduce"
     *atmId : 交易atm机
     *amount：交易金额
     *adminId: 管理员余额
     */
    public AtmRecord addTransactionRecord(String cardid,String optype,Double amount,String atmId, String adminId,String role,Double balance);
}
