package t01.six.atm_backend.service;

import t01.six.atm_backend.utils.Result;

public interface AdminService {
    public Result<?> checkAdminId(String adminId,String atmId);
    public Result<?> checkAdminPass(String adminId,String atmId,String password);
}
