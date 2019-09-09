package com.yzb.sec.domain.vo;

import com.yzb.sec.domain.orm.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class UserVO extends User {

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                '}';
    }
}
