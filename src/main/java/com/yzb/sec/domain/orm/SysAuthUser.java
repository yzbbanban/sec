package com.yzb.sec.domain.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wangban
 * @data 2019/9/11 15:40
 */
public class SysAuthUser implements UserDetails {
    private Integer id;
    private String account;
    private String pass;
    private String realName;
    private Boolean locked;
    private String mobile;
    private String token;
    private List<SysManageRole> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @JsonIgnore
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<SysManageRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysManageRole> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysAuthUser{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pass='" + pass + '\'' +
                ", realName='" + realName + '\'' +
                ", locked=" + locked +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (SysManageRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return !locked;
    }
}
