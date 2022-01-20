package com.cnsmash.pojo;

import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.pojo.entity.UserFighter;
import com.cnsmash.pojo.entity.UserRank;
import lombok.Getter;

/**
 * 对战结果
 * @author guanhuan_li
 */
@Getter
public enum BattleResultType {
    /** 赢 */
    win{
        @Override
        public void changeRank(UserRank userRank) {
            userRank.setWin(userRank.getWin() + 1);
            userRank.setTotal(userRank.getTotal() + 1);
        }

        @Override
        public void changeFighter(UserFighter userFighter) {
            userFighter.setWin(userFighter.getWin() + 1);
            userFighter.setTotal(userFighter.getTotal() + 1);
        }
    },
    /** 输 */
    lose{
        @Override
        public void changeRank(UserRank userRank) {
            userRank.setLost(userRank.getLost() + 1);
            userRank.setTotal(userRank.getTotal() + 1);
        }

        @Override
        public void changeFighter(UserFighter userFighter) {
            userFighter.setLost(userFighter.getLost() + 1);
            userFighter.setTotal(userFighter.getTotal() + 1);
        }
    },
    /** 平局 */
    tie;

    public void changeRank(UserRank userRank){
        throw new CodeException(ErrorCode.NOT_SUPPORT_ENUM, "不支持的枚举");
    }

    public void changeFighter(UserFighter userFighter){
        throw new CodeException(ErrorCode.NOT_SUPPORT_ENUM, "不支持的枚举");
    }
}
