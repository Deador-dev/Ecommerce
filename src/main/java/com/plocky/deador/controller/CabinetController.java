package com.plocky.deador.controller;

import com.plocky.deador.repository.global.GlobalData;
import com.plocky.deador.entity.Order;
import com.plocky.deador.entity.OrderItem;
import com.plocky.deador.entity.User;
import com.plocky.deador.service.CategoryService;
import com.plocky.deador.service.CustomUserDetailService;
import com.plocky.deador.service.OrderItemService;
import com.plocky.deador.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CabinetController {
    CustomUserDetailService customUserDetailService;
    OrderService orderService;
    CategoryService categoryService;
    OrderItemService orderItemService;

    @Autowired
    public CabinetController(CustomUserDetailService customUserDetailService, OrderService orderService, CategoryService categoryService, OrderItemService orderItemService) {
        this.customUserDetailService = customUserDetailService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/personalCabinet")
    public String getPersonalCabinet(Model model,
                                     @AuthenticationPrincipal User user) {
        String email = user.getEmail();
        List<Order> listOrders = orderService.getAllOrdersByEmail(email);
        List<OrderItem> listAllOrderItems = orderItemService.getAllOrderItems();
        List<OrderItem> listOrderItemUser = new ArrayList<>();

        for (OrderItem orderItem : listAllOrderItems) {
            if (Objects.equals(orderItem.getOrder().getUser().getId(), user.getId())) {
                listOrderItemUser.add(orderItem);
            }
        }

        model.addAttribute("listOrderItems", listOrderItemUser);
        model.addAttribute("user", user);
        model.addAttribute("listOrders", listOrders);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "/personalCabinet";
    }
}
