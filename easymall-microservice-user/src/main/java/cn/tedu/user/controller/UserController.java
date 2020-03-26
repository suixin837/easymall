package cn.tedu.user.controller;

import cn.tedu.user.service.UserService;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/manage/")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("checkUserName")
    public SysResult checkUsername(String userName){
        Integer exist = service.checkUsername(userName);
        if (exist == 0){
            return SysResult.ok();
        }else {
            return SysResult.build(201,"用户名已存在",null);
        }
    }
    /*
        请求地址	user.jt.com/user/save
        请求方式	post
        请求参数	User user对象接收(对应前台的传参key=value&key=value结构)
        返回数据	新增成功失败返回1/0
	 */
    @RequestMapping("save")
    public SysResult userSave(User user){
       try {
           service.userSave(user);
           return SysResult.ok();
       }catch (Exception e){
           e.printStackTrace();
           return SysResult.build(201,e.getMessage(),null);
       }
    }
    //用户登录逻辑
    @RequestMapping("login")
    public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response){
        String ticket=service.doLogin(user);
        if(StringUtils.isNotEmpty(ticket)){
            //登陆成功
            CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
            return SysResult.ok();
        }
        return SysResult.build(201, "", null);
    }
    @RequestMapping("query/{ticket}")
    public SysResult queryTicket(@PathVariable String ticket){
        if(StringUtils.isNotEmpty(ticket)){
            String userJson=service.queryTicket(ticket);
            return SysResult.build(200, "", userJson);
        }
        return SysResult.build(201, "", null);
    }
    @RequestMapping("logout")
    public SysResult logout(HttpServletRequest request,HttpServletResponse response){
        //删除cookie
        CookieUtils.deleteCookie(request, response, "EM_TICKET");
        return SysResult.ok();
    }

}
