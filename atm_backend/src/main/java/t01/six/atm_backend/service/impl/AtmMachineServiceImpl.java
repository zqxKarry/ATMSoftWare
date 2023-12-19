package t01.six.atm_backend.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import t01.six.atm_backend.entity.AtmMachine;
import t01.six.atm_backend.entity.AtmRecord;
import t01.six.atm_backend.mapper.AtmMachineMapper;
import t01.six.atm_backend.mapper.AtmRecordMapper;
import t01.six.atm_backend.service.AtmMachineService;
import t01.six.atm_backend.utils.Result;

@Service
public class AtmMachineServiceImpl extends ServiceImpl<AtmMachineMapper,AtmMachine> implements AtmMachineService{

    @Resource
    AtmMachineMapper atmMachineMapper;
    @Resource
    AtmRecordMapper atmRecordMapper;

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
    public Result<?> adminAddRMB(Integer addCount, String atmId,String adminId) {
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
                addTransactionRecord("", "add",addCount * 100.00, atmId,adminId, "admin", orginBalance);
                return Result.success(atm);
            }
            
        }
    }

    @Override
    public Result<?> adminTakeRMB(Integer takeCount, String atmId,String adminId) {
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
                addTransactionRecord("", "take",takeCount * 100.00, atmId,adminId, "admin", orginBalance);
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

    @Override
    public AtmRecord addTransactionRecord(String cardid, String optype, Double amount, String atmId, String adminId,String role, Double balance) {
        String recordid =  generateSerialNumber(atmId);
        AtmRecord newRecord  = atmRecordMapper.selectById(recordid);
        while(newRecord!=null){
            // 如果有重复，继续随机生成
            recordid =  generateSerialNumber(atmId);
            newRecord  = atmRecordMapper.selectById(recordid);
        }
        newRecord = new AtmRecord();
        // 订单号确定
        newRecord.setRecordId(recordid);
        newRecord.setCardId(cardid);
        newRecord.setAtmId(atmId);
        newRecord.setAdminId(adminId);
        newRecord.setAmount(amount);
        newRecord.setOpType(optype);
        newRecord.setRole(role);
        newRecord.setTime(LocalDateTime.now());
        newRecord.setBalance(balance);
        atmRecordMapper.save(newRecord);
        return newRecord;
    }

    public static String generateSerialNumber(String atmId) {
        // 机器号后5位+随机生成5位+时间戳前10位
        String truncatedMachineNumber = atmId.substring(atmId.length() - 5);
        String randomOrderNumber = String.format("%05d", new Random().nextInt(100000));
        String timestamp = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        String truncatedTimestamp = timestamp.substring(0, 10);
        String serialNumber = truncatedMachineNumber + randomOrderNumber + truncatedTimestamp;
        return serialNumber;
    }
    
}
