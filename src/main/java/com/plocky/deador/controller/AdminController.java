package com.plocky.deador.controller;

import com.plocky.deador.dto.ProductDTO;
import com.plocky.deador.entity.Category;
import com.plocky.deador.entity.Order;
import com.plocky.deador.entity.Product;
import com.plocky.deador.repository.OrderRepository;
import com.plocky.deador.service.CategoryService;
import com.plocky.deador.service.OrderItemService;
import com.plocky.deador.service.OrderService;
import com.plocky.deador.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages/";
    CategoryService categoryService;
    ProductService productService;
    OrderService orderService;
    OrderRepository orderRepository;
    OrderItemService orderItemService;

    @Autowired
    public AdminController(CategoryService categoryService,
                           ProductService productService,
                           OrderRepository orderRepository,
                           OrderService orderService,
                           OrderItemService orderItemService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "/adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "/categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model) {
        model.addAttribute("category", new Category());
        return "/categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        if (productService.getAllProductsByCategoryId(id) != null && productService.getAllProductsByCategoryId(id).isEmpty()) {
            categoryService.removeCategoryById(id);
        } else {
            System.out.println("DeleteCategory ERROR.");
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "/categoriesAdd";
        } else {
            return "/404";
        }
    }

    //Product Section
    @GetMapping("/admin/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "/products";
    }

    @GetMapping("/admin/products/add")
    public String productAddGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "/productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Product product = modelMapper
                .map(productDTO, Product.class);
        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            System.out.println("Error with reading image.");
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);


        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        ProductDTO productDTO = modelMapper
                .map(product, ProductDTO.class);
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        return "/productsAdd";
    }

    //Order Section
    @GetMapping("/admin/orders")
    public String getOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "/orders";
    }

    @GetMapping("/admin/order/orderMore/{id}")
    public String getOrderMore(@PathVariable Integer id,
                               Model model) {
        model.addAttribute("order", orderService.getOrderById(id).get());
        model.addAttribute("listOrderItems", orderItemService.getAllOrderItemsById(id));
        return "/orderMore";
    }

    @PostMapping("/admin/orders/orderMore/update/{id}")
    public String postUpdateOrder(@PathVariable Integer id,
                                  @ModelAttribute("orderToUpdate") Order orderToUpdatePost) {
        Order order = orderService.getOrderById(id).get();
        order.setDeliveryStatus(orderToUpdatePost.getDeliveryStatus());
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }


}


























