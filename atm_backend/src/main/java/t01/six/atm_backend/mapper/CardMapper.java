package t01.six.atm_backend.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import t01.six.atm_backend.entity.Card;


public interface CardMapper extends BaseMapper<Card> {
     @Select("SELECT * FROM card WHERE cardid = #{cardid}")
     Card selectById(@Param("cardid") String cardid);

     @Update("UPDATE card SET num = #{card.num}, cardpassword = #{card.cardpassword},"+
            " carduserid = #{card.carduserid}, cardbalance = #{card.cardbalance}, "+
            "cardopenplaceid = #{card.cardopenplaceid}, cardphone = #{card.cardphone}  WHERE cardid = #{card.cardid}")
     int updateById(@Param("card") Card card);
}
