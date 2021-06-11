package com.example.scoringsystem.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.scoringsystem.bean.*;
import com.example.scoringsystem.service.LoginService;
import com.example.scoringsystem.service.RegisterService;
import com.example.scoringsystem.service.ScoreService;
import com.example.scoringsystem.service.UserService;
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
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * @Description: 展示学生列表页面通过班级
     * @Param: [model]
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/user/show/class")
    @ResponseBody
    public ResponseData toShowlist(@NotNull String classId) {
        User user=new User();
        user.setClassId(classId);
        List<User> list = loginService.getAllStudentUserByClassId(user);
        if (list!=null&&list.size()>0) {
            return new ResponseData("查询成功","200",list);
        }
        return new ResponseData("查询失败","1331","[]");
    }


    @RequestMapping("/user/show/all")
    @ResponseBody
    public ResponseData toShowlist() {
        List<User> list = loginService.selAllStudentUser();
        if (list!=null&&list.size()>0) {
            return new ResponseData("查询成功","200",list);
        }
        return new ResponseData("查询失败","1331","[]");
    }

//    /**
//     * @Description: web端登录（包含验证码）
//     * @Param: [user, verifyCode, model, session]
//     * @return: java.lang.String
//     * @Date: 2021/4/27
//     */
//    @GetMapping("/login")
//    public String login(User user, String verifyCode, Model model, HttpSession session) {
//        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(verifyCode) || session.getAttribute("verificationCode") == null) {
//            model.addAttribute("msg", "请输入用户名和密码,验证码");
//            return "loginpage";
//        }
//        String code = session.getAttribute("verificationCode").toString();
//        System.out.println("&&" + code + "&&&" + verifyCode);
//        if (StringUtils.isEmpty(code) || !(code.equalsIgnoreCase(verifyCode))) {
//            model.addAttribute("msg", "验证码错误!");
//            return "loginpage";
//        }
//        //获取当前用户
//        Subject subject = SecurityUtils.getSubject();
//        //封装用户的登录数据
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUserName(), user.getPassword());
//        try {
//            //进行登录
//            subject.login(usernamePasswordToken);
//            model.addAttribute("msg", "登录成功");
//            return "index";
//        } catch (UnknownAccountException e) {
//            log.error("用户名不存在", e);
//            model.addAttribute("msg", "用户名不存在");
//            return "loginpage";
//        } catch (AuthenticationException e) {
//            log.error("账户或密码错误", e);
//            model.addAttribute("msg", "账户或密码错误");
//            return "loginpage";
//        } catch (AuthorizationException e) {
//            log.error("没有权限", e);
//            model.addAttribute("msg", "没有权限");
//            return "loginpage";
//        }
//    }

    /**
     * @Description: 安卓端登录，不包含验证码
     * @Param: [user, model, session]
     * @return: com.example.scoring_system.bean.ResponseData
     * @Date: 2021/4/27
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseData loginAndroid(User user, Model model, HttpSession session, String verifyCode, HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = new ResponseData();
        log.info("取得的user" + user.toString());
        if (StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())||StringUtils.isEmpty(verifyCode)) {
            model.addAttribute("msg", "请输入用户名和密码");
            responseData.setMessage("请输入用户名和密码,验证码");
            responseData.setCode("1002");
            return responseData;
        }
//        String code=session.getAttribute("verificationCode").toString();
//        System.out.println("&&"+code+"&&&"+verifyCode);
//        if (StringUtils.isEmpty(code)||!(code.equalsIgnoreCase(verifyCode)))
//        {
//            model.addAttribute("msg","验证码错误!");
//            responseData.setMessage("验证码错误!");
//            responseData.setCode("1001");
//            return responseData;
//        }
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getAccount(), user.getPassword());
        try {
            //进行登录
            subject.login(usernamePasswordToken);
            user = userService.getUserByAccountWithoutPrivacy(user);
            log.info("返回的user:" + user.toString());
            String newToken = userService.generateJwtToken(user);
            response.setHeader("x-auth-token", newToken);
            responseData.setMessage("登录成功!");
            responseData.setCode("200");
            responseData.setData(user);
            return responseData;
        } catch (UnknownAccountException e) {
            log.error("用户名不存在", e);
            model.addAttribute("msg", "用户名不存在");
            responseData.setMessage("用户名不存在");
            responseData.setCode("1003");
            return responseData;
        } catch (AuthenticationException e) {
            log.error("账户或密码错误", e);
            model.addAttribute("msg", "账户或密码错误");
            responseData.setMessage("账户或密码错误");
            responseData.setCode("1004");
            return responseData;
        } catch (AuthorizationException e) {
            log.error("没有权限", e);
            model.addAttribute("msg", "没有权限");
            responseData.setMessage("没有权限");
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
    @RequiresUser
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

//    /**
//     * @Description: 注册
//     * @Param: [user, model]
//     * @return: java.lang.String
//     * @Date: 2021/4/27
//     */
//    @RequestMapping("/register")
//    public String register(User user, Model model) {
//        if (user.getPassword() == null || user.getUserName() == null) {
//            model.addAttribute("msg", "用户名,密码不能为空");
//            return "register";
//        }
//        Integer code = registerService.register(user);
//        if (code == -1) {
//            model.addAttribute("msg", "用户名已经存在");
//            return "register";
//        } else if (code == 0) {
//            model.addAttribute("msg", "注册失败");
//            return "register";
//        }
//        return "redirect:/login";
//    }


    @RequestMapping("/register")
    @ResponseBody
    public ResponseData androidRegister(User user, Model model) {
        ResponseData responseData = new ResponseData();
        if (user.getPassword() == null || user.getAccount() == null) {
            responseData.setCode("1033");
            responseData.setMessage("用户名,密码不能为空");
        }
        Integer code = registerService.register(user);
        if (code == -1) {
            responseData.setCode("1032");
            responseData.setMessage("用户名已经存在");
            responseData.setData("[]");
        } else if (code == 0) {
            responseData.setCode("1031");
            responseData.setMessage("注册失败");
            responseData.setData("[]");
        } else {
            responseData.setCode("200");
            responseData.setMessage("注册成功");
            responseData.setData("[]");
        }
        return responseData;
    }


    @RequestMapping("/detailspage")
    public String toDetails() {
        return "detalis";
    }

    @RequestMapping("/details/import")
    @ResponseBody
    public ResponseData importDetails(MultipartFile excel, Task task, Model model) {
        log.info("上传的文件名称：" + excel.getOriginalFilename() + "上传的作业名称" + task.toString());
        task.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        task.getCreateUser().setId(task.getCreteUserId());
        task.getClassRoom().setId(Integer.parseInt(task.getClassRoomId()));
        ResponseData responseData = new ResponseData();
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(2);//header标题
        try {
            List<Details> details = ExcelImportUtil.importExcel(excel.getInputStream(), Details.class, params);
            task.setDetailsList(details);
            responseData = scoreService.importTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (responseData.getCode() == null) {
            responseData.setCode("1041");
            responseData.setMessage("操作错误");
        }
        return responseData;
    }

    @RequestMapping("/studentimport")
    public String toImportStudent() {
        return "showlist";
    }

    /**
     * @Description: 学生批量导入(excel)
     * @Param: [excel, model]
     * @return: java.lang.String
     * @Date: 2021/4/27
     */
    @RequestMapping("/student/import")
    @ResponseBody
    public ResponseData importStudent(MultipartFile excel, User user, Model model) {
        log.info("上传的文件名称：" + excel.getOriginalFilename());
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(1);//header标题
        try {
            List<User> userList = ExcelImportUtil.importExcel(excel.getInputStream(), User.class, params);
            log.info("导入的数量:" + userList.size());
            List<User> userList1=userService.insUserBatch(userList, user);
            if (userList1!=null&userList1.size()>0)
            {
                return new ResponseData("导入失败，导入的账号已存在", "1401", userList1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData("导入成功", "200", "[]");
    }


    /** 
    * @Description: 结对团队的导入 
    * @Param: [excel, classRoomId, model] 
    * @return: com.example.scoringsystem.bean.ResponseData 
    * @Date: 2021/6/11 
    */
    @RequestMapping("/pair/import")
    @ResponseBody
    public ResponseData importPairExcel(@NotNull MultipartFile excel,@NotNull String classRoomId, Model model)
    {
        log.info("上传的文件名称：" + excel.getOriginalFilename());
        ImportParams params = new ImportParams();
        params.setTitleRows(1);//一级标题
        params.setHeadRows(1);//header标题
        Boolean flag=false;
        try {
            List<Pair> pairList = ExcelImportUtil.importExcel(excel.getInputStream(), Pair.class, params);
            log.info("导入的数量:" + pairList.size());
            flag=userService.savePair(pairList,classRoomId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (flag)
            {
                return new ResponseData("导入成功", "200", "[]");
            }
            else
            {
                return new ResponseData("导入失败，可能excel存在错误数据","2001","[]");
            }
        }
    }





    /**
     * @Description: 学生导出（excel）
     * @Param: [response, request]
     * @return: void
     * @Date: 2021/4/27
     */
    @RequestMapping("/student/export")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<User> userList = loginService.selAllStudentUser();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("学生列表", "用户信息"), User.class, userList);
        try {
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("学生列表.xls", StandardCharsets.UTF_8));
            ServletOutputStream os = response.getOutputStream();
            workbook.write(os);
            os.close();
            workbook.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/student/export/formwork")
    public void exportStudetnFormworkExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<User> userList = loginService.selAllStudentUser();
//        String path = System.getProperty("user.dir");
        //待下载文件名
        String fileName = "student.xls";
        //设置为png格式的文件
        response.setHeader("content-type", "image/png");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        String path = "E:\\其他\\三下\\软件工程\\t2\\git\\meeting-system-8\\v2.0\\scoring_system\\src\\main\\resources\\static\\excel\\";
        path="/usr/java/rescource/";
        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(path + fileName)));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/details/export/formwork")
    public void exportDetailsFormworkExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<User> userList = loginService.selAllStudentUser();
//        String path = System.getProperty("user.dir");
        //待下载文件名
        String fileName = "taskDetails.xls";
        //设置为png格式的文件
        response.setHeader("content-type", "image/png");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        String path="E:\\其他\\三下\\软件工程\\t2\\git\\meeting-system-8\\v2.0\\scoring_system\\src\\main\\resources\\static\\excel\\";
        path="/usr/java/rescource/";
        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(path + fileName)));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping("/team/export/formwork")
    public void exportTeamFormworkExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<User> userList = loginService.selAllStudentUser();
//        String path = System.getProperty("user.dir");
        //待下载文件名
        String fileName = "team.xls";
        //设置为png格式的文件
        response.setHeader("content-type", "image/png");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;
        String path = "E:\\其他\\三下\\软件工程\\t2\\git\\meeting-system-8\\v2.0\\scoring_system\\src\\main\\resources\\static\\excel\\";
        path="/usr/java/rescource/";
        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(path + fileName)));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/user/userInfo")
    @ResponseBody
    public ResponseData getUserInfoByUserId(@NotNull String userId) {
        log.info("查询userId：" + userId);
        User user = new User();
        user.setId(userId);
        UserVO userVO = userService.getUserAndClassRoomByUserId(user);
        if (userVO != null && userVO.getId() != null) {
            return new ResponseData("查询成功", "200", userVO);
        }
        return new ResponseData("查询失败", "1191", "[]");
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
