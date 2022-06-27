package com.plocky.deador.repository.global;

import com.plocky.deador.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;

    static {
        cart = new ArrayList<Product>();
    }
    public static String userName;
    public static String userEmail;
}
