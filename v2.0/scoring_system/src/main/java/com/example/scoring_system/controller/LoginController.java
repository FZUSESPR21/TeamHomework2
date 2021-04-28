package com.example.scoring_system.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.scoring_system.bean.Details;
import com.example.scoring_system.bean.ResponseData;
import com.example.scoring_system.bean.User;
import com.example.scoring_system.service.LoginService;
import com.example.scoring_system.service.RegisterService;
import com.example.scoring_system.service.ScoreService;
import com.example.scoring_system.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
@Slf4j
@CrossOrigin
public class LoginController {

    @Autowired
    RegisterService registerService;
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Autowired
    ScoreService scoreService;

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping("/tologin")
    public String toLogin() {
        log.error("登录页面");
        return "loginpage";
    }

    /**
     * @Description: 展示学生列表页面
     * @Param: [model]
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/showlist")
    public String toShowlist(Model model) {
        List<User> list = loginService.selAllUser();
        model.addAttribute("users", list);
        return "showlist";
    }

    /**
     * @Description: web端登录（包含验证码）
     * @Param: [user, verifyCode, model, session]
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @GetMapping("/login")
    public String login(User user, String verifyCode, Model model, HttpSession session) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(verifyCode) || session.getAttribute("verificationCode") == null) {
            model.addAttribute("msg", "请输入用户名和密码,验证码");
            return "loginpage";
        }
        String code = session.getAttribute("verificationCode").toString();
        System.out.println("&&" + code + "&&&" + verifyCode);
        if (StringUtils.isEmpty(code) || !(code.equalsIgnoreCase(verifyCode))) {
            model.addAttribute("msg", "验证码错误!");
            return "loginpage";
        }
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            //进行登录
            subject.login(usernamePasswordToken);
            model.addAttribute("msg", "登录成功");
            return "index";
        } catch (UnknownAccountException e) {
            log.error("用户名不存在", e);
            model.addAttribute("msg", "用户名不存在");
            return "loginpage";
        } catch (AuthenticationException e) {
            log.error("账户或密码错误", e);
            model.addAttribute("msg", "账户或密码错误");
            return "loginpage";
        } catch (AuthorizationException e) {
            log.error("没有权限", e);
            model.addAttribute("msg", "没有权限");
            return "loginpage";
        }
    }

    /**
     * @Description: 安卓端登录，不包含验证码
     * @Param: [user, model, session]
     * @return: com.example.scoring_system.bean.ResponseData
     * @Date: 2021/4/27
     */
    @RequestMapping("/android/login")
    @ResponseBody
    public ResponseData loginAndroid(User user, Model model,String verifyCode, HttpSession session) {
        ResponseData responseData = new ResponseData();

        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            model.addAttribute("msg", "请输入用户名和密码");
            responseData.setMsg("请输入用户名和密码,验证码");
            responseData.setCode("1002");
            return responseData;
        }
        String code=session.getAttribute("verificationCode").toString();
        System.out.println("&&"+code+"&&&"+verifyCode);
        if (StringUtils.isEmpty(code)||!(code.equalsIgnoreCase(verifyCode)))
        {
            model.addAttribute("msg","验证码错误!");
            responseData.setMsg("验证码错误!");
            responseData.setCode("1001");
            return responseData;
        }
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            //进行登录
            subject.login(usernamePasswordToken);
            model.addAttribute("msg", "登录成功");
            responseData.setMsg("登录成功!");
            responseData.setCode("200");
            return responseData;
        } catch (UnknownAccountException e) {
            log.error("用户名不存在", e);
            model.addAttribute("msg", "用户名不存在");
            responseData.setMsg("用户名不存在");
            responseData.setCode("1003");
            return responseData;
        } catch (AuthenticationException e) {
            log.error("账户或密码错误", e);
            model.addAttribute("msg", "账户或密码错误");
            responseData.setMsg("账户或密码错误");
            responseData.setCode("1004");
            return responseData;
        } catch (AuthorizationException e) {
            log.error("没有权限", e);
            model.addAttribute("msg", "没有权限");
            responseData.setMsg("没有权限");
            responseData.setCode("1005");
            return responseData;
        }
    }

    /**
     * @Description: 权限测试admin
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequiresRoles("admin")
    @GetMapping("/admin")
    public String admin() {
        return "login admin";
    }

    /**
     * @Description: 权限测试
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequiresPermissions("query")
    @GetMapping("/query")
    public String query() {
        return "login query";
    }

    /**
     * @Description: 权限测试
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * @Description: 权限测试
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequiresPermissions("user:add")
    @GetMapping("/user/add")
    @ResponseBody
    public String addd() {
        return "add";
    }

    /**
     * @Description: 权限测试
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized() {
        return "没有通过权限验证";
    }

    /**
     * @Description: 退出登录
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * @Description: 转到注册页面
     * @Param: []
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/registerpage")
    public String toRegister() {
        return "register";
    }

    /**
     * @Description: 注册
     * @Param: [user, model]
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/register")
    public String register(User user, Model model) {
        if (user.getPassword() == null || user.getUserName() == null) {
            model.addAttribute("msg", "用户名,密码不能为空");
            return "register";
        }
        Integer code = registerService.register(user);
        if (code == -1) {
            model.addAttribute("msg", "用户名已经存在");
            return "register";
        } else if (code == 0) {
            model.addAttribute("msg", "注册失败");
            return "register";
        }
        return "redirect:/login";
    }


    @RequestMapping("/android/register")
    @ResponseBody
    public ResponseData androidRegister(User user, Model model) {
        ResponseData responseData=new ResponseData();
        if (user.getPassword() == null || user.getUserName() == null) {
            responseData.setCode("1033");
            responseData.setMsg("用户名,密码不能为空");
        }
        Integer code = registerService.register(user);
        if (code == -1) {
            responseData.setCode("1032");
            responseData.setMsg("用户名已经存在");
        } else if (code == 0) {
            responseData.setCode("1031");
            responseData.setMsg("注册失败");
        }
        else
        {
            responseData.setCode("200");
            responseData.setMsg("注册成功");
        }
        return responseData;
    }

    @RequestMapping("/detailspage")
    public String toDetails()
    {
        return "detalis";
    }

    @RequestMapping("/details/import")
    @ResponseBody
    public ResponseData importDetails(MultipartFile excel,Model model)
    {
        log.info("上传的文件名称："+excel.getOriginalFilename());
        ResponseData responseData=new ResponseData();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(2);//header标题
        try {
            List<Details> details = ExcelImportUtil.importExcel(excel.getInputStream(), Details.class, params);
            responseData=scoreService.importScoreDetais(details);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseData;
    }

    @RequestMapping("/studentimport")
    public String toImportStudent()
    {
        return "showlist";
    }

    /**
     * @Description: 学生批量导入(excel)
     * @Param: [excel, model]
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/student/import")
    public String importStudent(MultipartFile excel, Model model) {
        log.info("上传的文件名称：" + excel.getOriginalFilename());
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(1);//header标题
        try {
            List<User> userList = ExcelImportUtil.importExcel(excel.getInputStream(), User.class, params);
            log.info("导入的数量:" + userList.size());
            userService.insUserBatch(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/showlist";
    }

    /**
     * @Description: 学生导出（excel）
     * @Param: [response, request]
     * @return: void
     * @Date: 2021/4/27
     */
    @RequestMapping("/student/export")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<User> userList = loginService.selAllUser();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("学生列表", "用户信息"), User.class, userList);
        try {
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("学生列表.xls", "UTF-8"));
            ServletOutputStream os = response.getOutputStream();
            workbook.write(os);
            os.close();
            workbook.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    /**
     * @Description: 实现验证码
     * @Param: [httpServletRequest, httpServletResponse]
     * @return: void
     * @Date: 2021/4/27
     */
    @RequestMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        String code = defaultKaptcha.createText();
        httpServletRequest.getSession().setAttribute("verificationCode", code);
        BufferedImage image = defaultKaptcha.createImage(code);
        try {
            ImageIO.write(image, "jpg", jpegOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //输出图片
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        servletOutputStream.write(captchaChallengeAsJpeg);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
