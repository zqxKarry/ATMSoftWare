package t01.six.atm_backend.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import t01.six.atm_backend.entity.AtmMachine;

public interface AtmMachineMapper extends BaseMapper<AtmMachine> {
    @Select("SELECT * FROM atm_machine WHERE atmid = #{atmid}")
    AtmMachine selectById(@Param("atmid") String atmid);

    @Update("UPDATE atm_machine SET atmbalance = #{atm.atmbalance}, atmposition = #{atm.atmposition}, atmpapercount = #{atm.atmpapercount} WHERE atmid = #{atm.atmid}")
    int updateById(@Param("atm") AtmMachine atm);
    
}
