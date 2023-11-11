package t01.six.atm_backend.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@TableName("admin")
@Data
@ToString
public class Admin implements Serializable{

    @TableId(value="adminid")
    private String adminid;
    @TableField
    private String adminpass;
    private Integer num;

    public String getAdminId()
    {
        return adminid;
    }
    public void setAdminId(String adminid)
    {
        this.adminid = adminid;
    }

    public String getAdminPass()
    {
        return adminpass;
    }

    public void setAdminPass(String adminpass)
    {
        this.adminpass = adminpass;
    }

    public Integer getNum()
    {
        return this.num;
    }

    public void setNum(Integer num)
    {
        this.num = num;
    }

    
}
