package com.cnsmash.config.login.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 注意：这里的equals和hashCode重写过。通过id进行相等判断
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginUser loginUser = (LoginUser) o;
        return id.equals(loginUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
