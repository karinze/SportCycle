/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.Items;
import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.inteface.IOrderService;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.repository.OrderRepository;
import fpt.aptech.project.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manh_Chien
 */
@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    final JavaMailSender javaMailSender;

    public OrderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Orders createOrder(Orders orders) {
        return orderRepository.save(orders);
    }

    @Override
    public Orders findOne(int orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public List<Orders> findUser(Users users) {
        return orderRepository.listOrderByUserId(users.getUser_id());
    }

   @Transactional
public void sendBillMail(Users users, Orders order, List<OrderItems> orderItems) {
    try {
        StringBuilder billContent = new StringBuilder();
        billContent.append("<html><body style='font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9;'>")
                .append("<div style='max-width: 450px; margin: 40px auto; padding: 30px; border-radius: 10px; background: #ffffff; box-shadow: 0 15px 30px rgba(0,0,0,0.1); border: 1px solid #e0e0e0;'>")
                .append("<div style='text-align: center; padding: 30px 0; background: linear-gradient(135deg, #4CAF50, #2C2C2C); color: #fff; border-radius: 10px 10px 0 0;'>")
                .append("<h1 style='margin: 0; font-size: 36px; font-family: 'Poppins', sans-serif; font-weight: bold;'>Sport Cycle</h1>")
                .append("<p style='font-size: 18px; margin-top: 10px;'>Order Confirmation</p>")
                .append("</div>")
                .append("<div style='padding: 20px 30px;'>")
                .append("<p style='font-size: 18px; color: #333;'>Dear ").append(users.getUsername()).append(",</p>")
                .append("<p style='font-size: 16px; color: #666;'>Thank you for choosing Sport Cycle! We are excited to share the details of your order below:</p>")
                .append("<table style='width: 100%; border-collapse: collapse; margin-top: 20px; border-radius: 10px; overflow: hidden;'>")
                .append("<thead>")
                .append("<tr style='background: #4CAF50; color: #fff;'>")
                .append("<th style='padding: 12px; text-align: left;'>Item</th>")
                .append("<th style='padding: 12px; text-align: center;'>Quantity</th>")
                .append("<th style='padding: 12px; text-align: right;'>Price</th>")
                .append("</tr>")
                .append("</thead>")
                .append("<tbody>");

        for (OrderItems item : orderItems) {
            billContent.append("<tr style='border-bottom: 1px solid #ddd;'>")
                    .append("<td style='padding: 12px; color: #333;'>").append(item.getItem().getName()).append("</td>")
                    .append("<td style='padding: 12px; text-align: center; color: #333;'>").append(item.getQuantity()).append("</td>")
                    .append("<td style='padding: 12px; text-align: right; color: #333;'>$").append(item.getPrice().intValue()).append("</td>")
                    .append("</tr>");
        }

        billContent.append("<tr style='background: #f4f4f4;'>")
                .append("<td colspan='2' style='padding: 15px; text-align: right; font-weight: bold; color: #333;'>Total Amount:</td>")
                .append("<td style='padding: 15px; text-align: right; font-weight: bold; color: #333;'>$").append(order.getTotal_amount().intValue()).append("</td>")
                .append("</tr>")
                .append("</tbody>")
                .append("</table>")
                .append("<div style='padding: 20px 0; text-align: center;'>")
                .append("<p style='font-size: 16px; color: #666;'>We hope you enjoy your purchase! If you have any questions, feel free to <a href='#' style='color: #4CAF50; text-decoration: none;'>contact us</a>.</p>")
                .append("</div>")
                .append("<div style='text-align: center; padding: 20px; background: #f4f4f4; border-radius: 0 0 10px 10px;'>")
                .append("<p style='font-size: 14px; color: #888;'>© 2024 SportCycle Shop. All rights reserved.</p>")
                .append("</div>")
                .append("</div>")
                .append("</body></html>");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("SportCycle Shop <chientestphp@gmail.com>");
        helper.setTo(users.getEmail());
        helper.setSubject("Your Order Confirmation - SportCycle Shop");
        helper.setText(billContent.toString(), true);

        javaMailSender.send(message);
        System.out.println("Email sent successfully to " + users.getEmail());
    } catch (Exception e) {
        // Log the exception with details
        System.err.println("Failed to send email to " + users.getEmail() + ": " + e.getMessage());
        e.printStackTrace();
        // Add further logging as needed
    }
}


    @Override
    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> page(Users users, int pageNumber, int pageSize) {
        try {
            int validatedPageSize = (pageSize < 1) ? 5 : pageSize;
            Pageable pageable = PageRequest.of(pageNumber, validatedPageSize);
            Page<Orders> pageItems = this.orderRepository.searchOrderByUserId(users.getUser_id(), pageable);
            return pageItems.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch paginated items: " + ex.getMessage());
        }
    }

    @Override
    public Users findUser(UUID uuid) {
        return userRepository.findById(uuid).get();
    }

    @Override
    public BigDecimal getMonthlyRevenue() {
        return orderRepository.getMonthlyRevenue();
    }

    @Override
    public BigDecimal getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

    @Override
    public Long getPendingRequests() {
        return orderRepository.getPendingRequests();
    }

    @Override
    public List<Map<String, Object>> findMonthlyEarnings() {
        return orderRepository.findMonthlyEarnings();
    }

}
