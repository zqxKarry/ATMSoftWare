package t01.six.atm_backend.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import t01.six.atm_backend.entity.Admin;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT * FROM admin WHERE adminid = #{adminid}")
    Admin selectById(@Param("adminid") String adminid);

    @Update("UPDATE admin SET num = #{admin.num}, adminpass = #{admin.adminpass} WHERE adminid = #{admin.adminid}")
    int updateById(@Param("admin") Admin admin);
    
}
