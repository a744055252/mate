package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.mapper.TournamentMapper;
import com.cnsmash.mapper.TournamentPlayerMapper;
import com.cnsmash.mapper.TournamentSetMapper;
import com.cnsmash.pojo.TournamentSetStatus;
import com.cnsmash.pojo.TournamentStatus;
import com.cnsmash.pojo.entity.Tournament;
import com.cnsmash.pojo.entity.TournamentPlayer;
import com.cnsmash.pojo.entity.TournamentSet;
import com.cnsmash.pojo.ro.*;
import com.cnsmash.pojo.vo.*;
import com.cnsmash.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    TournamentMapper tournamentMapper;

    @Autowired
    TournamentPlayerMapper tournamentPlayerMapper;

    @Autowired
    TournamentSetMapper tournamentSetMapper;

    @Override
    public void addTournament(Long host, AddTournamentRo ro) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Tournament tournament = new Tournament();
        BeanUtils.copyProperties(ro, tournament);
        tournament.setHost(host);
        tournament.setCreateTime(now);
        tournament.setUpdateTime(now);
        tournament.setStatus(TournamentStatus.before);
        tournamentMapper.insert(tournament);
    }

    @Override
    public Page<TournamentThumbnailVo> page(PageTournamentRo ro) {
        System.out.println(ro.getStatus());
        return tournamentMapper.pageTournament(new Page<>(ro.getCurrent(), ro.getSize()), ro);
    }

    @Override
    public TournamentDetailVo detail(Long id) {
        return tournamentMapper.tournamentDetail(id);
    }

    @Override
    public List<UserThumbnailVo> playerList(Long id) {
        return tournamentMapper.playerList(id);
    }

    @Override
    public TournamentPlayer isRegister(Long playerId, Long tournamentId) {
        QueryWrapper<TournamentPlayer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("player_id", playerId)
                .eq("tournament_id", tournamentId);
        List<TournamentPlayer> registerList = tournamentPlayerMapper.selectList(queryWrapper);
        if (registerList.size() != 0) {
            return registerList.get(0);
        }
        return null;
    }

    @Override
    public String register(Long playerId, RegisterTournamentRo ro) {
        // 检查是否已经报名
        if (isRegister(playerId, ro.getTournamentId()) != null) {
            return "该比赛已经报名";
        }

        TournamentPlayer tournamentPlayer = new TournamentPlayer();
        tournamentPlayer.setBanMap(ro.getBanMap());
        tournamentPlayer.setTournamentId(ro.getTournamentId());
        tournamentPlayer.setPlayerId(playerId);
        tournamentPlayer.setSeed(-1);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        tournamentPlayer.setCreateTime(now);
        tournamentPlayer.setUpdateTime(now);
        tournamentPlayerMapper.insert(tournamentPlayer);
        return "success";
    }

    @Override
    public String updateRegister(Long playerId, RegisterTournamentRo ro) {
        TournamentPlayer tournamentPlayer = tournamentPlayerMapper.selectById(ro.getId());
        tournamentPlayer.setBanMap(ro.getBanMap());
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        tournamentPlayer.setUpdateTime(now);
        tournamentPlayerMapper.updateById(tournamentPlayer);
        return "success";
    }

    @Override
    public String unregister(Long playerId, Long tournamentId) {
        QueryWrapper<TournamentPlayer> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("player_id", playerId)
                .eq("tournament_id", tournamentId);
        tournamentPlayerMapper.delete(deleteWrapper);
        return "success";
    }

    private void seedGenerator(Long tournamentId) {
        Tournament tournament = tournamentMapper.selectById(tournamentId);
        Integer limit = tournament.getPlayerLimit();
        QueryWrapper<TournamentPlayer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tournament_id", tournamentId);
        queryWrapper.last("limit " + limit);
        List<TournamentPlayer> players = tournamentPlayerMapper.selectList(queryWrapper);
        Collections.shuffle(players);
        for (int i = 0; i < players.size(); i++) {
            TournamentPlayer tournamentPlayer = players.get(i);
            tournamentPlayer.setSeed(i + 1);
            tournamentPlayerMapper.updateById(tournamentPlayer);
        }
        QueryWrapper<TournamentPlayer> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("seed", -1);
        tournamentPlayerMapper.delete(deleteWrapper);
    }

    private void bracketGenerator(Long tournamentId) {
        Tournament tournament = tournamentMapper.selectById(tournamentId);
        QueryWrapper<TournamentPlayer> wrapper = new QueryWrapper<>();
        wrapper.eq("tournament_id", tournamentId)
                .orderByAsc("seed");
        List<TournamentPlayer> playerList = tournamentPlayerMapper.selectList(wrapper);
        List<TournamentSet> setList = new ArrayList<>();
        List<Integer> nodeInd = new ArrayList<>();

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        // 第一个节点手动插入
        TournamentSet origin = new TournamentSet();
        origin.setTournamentId(tournamentId);
        origin.setSerial(1);
        origin.setPlayer1Id(playerList.get(0).getPlayerId());
        origin.setPlayer2Id(playerList.get(1).getPlayerId());
        origin.setStatus(TournamentSetStatus.process);
        origin.setFocus(0);
        origin.setCreateTime(now);
        origin.setUpdateTime(now);
        setList.add(origin);

        int currentAssign = 0;
        nodeInd.add(0);
        nodeInd.add(0);

        // 剩下的按种子位插入
        for (int i = 2; i < playerList.size(); i ++) {
            if (currentAssign == 0) {
                currentAssign = i - 1;
            } else {
                currentAssign -= 1;
            }
            TournamentSet parentSet = setList.get(nodeInd.get(currentAssign));
            Integer index = parentSet.getSerial();
            Long assignId = playerList.get(currentAssign).getPlayerId();
            Integer offset = 0;
            if (assignId.equals(parentSet.getPlayer2Id())) {
                offset = 1;
                parentSet.setPlayer2Id(null);
            } else {
                parentSet.setPlayer1Id(null);
            }
            parentSet.setStatus(TournamentSetStatus.waiting);
            setList.set(nodeInd.get(currentAssign), parentSet);
            TournamentSet newSet = new TournamentSet();
            newSet.setTournamentId(tournamentId);
            newSet.setSerial(index * 2 + offset);
            newSet.setPlayer1Id(assignId);
            newSet.setPlayer2Id(playerList.get(i).getPlayerId());
            newSet.setStatus(TournamentSetStatus.process);
            newSet.setFocus(0);
            newSet.setCreateTime(now);
            newSet.setUpdateTime(now);
            nodeInd.set(currentAssign, setList.size());
            nodeInd.add(setList.size());
            setList.add(newSet);
            System.out.println(setList);
        }
        for (TournamentSet tournamentSet: setList) {
            tournamentSetMapper.insert(tournamentSet);
        }
    }

    @Override
    public void startToutnament(Long tournamentId) {
        seedGenerator(tournamentId);
        bracketGenerator(tournamentId);
    }

    @Override
    public String startTournament(Long tournamentId, Long playerId) {
        Tournament tournament = tournamentMapper.selectById(tournamentId);
        if (playerId.equals(tournament.getHost()) == false) {
            return "非管理人无权限开始比赛";
        }
        startToutnament(tournamentId);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        tournament.setStatus(TournamentStatus.process);
        tournament.setUpdateTime(now);
        tournamentMapper.updateById(tournament);
        return "success";
    }

    @Override
    public String updateTournament(Long host, AddTournamentRo ro) {
        Tournament tournament = tournamentMapper.selectById(ro.getId());
        if (host.equals(tournament.getHost()) == false) {
            return "非管理人无权限修改比赛";
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        BeanUtils.copyProperties(ro, tournament);
        tournament.setUpdateTime(now);
        tournamentMapper.updateById(tournament);
        return "success";
    }

    @Override
    public List<TournamentSetVo> setList(Long tournamentId) {
        return tournamentSetMapper.setList(tournamentId);
    }

    private Integer getHighBit(Integer num) {
        Integer[] twos = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};
        int ind = 0;
        while (num > twos[ind]) ind++;
        return twos[ind];
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String report(Long tournamentSetId, Long playerId) {
        TournamentSet currentSet = tournamentSetMapper.selectById(tournamentSetId);
        Integer serial = currentSet.getSerial();
        Long loserId = null;
        if (currentSet.getStatus() == TournamentSetStatus.waiting) {
            return "对局未开始";
        }
        if (currentSet.getWinnerId() != null) {
            return "对局已经结束";
        }
        if (playerId.equals(currentSet.getPlayer1Id()) == false &&
            playerId.equals(currentSet.getPlayer2Id()) == false) {
            return "数据错误";
        } else {
            if (playerId.equals(currentSet.getPlayer1Id())) {
                loserId = currentSet.getPlayer2Id();
            } else if (playerId.equals(currentSet.getPlayer2Id())) {
                loserId = currentSet.getPlayer1Id();
            }
        }
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        currentSet.setStatus(TournamentSetStatus.finish);
        currentSet.setWinnerId(playerId);
        currentSet.setUpdateTime(now);
        tournamentSetMapper.updateById(currentSet);

        // 更新败者成绩
        tournamentPlayerMapper.updateResult(currentSet.getTournamentId(), loserId, getHighBit(currentSet.getSerial() + 1));

        // 非决赛
        if (serial != 1) {
            QueryWrapper<TournamentSet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("tournament_id", currentSet.getTournamentId())
                    .eq("serial", serial / 2);
            TournamentSet nextSet = tournamentSetMapper.selectOne(queryWrapper);
            if (serial % 2 == 0) {
                nextSet.setPlayer1Id(playerId);
                if (nextSet.getPlayer2Id() != null) {
                    nextSet.setStatus(TournamentSetStatus.process);
                }
            } else {
                nextSet.setPlayer2Id(playerId);
                if (nextSet.getPlayer1Id() != null) {
                    nextSet.setStatus(TournamentSetStatus.process);
                }
            }
            nextSet.setUpdateTime(now);
            tournamentSetMapper.updateById(nextSet);
        } else {
            // 更新冠军成绩
            tournamentPlayerMapper.updateResult(currentSet.getTournamentId(), playerId, 1);

            Tournament tournament = tournamentMapper.selectById(currentSet.getTournamentId());
            tournament.setStatus(TournamentStatus.finish);
            tournament.setUpdateTime(now);
            tournamentMapper.updateById(tournament);
        }
        return "success";
    }

    @Override
    public void room(TournamentRoomRo ro) {
        TournamentSet tournamentSet = tournamentSetMapper.selectById(ro.getTournamentSetId());
        tournamentSet.setRoom(ro.getRoom());
        tournamentSetMapper.updateById(tournamentSet);
    }

    @Override
    public void focus(TournamentSetFocusRo ro) {
        TournamentSet tournamentSet = tournamentSetMapper.selectById(ro.getTournamentSetId());
        tournamentSet.setFocus(ro.getFocus());
        tournamentSetMapper.updateById(tournamentSet);
    }

    @Override
    public List<TournamentResultVo> playerResultList(Long id, Integer limit) {
        return tournamentPlayerMapper.getPlayerResultList(id, limit);
    }

}
