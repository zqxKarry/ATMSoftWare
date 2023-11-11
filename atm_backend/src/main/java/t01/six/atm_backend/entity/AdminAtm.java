package t01.six.atm_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("admin_atm")
@Data
public class AdminAtm {
    @TableId(value = "adminid", type = IdType.INPUT)
    private String adminId;
    
    @TableId(value = "atmid", type = IdType.INPUT)
    private String atmId;

    public void setAdminId(String adminId)
    {
        this.adminId = adminId;
    }

    public String getAdminId()
    {
        return this.adminId;
    }

    public void setAtmId(String atmId)
    {
        this.atmId = atmId;
    }
    public String getAtmId()
    {
        return this.atmId;
    }
      
}
