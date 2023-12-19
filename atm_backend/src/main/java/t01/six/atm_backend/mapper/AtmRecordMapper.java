package t01.six.atm_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import t01.six.atm_backend.entity.AtmRecord;



public interface AtmRecordMapper extends BaseMapper<AtmRecord>{
    
    @Insert("INSERT INTO atm_record (recordid, cardid, optype, balance, amount, time, atmid, adminid, role) " +
        "VALUES (#{atmRecord.recordid}, #{atmRecord.cardid}, #{atmRecord.optype}, " +
        "#{atmRecord.balance}, #{atmRecord.amount}, #{atmRecord.time}, #{atmRecord.atmid}, #{atmRecord.adminid}, #{atmRecord.role})")
    int save(@Param("atmRecord") AtmRecord cardRecord);

    @Select("SELECT * FROM atm_record WHERE recordid = #{recordid}")
    AtmRecord selectById(@Param("recordid") String recordid);
}