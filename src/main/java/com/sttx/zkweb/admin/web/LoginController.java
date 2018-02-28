package com.sttx.zkweb.admin.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import com.sttx.zkweb.admin.dao.LoginDaoFactory;
import com.sttx.zkweb.model.Admin;

@Controller
public class LoginController extends AbstractController {
  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
        return null;
    }

    @RequestMapping(value = "/")
    public String login(Locale locale, Model model) {
        return "login";
    }

    @RequestMapping("LoginSubmit")
    public @ResponseBody String loginSubmit(String name, String password, HttpServletRequest request) {
        logger.info("LoginSubmit:name={}" + name);
        Admin adminLogin = LoginDaoFactory.getLoginDaoImpl().adminLogin(name);
        if (adminLogin == null) {
            return "0";
        }
        if (password.equals(adminLogin.getAdminPwd())) {
            // 登陆用户密码都正确
            request.getSession().setAttribute("username", adminLogin.getAdminName());
            return "1";
        } else
            return "0";
    }

}
