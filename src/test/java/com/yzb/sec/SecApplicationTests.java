package com.yzb.sec;

import com.google.gson.Gson;
import com.yzb.sec.domain.vo.Menu;
import com.yzb.sec.service.ISysManageResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecApplicationTests {

    @Autowired
    private ISysManageResourceService iSysManageResourceService;

    @Test
    public void testMenu() {

        List<Menu> meuns = iSysManageResourceService.getAllMenu();
        System.out.println(new Gson().toJson(meuns));

    }


    @Test
    public void testRoleMenu() {

        List<Menu> meuns = iSysManageResourceService.listRoleTreeByRole(2);
        System.out.println(new Gson().toJson(meuns));

    }

    @Test
    public void testCheckRoleMenu() {

        List<Menu> meuns = iSysManageResourceService.listRoleTreeByRoleWithChecked(2);
        System.out.println(new Gson().toJson(meuns));

    }
}
