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
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
            billContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>")
                    .append("<h3 style='color: #4CAF50;'>Dear ").append(users.getUsername()).append(",</h3>")
                    .append("<p>Thank you for your order. Here are the details:</p>")
                    .append("<p><strong>Order Date:</strong> ").append(order.getOrder_date()).append("</p>")
                    .append("<p><strong>Total Amount:</strong> $").append(order.getTotal_amount().intValue()).append("</p>")
                    .append("<h4 style='color: #4CAF50;'>Items:</h4>")
                    .append("<table style='width: 100%; border-collapse: collapse;'>")
                    .append("<thead>")
                    .append("<tr style='background-color: #f2f2f2;'>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Item</th>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantity</th>")
                    .append("<th style='border: 1px solid #ddd; padding: 8px;'>Price</th>")
                    .append("</tr>")
                    .append("</thead>")
                    .append("<tbody>");

            for (OrderItems item : orderItems) {
                billContent.append("<tr>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px;'>").append(item.getItem().getName()).append("</td>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px; text-align: center;'>").append(item.getQuantity()).append("</td>")
                        .append("<td style='border: 1px solid #ddd; padding: 8px; text-align: right;'>$").append(item.getPrice().intValue()).append("</td>")
                        .append("</tr>");
            }

            billContent.append("</tbody>")
                    .append("</table>")
                    .append("<p style='margin-top: 20px;'>Best regards,<br>XYZ Team</p>")
                    .append("</body></html>");

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("XYZ Team <chientestphp@gmail.com>");
            helper.setTo(users.getEmail());
            helper.setSubject("Your Order Confirmation - XYZ");
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
