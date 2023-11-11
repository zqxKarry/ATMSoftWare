package t01.six.atm_backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("atm_machine")
@Data
public class AtmMachine {

    @TableId(value="atmid")
    private String atmid;

    @TableField
    private Double atmbalance;
    private String atmposition;
    private Integer atmpapercount;


    public String getAtmId(){
        return this.atmid;
    }

    public void setAtmId(String atmid){
        this.atmid = atmid;
    }

    public Double getAtmBalance(){
        return this.atmbalance;
    }

    public void setAtmBalance(Double atmbalance){
        this.atmbalance = atmbalance;
    }

    public String getAtmPosition(){
        return this.atmposition;
    }

    public void setAtmPosition(String atmposition){
        this.atmposition = atmposition;
    }

    public Integer getAtmPaperCount(){
        return this.atmpapercount;
    }

    public void setAtmPaperCount(Integer atmpapercount){
        this.atmpapercount = atmpapercount;
    }
    
}
