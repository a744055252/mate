package com.cnsmash.config.login.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author guanhuan_li
 * #date 2020/8/26 14:43
 */
@Data
public class LoginUser implements UserDetails, Serializable {
    private static final long serialVersionUID = -1257363449090585625L;
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private String mail;
    private String account;
    private String username;
    private String password;
    private RoleType roleType;
    private LoginType loginType;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
