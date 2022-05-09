package com.cnsmash.service.impl;

import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.Battle;
import com.cnsmash.pojo.entity.GameFighter;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.vo.MatchResultVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.service.AccountService;
import com.cnsmash.service.UserService;
import com.cnsmash.service.WechatService;
import com.cnsmash.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Override
    public void battleBegin(MatchResultVo vo) {

        UserDetail p1 = vo.getP1();
        UserDetail p2 = vo.getP2();

        List<WxMpTemplateData> dataList = getBattleBeginTemplateData(vo);

        // 发送用户1
        sendBattleBegin(vo, p1, dataList);

        // 发送用户2
        sendBattleBegin(vo, p2, dataList);

    }

    private void sendBattleBegin(MatchResultVo vo, UserDetail user, List<WxMpTemplateData> dataList) {
        Account account = accountService.get(user.getAccountId());
        if (account.getMappingId() == null) {
            log.debug("用户{}未绑定微信！", user.getAccountId());
            return;
        }
        wxUserService.get(account.getMappingId())
                .ifPresent(wxUser -> {
                    WxMpTemplateMessage msg = WxMpTemplateMessage.builder()
                            .templateId("_3_DaR4Cz3mw6YQDVWgfY4KkM-zbauczCziKg3FX-fg")
                            .url("https://mate.cnsmash.com/battle/" + vo.getBattleId())
                            .toUser(wxUser.getOpenid())
                            .data(dataList)
                            .build();
                    try {
                        wxMpService.getTemplateMsgService().sendTemplateMsg(msg);
                    } catch (WxErrorException e) {
                        log.error("发送微信公众号消息失败", e);
                    }
                });
    }

    @Override
    public void createRoom(Battle battle, Room room, List<GameFighter> gameFighters) {
        List<User> userList = gameFighters.stream().map(gameFighter -> userService.getById(gameFighter.getUserId()))
                .collect(Collectors.toList());

        List<WxMpTemplateData> dataList = getCreateTemplateData(battle, room);

        userList.forEach(user -> {
            Account account = accountService.get(user.getAccountId());
            wxUserService.get(account.getMappingId())
                    .ifPresent(wxUser -> {
                        WxMpTemplateMessage msg = WxMpTemplateMessage.builder()
                                .templateId("_3_DaR4Cz3mw6YQDVWgfY4KkM-zbauczCziKg3FX-fg")
                                .url("https://mate.cnsmash.com/battle/" + battle.getId())
                                .toUser(wxUser.getOpenid())
                                .data(dataList)
                                .build();
                        try {
                            wxMpService.getTemplateMsgService().sendTemplateMsg(msg);
                        } catch (WxErrorException e) {
                            log.error("发送微信公众号消息失败", e);
                        }
                    });
        });
    }


    private List<WxMpTemplateData> getBattleBeginTemplateData(MatchResultVo vo) {
        UserDetail p1 = vo.getP1();
        UserDetail p2 = vo.getP2();
        List<WxMpTemplateData> dataList = new ArrayList<>();
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("first");
            data.setValue("您好，您在聚能斗天梯功能中匹配成功，请前往页面确认!");
            dataList.add(data);
        }
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("keyword1");
            data.setValue(p1.getNickName() + " vs " + p2.getNickName());
            data.setColor("#DC143C");
            dataList.add(data);
        }
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("keyword2");
            data.setValue(DateFormatUtils.format(vo.getBattle().getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            dataList.add(data);
        }
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("remark");
            data.setValue("感谢您的使用，祝您游戏愉快。\r\n>>>>点击跳转页面!");
            dataList.add(data);
        }
        return dataList;
    }

    private List<WxMpTemplateData> getCreateTemplateData(Battle battle, Room room) {
        List<WxMpTemplateData> dataList = new ArrayList<>();
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("first");
            data.setValue("您好，您的对战房间已创建");
            dataList.add(data);
        }
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("keyword1");
            data.setValue("房间【" + room.getNo() + "】【" + room.getPwd() + "】【" + room.serverConvertToZh()+"】");
            data.setColor("#DC143C");
            dataList.add(data);
        }
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("keyword2");
            data.setValue(DateFormatUtils.format(battle.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            dataList.add(data);
        }
        {
            WxMpTemplateData data = new WxMpTemplateData();
            data.setName("remark");
            data.setValue("请尽快进入并开始比赛，感谢您的使用，祝您游戏愉快。\r\n>>>>点击跳转页面!");
            dataList.add(data);
        }
        return dataList;
    }
}
