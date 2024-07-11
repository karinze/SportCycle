/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.client.controller;

import fpt.aptech.client.dto.UsersDTO;
import fpt.aptech.client.models.Tokens;
import fpt.aptech.client.models.Users;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Manh_Chien
 */
@Controller
public class HomeController {
    
    public final String urladmin = "http://localhost:9999/api/admin/";
    public final String urlauth = "http://localhost:9999/api/auth/";
    public final String urlbikeproperties = "http://localhost:9999/api/bikeproperties/";
    public final String urlbikerentals = "http://localhost:9999/api/bikerentals/";
    public final String urlitems = "http://localhost:9999/api/items/";
    public final String urlorders = "http://localhost:9999/api/orders/";
    public final String urlusers = "http://localhost:9999/api/users/";
    public final String urluserorders = "http://localhost:9999/api/userorders/";
    private static final RestTemplate rt = new RestTemplate();
    
    @Value("${upload.path}")
    private String FileUpload;
    
    // Start Login
    @RequestMapping("/")
    public String page(Model model) {
        model.addAttribute("account", new Users());
        return "login/login";
    }

    @PostMapping("/login")
    public String dologin(Model model, @Param("username") String username, @Param("password") String password, HttpSession session) {

        Users users = rt.getForObject(urlusers + "/login/" + username+"/"+ password, Users.class);
        if (users != null) {
            if (users.isRole()) {
                session.setAttribute("admin", username);

                return "redirect:/indexProduct";
            } else {
                session.setAttribute("user", username);

                return "redirect:/indexProduct";
            }

        } else {
            model.addAttribute("account", new Users());
            return "login/login";

        }

    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new Users());
        return "login/register";
    }

    @PostMapping("/doregister")
    public String doregister(Model model, @Valid @ModelAttribute("account") UsersDTO user, HttpSession session, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {

            model.addAttribute("account", user);
            return "login/register";
        } else {
            user.setUser_id(UUID.randomUUID());
            user.setRole(false);
            user.setCreated_dt(Date.from(Instant.now()));
            Users newUsers = new Users(user.getUser_id(), user.getUsername(), user.getPassword(), user.getEmail(),user.isRole(), user.getCreated_dt());
            model.addAttribute("Users", rt.postForEntity(urlusers, newUsers, Users.class));
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {

        if (session.getAttribute("admin") != null) {
            session.removeAttribute("admin");
            return "redirect:/";
        } else {
            session.removeAttribute("user");
            return "redirect:/";

        }
    }

    @GetMapping("/forgotpassword")
    public String forgotpassword(Model model, HttpSession session) {
        model.addAttribute("account", new Users());
        return "login/forgotpassword";

    }

    @PostMapping("/doforgotpassword")
    public String doforgotpassword(Model model,@ModelAttribute("account") UsersDTO user) {
        Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
        if (users != null) {
            model.addAttribute("Tokens", rt.postForEntity(urlusers+"/sendmail", users, Tokens.class));
            return "redirect:/";
        }
        else {
            return "redirect:/forgotpassword";
        }

    }

    @GetMapping("/resetPassword/{token}")
    public String resetpassword(Model model, @PathVariable String token) {
        Tokens reset = rt.getForObject(urlusers + "/findtoken/" + token, Tokens.class);
        
        if (reset != null) {
            Users a = rt.getForObject(urlusers + "/findemail/" + reset.getUsers().getEmail(), Users.class);
            if (reset.getIs_active()== 0) {
                model.addAttribute("account", a);
                model.addAttribute("token", token);
                model.addAttribute("email", reset.getUsers().getEmail());
                return "login/resetpassword";
            } else {

                return "redirect:/forgotpassword?error";
            }
        } else {
            return "redirect:/forgotpassword?error";
        }

    }

    @PostMapping("/resetPassword")
    public String doresetpassword(Model model, @ModelAttribute("account") UsersDTO user) {
        Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
        String t = user.getUsername();
        Tokens reset = rt.getForObject(urlusers + "/findtoken/" + t, Tokens.class);
        if (users != null) {
            if(reset != null){
                reset.setIs_active(1);
                Tokens resetpass = new Tokens(reset.getToken_id(), reset.getToken(), reset.getToken_expiry_date(), reset.getIs_active(), users);
                model.addAttribute("Tokens", rt.postForEntity(urlusers + "/savetoken", resetpass, Tokens.class));
            }
            users.setPassword(users.getPassword());
            
            users.setUsername(users.getUsername());
            

  
            Users acc = new Users(users.getUser_id(), users.getUsername(), users.getPassword(), users.getEmail(), users.isRole(),users.getCreated_dt());
            
            model.addAttribute("Users", rt.postForEntity(urlusers + "/resetpassword", acc, Users.class));
            

        }
        return "redirect:/";
    }
    // End Login

    
}
