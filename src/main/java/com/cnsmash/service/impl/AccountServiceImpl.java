package com.cnsmash.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cnsmash.config.login.MateAuthenticationProvider;
import com.cnsmash.config.login.pojo.LoginUser;
import com.cnsmash.config.login.service.LoginUserService;
import com.cnsmash.exception.CodeException;
import com.cnsmash.exception.ErrorCode;
import com.cnsmash.mapper.AccountMapper;
import com.cnsmash.pojo.entity.Account;
import com.cnsmash.pojo.entity.Quarter;
import com.cnsmash.pojo.entity.UploadFile;
import com.cnsmash.pojo.entity.User;
import com.cnsmash.pojo.ro.AccountUserRo;
import com.cnsmash.pojo.ro.RegisterUserRo;
import com.cnsmash.pojo.ro.UpdatePasswordRo;
import com.cnsmash.pojo.ro.UpdateUserInfoRo;
import com.cnsmash.pojo.vo.AccountUserVo;
import com.cnsmash.pojo.vo.UserDetail;
import com.cnsmash.pojo.vo.UserInfo;
import com.cnsmash.service.AccountService;
import com.cnsmash.service.FileService;
import com.cnsmash.service.QuarterService;
import com.cnsmash.service.UserService;
import com.cnsmash.util.JsonUtil;
import com.cnsmash.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guanhuan_li
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Autowired
    QuarterService quarterService;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public Account login(String username){
        return getAccountByAccount(username);
    }

    @Override
    public AccountUserVo user(AccountUserRo ro) {
        Account account = getAccountByAccount(ro.getAccount());

        if (account == null) {
            // 账号不存在
            throw new BadCredentialsException("账号不存在！");
        }

        if (!encoder.matches(ro.getPassword(), account.getPassword())) {
            // 密码不对
            throw new BadCredentialsException("密码错误！");
        }

        List<User> users = userService.listByAccountId(account.getId());
        AccountUserVo vo = new AccountUserVo();
        account.setPassword("");
        vo.setAccount(account);
        vo.setUserList(users);
        long auth = SnowFlake.nextId();
        vo.setAuth(String.valueOf(auth));
        return vo;
    }

    @Override
    public User register(RegisterUserRo ro) {

        if (getAccountByAccount(ro.getAccount()) != null) {
            throw new CodeException(ErrorCode.ACCOUNT_EXIT, "账号已存在");
        }

        Account account = new Account();
        BeanUtils.copyProperties(ro, account);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        account.setPassword(passwordEncoder.encode(ro.getPassword().trim()));
        account.setUpdateTime(now);
        account.setCreateTime(now);
        accountMapper.insert(account);

        User user = new User();
        BeanUtils.copyProperties(ro, user);
        user.setAccountId(account.getId());
        user.setNickName(ro.getAccount());
        user.setCode("");
        user.setTagJson(JsonUtil.toJson(ro.getTags()));
        user.setUpdateTime(now);
        user.setCreateTime(now);
        userService.add(user);

        return user;
    }



    @Override
    public UserInfo info(Long accountId) {
        Account account = accountMapper.selectById(accountId);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(account, userInfo);

        List<User> users = userService.listByAccountId(accountId);
        List<UserDetail> userList = users.stream()
                .map(user -> {
                    UserDetail userDetail = new UserDetail();
                    BeanUtils.copyProperties(user, userDetail);
                    return userDetail;
                })
                .peek(userDetail -> {
                    UploadFile head = fileService.findById(userDetail.getHead());
                    if (head != null) {
                        userDetail.setHead(head.getId());
                        userDetail.setHeadSrc(head.getSrc());
                    }
                })
                .collect(Collectors.toList());
        userInfo.setUserList(userList);
        return userInfo;
    }



    @Override
    public void updatePassword(LoginUser loginUser, UpdatePasswordRo ro) {
        Account account = accountMapper.selectById(loginUser.getId());
        LoginUserService loginUserService = MateAuthenticationProvider.getType2service().get(loginUser.getLoginType());
        PasswordEncoder encoder = loginUserService.getPasswordEncoder();
        if (!encoder.matches(ro.getOldPassword(), account.getPassword())) {
            // 密码不对
            throw new CodeException(ErrorCode.PASSWORD_ERROR, "密码错误！");
        }
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        account.setUpdateTime(now);
        account.setPassword(encoder.encode(ro.getNewPassword()));
        accountMapper.updateById(account);
    }

    @Override
    public void updateUserInfo(LoginUser loginUser, UpdateUserInfoRo ro) {
        Account account = accountMapper.selectById(loginUser.getId());
        User user = userService.getById(loginUser.getUserId());

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        BeanUtils.copyProperties(ro, account);
        account.setUpdateTime(now);

        BeanUtils.copyProperties(ro, user);
        user.setUpdateTime(now);
        user.setTagJson(JsonUtil.toJson(ro.getTags()));

        accountMapper.updateById(account);
        userService.update(user);
    }

    @Override
    public Account get(Long accountId) {
        return accountMapper.selectById(accountId);
    }

    private Account getAccountByAccount(String username) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", username);
        return accountMapper.selectOne(queryWrapper);
    }

    private boolean exit(String username, String nickName) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nick_name", nickName).or().eq("account", username);
        return accountMapper.selectCount(queryWrapper) > 0;
    }
}
