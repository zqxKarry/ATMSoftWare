package t01.six.atm_backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import t01.six.atm_backend.entity.CardRecord;


public interface CardRecordMapper extends BaseMapper<CardRecord>{
    
    @Insert("INSERT INTO card_record (recordid, cardid, optype, opway, amount, time, cardid1) " +
        "VALUES (#{cardRecord.recordid}, #{cardRecord.cardid}, #{cardRecord.optype}, " +
        "#{cardRecord.opway}, #{cardRecord.amount}, #{cardRecord.time}, #{cardRecord.cardid1})")
    int save(@Param("cardRecord") CardRecord cardRecord);
}