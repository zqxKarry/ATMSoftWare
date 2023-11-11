package t01.six.atm_backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("user")
@Data
public class User {


    @TableId(value = "userid")
    private String userid;

    @TableField
    private String username;
    private String usersex;

    public String geUserId(){
        return userid;
    }

    public void setUserId(String userid){
        this.userid = userid;
    }

    public String getUserName(){
        return username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    public String getUserSex(){
        return usersex;
    }

    public void setUserSex(String usersex){
        this.usersex = usersex;
    }
    
}
