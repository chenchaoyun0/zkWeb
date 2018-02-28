package com.sttx.zkweb.admin.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sttx.zkweb.admin.dao.LoginDaoFactory;
import com.sttx.zkweb.model.Admin;

@Controller
@RequestMapping("/operation/zk")
public class ChangePwdController extends AbstractController {
  private static final Logger logger = LoggerFactory.getLogger(ChangePwdController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        return null;
    }

    @RequestMapping(value = "/changePwd")
    public String changePwd(Locale locale, Model model) {
        return "ChangePwd";
    }

    @RequestMapping(value = "/changePwdOperation")
    public @ResponseBody String changePwdOperation(@RequestParam(required = true) String oldPwd,
            @RequestParam(required = true) String newPwd, Locale locale, Model model, HttpServletRequest request)
                    throws IOException {
        String adminName = (String) request.getSession().getAttribute("username");
        Admin adminLogin = LoginDaoFactory.getLoginDaoImpl().adminLogin(adminName);
        logger.info("changePwdOperation info:{}" + adminName);
        if (oldPwd.equals(adminLogin.getAdminPwd())) {
            adminLogin.setAdminPwd(newPwd);
            LoginDaoFactory.getLoginDaoImpl().updateAdminPwd(adminLogin);
            return "1";// 输入当前的密码正确
        }
        return "0";// 输入当前的密码错误

    }

    @RequestMapping(value = "/exitLogin")
    public @ResponseBody String changePwdOperation(Locale locale, Model model, HttpServletRequest request)
            throws IOException {
        logger.info("changePwdOperation exitLogin:{}");
        request.getSession().setAttribute("username", null);
        return "退出成功！";
    }

}
