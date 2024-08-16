/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import fpt.aptech.client.dto.BikePropertiesDTO;
import fpt.aptech.client.dto.CouponsDTO;
import fpt.aptech.client.dto.ItemsDTO;
import fpt.aptech.client.dto.OrdersDTO;
import fpt.aptech.client.dto.UserDetailsDTO;
import fpt.aptech.client.dto.UsersDTO;
import fpt.aptech.client.models.BikeProperties;
import fpt.aptech.client.models.BikeRentals;
import fpt.aptech.client.models.CartItems;
import fpt.aptech.client.models.CouponUsers;
import fpt.aptech.client.models.Coupons;
import fpt.aptech.client.models.ExternalTokens;
import fpt.aptech.client.models.Items;
import fpt.aptech.client.models.OrderItems;
import fpt.aptech.client.models.Orders;
import fpt.aptech.client.models.Tokens;
import fpt.aptech.client.models.UserDetails;
import fpt.aptech.client.models.Users;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    public final String urlexternaltoken = "http://localhost:9999/api/externalTokens/";
    public final String urlorderitems = "http://localhost:9999/api/orderitems/";
    public final String urlcoupons = "http://localhost:9999/api/coupons/";
    public final String urlcouponusers = "http://localhost:9999/api/couponusers/";
    public final String urluserdetail = "http://localhost:9999/api/userdetail/";

    private static final RestTemplate rt = new RestTemplate();

    @Value("${paypal.client.id}")
    private String clientIdPaypal;

    @Value("${paypal.client.secret}")
    private String clientSecretPaypal;

    @Value("${paypal.mode}")
    private String mode;

    @Value("${paypal.cancel.url}")
    private String cancelUrl;

    @Value("${paypal.success.url}")
    private String successUrl;

    @Value("${upload.path}")
    private String FileUpload;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    private final String authUrl = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String tokenUrl = "https://oauth2.googleapis.com/token";
    private final String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

    // Start Login
    @GetMapping("/login")
    public String page(Model model) {
        model.addAttribute("account", new Users());
        return "login/login";
    }

    @PostMapping("/login")
    public String dologin(Model model, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        String email = username;
        Users users = rt.getForObject(urlauth + "/login/" + email + "/" + username + "/" + password, Users.class);
        if (users != null) {
            if (users.isRole()) {
                session.setAttribute("admin", username);
                return "redirect:/dashboard";
            } else {
                session.setAttribute("user", users.getEmail());
                Users user = rt.getForObject(urlusers + "/findemail/" + users.getEmail(), Users.class);
                session.setAttribute("username", user.getUsername());
                return "redirect:/";
            }
        } else {
            model.addAttribute("account", new Users());
            model.addAttribute("error", "Account does not exist");
            return "login/login";
        }
    }

    @GetMapping("/loginGoogle")
    public RedirectView loginGoogle() {
        String url = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=profile email",
                authUrl, clientId, redirectUri);
        return new RedirectView(url);
    }

    @GetMapping("/oauth2/callback/google")
    public ModelAndView callback(@RequestParam("code") String code, HttpSession session) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Exchange authorization code for access token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = String.format(
                "code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
                code, clientId, clientSecret, redirectUri);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, String.class);

        // Extract access token, refresh token, and expires in from response
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseJson = mapper.readTree(responseBody);
        String accessToken = responseJson.get("access_token").asText();
        String refreshToken = responseJson.has("refresh_token") ? responseJson.get("refresh_token").asText() : null;
        int expiresIn = responseJson.get("expires_in").asInt();
        Date tokenExpires = new Date(System.currentTimeMillis() + (expiresIn * 1000L));

        // Fetch user info
        String userInfo = restTemplate.getForObject(userInfoUrl + "?access_token=" + accessToken, String.class);
        JsonNode userInfoJson = mapper.readTree(userInfo);
        String email = userInfoJson.get("email").asText();
        String username = userInfoJson.get("name").asText();

        // Check if the user already exists
        Users user = restTemplate.getForObject(urlusers + "/findemail/" + email, Users.class);
        if (user == null) {
            // Create new user if not found
            user = new Users();
            user.setUser_id(UUID.randomUUID());
            user.setEmail(email);
            user.setUsername(username);
            user.setRole(false); // Set default role or based on logic
            user.setCreated_dt(new Date());
            restTemplate.postForEntity(urlusers, user, Users.class);
        } else {
            // Check if the user already has a valid token
            ExternalTokens existingToken = restTemplate.getForObject(urlexternaltoken + "/findbyuser/" + user.getUser_id(), ExternalTokens.class);
            if (existingToken != null && existingToken.getToken_expires().after(new Date())) {
                // Token is still valid, set user in session and redirect
                session.setAttribute("user", email);
                return new ModelAndView("redirect:/");
            }
        }

        // Save new access token and other details
        ExternalTokens externalToken = new ExternalTokens();
        externalToken.setAccess_token(accessToken);
        externalToken.setRefresh_token(refreshToken);
        externalToken.setToken_expires(tokenExpires);
        externalToken.setCreated_dt(new Date());
        externalToken.setUsers(user);
        restTemplate.postForEntity(urlexternaltoken, externalToken, ExternalTokens.class);

        session.setAttribute("user", email);

        return new ModelAndView("redirect:/");
    }

    private String extractAccessToken(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new Users());
        return "login/register";
    }

    @PostMapping("/doregister")
    public String doregister(Model model, @Valid @ModelAttribute("account") UsersDTO user, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", user);
            if (!user.getEmail().isEmpty()) {
                Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
                if (users != null) {
                    bindingResult.rejectValue("email", "error.account", "Email already exists");
                }
            }
            if (!user.getUsername().isEmpty()) {
                Users users = rt.getForObject(urlusers + "/findusername/" + user.getUsername(), Users.class);
                if (users != null) {
                    bindingResult.rejectValue("username", "error.account", "Username already exists");
                }
            }
            return "login/register";
        } else {
            if (!user.getEmail().isEmpty()) {
                Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
                if (users != null) {
                    bindingResult.rejectValue("email", "error.account", "Email already exists");
                    return "login/register";
                }
            }

            user.setUser_id(UUID.randomUUID());
            user.setRole(false);
            user.setCreated_dt(Date.from(Instant.now()));
            Users newUsers = new Users(user.getUser_id(), user.getUsername(), user.getPassword(), user.getEmail(), user.isRole(), user.getCreated_dt());
            rt.postForEntity(urlusers, newUsers, Users.class);
            model.addAttribute("success", "Account registration successful");
            model.addAttribute("account", new Users());
            return "login/login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {

        if (session.getAttribute("admin") != null) {
            session.removeAttribute("admin");
            return "redirect:/login";
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
        Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class
        );

        if (users != null) {
            model.addAttribute("Tokens", rt.postForEntity(urlusers + "/sendmail", users, Tokens.class));
            model.addAttribute("email", "We have sent the link to reset your password to your email!");
            model.addAttribute("error", null); // No error if email is found
        } else {
            model.addAttribute("error", "This email address does not exist");
            model.addAttribute("email", null); // No success message if email is not found
        }
        model.addAttribute("account", user);
        return "login/forgotpassword";

    }

    @GetMapping("/resetPassword/{token}")
    public String resetpassword(Model model, @PathVariable String token) {
        Tokens reset = rt.getForObject(urlusers + "/findtoken/" + token, Tokens.class
        );

        if (reset != null) {
            Users a = rt.getForObject(urlusers + "/findemail/" + reset.getUsers().getEmail(), Users.class
            );
            if (reset.getIs_active() == 0) {
                model.addAttribute("account", a);
                model.addAttribute("token", token);
                model.addAttribute("email", reset.getUsers().getEmail());
                return "login/resetpassword";
            } else {

                model.addAttribute("account", new Users());
                model.addAttribute("error", "The link has expired");
                return "login/forgotpassword";
            }
        } else {
            return "redirect:/forgotpassword?error";
        }

    }

    @PostMapping("/resetPassword")
    public String doresetpassword(Model model, @ModelAttribute("account") UsersDTO user) {
        Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class
        );
        String t = user.getUsername();
        Tokens reset = rt.getForObject(urlusers + "/findtoken/" + t, Tokens.class
        );
        if (users != null) {
            if (reset != null) {
                reset.setIs_active(1);
                Tokens resetpass = new Tokens(reset.getToken_id(), reset.getToken(), reset.getToken_expiry_date(), reset.getIs_active(), users);
                model
                        .addAttribute("Tokens", rt.postForEntity(urlusers + "/savetoken", resetpass, Tokens.class
                        ));
            }
            users.setPassword(user.getPassword());

            users.setUsername(users.getUsername());

            Users acc = new Users(users.getUser_id(), users.getUsername(), users.getPassword(), users.getEmail(), users.isRole(), users.getCreated_dt());

            model.addAttribute("Users", rt.postForEntity(urlusers + "/resetpassword", acc, Users.class));
        }
        model.addAttribute("success", "Changed password successfully");
        model.addAttribute("account", new Users());
        return "login/login";
    }

    // End Login
    //Start Items
    @GetMapping("/indexAdminItems")
    public String indexAdmin(Model model, HttpSession session) {
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "5") int pageSize
        if (session.getAttribute("admin") != null) {
//            ResponseEntity<List<Items>> response = rt.exchange(
//                    urlitems + "/item" + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<Items>>() {
//            }
//            );
            List<Items> p = rt.getForObject(urlitems + "/", List.class
            );
//
//            List<Items> itemList = response.getBody();
//
//            int totalItems = p.size(); // Ensure totalItems is calculated correctly
//            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
//
//            if (session.getAttribute("countcartItems") != null) {
//                int count = (int) session.getAttribute("countcartItems");
//                model.addAttribute("countcartItems", count);
//            } else {
//                model.addAttribute("countcartItems", 0);
//            }
//
            model.addAttribute("list", p);
//            model.addAttribute("currentPage", pageNumber);
//            model.addAttribute("pageSize", pageSize);
//            model.addAttribute("totalPages", totalPages);

            return "admin/itemsAdmin";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) throws JsonProcessingException {
        if (session.getAttribute("admin") != null) {
            BigDecimal MonthlyRevenue = rt.getForObject("http://localhost:9999/api/orders/getMonthlyRevenue", BigDecimal.class);
            BigDecimal TotalRevenue = rt.getForObject("http://localhost:9999/api/orders/getTotalRevenue", BigDecimal.class);
            Long PendingRequests = rt.getForObject("http://localhost:9999/api/orders/getPendingRequests", Long.class);
            Long TotalQuantitySold = rt.getForObject("http://localhost:9999/api/orderitems/getTotalQuantitySold", Long.class);

            List<Map<String, Object>> monthlyEarnings = rt.getForObject("http://localhost:9999/api/orders/findMonthlyEarnings", List.class);

            List<String> labels = new ArrayList<>();
            List<BigDecimal> earnings = new ArrayList<>();
            for (Map<String, Object> entry : monthlyEarnings) {
                labels.add("Month " + entry.get("month"));
                Double totalAsDouble = (Double) entry.get("total");
                BigDecimal totalAsBigDecimal = BigDecimal.valueOf(totalAsDouble);
                earnings.add(totalAsBigDecimal);
            }

//            ObjectMapper objectMapper = new ObjectMapper();
//            String labelsJson = objectMapper.writeValueAsString(labels);
//            String earningsJson = objectMapper.writeValueAsString(earnings);
            DecimalFormat formatter = new DecimalFormat("#,###");
            String formattedRevenue = MonthlyRevenue != null ? formatter.format(MonthlyRevenue) : "0";
            String formattedTotalRevenue = TotalRevenue != null ? formatter.format(TotalRevenue) : "0";
            Long formattedTotalQuantitySold = TotalQuantitySold != null ? TotalQuantitySold : 0;

// Add to model
            model.addAttribute("monthlyRevenue", formattedRevenue);
            model.addAttribute("totalRevenue", formattedTotalRevenue);
            model.addAttribute("totalQuantitySold", formattedTotalQuantitySold);
            model.addAttribute("pendingRequests", PendingRequests);
            model.addAttribute("earnings", earnings);
            model.addAttribute("labels", labels);

            return "admin/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/indexAdminCoupons")
    public String indexAdminCoupons(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            List<Coupons> p = rt.getForObject(urlcoupons + "/", List.class);
            model.addAttribute("list", p);
            return "admin/couponsAdmin";
        } else {

            return "redirect:/login";
        }

    }

    @GetMapping("/createAdminCoupons")
    public String createAdminCoupons(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            model.addAttribute("coupons", new Coupons());
            return "admin/createAdminCoupons";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping("/createAdminCoupons")
    public String docreateAdminCoupons(Model model, @Valid @ModelAttribute("coupons") CouponsDTO coupons, BindingResult bindingResult) throws IOException {
        if (coupons.getCode().isEmpty()) {
            if (bindingResult.hasErrors()) {
                System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());
                model.addAttribute("coupons", coupons);
                return "admin/createAdminCoupons";
            }
        } else {
            Coupons c = rt.getForObject(urlcoupons + "/findcode/" + coupons.getCode(), Coupons.class);
            if (bindingResult.hasErrors()) {
                System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());
                if (c != null) {
                    bindingResult.rejectValue("code", "error.code", "Code already exists.");
                }
                model.addAttribute("coupons", coupons);
                return "admin/createAdminCoupons";
            } else if (c != null) {
                bindingResult.rejectValue("code", "error.code", "Code already exists.");
                model.addAttribute("coupons", coupons);
                return "admin/createAdminCoupons";
            } else {
                coupons.setCreated_dt(Date.from(Instant.now()));

                Coupons coupon = new Coupons(coupons.getCode(), coupons.getDiscount_amount(), coupons.getExpiration_date(), coupons.getUsage_limit(), coupons.isIs_active(), coupons.getCreated_dt());
                model.addAttribute("Coupons", rt.postForEntity(urlcoupons, coupon, Coupons.class));
                return "redirect:/indexAdminCoupons";
            }
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/editAdminCoupons/{id}")
    public String editAdminCoupons(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Coupons cou = rt.getForObject(urlcoupons + "/" + id, Coupons.class);
            model.addAttribute("coupons", cou);
            return "admin/editAdminCoupons";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping("/editAdminCoupons")
    public String doeditAdminCoupons(Model model, @Valid @ModelAttribute("coupons") CouponsDTO coupons, BindingResult bindingResult) throws IOException {
        if (coupons.getCode().isEmpty()) {
            if (bindingResult.hasErrors()) {
                System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());
                model.addAttribute("coupons", coupons);
                return "admin/editAdminCoupons";
            }
        } else {
            Coupons c = rt.getForObject(urlcoupons + "/findcode/" + coupons.getCode(), Coupons.class);
            Coupons cou = rt.getForObject(urlcoupons + "/" + coupons.getCoupon_id(), Coupons.class);

            if (bindingResult.hasErrors()) {
                System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());
                model.addAttribute("coupons", coupons);
                return "admin/editAdminCoupons";
            } else if (c != null && !coupons.getCode().equals(cou.getCode())) {
                bindingResult.rejectValue("code", "error.code", "Code already exists.");
                model.addAttribute("coupons", coupons);
                return "admin/editAdminCoupons";
            } else {
                Coupons coupon = new Coupons(cou.getCoupon_id(), coupons.getCode(), coupons.getDiscount_amount(),
                        coupons.getExpiration_date(), coupons.getUsage_limit(),
                        coupons.isIs_active(), cou.getCreated_dt());
                model.addAttribute("Coupons", rt.postForEntity(urlcoupons, coupon, Coupons.class));
                return "redirect:/indexAdminCoupons";
            }
        }
        return null;
    }

    @GetMapping("/deleteAdminCoupons/{id}")
    public String deleteAdminCoupons(Model model, @PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("admin") != null) {
            List<CouponUsers> p = rt.getForObject(urlcouponusers + "/coupon/" + id, List.class);

            if (p != null) {
                for (CouponUsers couponUsers : p) {
                    rt.delete(urlcouponusers + "/" + couponUsers.getCoupon_user_id(), CouponUsers.class);
                }

            }

            rt.delete(urlcoupons + "/" + id, Coupons.class);
            return "redirect:/indexAdminCoupons";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/editAdminUserProfile/{id}")
    public String editAdminUserProfile(Model model, @PathVariable UUID id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Users user = rt.getForObject(urlusers + "/" + id, Users.class);
            model.addAttribute("userprofile", new UserDetails());
            UserDetails[] cou = rt.getForObject(urluserdetail + "/user/" + id, UserDetails[].class);
            if (cou != null) {
                for (UserDetails userDetails : cou) {
                    model.addAttribute("userprofile", userDetails);
                }

            }

            model.addAttribute("email", user.getEmail());
            return "admin/editUserProfile";
        } else {

            return "redirect:/login";
        }
    }

    @PostMapping("/editAdminUserProfile")
    public String doeditAdminUserProfile(Model model, @Valid @ModelAttribute("userprofile") UserDetailsDTO ud, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());
            model.addAttribute("email", ud.getEmail());
            model.addAttribute("userprofile", ud);
            return "admin/editUserProfile";
        } else {
            Users user = rt.getForObject(urlusers + "/findemail/" + ud.getEmail(), Users.class);
            if (ud.getUserdetail_id() == 0) {
                ud.setCreated_dt(new Date());
                UserDetails userd = new UserDetails(user, ud.getFirst_name(), ud.getLast_name(), ud.getEmail(), ud.getPhone_number(), ud.getAddress(), ud.getNote(), ud.getCreated_dt());
                rt.postForObject(urluserdetail, userd, UserDetails.class);
            } else {
                UserDetails[] ude = rt.getForObject(urluserdetail + "/user/" + user.getUser_id(), UserDetails[].class);
                for (UserDetails userDetails : ude) {
                    UserDetails userd = new UserDetails(ud.getUserdetail_id(), user, ud.getFirst_name(), ud.getLast_name(), ud.getEmail(), ud.getPhone_number(), ud.getAddress(), ud.getNote(), userDetails.getCreated_dt());
                    rt.postForObject(urluserdetail, userd, UserDetails.class);
                }

            }
        }
        return "redirect:/indexAdminUsers";
    }

    @GetMapping("/createAdminItems")
    public String create(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            model.addAttribute("items", new Items());
            return "admin/createAdminItems";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping("/createAdminItems")
    public String docreate(Model model, @Valid @ModelAttribute("items") ItemsDTO items, BindingResult bindingResult, @RequestParam(value = "otherBrand", required = false) String otherBrand) throws IOException {
        MultipartFile multipartFile = items.getImage();

        if (bindingResult.hasErrors() || (multipartFile != null && !multipartFile.isEmpty() && !multipartFile.getContentType().startsWith("image/"))) {
            System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());

            if (multipartFile != null && !multipartFile.isEmpty() && !multipartFile.getContentType().startsWith("image/")) {
                bindingResult.rejectValue("image", "error.items", "Only image files are allowed.");
            }
            model.addAttribute("otherBrand", otherBrand);
            model.addAttribute("items", items);
            return "admin/createAdminItems";
        } else {
            if ("0".equals(items.getBrand())) {
                items.setBrand(otherBrand);
            }
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                FileCopyUtils.copy(items.getImage().getBytes(), new File(FileUpload, fileName));
                items.setCreated_dt(Date.from(Instant.now()));

                Items newItem = new Items(
                        items.getName(), items.getBrand(), items.getDescription(),
                        items.getPrice(), items.getStock(), items.getRentalquantity(), items.getType(),
                        fileName, items.isIs_visible(), items.getCreated_dt()
                );

                ResponseEntity<Items> response = rt.postForEntity(urlitems, newItem, Items.class);
                Items item = response.getBody();

                if ("Bike".equals(items.getType())) {
                    return "redirect:/createAdminBikeProperties?itemId=" + item.getItem_id();
                } else {
                    BikeProperties bikeProperties = new BikeProperties();
                    bikeProperties.setCreated_dt(Date.from(Instant.now()));

                    BikeProperties newBikeProperties = new BikeProperties(item, null, null, null, null, null, bikeProperties.getCreated_dt());
                    model.addAttribute("BikeProperties", rt.postForEntity(urlbikeproperties, newBikeProperties, BikeProperties.class));
                    return "redirect:/indexAdminItems";
                }
            } else {
                bindingResult.rejectValue("image", "error.items", "Image file is required.");
                return "admin/createAdminItems";
            }
        }
    }

    @GetMapping("/createAdminBikeProperties")
    public String createBikeProperties(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            model.addAttribute("bikeProperties", new BikeProperties());
            return "admin/createAdminBikeProperties";
        } else {
            return "redirect:/login";
        }

    }

    @PostMapping("/createAdminBikeProperties")
    public String docreateBikeProperties(Model model, @RequestParam("itemId") Long itemId, @Valid @ModelAttribute("bikeProperties") BikePropertiesDTO bikeProperties, BindingResult bindingResult) {

        Items item = rt.getForObject(urlitems + "/" + itemId, Items.class);
        bikeProperties.setCreated_dt(Date.from(Instant.now()));

        BikeProperties newBikeProperties = new BikeProperties(item, bikeProperties.getBike_size(), bikeProperties.getBike_wheel_size(), bikeProperties.getBike_color(), bikeProperties.getBike_material(), bikeProperties.getBike_brake_type(), bikeProperties.getCreated_dt());
        model.addAttribute("BikeProperties", rt.postForEntity(urlbikeproperties, newBikeProperties, BikeProperties.class));
        return "redirect:/indexAdminItems";

    }

    @GetMapping("/editAdminBikeProperties/{id}")
    public String editAdminBikeProperties(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            BikeProperties p = rt.getForObject(urlbikeproperties + "/" + id, BikeProperties.class
            );
            model.addAttribute("bikeProperties", p);
            return "admin/editAdminBikeProperties";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping("/editAdminBikeProperties")
    public String doeditAdminBikeProperties(Model model, @RequestParam("itemId") Long itemId, @Valid @ModelAttribute("bikeProperties") BikePropertiesDTO bikeProperties, BindingResult bindingResult) {

        BikeProperties item = rt.getForObject(urlbikeproperties + "/" + bikeProperties.getBike_property_id(), BikeProperties.class);
        bikeProperties.setCreated_dt(Date.from(Instant.now()));

        BikeProperties newBikeProperties = new BikeProperties(item.getBike_property_id(), item.getItem(), bikeProperties.getBike_size(), bikeProperties.getBike_wheel_size(), bikeProperties.getBike_color(), bikeProperties.getBike_brake_type(), bikeProperties.getBike_material(), item.getCreated_dt());
        model.addAttribute("BikeProperties", rt.postForEntity(urlbikeproperties, newBikeProperties, BikeProperties.class));
        return "redirect:/indexAdminItems";

    }

    @GetMapping("/editAdminItems/{id}")
    public String edit(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Items p = rt.getForObject(urlitems + "/" + id, Items.class
            );
            model.addAttribute("items", p);
            model.addAttribute("otherBrand", null);
            return "admin/editAdminItems";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping("/saveAdminItems")
    public String doedit(Model model, @Valid @ModelAttribute("items") ItemsDTO item, BindingResult bindingResult, @RequestParam(value = "otherBrand", required = false) String otherBrand) throws IOException {
        Items items = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class);

        if (bindingResult.hasErrors()) {
            Items p = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class);
            model.addAttribute("otherBrand", otherBrand);
            model.addAttribute("items", item);
            return "admin/editAdminItems";
        } else {
            if ("0".equals(item.getBrand())) {
                item.setBrand(otherBrand);
            }
            MultipartFile multipartFile = item.getImage();
            String fileName = multipartFile.getOriginalFilename();
            item.setCreated_dt(items.getCreated_dt());

            if (!multipartFile.isEmpty()) {
                // Check if the uploaded file is an image
                String contentType = multipartFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    model.addAttribute("fileError", "The uploaded file is not an image.");
                    return "admin/editAdminItems";
                }
            }

            if (multipartFile.isEmpty()) {
                String file = items.getImage();
                Items editItems = new Items(item.getItem_id(), item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getRentalquantity(), item.getType(), file, item.isIs_visible(), item.getCreated_dt());
                model.addAttribute("Items", rt.postForEntity(urlitems, editItems, Items.class));
                return "redirect:/indexAdminItems";
            } else {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(FileUpload, fileName));
                Items editItems = new Items(item.getItem_id(), item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getRentalquantity(), item.getType(), fileName, item.isIs_visible(), item.getCreated_dt());
                model.addAttribute("Items", rt.postForEntity(urlitems, editItems, Items.class));
                return "redirect:/indexAdminItems";
            }
        }
    }

    @GetMapping("/deleteAdminItems/{id}")
    public String delete(Model model, @PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("admin") != null) {
            BikeProperties p = rt.getForObject(urlbikeproperties + "/item/" + id, BikeProperties.class);

            // Change this line to handle a list of OrderItems
            OrderItems[] orderItemsArray = rt.getForObject(urlorderitems + "/items/" + id, OrderItems[].class);

            if (orderItemsArray == null || orderItemsArray.length == 0) {
                if (p == null) {
                    rt.delete(urlitems + "/" + id, Items.class);
                    return "redirect:/indexAdminItems";
                } else {
                    rt.delete(urlbikeproperties + "/" + p.getBike_property_id());
                    rt.delete(urlitems + "/" + id, Items.class);
                    return "redirect:/indexAdminItems";
                }

            } else {
                redirectAttributes.addFlashAttribute("error", "Products that have been sold cannot be deleted");
                return "redirect:/indexAdminItems";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/searchAdminItems")
    public String search(Model model, @RequestParam("name") String name, HttpSession session,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        if (session.getAttribute("admin") != null) {
            if (name != null && !name.isEmpty()) {
                ResponseEntity<List<Items>> response = rt.exchange(
                        urlitems + "/searchp/" + name + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Items>>() {
                });

                List<Items> p = rt.getForObject(urlitems + "/search/" + name, List.class
                );

                List<Items> itemList = response.getBody();

                int totalItems = p.size(); // Ensure totalItems is calculated correctly
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
//                return index(model, session, pageNumber, pageSize);
                return "redirect:/";
            }
        } else {
            return "redirect:/login"; // Redirect to homepage if admin session is not found
        }
    }

    @GetMapping("/indexAdminUsers")
    public String
            indexAdminUsers(Model model, HttpSession session) {
//            @RequestParam(defaultValue = "0") int pageNumber,
//            @RequestParam(defaultValue = "5") int pageSize
        if (session.getAttribute("admin") != null) {
//            ResponseEntity<List<Items>> response = rt.exchange(
//                    urlitems + "/item" + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<Items>>() {
//            }
//            );
            List<Items> p = rt.getForObject(urlusers + "/", List.class
            );
//
//            List<Items> itemList = response.getBody();
//
//            int totalItems = p.size(); // Ensure totalItems is calculated correctly
//            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
//
//            if (session.getAttribute("countcartItems") != null) {
//                int count = (int) session.getAttribute("countcartItems");
//                model.addAttribute("countcartItems", count);
//            } else {
//                model.addAttribute("countcartItems", 0);
//            }
//
            model.addAttribute("list", p);
//            model.addAttribute("currentPage", pageNumber);
//            model.addAttribute("pageSize", pageSize);
//            model.addAttribute("totalPages", totalPages);

            return "admin/usersAdmin";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/indexAdminOrders")
    public String
            indexAdminOrders(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {

            List<Items> p = rt.getForObject(urlorders + "/", List.class
            );

            model.addAttribute("list", p);

            return "admin/ordersAdmin";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/viewAdminOrder/{id}")
    public String viewOrderDetail(@PathVariable("id") int orderId, Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Orders order = rt.getForObject(urlorders + "/" + orderId, Orders.class);
            ResponseEntity<List<OrderItems>> response = rt.exchange(
                    urlorderitems + "/order/" + orderId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<OrderItems>>() {
            }
            );
            List<OrderItems> orderItems = response.getBody();

            // Calculate total price of all items using BigDecimal
            BigDecimal totalPrice = orderItems.stream()
                    .map(item -> item.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Calculate the discount
            BigDecimal discount = totalPrice.subtract(order.getTotal_amount());

            // Add attributes to the model
            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("discount", discount);

            return "admin/viewOrdersAdmin";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/indexAdminRentals")
    public String indexAdminRentals(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {

            List<Items> p = rt.getForObject(urlbikerentals + "/", List.class);

            model.addAttribute("list", p);

            return "admin/rentalsAdmin";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editAdminOrders")
    public String editAdminOrders(Model model, @Valid @ModelAttribute("order") OrdersDTO order, BindingResult bindingResult, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Orders orders = rt.getForObject(urlorders + "/" + order.getOrder_id(), Orders.class);

            Orders o = new Orders(orders.getOrder_id(), orders.getUsers(), orders.getTotal_amount(), orders.getOrder_date(), order.getStatus(), orders.getCreated_dt());
            rt.postForEntity(urlorders, o, Orders.class);

            return "redirect:/indexAdminOrders";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/CancelOrderlast")
    public String CancelOrderlast(Model model, @Valid @ModelAttribute("order") OrdersDTO order, BindingResult bindingResult, HttpSession session) {
        Orders orders = rt.getForObject(urlorders + "/" + order.getOrder_id(), Orders.class);

        Orders o = new Orders(orders.getOrder_id(), orders.getUsers(), orders.getTotal_amount(), orders.getOrder_date(), order.getStatus(), orders.getCreated_dt());
        rt.postForEntity(urlorders, o, Orders.class);
        OrderItems[] orderItems = rt.getForObject(urlorderitems + "/order/" + order.getOrder_id(), OrderItems[].class);

        // Loop through each order item to update the stock
        for (OrderItems orderItem : orderItems) {
            Items items = rt.getForObject(urlitems + "/" + orderItem.getItem().getItem_id(), Items.class);

            // Update the stock
            int newStock = items.getStock() + orderItem.getQuantity();
            items.setStock(newStock);

            // Set visibility based on the new stock
            if (newStock > 0) {
                items.setIs_visible(true);
            }
            Items item = new Items(items.getItem_id(), items.getName(), items.getBrand(), items.getDescription(), items.getPrice(), newStock, items.getRentalquantity(), items.getType(), items.getImage(), items.isIs_visible(), items.getCreated_dt());
            // Update the item details
            rt.postForObject(urlitems + "/", item, Items.class);
        }

        return "redirect:/latestOrder";
    }

    @PostMapping("/CancelOrder")
    public String CancelOrder(Model model, @Valid @ModelAttribute("oorder") OrdersDTO order, BindingResult bindingResult, HttpSession session) {
        Orders orders = rt.getForObject(urlorders + "/" + order.getOrder_id(), Orders.class);

        Orders o = new Orders(orders.getOrder_id(), orders.getUsers(), orders.getTotal_amount(), orders.getOrder_date(), order.getStatus(), orders.getCreated_dt());
        rt.postForEntity(urlorders, o, Orders.class);
        OrderItems[] orderItems = rt.getForObject(urlorderitems + "/order/" + order.getOrder_id(), OrderItems[].class);

        // Loop through each order item to update the stock
        for (OrderItems orderItem : orderItems) {
            Items items = rt.getForObject(urlitems + "/" + orderItem.getItem().getItem_id(), Items.class);

            // Update the stock
            int newStock = items.getStock() + orderItem.getQuantity();
            items.setStock(newStock);

            // Set visibility based on the new stock
            if (newStock > 0) {
                items.setIs_visible(true);
            }
            Items item = new Items(items.getItem_id(), items.getName(), items.getBrand(), items.getDescription(), items.getPrice(), newStock, items.getRentalquantity(), items.getType(), items.getImage(), items.isIs_visible(), items.getCreated_dt());
            // Update the item details
            rt.postForObject(urlitems + "/", item, Items.class);
        }

        return "redirect:/informationLine";
    }

    @PostMapping("/addToCart/{itemId}/{quantity}")
    @ResponseBody
    public ResponseEntity<Integer> addToCart(@PathVariable int itemId, @PathVariable int quantity, HttpSession session) {
        // Retrieve item details from API or database
        CartItems item = rt.getForObject(urlitems + "/" + itemId, CartItems.class
        );

        // Retrieve cart from session
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            session.setAttribute("cartItems", cartItems);
        }

        // Check if the item is already in the cart
        boolean itemExists = false;
        for (CartItems cartItem : cartItems) {
            if (cartItem.getItem_id() == item.getItem_id()) {
                cartItem.setTotalQuantity(cartItem.getTotalQuantity() + quantity);
                itemExists = true;
                break;
            }
        }

        // If the item is not in the cart, add it with the specified quantity
        if (!itemExists) {
            item.setTotalQuantity(quantity);
            cartItems.add(item);
        }

        // Update the total number of items in the cart
        int totalItems = cartItems.stream().mapToInt(CartItems::getTotalQuantity).sum();
        session.setAttribute("countcartItems", totalItems);

        // Return the new cart item count
        return ResponseEntity.ok(totalItems);
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
//        ParameterizedTypeReference<List<Items>> responseType = new ParameterizedTypeReference<List<Items>>() {
//        };
//        ResponseEntity<List<Items>> response = rt.exchange(urlitems + "/", HttpMethod.GET, null, responseType);
//        List<Items> p = response.getBody();
//
//        // Initialize a new list to hold the top 10 items
//        List<Items> a = new ArrayList<>();
//
//        // Ensure there are at least 10 items in the list
//        int limit = Math.min(p.size(), 10);
//        for (int i = 0; i < limit; i++) {
//            a.add(p.get(i));
//        }

        List<Items> a = rt.getForObject(urlitems + "/top10", List.class);
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        model.addAttribute("top10", a);
        return "user/index";
    }

    @GetMapping("/about")
    public String about(Model model, HttpSession session) {
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }
        model.addAttribute("account", new Users());
        return "user/about";
    }

//    @GetMapping("/rentals")
//    public String rentals(Model model, HttpSession session) {
//        String email = (String) session.getAttribute("user");
//        if (email == null) {
//            return "redirect:/login";
//        }
//
//        Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
//        List<BikeRentals> rentals = rt.getForObject(urlbikerentals + "/user/" + user.getUser_id(), List.class);
//
//        model.addAttribute("rentals", rentals);
//        return "user/rentals";
//    }
    @GetMapping("/rentals")
    public String rentals(@RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "2") int pageSize,
            Model model,
            HttpSession session) {
        String email = (String) session.getAttribute("user");

        if (email == null) {
            return "redirect:/login";
        }

        Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
        // Fetch the rentals and total count separately
        List<BikeRentals> rentalsPage = rt.getForObject(
                urlbikerentals + "/userpage/" + user.getUser_id() + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                List.class);

        List<BikeRentals> totalItems = rt.getForObject(urlbikerentals + "/user/" + user.getUser_id(), List.class);
        int totalRentals = totalItems.size();
        int totalPages = (int) Math.ceil((double) totalRentals / pageSize);

        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }
        model.addAttribute("rentals", rentalsPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        return "user/rentals";
    }

    @GetMapping("/forrent")
    public String forrent(@RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "2") int pageSize,
            Model model,
            HttpSession session) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            return "redirect:/login";
        }

        Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
        // Fetch the rentals and total count separately
        List<BikeRentals> rentalsPage = rt.getForObject(
                urlbikerentals + "/userpages/" + user.getUser_id() + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                List.class);

        List<BikeRentals> totalItems = rt.getForObject(urlbikerentals + "/users/" + user.getUser_id(), List.class);
        int totalRentals = totalItems.size();
        int totalPages = (int) Math.ceil((double) totalRentals / pageSize);
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        model.addAttribute("rentals", rentalsPage);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        return "user/forrent";
    }

    @PostMapping("/rentBicycle")
    public String rentBicycle(@RequestParam("itemId") int itemId,
            @RequestParam("rentalStartDate") String rentalStartDateStr,
            @RequestParam("rentalEndDate") String rentalEndDateStr,
            HttpSession session, Model model) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            return "redirect:/login";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime rentalStartDate = LocalDateTime.parse(rentalStartDateStr, formatter);
        LocalDateTime rentalEndDate = LocalDateTime.parse(rentalEndDateStr, formatter);

        double hourlyRate = 10 / 24.0;
        double costPerMinute = hourlyRate / 60.0;
        long minutesBetween = java.time.Duration.between(rentalStartDate, rentalEndDate).toMinutes();
        double cost = costPerMinute * minutesBetween;

        // PayPal payment
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", cost));

        Transaction transaction = new Transaction();
        transaction.setDescription("Bike rental payment");
        transaction.setAmount(amount);

        List<Transaction> transactions = List.of(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8888/rentCancel"); // URL to redirect if the user cancels the payment
        redirectUrls.setReturnUrl("http://localhost:8888/rentSuccess"); // URL to redirect after successful payment

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        if (session.getAttribute("itemid") != null) {
            session.removeAttribute("itemid");
        }
        if (session.getAttribute("rentalEndDateStr") != null) {
            session.removeAttribute("rentalEndDateStr");
        }

        session.setAttribute("itemid", itemId);
        session.setAttribute("rentalStartDateStr", rentalStartDateStr);
        session.setAttribute("rentalEndDateStr", rentalEndDateStr);

        try {
            APIContext apiContext = new APIContext("AWqlKfsHAv3mt15xEpyoO20PckFdXO_6gXAZWvvReKLEShYmakNINoAAZLj94O8T66LtSmToXmAdtR8C", "EPHptZexuSMZcyBBBpm_-y5TSt89l6R_eP2C8cXAon74BkajFfjJIX5uzvJboIRT7rBrhUEUqVvl1YE4", "sandbox");
            Payment createdPayment = payment.create(apiContext);

            for (Links link : createdPayment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error occurred during payment processing.");
            return "redirect:/shop"; // Return to the form with an error message
        }

        return "redirect:/forrent";
    }

    @GetMapping("/rentSuccess")
    public String rentSuccess(HttpSession session, Model model) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            return "redirect:/login";
        }
        String rentalStartDateStr = (String) session.getAttribute("rentalStartDateStr");
        String rentalEndDateStr = (String) session.getAttribute("rentalEndDateStr");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime rentalStartDate = LocalDateTime.parse(rentalStartDateStr, formatter);
        LocalDateTime rentalEndDate = LocalDateTime.parse(rentalEndDateStr, formatter);

        int itemId = (int) session.getAttribute("itemid");
        Items item = rt.getForObject(urlitems + "/" + itemId, Items.class);
        Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);

        BikeRentals bikeRental = new BikeRentals();
        bikeRental.setItem(item);
        bikeRental.setUsers(user);
        bikeRental.setRental_start_date(Date.from(rentalStartDate.atZone(ZoneId.systemDefault()).toInstant()));
        bikeRental.setRental_end_date(Date.from(rentalEndDate.atZone(ZoneId.systemDefault()).toInstant()));
        bikeRental.setIs_active(true);
        bikeRental.setCreated_dt(Date.from(Instant.now()));

        item.setRentalquantity(item.getRentalquantity() - 1);

        Items it = new Items(itemId, item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getRentalquantity(), item.getType(), item.getImage(), item.isIs_visible(), item.getCreated_dt());
        BikeRentals rentals = rt.postForObject(urlbikerentals + "/", bikeRental, BikeRentals.class);
        Items i = rt.postForObject(urlitems + "/", it, Items.class);

        session.removeAttribute("rentalStartDateStr");
        session.removeAttribute("rentalEndDateStr");
        session.removeAttribute("itemid");

        model.addAttribute("message", "Payment successful. Your bike rental has been confirmed.");
        return "redirect:/forrent"; // Redirect to a success page
    }

    @GetMapping("/rentCancel")
    public String rentCancel(Model model, HttpSession session) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            return "redirect:/login";
        }
        session.removeAttribute("rentalEndDateStr");
        int itemId = (int) session.getAttribute("itemid");
        model.addAttribute("message", "Payment was cancelled.");
        return "redirect:/shopdetail/" + itemId; // Redirect to a cancellation page
    }

    @PostMapping("/endRental")
    @ResponseBody
    public ResponseEntity<String> endRental(@RequestParam("rentalId") int rentalId, HttpSession session) {
        String email = (String) session.getAttribute("user");

        BikeRentals rental = rt.getForObject(urlbikerentals + "/" + rentalId, BikeRentals.class);
        if (rental == null) {
            return ResponseEntity.badRequest().body("Rental not found");
        }
        if (!rental.isIs_active()) {
            return ResponseEntity.badRequest().body("Rental is already inactive");
        }

        Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
        Items item = rental.getItem();
        Items i = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class);
        item.setRentalquantity(item.getRentalquantity() + 1);
        rental.setIs_active(false);

        Items it = new Items(item.getItem_id(), i.getName(), i.getBrand(), i.getDescription(), i.getPrice(), item.getStock(), item.getRentalquantity(), i.getType(), i.getImage(), i.isIs_visible(), i.getCreated_dt());
        rt.postForObject(urlitems + "/", it, Items.class);
        rt.postForObject(urlbikerentals + "/", rental, BikeRentals.class);

        return ResponseEntity.ok("Rental ended and stock updated");
    }

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");

        if (cartItems == null) {
            cartItems = new ArrayList<>(); // Initialize empty list if null
        }

        // Calculate total price and merge cart items
        Map<Integer, CartItems> mergedCart = new LinkedHashMap<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItems item : cartItems) {
            if (mergedCart.containsKey(item.getItem_id())) {
                CartItems existingItem = mergedCart.get(item.getItem_id());
                existingItem.setTotalQuantity(existingItem.getTotalQuantity() + item.getTotalQuantity());
            } else {
                CartItems newItem = new CartItems();
                newItem.setItem_id(item.getItem_id());
                newItem.setName(item.getName());
                newItem.setPrice(item.getPrice());
                newItem.setImage(item.getImage());
                newItem.setTotalQuantity(item.getTotalQuantity());
                mergedCart.put(item.getItem_id(), newItem);
            }

            totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getTotalQuantity())));
        }

        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        List<CartItems> mergedCartItems = new ArrayList<>(mergedCart.values());

        // Apply coupons
        List<Coupons> appliedCoupons = (List<Coupons>) session.getAttribute("appliedCoupons");
        if (appliedCoupons == null) {
            appliedCoupons = new ArrayList<>();
        }

        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (Coupons coupon : appliedCoupons) {
            totalDiscount = totalDiscount.add(coupon.getDiscount_amount());
        }

        BigDecimal finalTotalPrice = totalPrice.subtract(totalDiscount);

        model.addAttribute("cartItems", mergedCartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("finalTotalPrice", finalTotalPrice);
        model.addAttribute("appliedCoupons", appliedCoupons);

        return "user/cart";
    }

    @PostMapping("/apply-coupon")
    public String applyCoupon(@RequestParam("couponCode") String couponCode, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") != null) {

            if (couponCode == null) {
                return "redirect:/cart";
            }
            List<Coupons> appliedCoupons = (List<Coupons>) session.getAttribute("appliedCoupons");
            String email = (String) session.getAttribute("user");
            if (appliedCoupons == null) {
                appliedCoupons = new ArrayList<>();
            }

            for (Coupons coupon : appliedCoupons) {
                if (coupon.getCode().equalsIgnoreCase(couponCode)) {
                    redirectAttributes.addFlashAttribute("couponError", "This coupon code is already applied.");
                    return "redirect:/cart";
                }
            }

            Coupons coupon = rt.getForObject(urlcoupons + "/findcode/" + couponCode, Coupons.class);
            if (coupon == null || !coupon.isIs_active() || coupon.getExpiration_date().before(new Date()) || coupon.getUsage_limit() <= 0) {
                redirectAttributes.addFlashAttribute("couponError", "Invalid coupon code.");
                return "redirect:/cart";
            }

            Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
            CouponUsers[] couuser = rt.getForObject(urlcouponusers + "/user/" + user.getUser_id(), CouponUsers[].class);

            for (CouponUsers couponUsers : couuser) {
                if (couponCode.equals(couponUsers.getCoupons().getCode())) {
                    redirectAttributes.addFlashAttribute("couponError", "You have already used the discount code.");
                    return "redirect:/cart";
                }
            }

            coupon.setUsage_limit(coupon.getUsage_limit() - 1);

            appliedCoupons.add(coupon);
            session.setAttribute("appliedCoupons", appliedCoupons);
            redirectAttributes.addFlashAttribute("couponSuccess", "Coupon applied successfully.");
            session.setAttribute("couponCode", couponCode);
            return "redirect:/cart";

        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            String email = (String) session.getAttribute("user");
            List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>(); // Initialize empty list if null
            }

            BigDecimal totalPrice = BigDecimal.ZERO;

            for (CartItems item : cartItems) {
                totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getTotalQuantity())));
                Items items = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class);
                if (items.getStock() < item.getTotalQuantity()) {
                    model.addAttribute("error", "The quantity in the shopping cart is greater than the quantity in stock");

                    // Recalculate total price and merge cart items for cart page
                    Map<Integer, CartItems> mergedCart = new LinkedHashMap<>();
                    BigDecimal totalPrices = BigDecimal.ZERO;

                    for (CartItems i : cartItems) {
                        if (mergedCart.containsKey(i.getItem_id())) {
                            CartItems existingItem = mergedCart.get(i.getItem_id());
                            existingItem.setTotalQuantity(existingItem.getTotalQuantity() + i.getTotalQuantity());
                        } else {
                            CartItems newItem = new CartItems();
                            newItem.setItem_id(i.getItem_id());
                            newItem.setName(i.getName());
                            newItem.setPrice(i.getPrice());
                            newItem.setImage(i.getImage());
                            newItem.setTotalQuantity(i.getTotalQuantity());
                            mergedCart.put(i.getItem_id(), newItem);
                        }
                        totalPrices = totalPrices.add(i.getPrice().multiply(BigDecimal.valueOf(i.getTotalQuantity())));
                    }

                    if (session.getAttribute("countcartItems") != null) {
                        int count = (int) session.getAttribute("countcartItems");
                        model.addAttribute("countcartItems", count);
                    } else {
                        model.addAttribute("countcartItems", 0);
                    }

                    List<CartItems> mergedCartItems = new ArrayList<>(mergedCart.values());

                    model.addAttribute("cartItems", mergedCartItems);
                    model.addAttribute("totalPrice", totalPrices);
                    return "user/cart";
                }
            }

            // Apply coupons
            List<Coupons> appliedCoupons = (List<Coupons>) session.getAttribute("appliedCoupons");
            if (appliedCoupons == null) {
                appliedCoupons = new ArrayList<>();
            }

            BigDecimal totalDiscount = BigDecimal.ZERO;
            for (Coupons coupon : appliedCoupons) {
                totalDiscount = totalDiscount.add(coupon.getDiscount_amount());
            }

            BigDecimal finalTotalPrice = totalPrice.subtract(totalDiscount);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalDiscount", totalDiscount);
            model.addAttribute("finalTotalPrice", finalTotalPrice);

            if (session.getAttribute("countcartItems") != null) {
                int count = (int) session.getAttribute("countcartItems");
                model.addAttribute("countcartItems", count);
            } else {
                model.addAttribute("countcartItems", 0);
            }
            model.addAttribute("account", new Users());
            Users u = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
            model.addAttribute("userdetail", new UserDetails());
            UserDetails[] ud = rt.getForObject(urluserdetail + "/user/" + u.getUser_id(), UserDetails[].class);

            if (ud != null) {
                for (UserDetails userDetails : ud) {
                    model.addAttribute("userdetail", userDetails);
                }
            }
            model.addAttribute("email", email);
            return "user/checkout";
        } else {
            return "redirect:/login";
        }
    }

    private APIContext getAPIContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientIdPaypal, clientSecretPaypal, mode);
        return apiContext;
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("paymentMethod") String paymentMethod, @Valid @ModelAttribute("userdetail") UserDetailsDTO userdetail, BindingResult bindingResult,
            @ModelAttribute("account") Users account,
            HttpSession session, Model model) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            model.addAttribute("message", "User not logged in.");
            return "user/checkout";
        }

        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("message", "Your cart is empty.");
            return "user/checkout";
        }
        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getTotalQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply coupons
        List<Coupons> appliedCoupons = (List<Coupons>) session.getAttribute("appliedCoupons");
        if (appliedCoupons == null) {
            appliedCoupons = new ArrayList<>();
        }

        BigDecimal totalDiscount = appliedCoupons.stream()
                .map(Coupons::getDiscount_amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal finalTotalPrice = totalPrice.subtract(totalDiscount);

        // Add cart details to the model
        if (bindingResult.hasErrors()) {
            model.addAttribute("userdetail", userdetail);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalDiscount", totalDiscount);
            model.addAttribute("finalTotalPrice", finalTotalPrice);
            if (session.getAttribute("countcartItems") != null) {
                int count = (int) session.getAttribute("countcartItems");
                model.addAttribute("countcartItems", count);
            } else {
                model.addAttribute("countcartItems", 0);
            }
            model.addAttribute("account", new Users());
            return "user/checkout";
        } else {
            Users users;
            try {
                users = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
            } catch (Exception e) {
                model.addAttribute("message", "Failed to retrieve user details.");
                return "user/checkout";
            }

            if (cartItems == null || cartItems.isEmpty()) {
                model.addAttribute("message", "Your cart is empty.");
                return "user/checkout";
            }

            // Apply coupons
            if (appliedCoupons == null) {
                appliedCoupons = new ArrayList<>();
            }
            Users user = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
            if (userdetail.getUserdetail_id() == 0) {
                userdetail.setCreated_dt(new Date());
                UserDetails userd = new UserDetails(user, userdetail.getFirst_name(), userdetail.getLast_name(), userdetail.getEmail(), userdetail.getPhone_number(), userdetail.getAddress(), userdetail.getNote(), userdetail.getCreated_dt());
                rt.postForObject(urluserdetail, userd, UserDetails.class);
            } else {
                UserDetails[] ud = rt.getForObject(urluserdetail + "/user/" + user.getUser_id(), UserDetails[].class);
                for (UserDetails userDetails : ud) {
                    UserDetails userd = new UserDetails(userdetail.getUserdetail_id(), user, userdetail.getFirst_name(), userdetail.getLast_name(), userdetail.getEmail(), userdetail.getPhone_number(), userdetail.getAddress(), userdetail.getNote(), userDetails.getCreated_dt());
                    rt.postForObject(urluserdetail, userd, UserDetails.class);
                }

            }

            session.setAttribute("totalPrice", finalTotalPrice);

            Orders order = new Orders();
            order.setUsers(users);
            order.setTotal_amount(finalTotalPrice);
            order.setOrder_date(new Date());
            order.setStatus("OnlinePayment");
            order.setCreated_dt(new Date());

            session.setAttribute("pendingOrder", order);
            session.setAttribute("cartItems", cartItems);
            session.setAttribute("appliedCoupons", appliedCoupons);

            if ("online".equals(paymentMethod)) {
                // Handle PayPal payment
                try {
                    Payment payment = createPayment(finalTotalPrice.doubleValue(), "USD", "paypal",
                            "sale", "Order payment", cancelUrl, successUrl);
                    for (Links link : payment.getLinks()) {
                        if (link.getRel().equals("approval_url")) {
                            return "redirect:" + link.getHref();
                        }
                    }
                } catch (PayPalRESTException e) {
                    e.printStackTrace();
                    model.addAttribute("message", "Failed to process PayPal payment.");
                    return "user/checkout";
                }
            } else if ("offline".equals(paymentMethod)) {

                order.setStatus("Pending");
                Orders savedOrder = rt.postForObject(urlorders, order, Orders.class);
                List<OrderItems> orderItems = new ArrayList<>();
                for (CartItems cartItem : cartItems) {
                    try {
                        Items items = rt.getForObject(urlitems + "/" + cartItem.getItem_id(), Items.class);
                        OrderItems orderItem = new OrderItems();
                        orderItem.setOrders(savedOrder);
                        orderItem.setItem(items);
                        orderItem.setQuantity(cartItem.getTotalQuantity());
                        orderItem.setPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getTotalQuantity())));
                        orderItem.setCreated_dt(new Date());

                        OrderItems savedOrderItem = rt.postForObject(urlorderitems, orderItem, OrderItems.class);

                        int sub = items.getStock() - cartItem.getTotalQuantity();
                        if (sub <= 0) {
                            items.setIs_visible(false);
                        }
                        Items item = new Items(items.getItem_id(), items.getName(), items.getBrand(), items.getDescription(), items.getPrice(), sub, items.getRentalquantity(), items.getType(), items.getImage(), items.isIs_visible(), items.getCreated_dt());
                        rt.postForObject(urlitems + "/", item, Items.class);
                        orderItems.add(savedOrderItem);
                    } catch (Exception e) {
                        model.addAttribute("message", "Failed to save order item for " + cartItem.getItem_id());
                        return "user/checkout";
                    }
                }
                for (Coupons coupon : appliedCoupons) {
                    CouponUsers couponUser = new CouponUsers();
                    couponUser.setCoupons(coupon);
                    couponUser.setUsers(order.getUsers());
                    couponUser.setCreated_dt(new Date());
                    rt.postForObject(urlcouponusers, couponUser, CouponUsers.class);
                }
                String couponCode = (String) session.getAttribute("couponCode");
                if (couponCode != null) {
                    Coupons coupon = rt.getForObject(urlcoupons + "/findcode/" + couponCode, Coupons.class);
                    if (coupon.getUsage_limit() > 0) {
                        int usagelimit = coupon.getUsage_limit() - 1;
                        if (usagelimit <= 0) {
                            coupon.setIs_active(false);
                        }
                        Coupons c = new Coupons(coupon.getCoupon_id(), coupon.getCode(), coupon.getDiscount_amount(), coupon.getExpiration_date(), usagelimit, coupon.isIs_active(), coupon.getCreated_dt());
                        rt.postForObject(urlcoupons + "/", c, Coupons.class);
                    }
                    session.removeAttribute("couponCode");
                }

                session.removeAttribute("cartItems");
                session.setAttribute("countcartItems", 0);
                session.setAttribute("totalPrice", BigDecimal.ZERO);
                session.removeAttribute("appliedCoupons");
                session.removeAttribute("pendingOrder");

                MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
                parts.add("users", users);
                parts.add("orders", savedOrder);
                parts.add("orderItems", orderItems);

                // Send the request using a POST request with multipart/form-data
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

                rt.postForEntity(urlorders + "/sendbillmail", requestEntity, Void.class);
                model.addAttribute("paymentMethod", "offline");

                if (session.getAttribute("countcartItems") != null) {
                    int count = (int) session.getAttribute("countcartItems");
                    model.addAttribute("countcartItems", count);
                } else {
                    model.addAttribute("countcartItems", 0);
                }

                return "user/thankyou";
            }
        }

        return "user/checkout";
    }

    @GetMapping("/checkout/cancel")
    public String cancelPay() {
        return "redirect:/checkout";
    }

    @GetMapping("/checkout/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model, HttpSession session) {
        try {
            Payment payment = executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                Orders order = (Orders) session.getAttribute("pendingOrder");
                List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
                List<Coupons> appliedCoupons = (List<Coupons>) session.getAttribute("appliedCoupons");

                Orders savedOrder = rt.postForObject(urlorders, order, Orders.class);

                List<OrderItems> orderItems = new ArrayList<>();

                for (CartItems cartItem : cartItems) {
                    try {
                        Items items = rt.getForObject(urlitems + "/" + cartItem.getItem_id(), Items.class);
                        OrderItems orderItem = new OrderItems();
                        orderItem.setOrders(savedOrder);
                        orderItem.setItem(items);
                        orderItem.setQuantity(cartItem.getTotalQuantity());
                        orderItem.setPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getTotalQuantity())));
                        orderItem.setCreated_dt(new Date());

                        OrderItems savedOrderItem = rt.postForObject(urlorderitems, orderItem, OrderItems.class);

                        int sub = items.getStock() - cartItem.getTotalQuantity();
                        if (sub <= 0) {
                            items.setIs_visible(false);
                        }
                        Items item = new Items(items.getItem_id(), items.getName(), items.getBrand(), items.getDescription(), items.getPrice(), sub, items.getRentalquantity(), items.getType(), items.getImage(), items.isIs_visible(), items.getCreated_dt());
                        rt.postForObject(urlitems + "/", item, Items.class);
                        orderItems.add(savedOrderItem);
                    } catch (Exception e) {
                        model.addAttribute("message", "Failed to save order item for " + cartItem.getItem_id());
                        return "user/checkout";
                    }
                }
                String email = (String) session.getAttribute("user");
                Users users = rt.getForObject(urlusers + "/findemail/" + email, Users.class);

                // Save applied coupons to CouponUsers table
                for (Coupons coupon : appliedCoupons) {
                    CouponUsers couponUser = new CouponUsers();
                    couponUser.setCoupons(coupon);
                    couponUser.setUsers(order.getUsers());
                    couponUser.setCreated_dt(new Date());
                    rt.postForObject(urlcouponusers, couponUser, CouponUsers.class);
                }

                String couponCode = (String) session.getAttribute("couponCode");
                if (couponCode != null) {
                    Coupons coupon = rt.getForObject(urlcoupons + "/findcode/" + couponCode, Coupons.class);
                    if (coupon.getUsage_limit() > 0) {
                        int usagelimit = coupon.getUsage_limit() - 1;
                        if (usagelimit <= 0) {
                            coupon.setIs_active(false);
                        }
                        Coupons c = new Coupons(coupon.getCoupon_id(), coupon.getCode(), coupon.getDiscount_amount(), coupon.getExpiration_date(), usagelimit, coupon.isIs_active(), coupon.getCreated_dt());
                        rt.postForObject(urlcoupons + "/", c, Coupons.class);
                    }
                    session.removeAttribute("couponCode");
                }

                session.removeAttribute("cartItems");
                session.setAttribute("countcartItems", 0);
                session.setAttribute("totalPrice", BigDecimal.ZERO);
                session.removeAttribute("appliedCoupons");
                session.removeAttribute("pendingOrder");

                MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
                parts.add("users", users);
                parts.add("orders", savedOrder);
                parts.add("orderItems", orderItems);

                // Send the request using a POST request with multipart/form-data
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

                rt.postForEntity(urlorders + "/sendbillmail", requestEntity, Void.class);
                model.addAttribute("paymentMethod", "online");
                if (session.getAttribute("countcartItems") != null) {
                    int count = (int) session.getAttribute("countcartItems");
                    model.addAttribute("countcartItems", count);
                } else {
                    model.addAttribute("countcartItems", 0);
                }

                return "user/thankyou";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "Payment failed.");
        return "user/checkout";
    }

    private Payment createPayment(Double total, String currency, String method,
            String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(getAPIContext());
    }

    private Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(getAPIContext(), paymentExecution);
    }

    @GetMapping("/informationLine")
    public String orderHistory(Model model, HttpSession session,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "1") int pageSize) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            return "redirect:/login";
        }

        try {
            Users users = rt.getForObject(urlusers + "/findemail/" + email, Users.class);
            List<Orders> allOrders = rt.getForObject(urlorders + "/users/" + users.getUser_id(), List.class);

            ResponseEntity<List<Orders>> responseEntity = rt.exchange(
                    urlorders + "/userpage/" + users.getUser_id() + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Orders>>() {
            }
            );
            List<Orders> orders = responseEntity.getBody();
            int totalItems = allOrders.size();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            Map<Integer, List<OrderItems>> orderItemsMap = new HashMap<>();
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (Orders order : orders) {
                List<OrderItems> items = rt.exchange(
                        urlorderitems + "/order/" + order.getOrder_id(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<OrderItems>>() {
                }
                ).getBody();

                if (items != null) {
                    orderItemsMap.put(order.getOrder_id(), items);
                    totalPrice = totalPrice.add(
                            items.stream()
                                    .map(OrderItems::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    );
                }
                model.addAttribute("oorder", order);

            }

            // Assuming order.getTotal_amount() is the discounted price
            BigDecimal discount = totalPrice.subtract(
                    orders.stream()
                            .map(Orders::getTotal_amount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
            

            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("orders", orders);
            model.addAttribute("orderItemsMap", orderItemsMap);
            model.addAttribute("discount", discount);
            if (session.getAttribute("countcartItems") != null) {
                int count = (int) session.getAttribute("countcartItems");
                model.addAttribute("countcartItems", count);
            } else {
                model.addAttribute("countcartItems", 0);
            }
            return "user/informationLine";

        } catch (RestClientException e) {
            model.addAttribute("message", "Failed to retrieve data: " + e.getMessage());
            return "user/informationLine";
        }
    }

    @GetMapping("/latestOrder")
    public String latestOrder(Model model, HttpSession session) {
        String email = (String) session.getAttribute("user");
        if (email == null) {
            return "redirect:/login";
        }

        try {
            // Retrieve user details using email
            Users users = rt.getForObject(urlusers + "/findemail/" + email, Users.class);

            // Retrieve the orders for the user
            ResponseEntity<List<Orders>> responseEntity = rt.exchange(
                    urlorders + "/users/" + users.getUser_id(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Orders>>() {
            }
            );
            List<Orders> orders = responseEntity.getBody();

            // Ensure orders are sorted by date in descending order
            if (orders != null && !orders.isEmpty()) {
                orders.sort((o1, o2) -> o2.getOrder_date().compareTo(o1.getOrder_date())); // Replace 'getOrderDate()' with your date field getter method
            }

            Orders latestOrder = (orders == null || orders.isEmpty()) ? null : orders.get(0);

            Map<Integer, List<OrderItems>> orderItemsMap = new HashMap<>();
            BigDecimal totalPrice = BigDecimal.ZERO;
            if (latestOrder != null) {
                List<OrderItems> items = rt.exchange(
                        urlorderitems + "/order/" + latestOrder.getOrder_id(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<OrderItems>>() {
                }
                ).getBody();

                if (items != null) {
                    orderItemsMap.put(latestOrder.getOrder_id(), items);

                    // Calculate total price of all items using BigDecimal
                    totalPrice = items.stream()
                            .map(OrderItems::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                }
            }

            // Calculate the discount assuming latestOrder.getTotal_amount() is the discounted price
            BigDecimal discount = totalPrice.subtract(latestOrder != null ? latestOrder.getTotal_amount() : BigDecimal.ZERO);

            model.addAttribute("order", latestOrder);
            model.addAttribute("orderItemsMap", orderItemsMap);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("discount", discount);
            if (session.getAttribute("countcartItems") != null) {
                int count = (int) session.getAttribute("countcartItems");
                model.addAttribute("countcartItems", count);
            } else {
                model.addAttribute("countcartItems", 0);
            }
            return "user/latestOrder";

        } catch (RestClientException e) {
            model.addAttribute("message", "Failed to retrieve data: " + e.getMessage());
            return "user/latestOrder";
        }
    }

    @GetMapping("/contact")
    public String contact(Model model, HttpSession session) {
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }
        model.addAttribute("account", new Users());
        return "user/contact";
    }

    @GetMapping("/shop")
    public String shop(Model model, HttpSession session,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "9") int pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "bikeSize", required = false) String bikeSize,
            @RequestParam(value = "bikeWheelSize", required = false) String bikeWheelSize,
            @RequestParam(value = "bikeColor", required = false) String bikeColor,
            @RequestParam(value = "bikeMaterial", required = false) String bikeMaterial,
            @RequestParam(value = "bikeBrakeType", required = false) String bikeBrakeType,
            @RequestParam(value = "otherBrand", required = false) String otherBrand) {

        String filterUrl = urlitems + "/filter?pageNumber=" + pageNumber + "&pageSize=" + pageSize;
        String showfilterUrl = urlitems + "/showfilter?name=" + (name != null ? name : "");

        if (name != null) {
            filterUrl += "&name=" + name;
        }
        if (brand != null && !"Other".equals(brand)) {
            filterUrl += "&brand=" + brand;
            showfilterUrl += "&brand=" + brand;
        } else if (otherBrand != null && !otherBrand.isEmpty()) {
            filterUrl += "&brand=" + otherBrand;
            showfilterUrl += "&brand=" + otherBrand;
        }
        if (type != null) {
            filterUrl += "&type=" + type;
            showfilterUrl += "&type=" + type;
        }
        if (minPrice != null) {
            filterUrl += "&minPrice=" + minPrice;
            showfilterUrl += "&minPrice=" + minPrice;
        }
        if (maxPrice != null) {
            filterUrl += "&maxPrice=" + maxPrice;
            showfilterUrl += "&maxPrice=" + maxPrice;
        }
        if (bikeSize != null) {
            filterUrl += "&bikeSize=" + bikeSize;
            showfilterUrl += "&bikeSize=" + bikeSize;
        }
        if (bikeWheelSize != null) {
            filterUrl += "&bikeWheelSize=" + bikeWheelSize;
            showfilterUrl += "&bikeWheelSize=" + bikeWheelSize;
        }
        if (bikeColor != null) {
            filterUrl += "&bikeColor=" + bikeColor;
            showfilterUrl += "&bikeColor=" + bikeColor;
        }
        if (bikeMaterial != null) {
            filterUrl += "&bikeMaterial=" + bikeMaterial;
            showfilterUrl += "&bikeMaterial=" + bikeMaterial;
        }
        if (bikeBrakeType != null) {
            filterUrl += "&bikeBrakeType=" + bikeBrakeType;
            showfilterUrl += "&bikeBrakeType=" + bikeBrakeType;
        }

        ResponseEntity<List<Items>> response = rt.exchange(
                filterUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Items>>() {
        });

        List<Items> itemList = response.getBody();

        ResponseEntity<List<Items>> allItemsResponse = rt.exchange(
                urlitems,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Items>>() {
        });

        List<Items> allItems = rt.getForObject(showfilterUrl, List.class);
        int totalItems = allItems.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        model.addAttribute("list", itemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("name", name);
        model.addAttribute("brand", brand);
        model.addAttribute("type", type);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("bikeSize", bikeSize);
        model.addAttribute("bikeWheelSize", bikeWheelSize);
        model.addAttribute("bikeColor", bikeColor);
        model.addAttribute("bikeMaterial", bikeMaterial);
        model.addAttribute("bikeBrakeType", bikeBrakeType);
        model.addAttribute("otherBrand", otherBrand);

        return "user/shop";
    }

    @GetMapping("/shopsearch")
    public String shopsearch(Model model, HttpSession session, @RequestParam("name") String name, @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "9") int pageSize) {
        if (name != null && !name.isEmpty()) {
            ResponseEntity<List<Items>> response = rt.exchange(
                    urlitems + "/searchp/" + name + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Items>>() {
            });

            List<Items> p = rt.getForObject(urlitems + "/search/" + name, List.class
            );

            List<Items> itemList = response.getBody();

            int totalItems = p.size(); // Ensure totalItems is calculated correctly
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);

            if (session.getAttribute("countcartItems") != null) {
                int count = (int) session.getAttribute("countcartItems");
                model.addAttribute("countcartItems", count);
            } else {
                model.addAttribute("countcartItems", 0);
            }
            // Add the paginated list and pagination parameters to the model
            model.addAttribute("list", itemList);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("name", name);
            return "user/shop";
        } else {
            return "redirect:/shop";
        }
    }

    @GetMapping("/shopdetail/{id}")
    public String shopdetail(Model model, @PathVariable int id, HttpSession session) {
//        ParameterizedTypeReference<List<Items>> responseType = new ParameterizedTypeReference<List<Items>>() {
//        };
//        ResponseEntity<List<Items>> response = rt.exchange(urlitems + "/", HttpMethod.GET, null, responseType);
//        List<Items> p = response.getBody();
//
//        // Initialize a new list to hold the top 10 items
//        List<Items> a = new ArrayList<>();
//
//        // Ensure there are at least 10 items in the list
//        int limit = Math.min(p.size(), 10);
//        for (int i = 0; i < limit; i++) {
//            a.add(p.get(i));
//        }
        List<Items> a = rt.getForObject(urlitems + "/top10", List.class);
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        model.addAttribute("top10", a);
        Items items = rt.getForObject(urlitems + "/" + id, Items.class);
        BikeProperties bikeProperties = rt.getForObject(urlbikeproperties + "/item/" + items.getItem_id(), BikeProperties.class);
        model.addAttribute("items", items);
        model.addAttribute("bikeProperties", bikeProperties);
        return "user/shopdetail";

    }

    @GetMapping("/thankyou")
    public String thankyou(Model model, HttpSession session) {
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }
        model.addAttribute("account", new Users());
        return "user/thankyou";
    }

    @PostMapping("/updatecart")
    public String updateCart(@RequestParam(name = "itemId", defaultValue = "") List<Integer> itemIds,
            @RequestParam(name = "quantity", defaultValue = "") List<Integer> quantities,
            HttpSession session) {
        if (itemIds.isEmpty() || quantities.isEmpty()) {
            return "redirect:/cart?error=empty";
        }

        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Create a map to store updated quantities
        Map<Integer, Integer> quantityMap = new LinkedHashMap<>();
        for (int i = 0; i < itemIds.size(); i++) {
            quantityMap.put(itemIds.get(i), quantities.get(i));
        }

        List<CartItems> updatedCartItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalItemCount = 0;

        for (CartItems item : cartItems) {
            if (quantityMap.containsKey(item.getItem_id())) {
                int newQuantity = quantityMap.get(item.getItem_id());
                if (newQuantity > 0) {
                    item.setTotalQuantity(newQuantity);
                    updatedCartItems.add(item);
                    totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
                    totalItemCount += newQuantity;
                }
                quantityMap.remove(item.getItem_id());
            } else {
                updatedCartItems.add(item);
                totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getTotalQuantity())));
                totalItemCount += item.getTotalQuantity();
            }
        }

        // Add any new items that were not in the original cart
        for (Map.Entry<Integer, Integer> entry : quantityMap.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();

            if (quantity > 0) {
                CartItems newItem = rt.getForObject(urlitems + "/" + itemId, CartItems.class
                );
                newItem.setTotalQuantity(quantity);
                updatedCartItems.add(newItem);
                totalPrice = totalPrice.add(newItem.getPrice().multiply(BigDecimal.valueOf(quantity)));
                totalItemCount += quantity;
            }
        }

        session.setAttribute("cartItems", updatedCartItems);
        session.setAttribute("countcartItems", totalItemCount);
        session.setAttribute("totalPrice", totalPrice);

        return "redirect:/cart";
    }

    @GetMapping("/deletecart/{itemId}")
    public String deleteCart(@PathVariable int itemId, HttpSession session) {
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
        if (cartItems != null) {
            cartItems.removeIf(item -> item.getItem_id() == itemId);
            BigDecimal totalPrice = BigDecimal.ZERO;
            int totalItemCount = 0;
            for (CartItems item : cartItems) {
                totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getTotalQuantity())));
                totalItemCount += item.getTotalQuantity();
            }
            session.setAttribute("cartItems", cartItems);
            session.setAttribute("countcartItems", totalItemCount);
            session.setAttribute("totalPrice", totalPrice);
        }
        return "redirect:/cart";
    }

}
