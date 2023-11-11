package t01.six.atm_backend.service;

import t01.six.atm_backend.utils.Result;

public interface AtmMachineService {
    public Result<?> checkAtmBalance(String atmId);
    public Result<?> adminAddRMB(Integer addCount,String atmId);
    public Result<?> adminTakeRMB(Integer takeCount,String atmId);
    public Result<?> adminAddPaper(Integer addPaperCount,String atmId);
}
