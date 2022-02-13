package com.plocky.deador.controller;


import com.plocky.deador.global.GlobalData;
import com.plocky.deador.service.CategoryService;
import com.plocky.deador.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "/index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "/shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        return "/shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "/viewProduct";
    }

//    @GetMapping("/shop/search/{word}")
//    public String searchProduct(@PathVariable String word, Model model){
//        return "/shop";
//    }
}
