package t01.six.atm_backend.service;

import t01.six.atm_backend.utils.Result;

public interface CardService {

    public Result<?> checkCardId(String cardid);
    public Result<?> checkCardPassword(String cardid,String cardpassword);
    public Result<?> checkBalance(String cardid);
    public Result<?> printVoucher(String recordid);
    public Result<?> userAddRMB(String cardid,Double addAmount,String atmId);
    public Result<?> userTakeRMB(String cardid, Double takeAmount, String atmId);
    public Result<?> getAllCardRecord(String cardid);
    public Result<?> changePassword(String cardid, String newpassword);
    public Result<?> transfer(String cardid1,Double amount,String cardid2);

    /*增加交易记录:随机生成recordid
     *cardid1：本人卡号
     *opway："other" or "atm" ###====本系统上都是"atm"====##
     *optype: "add" or "reduce"
     *cardid2：对方卡号 or "atm"
     *amount：交易金额
     */
    public Result<?> addTransactionRecord(String cardid1,String opway,Double amount,String cardid2);
}