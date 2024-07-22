/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Service.java to edit this template
 */
package fpt.aptech.project.service;

import fpt.aptech.project.entities.OrderItems;
import fpt.aptech.project.inteface.IOrderService;
import fpt.aptech.project.entities.Orders;
import fpt.aptech.project.entities.Users;
import fpt.aptech.project.repository.OrderRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
            billContent.append("<html><body>")
                    .append("<h3>Dear ").append(users.getUsername()).append(",</h3>")
                    .append("<p>Thank you for your order. Here are the details:</p>")
                    .append("<p><strong>Order ID:</strong> ").append(order.getOrder_id()).append("</p>")
                    .append("<p><strong>Order Date:</strong> ").append(order.getOrder_date()).append("</p>")
                    .append("<p><strong>Total Amount:</strong> ").append(order.getTotal_amount()).append("</p>")
                    .append("<h4>Items:</h4>")
                    .append("<ul>");

            for (OrderItems item : orderItems) {
                billContent.append("<li>")
                        .append(item.getItem().getName())
                        .append(" x ").append(item.getQuantity())
                        .append(": ").append(item.getPrice())
                        .append("</li>");
            }

            billContent.append("</ul>")
                    .append("<p>Best regards,<br>XYZ Team</p>")
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

}
