package t01.six.atm_backend.service.impl;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import t01.six.atm_backend.entity.Admin;
import t01.six.atm_backend.entity.Card;
// import t01.six.atm_backend.entity.CardRecord;
import t01.six.atm_backend.mapper.AtmMachineMapper;
import t01.six.atm_backend.mapper.CardMapper;
import t01.six.atm_backend.service.CardService;
import t01.six.atm_backend.utils.Result;

@Service
public class CardServiceImpl implements CardService{

    @Resource
    CardMapper cardMapper;
    AtmMachineMapper atmMachineMapper;

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
    public Result<?> printVoucher(String recordid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printVoucher'");
    }

    @Override
    public Result<?> userAddRMB(String cardid, Double addAmount, String atmId) {
        Card card = cardMapper.selectById(cardid);
        if(card==null){
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else{
            Double orginBalance = card.getCardBalance();
            orginBalance += addAmount;
            card.setCardBalance(orginBalance);
            // CardRecord newRecord = new CardRecord();
            // 随机生成recordid并插入记录

            // 控制增加机箱内余额的钱
            return Result.success();
        }
    }

    @Override
    public Result<?> userTakeRMB(String cardid, Double takeAmount, String atmId) {
        Card card = cardMapper.selectById(cardid);
        if(card==null){
            return Result.error("1","出错了,卡已退出,请重试");
        }
        else{
            Double orginBalance = card.getCardBalance();
            if(takeAmount>orginBalance)
            {
                return Result.error("2", "您的余额不足,请重新选择金额");
            }
            else{
                // 检查机箱内余额
                atmMachineMapper.selectById(cardid);
                
            }
            orginBalance -= takeAmount;
            card.setCardBalance(orginBalance);
            // CardRecord newRecord = new CardRecord();
            // 随机生成recordid并插入记录
            return Result.success();
        }
    }

    @Override
    public Result<?> getAllCardRecord(String cardid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCardRecord'");
    }

    @Override
    public Result<?> changePassword(String cardid, String newpassword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public Result<?> transfer(String cardid1, Double amount, String cardid2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transfer'");
    }

    @Override
    public Result<?> addTransactionRecord(String cardid1, String opway, Double amount, String cardid2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTransactionRecord'");
    }
    
}
