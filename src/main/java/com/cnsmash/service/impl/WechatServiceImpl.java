package com.cnsmash.service.impl;

import com.cnsmash.pojo.bean.Room;
import com.cnsmash.pojo.entity.Account;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        // 模板 wf_nBNgK7kq4dIpRomnWUfe5gbdj3SdJXIQB4cy2Fk0
        // {{first.DATA}}
        //比赛：{{keyword1.DATA}}
        //时间：{{keyword2.DATA}}
        //{{remark.DATA}}


        // 发送用户1
        UserDetail p1 = vo.getP1();
        Account account = accountService.get(p1.getAccountId());
        wxUserService.get(account.getMappingId())
                .ifPresent(wxUser -> {
                    List<WxMpTemplateData> data = new ArrayList<>();
                    WxMpTemplateMessage msg = WxMpTemplateMessage.builder()
                            .templateId("wf_nBNgK7kq4dIpRomnWUfe5gbdj3SdJXIQB4cy2Fk0")
                            .url("https://mate.cnsmash.com/battle/" + vo.getBattleId())
                            .toUser(wxUser.getOpenid())
                            .data(data)
                            .build();
                    try {
                        wxMpService.getTemplateMsgService().sendTemplateMsg(msg);
                    } catch (WxErrorException e) {
                        log.error("发送微信公众号消息失败", e);
                    }
                });
    }

    @Override
    public void createRoom(Room room, List<GameFighter> gameFighters) {
        for (GameFighter gameFighter : gameFighters) {
            User user = userService.getById(gameFighter.getUserId());
            Account account = accountService.get(user.getAccountId());
            wxUserService.get(account.getMappingId())
                    .ifPresent(wxUser -> {
                        List<WxMpTemplateData> dataList = new ArrayList<>();
                        {
                            WxMpTemplateData data = new WxMpTemplateData();
                        }
                        WxMpTemplateMessage msg = WxMpTemplateMessage.builder()
                                .templateId("wf_nBNgK7kq4dIpRomnWUfe5gbdj3SdJXIQB4cy2Fk0")
//                                .url("https://mate.cnsmash.com/battle/" + vo.getBattleId())
                                .toUser(wxUser.getOpenid())
//                                .data(data)
                                .build();
                        try {
                            wxMpService.getTemplateMsgService().sendTemplateMsg(msg);
                        } catch (WxErrorException e) {
                            log.error("发送微信公众号消息失败", e);
                        }
                    });

        }
    }
}
