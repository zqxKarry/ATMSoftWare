package t01.six.atm_backend.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import t01.six.atm_backend.entity.Admin;
import t01.six.atm_backend.entity.AdminAtm;
import t01.six.atm_backend.mapper.AdminAtmMapper;
import t01.six.atm_backend.mapper.AdminMapper;
import t01.six.atm_backend.service.AdminService;
import t01.six.atm_backend.utils.Result;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService{

    @Resource
    AdminAtmMapper adminatmMapper;
    @Resource
    AdminMapper adminMapper;

    @Override
    public Result<?> checkAdminId(String adminId,String atmId)
    {
        System.out.println(adminId);
        Admin admin = adminMapper.selectById(adminId);
        if(admin == null)
        {
            // 员工号错误
            return Result.error("1","错误员工号，系统中没有此员工号" );
        }
        else
        {
            if(admin.getNum()==0)
            {
                return Result.error("3", "账号存在,已锁定");
            }
            else{
               AdminAtm adt = adminatmMapper.findByAdminIdAndAtmId(adminId, atmId);
               // 员工号存在，但是对此台机器没有管理权限
               if(adt==null)
               {
                 return Result.error("2", "此员工号对此台机器无管理权限");
               }  
            }
           
        }
        // 存在且有权限
        return Result.success();
    }

    @Override
    public Result<?> checkAdminPass(String adminId, String atmId, String adminPass) {
        Admin admin = adminMapper.selectById(adminId);
        // System.out.println("pass:"+adminPass);
        // System.out.println("apass:"+admin.getAdminPass());
        if(admin.getAdminPass().equals(adminPass))
        {
            //登录成功之后,次数恢复到3，不成功的话，除非解锁，不可能变成3
            admin.setNum(3);
            adminMapper.updateById(admin);
            return Result.success(admin);
        }
        else
        {
            Integer num = admin.getNum();
            num--;
            admin.setNum(num);
            adminMapper.updateById(admin);
            Result<Admin> result = new Result<>();
            result.setCode("1");
            result.setMsg("密码错误!");
            result.setData(admin);
            return result;
        }
    }   
}
