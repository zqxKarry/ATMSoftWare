package t01.six.atm_backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("card")
@Data
public class Card {

    @TableId(value="cardid")
    private String cardid;

    @TableField
    private String carduserid;
    private String cardpassword;
    private Double cardbalance;
    private String cardopenid;
    private String cardphone;
    private Integer num;

    public String getCardId(){
        return this.cardid;
    }

    public void setCardId(String cardid){
        this.cardid = cardid;
    }

    public void setCardUserId(String carduserid){
        this.carduserid = carduserid;
    }

    public String getCardUserId(){
        return this.carduserid;
    }

    public String getCardPassword(){
        return this.cardpassword;
    }

    public void setCardPassword(String cardpassword){
        this.cardpassword = cardpassword;
    }

    public Double getCardBalance(){
        return this.cardbalance;
    }

    public void setCardBalance(Double cardbalance){
        this.cardbalance = cardbalance;
    }

    public String getCardOpenId(){
        return this.cardopenid;
    }

    public void setCardOpenId(String cardopenid){
        this.cardopenid = cardopenid;
    }

    public String getCardPhone(){
        return this.cardphone;
    }

    public void setCardPhone(String cardphone){
        this.cardphone = cardphone;
    }

    public Integer getNum(){
        return this.num;
    }

    public void setNum(Integer num){
        this.num = num;
    }
}
