package t01.six.atm_backend.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("atm_record")
@Data
public class AtmRecord {

    @TableId(value="recordid")
    private String recordid;

    @TableField
    private String cardid;
    private String optype;
    private double amount;
    private LocalDateTime time;
    private String atmid;
    private String adminid;
    private double balance; //交易后余额
    private String role;

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAtmId(){
        return atmid;
    }

    public void setAtmId(String atmid){
        this.atmid = atmid;
    }

    public String getAdminId(){
        return adminid;
    }

    public void setAdminId(String adminid){
        this.adminid = adminid;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }
}