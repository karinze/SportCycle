/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.client.controller;

import fpt.aptech.client.dto.ItemsDTO;
import fpt.aptech.client.dto.UsersDTO;
import fpt.aptech.client.models.Items;
import fpt.aptech.client.models.Tokens;
import fpt.aptech.client.models.Users;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        Users users = rt.getForObject(urlauth + "/login/" + username + "/" + password, Users.class);
        if (users != null) {
            if (users.isRole()) {
                session.setAttribute("admin", username);

                return "redirect:/indexAdminItems";
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
            Users newUsers = new Users(user.getUser_id(), user.getUsername(), user.getPassword(), user.getEmail(), user.isRole(), user.getCreated_dt());
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
    public String doforgotpassword(Model model, @ModelAttribute("account") UsersDTO user) {
        Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
        if (users != null) {
            model.addAttribute("Tokens", rt.postForEntity(urlusers + "/sendmail", users, Tokens.class));
            return "redirect:/";
        } else {
            return "redirect:/forgotpassword";
        }

    }

    @GetMapping("/resetPassword/{token}")
    public String resetpassword(Model model, @PathVariable String token) {
        Tokens reset = rt.getForObject(urlusers + "/findtoken/" + token, Tokens.class);

        if (reset != null) {
            Users a = rt.getForObject(urlusers + "/findemail/" + reset.getUsers().getEmail(), Users.class);
            if (reset.getIs_active() == 0) {
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
            if (reset != null) {
                reset.setIs_active(1);
                Tokens resetpass = new Tokens(reset.getToken_id(), reset.getToken(), reset.getToken_expiry_date(), reset.getIs_active(), users);
                model.addAttribute("Tokens", rt.postForEntity(urlusers + "/savetoken", resetpass, Tokens.class));
            }
            users.setPassword(users.getPassword());

            users.setUsername(users.getUsername());

            Users acc = new Users(users.getUser_id(), users.getUsername(), users.getPassword(), users.getEmail(), users.isRole(), users.getCreated_dt());

            model.addAttribute("Users", rt.postForEntity(urlusers + "/resetpassword", acc, Users.class));

        }
        return "redirect:/";
    }
    // End Login

    //Start Items
    @GetMapping("/indexAdminItems")
public String index(Model model, HttpSession session,
                    @RequestParam(defaultValue = "0") int pageNumber,
                    @RequestParam(defaultValue = "10") int pageSize) {
    if (session.getAttribute("admin") != null) {
        // Assuming rt is your RestTemplate instance for making HTTP requests
        ResponseEntity<List<Items>> response = rt.exchange(
                urlitems + "/item" + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Items>>() {}
        );

        List<Items> itemList = response.getBody();

        // Get total number of items from the response headers or body
        int totalItems = itemList.size(); // Example: Replace with actual total items count

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // Add the paginated list, pagination parameters, and totalPages to the model
        model.addAttribute("list", itemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages); // Add totalPages to the model

        return "admin/indexAdminItems";
    } else {
        return "redirect:/";
    }
}

    @GetMapping("/createAdminItems")
    public String create(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            model.addAttribute("items", new Items());
            return "admin/createAdminItems";
        } else {

            return "redirect:/indexAdminItems";
        }

    }

    @PostMapping("/docreateAdminItems")
    public String docreate(Model model, @Valid @ModelAttribute("items") ItemsDTO items, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", new Items());
            return "admin/createAdminItems";
        } else {
            MultipartFile multipartFile = items.getImage();
            String fileName = multipartFile.getOriginalFilename();
            FileCopyUtils.copy(items.getImage().getBytes(), new File(FileUpload, fileName));
            items.setCreated_dt(Date.from(Instant.now()));
            Items newItem = new Items(items.getName(), items.getBrand(), items.getDescription(), items.getPrice(), items.getStock(), items.getType(), fileName, items.isIs_visible(), items.getCreated_dt());
            model.addAttribute("Items", rt.postForEntity(urlitems, newItem, Items.class));
            return "redirect:/indexAdminItems";

        }
    }

    @GetMapping("/editAdminItems/{id}")
    public String edit(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Items p = rt.getForObject(urlitems + "/" + id, Items.class);
            model.addAttribute("items", p);
            return "admin/editAdminItems";
        } else {

            return "redirect:/indexAdminItems";
        }

    }

    @PostMapping("/saveAdminItems")
    public String doedit(Model model, @Valid @ModelAttribute("items") ItemsDTO item, BindingResult bindingResult) throws IOException {
        Items items = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class);
        if (bindingResult.hasErrors()) {
            Items p = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class);
            model.addAttribute("items", p);
            return "admin/editAdminItems";
        } else {

            MultipartFile multipartFile = item.getImage();
            String fileName = multipartFile.getOriginalFilename();
            item.setCreated_dt(items.getCreated_dt());

            if (item.getImage().isEmpty()) {
                String file = items.getImage();
                Items editItems = new Items(item.item_id,item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getType(), file, item.isIs_visible(), item.getCreated_dt());
                model.addAttribute("Items", rt.postForEntity(urlitems, editItems, Items.class));
                return "redirect:/indexAdminItems";

            } else {
                FileCopyUtils.copy(item.getImage().getBytes(), new File(FileUpload, fileName));
                Items editItems = new Items(item.item_id,item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getType(), fileName, item.isIs_visible(), item.getCreated_dt());
                model.addAttribute("Items", rt.postForEntity(urlitems, editItems, Items.class));
                return "redirect:/indexAdminItems";
            }
        }
    }

    @GetMapping("/deleteAdminItems/{id}")
    public String delete(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            rt.delete(urlitems + "/" + id, Items.class);
            return "redirect:/indexAdminItems";
        } else {
            return "redirect:/";
        }

    }

    @GetMapping("/searchAdminItems")
public String search(Model model, @RequestParam("name") String name, HttpSession session,
                     @RequestParam(defaultValue = "0") int pageNumber,
                     @RequestParam(defaultValue = "10") int pageSize) {
    if (session.getAttribute("admin") != null) {
        if (name != null && !name.isEmpty()) {
            
            ResponseEntity<List<Items>> response = rt.exchange(
            urlitems + "/search/" + name + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Items>>() {}
        );
            
            List<Items> itemList = response.getBody();
            int totalItems = itemList.size(); // Example: Replace with actual total items count

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        // Add the paginated list and pagination parameters to the model
        model.addAttribute("list", itemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);
model.addAttribute("name", name);
        return "admin/indexAdminItems";
            
        } else {
            // Call index method if name is empty or null
            return index(model, session, pageNumber, pageSize);
        }
    } else {
        return "redirect:/"; // Redirect to homepage if admin session is not found
    }
}
}
