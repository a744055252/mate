package com.cnsmash.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.pojo.bean.ReposResult;
import com.cnsmash.pojo.entity.Tournament;
import com.cnsmash.pojo.entity.TournamentPlayer;
import com.cnsmash.pojo.ro.*;
import com.cnsmash.pojo.vo.*;
import com.cnsmash.service.TournamentService;
import com.cnsmash.util.MateAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Amaki
 */
@CrossOrigin
@RestController
@RequestMapping("/tournament")
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @PostMapping("/add")
    public ReposResult<Tournament> add(@RequestBody AddTournamentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        tournamentService.addTournament(loginUser.getUserId(), ro);
        return ReposResult.ok();
    }

    @GetMapping("/list")
    public ReposResult<Page<TournamentThumbnailVo>> page(PageTournamentRo ro) {
        return ReposResult.ok(tournamentService.page(ro));
    }

    @GetMapping("/detail")
    public ReposResult<TournamentDetailVo> detail(@RequestParam Long id) {
        return ReposResult.ok(tournamentService.detail(id));
    }

    @GetMapping("/playerList")
    public ReposResult<List<UserThumbnailVo>> playerList(@RequestParam Long id) {
        return ReposResult.ok(tournamentService.playerList(id));
    }

    @GetMapping("/isRegister")
    public ReposResult<TournamentPlayer> isRegister(@RequestParam Long tournamentId) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.isRegister(loginUser.getUserId(), tournamentId));
    }

    @PostMapping("/register")
    public ReposResult<String> register(@RequestBody @Valid RegisterTournamentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.register(loginUser.getUserId(), ro));
    }

    @PostMapping("/updateRegister")
    public ReposResult<String> updateRegister(@RequestBody @Valid RegisterTournamentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.updateRegister(loginUser.getUserId(), ro));
    }

    @PostMapping("/unregister")
    public ReposResult<String> unregister(@RequestBody @Valid RegisterTournamentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.unregister(loginUser.getUserId(), ro.getTournamentId()));
    }

    @GetMapping("/player")
    public ReposResult<TournamentPlayerVo> player(@RequestParam Long playerId) {
        return ReposResult.ok();
    }

    @PostMapping("/start")
    public ReposResult<String> start(@RequestBody @Valid StartTournamentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.startTournament(ro.getTournamentId(), loginUser.getUserId()));
    }

    @PostMapping("/update")
    public ReposResult<String> update(@RequestBody @Valid AddTournamentRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.updateTournament(loginUser.getUserId(), ro));
    }

    @GetMapping("/setlist")
    public ReposResult<List<TournamentSetVo>> setList(@RequestParam Long id) {
        return ReposResult.ok(tournamentService.setList(id));
    }

    @PostMapping("/report")
    public ReposResult<String> report(@RequestBody @Valid TournamentReportRo ro) {
        LoginUser loginUser = MateAuthUtils.getLoginUser();
        return ReposResult.ok(tournamentService.report(ro.getTournamentSetId(), loginUser.getUserId()));
    }

    @PostMapping("/room")
    public ReposResult<Void> room(@RequestBody @Valid TournamentRoomRo ro) {
        tournamentService.room(ro);
        return ReposResult.ok();
    }

    @PostMapping("/focus")
    public ReposResult<Void> focus(@RequestBody @Valid TournamentSetFocusRo ro) {
        tournamentService.focus(ro);
        return ReposResult.ok();
    }

    @GetMapping("/resultList")
    public ReposResult<List<TournamentResultVo>> playerResultList(PlayerResultListRo ro) {
        return ReposResult.ok(tournamentService.playerResultList(ro.getId(), ro.getLimit()));
    }

}
