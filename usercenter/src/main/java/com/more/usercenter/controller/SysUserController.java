package com.more.usercenter.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.more.usercenter.entity.SysUser;
import com.more.usercenter.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2021-07-22 15:03:36
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUser selectOne(String id) {
        return this.sysUserService.queryById(id);
    }

    /**
     * 密码暂时使用明文 后期改
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody SysUser sysUser) {
        String msg = "用户名不存在或密码不正确！";
        SysUser su = sysUserService.queryByName(sysUser.getUsername());
        if (ObjectUtils.isNotEmpty(su)) {
            if(su.getPassword().equals(sysUser.getPassword())){
                return R.ok(msg);
            }
        }
        return R.failed(msg);
    }

    @PostMapping("/sign")
    public R Sign(@RequestBody SysUser sysUser) {
        Long idL = System.currentTimeMillis();

        sysUser.setId(String.valueOf(idL));
        try{
            sysUserService.insert(sysUser);
            return R.ok("成功");
        }catch (Exception e){
            return R.failed("注册失败："+e);
        }
    }
}
