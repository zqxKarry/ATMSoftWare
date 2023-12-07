package t01.six.atm_backend.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import t01.six.atm_backend.entity.AtmMachine;
import t01.six.atm_backend.entity.Card;
import t01.six.atm_backend.entity.CardRecord;
import t01.six.atm_backend.mapper.AtmMachineMapper;
import t01.six.atm_backend.mapper.CardMapper;
import t01.six.atm_backend.mapper.CardRecordMapper;
import t01.six.atm_backend.service.CardService;
import t01.six.atm_backend.utils.Result;

@Service
public class CardServiceImpl implements CardService{

    @Resource
    CardMapper cardMapper;
    @Resource
    AtmMachineMapper atmMachineMapper;
    @Resource
    CardRecordMapper cardRecordMapper;

    @Override
    public Result<?> checkCardId(String cardid) {
        Card card = cardMapper.selectById(cardid);
        if(card==null){
            return Result.error("1","此卡不是本行银行卡,已退卡");
        }
        else{
            Integer num = card.getNum();
            if(num<=0){
                return Result.error("2", "密码连续错误三次,此卡已被锁定,请到柜台解锁");
            }
            else{
                return Result.success();
            }
        }
    }

    @Override
    public Result<?> checkCardPassword(String cardid, String cardpassword) {
        Card card = cardMapper.selectById(cardid);
        System.out.println(cardid+"  "+cardpassword);
        if(card==null){
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else{
            if(card.getCardPassword().equals(cardpassword))
            {
                return Result.success(card);
            }
            else{
                Integer num = card.getNum();
                num--;
                card.setNum(num);
                cardMapper.updateById(card);
                Result<Card> result = new Result<>();
                result.setCode("2");
                result.setMsg("密码错误!");
                result.setData(card);
                return result;
            }
        }
    }

    @Override
    public Result<?> checkBalance(String cardid) {
      Card card = cardMapper.selectById(cardid);
      if(card==null){
        return Result.error("1","出错了,卡已退出,请重试");
      }
      else{
        return Result.success(card);
      }
    }

    @Override
    public Result<?> userStoreRMB(String cardid, Integer reallyNum, String atmId) {
        System.out.println(cardid + " " +atmId);
        Card card = cardMapper.selectById(cardid);
        AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(card==null || atm == null){
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else
        {
            Result<CardRecord> result = new Result<>();
            if( reallyNum > (int)(1000-atm.getAtmBalance()/100))
            {
                reallyNum = (int) (1000-atm.getAtmBalance()/100);
                result.setCode("2");
                result.setMsg("机箱余量不足");
                //如果一分钱都没有直接返回不存，不会生成订单记录
                if(1000-atm.getAtmBalance()/100<=0){
                    return result;
                }
            }
            else{
                result.setCode("3");
                result.setMsg("全部存入");
            }
            Double orginBalance = card.getCardBalance();
            orginBalance += reallyNum*100.00;
            card.setCardBalance(orginBalance);
            cardMapper.updateById(card);
            Double atmBalance = atm.getAtmBalance();
            atmBalance +=  reallyNum*100.00;
            atm.setAtmBalance(atmBalance);
            atmMachineMapper.updateById(atm);
            CardRecord newRecord = addTransactionRecord(cardid, "store", "atm", reallyNum*100.00, atmId, atmId,orginBalance);
            result.setData(newRecord);
            return result;
        }
    }

    @Override
    public Result<?> userTakeRMB(String cardid, Double takeAmount, String atmId) {
        // System.out.println(cardid + " " +atmId);
        Card card = cardMapper.selectById(cardid);
        AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(card==null || atm == null)
        {
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else
        {
            Result<CardRecord> result = new Result<>();
            if( takeAmount > atm.getAtmBalance())
            {
                result.setCode("3");
                result.setMsg("银行卡余额不足");
                //一分钱都取不出来，不会生成订单记录
                return result;
            }
            else if(takeAmount >card.getCardBalance())
            {
                result.setCode("2");
                result.setMsg("机箱余量不足");
                return result;
            }
            Double orginBalance = card.getCardBalance();
            orginBalance -= takeAmount;
            card.setCardBalance(orginBalance);
            cardMapper.updateById(card);
            Double atmBalance = atm.getAtmBalance();
            atmBalance -= takeAmount;
            atm.setAtmBalance(atmBalance);
            atmMachineMapper.updateById(atm);
            CardRecord newRecord = addTransactionRecord(cardid, "take", "atm", takeAmount, atmId, atmId,orginBalance);
            result.setCode("4");
            result.setMsg("成功");
            result.setData(newRecord);
            return result;
        }
    }

    // 查询近十条记录
    @Override
    public Result<?> getRecentCardRecord(String cardid) {
        List<CardRecord> records = cardRecordMapper.selectRecentRecordsByCardId(cardid);
        return Result.success(records);
    }

    @Override
    public Result<?> changePassword(String cardid, String newpassword) {
        Card card = cardMapper.selectById(cardid);
        if(card==null){
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else{
            card.setCardPassword(newpassword);
            cardMapper.updateById(card);
            return Result.success();
        }
    }

    @Override
    public Result<?> transfer(String cardid1, Double amount, String cardid2, String atmId) {
        Card card1 = cardMapper.selectById(cardid1);
        Card card2 = cardMapper.selectById(cardid2);
        // AtmMachine atm = atmMachineMapper.selectById(atmId);
        if(card1==null||card2 == null)
        {
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else
        {
            // 情况2:余额不足
            if(card1.getCardBalance()<amount){
                return Result.error("2","余额不足");
            }
            else
            {
                //情况3可以转账
                Double orginBalance1 = card1.getCardBalance();
                orginBalance1 -= amount;
                card1.setCardBalance(orginBalance1);
                cardMapper.updateById(card1);
                Double orginBalance2 = card2.getCardBalance();
                orginBalance2 += amount;
                card2.setCardBalance(orginBalance2);
                cardMapper.updateById(card2);
                CardRecord newRecord1 = addTransactionRecord(cardid1, "transreduce", "atm", amount, cardid2, atmId,orginBalance1);
                addTransactionRecord(cardid2, "transadd", "atm", amount, cardid1, atmId,orginBalance2);
                Result<CardRecord> result = new Result<>();
                result.setCode("0");
                result.setData(newRecord1);
                result.setMsg("转账成功");
                return result;
            }
        }
    }

    @Override
    public CardRecord addTransactionRecord(String cardid1, String optype, String opway, Double amount, String cardid2, String atmId, Double balance) {
        String recordid =  generateSerialNumber(atmId);
        CardRecord newRecord  = cardRecordMapper.selectById(recordid);
        while(newRecord!=null){
            // 如果有重复，继续随机生成
            recordid =  generateSerialNumber(atmId);
            newRecord  = cardRecordMapper.selectById(recordid);
        }
        newRecord = new CardRecord();
        // 订单号确定
        newRecord.setRecordId(recordid);
        newRecord.setCardId(cardid1);
        newRecord.setCardId1(cardid2);
        newRecord.setAmount(amount);
        newRecord.setOpType(optype);
        newRecord.setOpWay(opway);
        newRecord.setTime(LocalDateTime.now());
        newRecord.setBalance(balance);
        cardRecordMapper.save(newRecord);
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

    @Override
    public Result<?> getOneCardRecord(String recordid) {
        CardRecord oneRecord = cardRecordMapper.selectOneRecordById(recordid);
        if(oneRecord == null){
            return Result.error("1", "打印出错，请重新操作或者到柜台办理");
        }
        else
        {
            return Result.success(oneRecord);
        }
    }


    
}
