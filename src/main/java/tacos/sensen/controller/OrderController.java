package tacos.sensen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.sensen.dao.OrderRepository;
import tacos.sensen.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderForm";
    }
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepository.save(order);
        Iterable<Order> all = orderRepository.findAll();
        sessionStatus.setComplete();
        log.info("order submitted: "+ order);
        return "redirect:/";
    }
}
