package t01.six.atm_backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import t01.six.atm_backend.entity.CardRecord;


public interface CardRecordMapper extends BaseMapper<CardRecord>{
    
    @Insert("INSERT INTO user_record (recordid, cardid, optype, opway, amount, time, cardid1, balance) " +
        "VALUES (#{cardRecord.recordid}, #{cardRecord.cardid}, #{cardRecord.optype}, " +
        "#{cardRecord.opway}, #{cardRecord.amount}, #{cardRecord.time}, #{cardRecord.cardid1}, #{cardRecord.balance})")
    int save(@Param("cardRecord") CardRecord cardRecord);

    @Select("SELECT * FROM user_record WHERE recordid = #{recordid}")
    CardRecord selectById(@Param("recordid") String recordid);

    @Select("SELECT * FROM user_record WHERE cardid = #{cardid} ORDER BY time DESC LIMIT 10")
    List<CardRecord> selectRecentRecordsByCardId(@Param("cardid") String cardid);

    @Select("SELECT * FROM user_record WHERE recordid = #{recordid}")
    CardRecord selectOneRecordById(@Param("recordid") String recordid);
}
