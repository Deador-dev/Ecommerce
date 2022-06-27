package com.plocky.deador.controller;

import com.plocky.deador.dto.OrderDTO;
import com.plocky.deador.entity.Order;
import com.plocky.deador.entity.OrderItem;
import com.plocky.deador.entity.Product;
import com.plocky.deador.entity.User;
import com.plocky.deador.repository.OrderItemRepository;
import com.plocky.deador.repository.OrderRepository;
import com.plocky.deador.repository.UserRepository;
import com.plocky.deador.repository.global.GlobalData;
import com.plocky.deador.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/cart";
    }

    @GetMapping("/buyNow/{id}")
    public String buyNow(@PathVariable int id) {
        GlobalData.cart.clear();
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/checkout";
    }

    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "/cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "/checkout";
    }

    @PostMapping("/checkout")
    public String orderPost(@ModelAttribute("orderDTO") OrderDTO orderDTO,
                            @AuthenticationPrincipal User authenticationUser) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Order order = modelMapper
                .map(orderDTO, Order.class);
        order.setDeliveryStatus("Preparation");
        order.setTotalAmount((int) GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        order.setEmail(authenticationUser.getEmail());
        order.setUser(authenticationUser);
        // --- ORDER SAVE ---
        orderRepository.save(order);
        // --- ORDER ITEMS ---
        for (Product product : GlobalData.cart) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
        // --- USERS ORDERS ---
        GlobalData.cart.clear();
        return "redirect:/shop";
    }


}
