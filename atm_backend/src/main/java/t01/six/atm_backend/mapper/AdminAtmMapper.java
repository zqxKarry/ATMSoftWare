package t01.six.atm_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import t01.six.atm_backend.entity.AdminAtm;

@Mapper
public interface AdminAtmMapper extends BaseMapper<AdminAtm> {
    // 根据adminId和atmId查询AdminAtm
    @Select("SELECT * FROM admin_atm WHERE adminid = #{adminId} AND atmid = #{atmId}")
    AdminAtm findByAdminIdAndAtmId(@Param("adminId") String adminId, @Param("atmId") String atmId);
}