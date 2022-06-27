package com.plocky.deador.controller;


import com.plocky.deador.repository.global.GlobalData;
import com.plocky.deador.repository.global.PageUrlPrefix;
import com.plocky.deador.entity.Product;
import com.plocky.deador.repository.ProductRepository;
import com.plocky.deador.service.CategoryService;
import com.plocky.deador.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;


    @GetMapping("/shop")
    public String shop(Model model) {
        return shopFindPaginatedShop(1, "id", "desc", model);
    }


    @GetMapping("/shop/page/{pageNo}")
    public String shopFindPaginatedShop(@PathVariable(value = "pageNo") int pageNo,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir,
                                        Model model) {
        int pageSize = 16;
        PageUrlPrefix pageUrlPrefix = new PageUrlPrefix();
        pageUrlPrefix.setPageUrlPrefixString("/shop");
        Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Product> listProducts = page.getContent();
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("pageUrlPrefix", pageUrlPrefix);
        return "/shop";
    }

    @GetMapping("/shop/category/{id}/page/{pageNo}")
    public String shopByCategory(@PathVariable(value = "id") int id,
                                 @PathVariable(value = "pageNo") int pageNo,
                                 @RequestParam("sortField") String sortField,
                                 @RequestParam("sortDir") String sortDir,
                                 Model model) {
        int pageSize = 16;
        PageUrlPrefix pageUrlPrefix = new PageUrlPrefix();
        pageUrlPrefix.setPageUrlPrefixString("/shop/category/" + id);
        Page<Product> page = productService.findPaginatedInCategory(pageNo, pageSize, sortField, sortDir, id);
        List<Product> listProducts = page.getContent();


        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("cartCount", GlobalData.cart.size());

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("pageUrlPrefix", pageUrlPrefix);
        model.addAttribute("listProducts", listProducts);
        return "/shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id).get();
        if (product.getCountOfViews() == null) {
            product.setCountOfViews(0);
        }
        Integer countOfView = product.getCountOfViews() + 1;
        product.setCountOfViews(countOfView);
        productRepository.save(product);
        model.addAttribute("product", product);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "/viewProduct";
    }


    @GetMapping("/search/page/{pageNo}")
    public String searchProduct(@RequestParam(required = false, value = "keyword") String keyword,
                                @PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 500;
        PageUrlPrefix pageUrlPrefix = new PageUrlPrefix();
        pageUrlPrefix.setPageUrlPrefixString("/search");

        Page<Product> page = productService.findPaginatedInSearch(pageNo, pageSize, sortField, sortDir, keyword);
        List<Product> listProducts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("pageUrlPrefix", pageUrlPrefix);

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("keyword", keyword);
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("listProducts", listProducts);
        return "/shop";
    }

    @RequestMapping(value = "/sortByPrice/{id}", method = RequestMethod.GET)
    public String sortProductsByPrice(@RequestParam int id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("products", productService.getAllProductsByCategoryIdOrderByPrice(id));
        return "/shop";
    }

}
