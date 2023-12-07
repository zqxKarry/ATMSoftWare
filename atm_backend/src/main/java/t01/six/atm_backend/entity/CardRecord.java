package t01.six.atm_backend.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("user_record")
@Data
public class CardRecord {

    @TableId(value="recordid")
    private String recordid;

    @TableField
    private String cardid;
    private String optype;
    private String opway;
    private double amount;
    private LocalDateTime time;
    private String cardid1;
    private double balance; //交易后余额

    public String getRecordId() {
        return recordid;
    }

    public void setRecordId(String recordid) {
        this.recordid = recordid;
    }

    public String getCardId() {
        return cardid;
    }

    public void setCardId(String cardid) {
        this.cardid = cardid;
    }

    public String getOpType() {
        return optype;
    }

    public void setOpType(String optype) {
        this.optype = optype;
    }

    public String getOpWay() {
        return opway;
    }

    public void setOpWay(String opway) {
        this.opway = opway;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCardId1() {
        return cardid1;
    }

    public void setCardId1(String cardid1) {
        this.cardid1 = cardid1;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}