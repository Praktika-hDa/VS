/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class ShopInstance {
    private List<Product> _products = new ArrayList<Product>();
    public ShopInstance() {
    
    }
    public void AddProduct(String name, float price) {
    Product newProduct = new Product();
    System.out.println(name + " " + price);
    newProduct.Name = name;
    newProduct.Price = price;
    _products.add(newProduct);
    }
    public float getProductPrice(String name) {
        for (int i = 0; i < _products.size(); i++) {
            if(_products.get(i).Name.equals(name)) {
                return _products.get(i).Price;
            }
        }
        return 9999;
    }
}
