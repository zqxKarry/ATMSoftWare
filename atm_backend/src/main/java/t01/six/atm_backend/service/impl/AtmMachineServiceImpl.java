package t01.six.atm_backend.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import t01.six.atm_backend.entity.AtmMachine;
import t01.six.atm_backend.mapper.AtmMachineMapper;
import t01.six.atm_backend.service.AtmMachineService;
import t01.six.atm_backend.utils.Result;

@Service
public class AtmMachineServiceImpl extends ServiceImpl<AtmMachineMapper,AtmMachine> implements AtmMachineService{

    @Resource
    AtmMachineMapper atmMachineMapper;
    @Override
    public Result<?> checkAtmBalance(String atmId) {
        AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(atm==null){
            return Result.error("1","网络不稳定或机器出现故障,请稍后重试或者进行维修");
        }
        else{
            return Result.success(atm);
        }
    }

    @Override
    public Result<?> checkAtmPaperCount(String atmId) {
         AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(atm==null){
            return Result.error("1","网络不稳定或机器出现故障,请稍后重试或者进行维修");
        }
        else{
            return Result.success(atm);
        }
    }

    @Override
    public Result<?> adminAddRMB(Integer addCount, String atmId) {
        AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(atm==null){
            return Result.error("1","网络不稳定或机器出现故障,请稍后重试或者进行维修");
        }
        else{
            if(addCount>(1000-atm.getAtmBalance()/100))
            {
                 return Result.error("2","机箱剩余空间不足,请减少添加数量");
            }
            else
            {
                Double orginBalance = atm.getAtmBalance() + addCount * 100.00;
                atm.setAtmBalance(orginBalance);
                atmMachineMapper.updateById(atm);
                return Result.success(atm);
            }
            
        }
    }

    @Override
    public Result<?> adminTakeRMB(Integer takeCount, String atmId) {
        AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(atm==null){
            return Result.error("1","网络不稳定或机器出现故障,请稍后重试或者进行维修");
        }
        else{
            if(takeCount>atm.getAtmBalance()/100)
            {
                 return Result.error("2","机箱余额不足,请留下一定数量的金额，以便用户存取");
            }
            else
            {
                Double orginBalance = atm.getAtmBalance() - takeCount * 100.00;
                atm.setAtmBalance(orginBalance);
                atmMachineMapper.updateById(atm);
                return Result.success(atm);
            }
            
        }
    }

    @Override
    public Result<?> adminAddPaper(Integer addPaperCount, String atmId) {
       AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(atm==null){
            return Result.error("1","网络不稳定或机器出现故障,请稍后重试或者进行维修");
        }
        else{
            if(addPaperCount>(1000-atm.getAtmPaperCount()))
            {
                 return Result.error("2","纸箱剩余空间不足,请减少添加数量");
            }
            else
            {
                Integer orginCount = atm.getAtmPaperCount() + addPaperCount;
                atm.setAtmPaperCount(orginCount);
                atmMachineMapper.updateById(atm);
                return Result.success(atm);
            } 
        }
    }

    @Override
    public Result<?> printRecudePaper(String atmId) {
        AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(atm==null){
            return Result.error("1","网络不稳定或机器出现故障,请稍后重试或者进行维修");
        }
        else{
            // 打印用掉纸张
            Integer orginCount = atm.getAtmPaperCount() - 1;
            atm.setAtmPaperCount(orginCount);
            atmMachineMapper.updateById(atm);
            return Result.success(atm);
        } 
    }
    
}
