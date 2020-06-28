package com.demo.service.impl;

import com.demo.dao.AdminMapper;
import com.demo.pojo.Admin;
import com.demo.pojo.AdminExample;
import com.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(Admin admin) {
        AdminExample adminExample = new AdminExample();
        adminExample.setDistinct(true);
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(admin.getUsername());
        criteria.andPasswordEqualTo(admin.getPassword());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        return adminList.size() > 0 ? adminList.get(0) : null;
    }


}
