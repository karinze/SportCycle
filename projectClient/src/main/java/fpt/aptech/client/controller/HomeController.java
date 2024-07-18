/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.client.controller;

import fpt.aptech.client.dto.ItemsDTO;
import fpt.aptech.client.dto.UsersDTO;
import fpt.aptech.client.models.CartItems;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/login")
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

                return "redirect:/dashboard";
            } else {
                session.setAttribute("user", users.email);
                return "redirect:/";
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
    public String doregister(Model model, @Valid @ModelAttribute("account") UsersDTO user, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", user);

            if (user.getEmail().isEmpty()) {
                model.addAttribute("account", user);
            } else {
                Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
                if (users != null) {
                    bindingResult.rejectValue("email", "error.account", "Email already exists");
                    model.addAttribute("account", user);
                }
            }
            return "login/register";
        } else {
            if (!user.getEmail().isEmpty()) {
                Users users = rt.getForObject(urlusers + "/findemail/" + user.getEmail(), Users.class);
                if (users != null) {
                    bindingResult.rejectValue("email", "error.account", "Email already exists");
                    model.addAttribute("account", user);
                    return "login/register";
                }
            }

            user.setUser_id(UUID.randomUUID());
            user.setRole(false);
            user.setCreated_dt(Date.from(Instant.now()));
            Users newUsers = new Users(user.getUser_id(), user.getUsername(), user.getPassword(), user.getEmail(), user.isRole(), user.getCreated_dt());
            rt.postForEntity(urlusers, newUsers, Users.class);
            return "redirect:/login";
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

                return "redirect:/forgotpassword?error";
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
            users.setPassword(users.getPassword());

            users.setUsername(users.getUsername());

            Users acc = new Users(users.getUser_id(), users.getUsername(), users.getPassword(), users.getEmail(), users.isRole(), users.getCreated_dt());

            model
                    .addAttribute("Users", rt.postForEntity(urlusers + "/resetpassword", acc, Users.class
                    ));

        }
        return "redirect:/login";
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
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("admin") != null) {

            return "admin/dashboard";
        } else {

            return "redirect:/login";
        }

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
    public String docreate(Model model, @Valid
            @ModelAttribute("items") ItemsDTO items, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding Result Errors: " + bindingResult.getAllErrors());

            MultipartFile multipartFile = items.getImage();
            if (multipartFile != null && !multipartFile.isEmpty()) {
                model.addAttribute("items", items);
                return "admin/createAdminItems";
            } else {
                model.addAttribute("items", items);
                bindingResult.rejectValue("image", "error.items", "Image file is required.");
                return "admin/createAdminItems";
            }

        } else {
            MultipartFile multipartFile = items.getImage();
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = multipartFile.getOriginalFilename();
                FileCopyUtils.copy(items.getImage().getBytes(), new File(FileUpload, fileName));
                items.setCreated_dt(Date.from(Instant.now()));

                Items newItem = new Items(items.getName(), items.getBrand(), items.getDescription(), items.getPrice(), items.getStock(), items.getType(), fileName, items.isIs_visible(), items.getCreated_dt());
                model
                        .addAttribute("Items", rt.postForEntity(urlitems, newItem, Items.class
                        ));
                return "redirect:/indexAdminItems";
            } else {
                bindingResult.rejectValue("image", "error.items", "Image file is required.");
                return "admin/createAdminItems";
            }
        }
    }

    @GetMapping("/editAdminItems/{id}")
    public String edit(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            Items p = rt.getForObject(urlitems + "/" + id, Items.class
            );
            model.addAttribute("items", p);
            return "admin/editAdminItems";
        } else {

            return "redirect:/login";
        }

    }

    @PostMapping("/saveAdminItems")
    public String doedit(Model model, @Valid
            @ModelAttribute("items") ItemsDTO item, BindingResult bindingResult) throws IOException {
        Items items = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class
        );

        if (bindingResult.hasErrors()) {
            Items p = rt.getForObject(urlitems + "/" + item.getItem_id(), Items.class
            );
            model.addAttribute("items", item);
            return "admin/editAdminItems";
        } else {

            MultipartFile multipartFile = item.getImage();
            String fileName = multipartFile.getOriginalFilename();
            item.setCreated_dt(items.getCreated_dt());

            if (item.getImage().isEmpty()) {
                String file = items.getImage();
                Items editItems = new Items(item.getItem_id(), item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getType(), file, item.isIs_visible(), item.getCreated_dt());
                model
                        .addAttribute("Items", rt.postForEntity(urlitems, editItems, Items.class
                        ));
                return "redirect:/indexAdminItems";

            } else {
                FileCopyUtils.copy(item.getImage().getBytes(), new File(FileUpload, fileName));
                Items editItems = new Items(item.getItem_id(), item.getName(), item.getBrand(), item.getDescription(), item.getPrice(), item.getStock(), item.getType(), fileName, item.isIs_visible(), item.getCreated_dt());
                model
                        .addAttribute("Items", rt.postForEntity(urlitems, editItems, Items.class
                        ));
                return "redirect:/indexAdminItems";
            }
        }
    }

    @GetMapping("/deleteAdminItems/{id}")
    public String delete(Model model, @PathVariable String id, HttpSession session) {
        if (session.getAttribute("admin") != null) {
            rt.delete(urlitems + "/" + id, Items.class
            );
            return "redirect:/indexAdminItems";
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

        // Add item to cart with the specified quantity
        for (int i = 0; i < quantity; i++) {
            cartItems.add(item);
        }
        session.setAttribute("countcartItems", cartItems.size());

        // Return the new cart item count
        return ResponseEntity.ok(cartItems.size());
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        ParameterizedTypeReference<List<Items>> responseType = new ParameterizedTypeReference<List<Items>>() {
        };
        ResponseEntity<List<Items>> response = rt.exchange(urlitems + "/", HttpMethod.GET, null, responseType);
        List<Items> p = response.getBody();

        // Initialize a new list to hold the top 10 items
        List<Items> a = new ArrayList<>();

        // Ensure there are at least 10 items in the list
        int limit = Math.min(p.size(), 10);
        for (int i = 0; i < limit; i++) {
            a.add(p.get(i));
        }
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
                existingItem.setTotalQuantity(existingItem.getTotalQuantity() + 1);
            } else {
                CartItems newItem = new CartItems();
                newItem.setItem_id(item.getItem_id());
                newItem.setName(item.getName());
                newItem.setPrice(item.getPrice());
                newItem.setImage(item.getImage());
                newItem.setTotalQuantity(1);
                mergedCart.put(item.getItem_id(), newItem);
            }

            totalPrice = totalPrice.add(item.getPrice());
        }

        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        List<CartItems> mergedCartItems = new ArrayList<>(mergedCart.values());

        model.addAttribute("cartItems", mergedCartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "user/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }
        model.addAttribute("account", new Users());
        return "user/checkout";
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
    public String shop(Model model, HttpSession session, @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "9") int pageSize) {
        ResponseEntity<List<Items>> response = rt.exchange(
                urlitems + "/item" + "?pageNumber=" + pageNumber + "&pageSize=" + pageSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Items>>() {
        }
        );
        List<Items> p = rt.getForObject(urlitems + "/", List.class
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

        model.addAttribute("list", itemList);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);

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
            // Call index method if name is empty or null
            return shop(model, session, pageNumber, pageSize);
        }
    }

    @GetMapping("/shopdetail/{id}")
    public String shopdetail(Model model, @PathVariable int id, HttpSession session) {
        ParameterizedTypeReference<List<Items>> responseType = new ParameterizedTypeReference<List<Items>>() {
        };
        ResponseEntity<List<Items>> response = rt.exchange(urlitems + "/", HttpMethod.GET, null, responseType);
        List<Items> p = response.getBody();

        // Initialize a new list to hold the top 10 items
        List<Items> a = new ArrayList<>();

        // Ensure there are at least 10 items in the list
        int limit = Math.min(p.size(), 10);
        for (int i = 0; i < limit; i++) {
            a.add(p.get(i));
        }
        if (session.getAttribute("countcartItems") != null) {
            int count = (int) session.getAttribute("countcartItems");
            model.addAttribute("countcartItems", count);
        } else {
            model.addAttribute("countcartItems", 0);
        }

        model.addAttribute("top10", a);
        Items items = rt.getForObject(urlitems + "/" + id, Items.class
        );
        model.addAttribute("items", items);
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
    public String updateCart(@RequestParam(name = "itemId", defaultValue = "") List<Integer> itemIds, @RequestParam(name = "quantity", defaultValue = "") List<Integer> quantities, HttpSession session) {
        List<CartItems> cartItems = new ArrayList<>();
        if (itemIds.isEmpty()) {
            return "redirect:/cart?error=empty";
        } else {
            for (int i = 0; i < itemIds.size(); i++) {
                int itemId = itemIds.get(i);
                int quantity = quantities.get(i);

                CartItems item = rt.getForObject(urlitems + "/" + itemId, CartItems.class
                );

                for (int j = 0; j < quantity; j++) {
                    cartItems.add(item);
                }
            }

            session.setAttribute("cartItems", cartItems);
            session.setAttribute("countcartItems", cartItems.size());

            return "redirect:/cart";
        }

    }

    @GetMapping("/deletecart/{itemId}")
    public String deleteCart(@PathVariable int itemId, HttpSession session) {
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
        if (cartItems != null) {
            cartItems.removeIf(item -> item.getItem_id() == itemId);
            session.setAttribute("cartItems", cartItems);
            session.setAttribute("countcartItems", cartItems.size());
        }
        return "redirect:/cart";
    }

}
