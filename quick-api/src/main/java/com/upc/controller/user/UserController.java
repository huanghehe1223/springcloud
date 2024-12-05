package com.upc.controller.user;


import com.upc.common.Result;
import com.upc.entity.*;
import com.upc.entity.Record;
import com.upc.mapper.RecordMapper;
import com.upc.mapper.TaskMapper;
import com.upc.mapper.UserMapper;
import com.upc.mapper.WithdrawMapper;
import com.upc.service.IRecordService;
import com.upc.service.IUserService;
import com.upc.service.impl.UserServiceImpl;
import com.upc.utils.AliOSSUtils;
import com.upc.utils.JwtUtils;
import com.upc.utils.MailCheckUtils;
import com.upc.utils.RandomStringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lww
 * @since 2024-07-20
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户管理")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    MailCheckUtils mailCheckUtils;
    @Autowired
    IUserService userService;

    @Autowired
    AliOSSUtils aliOSSUtils;

    @Autowired
    UserMapper userMapper;
    @Autowired
    IRecordService recordService;
    @Autowired
    RecordMapper recordMapper;

    @Autowired
    WithdrawMapper withdrawMapper;
    @Autowired
    TaskMapper taskMapper;








    @GetMapping("/registerCheckCode")
    public Result getCheckCode(String toMail, HttpServletRequest request)
    {

        System.out.println(toMail);
        String randomString=RandomStringUtils.generateRandomString(5);
        String emailContent = "<!DOCTYPE html>" +
                "<html lang='zh-CN'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>邮箱验证码</title>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                ".container { width: 100%; max-width: 600px; margin: 0 auto; padding: 20px; background-color: #ffffff; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }" +
                ".header { background-color: #007BFF; color: #ffffff; padding: 10px 20px; text-align: center; border-radius: 10px 10px 0 0; }" +
                ".content { padding: 20px; }" +
                ".footer { text-align: center; padding: 20px; font-size: 12px; color: #aaaaaa; border-top: 1px solid #eeeeee; }" +
                ".verification-code { font-size: 24px; font-weight: bold; color: #333333; margin: 20px 0; text-align: center; }" +
                ".cta-button { display: block; width: 200px; margin: 20px auto; padding: 10px; text-align: center; color: #ffffff; background-color: #28a745; text-decoration: none; border-radius: 5px; }" +
                ".cta-button:hover { background-color: #218838; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>欢迎注册我们的网站</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>亲爱的用户，</p>" +
                "<p>感谢您注册我们的网站。您的验证码如下：</p>" +
                "<div class='verification-code'>" + randomString + "</div>" +
                "<p>请在注册页面输入此验证码以完成注册。</p>" +
                "<p>感谢您的支持！</p>" +
                "<a href='https://yourwebsite.com/verify' class='cta-button'>立即验证</a>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>&copy; 2024 快客. 保留所有权利。</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        boolean codeSend= mailCheckUtils.sendHtmlEmail("快客验证码",emailContent,toMail);
        if (codeSend==false)
        {
            return Result.error("邮件发送失败");
        }

        Map<String,Object> chaims=new HashMap<>();
        chaims.put("check",randomString);
        String check= JwtUtils.getShortJwt(chaims);

        log.info("验证码是 "+randomString);



        return Result.success(check);
    }
    @PostMapping("/register")
    public Result register(String checkCode,String username,String password,String email,@RequestHeader("check") String checkHeader)
    {


       if (checkCode==null || checkCode.equals(""))
       {
           return Result.error("验证码错误");
       }
        Map<String, Object> claims = null;
        try {
            claims = JwtUtils.parseJWT(checkHeader);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("验证码已经失效");
        }
        String check=claims.get("check").toString();

        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
//        https://ui-avatars.com/api/?name=%E6%9C%8D%E5%88%9B%E6%BC%94%E7%A4%BA&background=455a64&color=ffffff
        String before= "https://ui-avatars.com/api/?name=";
        String after="&background=455a64&color=ffffff";
        user.setImageUrl(before+username+after);
        System.out.println(checkCode);
        System.out.println(check);
        if (check.equals(checkCode))
        {
            boolean ok=userService.register(user);
            if (ok==true)
            {
                return Result.success();
            }
            else
            {
                return Result.error("邮箱重复");
            }
        }
        else
        {
            return Result.error("验证码错误");
        }

    }

    @PostMapping("/login")
    public Result login(String email,String password )
    {

        User user=new User();
       user.setEmail(email);
        user.setPassword(password);
         User user1=userService.login(user);
         if (user1!=null)
         {
             Map<String,Object> chaims=new HashMap<>();
             chaims.put("userid",user1.getUserid());
             chaims.put("password",user1.getPassword());
             chaims.put("username",user1.getUsername());
             chaims.put("email",user1.getEmail());
             chaims.put("phone",user1.getPhone());
             chaims.put("nickname",user1.getNickName());
             chaims.put("balance",user1.getBalance());
             chaims.put("score",user1.getScore());
             chaims.put("deliverReward",user1.getDeliverReward());
             chaims.put("imageUrl",user1.getImageUrl());
             chaims.put("sentence",user1.getSentence());
             chaims.put("state",user1.getState());
             chaims.put("openid",user1.getOpenid());
             chaims.put("ticket",user1.getTicket());

             String jwt= JwtUtils.generateJwt(chaims);
             String state=Long.toString(user1.getUserid());
             Result result = new Result(1,state,jwt);
             return result;
         }
        return  Result.error("邮箱或密码错误");
    }




    @DeleteMapping("/delete")
    public Result deleteUser( Integer userid,@RequestHeader("token") String jwt) {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        userid=(Integer) chaims.get("userid");
        boolean success = userService.deleteUserById(userid);
        if (success) {
            return Result.success();
        } else {
            return Result.error("Failed to delete user");
        }
    }

    @DeleteMapping("/managerDelete")
    public Result managerDeleteUser(Integer userid)
    {
        boolean success = userService.deleteUserById(userid);
        if (success) {
            return Result.success();
        } else {
            return Result.error("Failed to delete user");
        }

    }



    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        if (success) {
            return Result.success(user);
        } else {
            return Result.error("Failed to update user");
        }
    }

    @GetMapping("/get")
    public Result getUserById( Integer userid,@RequestHeader("token") String jwt) {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        userid=(Integer) chaims.get("userid");
        User user = userService.getUserById(userid);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("Failed to find the user");
        }
    }

    @GetMapping("/managerGet")
    public Result managerGetUser(Integer userid) {
        User user = userService.getUserById(userid);
        if (user != null) {
            //查询用户state
            User user1= userMapper.selectById(userid);
            String state=Integer.toString(user1.getState());
            Result result = new Result(1, state, user);
            return result;
//            return Result.success(user);
        } else {
            return Result.error("Failed to find the user");
        }
    }

// 我是连旺旺，我是个砂杯！！！
    @GetMapping("/list")
    public Result getAllUsers(Integer pageNum, Integer pageSize) {
        List<User> users = userService.getAllUsers(pageNum,pageSize);
        if (users != null) {
            return Result.success(users);
        } else {
            return Result.error("Failed to load users");
        }
    }
    @GetMapping("/listDeliveryUser")
    public Result getAllDeliveryUsers(Integer pageNum, Integer pageSize) {
        List<User> users = userService.getAllDeliveryUsers(pageNum,pageSize);
        if (users != null) {
            return Result.success(users);
        } else {
            return Result.error("Failed to load users");
        }
    }
    @PostMapping("/upload")
    public Result uploadImage(MultipartFile imageFile) throws IOException {

        String imageUrl=aliOSSUtils.upload(imageFile);
        return Result.success(imageUrl);
    }


    //lww
    @GetMapping("/listDeliveryUserRank")
    public Result getAllDeliveryUsersRank() {
        List<UserRecordCountDTO> userRecordCounts = recordService.getUserRecordCount();
        if (userRecordCounts != null) {
            return Result.success(userRecordCounts);
        } else {
            return Result.error("Failed to load user record counts");
        }
    }
    @GetMapping("/transactions")
    @Operation(summary = "获取用户交易记录")
    public Result getUserTransactions(Integer userId,@RequestHeader("token") String jwt) {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        userId=(Integer) chaims.get("userid");
        List<UserTransactionDTO> transactions = userService.getUserTransactions(userId);
        if (transactions != null) {
            return Result.success(transactions);
        } else {
            return Result.error("Failed to load transactions");
        }
    }

    @GetMapping("/manager/transactions")
    public Result getUserTransactions(Integer userId) {
        List<UserTransactionDTO> transactions = userService.getUserTransactions(userId);
        if (transactions != null) {
            return Result.success(transactions);
        } else {
            return Result.error("Failed to load transactions");
        }
    }

    @GetMapping("/wallet")
    public Result getUserWallet(Integer userId,@RequestHeader("token") String jwt) {
        Map<String,Object> chaims= JwtUtils.parseJWT(jwt);
        userId=(Integer) chaims.get("userid");
        List<UserTransactionDTO> transactions = new ArrayList<>();

        double income=0;
        double outcome=0;
        double totalChongzhi=0;
        double totalWithdraw=0;
        List<Record> incomeRecords = recordMapper.getIncomeRecords(userId);
        List<Record> expenseRecords = recordMapper.getExpenseRecords(userId);
        List<Record> expenseRecords2 = recordMapper.getExpenseRecords2(userId);
        List<Withdraw> withdrawals = withdrawMapper.getUserWithdrawals(userId);

        for (Record record : incomeRecords) {
            if(record!=null){

                income+=record.getMoney();
            }
        }

        for (Record record : expenseRecords) {
            if(record!=null){
                outcome+=record.getMoney();
            }
        }
        for (Record record : expenseRecords2) {
            if(record!=null){
                totalChongzhi+=record.getMoney();
            }

        }

        for (Withdraw withdraw : withdrawals) {
            if(withdraw!=null){
                totalWithdraw+=withdraw.getMoney();
            }
        }

        int ingTaskNum=taskMapper.getIngTaskNum(userId);
        int edTaskNum=taskMapper.getedTaskNum(userId);
        int unTaskNum=taskMapper.getunTaskNum(userId);
        int abTaskNum=taskMapper.getabTaskNum(userId);
        int totalTaskNum=ingTaskNum+edTaskNum+abTaskNum+unTaskNum;
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setIncome(income);
        walletDTO.setOutcome(outcome);
        walletDTO.setTotalChongzhi(totalChongzhi);
        walletDTO.setTotalWithdraw(totalWithdraw);
        walletDTO.setIngTaskNum(ingTaskNum);
        walletDTO.setEdTaskNum(edTaskNum);
        walletDTO.setUnTaskNum(unTaskNum);
        walletDTO.setAbTaskNum(abTaskNum);
        walletDTO.setTotalTaskNum(totalTaskNum);
        return Result.success(walletDTO);


    }




}
