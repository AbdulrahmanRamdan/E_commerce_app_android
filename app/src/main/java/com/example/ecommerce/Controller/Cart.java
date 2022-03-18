package com.example.ecommerce.Controller;

import android.util.Pair;

import com.example.ecommerce.Models.Product;

import java.util.Hashtable;
import java.util.Set;

public class Cart {
    public static int CustID , OrdID;
    public static  String Cust_Address = "";

    // Key => Product Id
    // Pair<Product , Quantity>
    public static Hashtable<Integer , Pair<Product, Integer>> product_and_quantity = new Hashtable<Integer , Pair<Product , Integer>>();

    public static void AddProductToCart(Product product)
    {
        int ProductId = product.ProductID;
        Pair<Product,Integer> p ;
        if(!product_and_quantity.containsKey(ProductId)){
            product.ProductQuantity = 1;
            p = new Pair<Product,Integer>(product , 1);
        }
        else{
            product.ProductQuantity = product_and_quantity.get(ProductId).second + 1;
            product.ProductPrice = product_and_quantity.get(ProductId).first.ProductPrice + product.ProductPrice;
            p =  new Pair<Product,Integer>(product , product_and_quantity.get(ProductId).second + 1);
        }
        product_and_quantity.put(ProductId , p);
    }

    public static void UpdateProductToCart(Product product , int Quantity){
        int ProductId = product.ProductID;
        if(product_and_quantity.containsKey(ProductId)){
            product_and_quantity.put(ProductId ,  new Pair<Product,Integer>(product,Quantity));
        }
    }

    public static void DeleteProductFromCart(Product product){
        int ProductId = product.ProductID;
        if(product_and_quantity.containsKey(ProductId)){
            product_and_quantity.remove(ProductId);
        }
    }


    // Not Relate to Function
    public static int GetNumberFromString(String s){
        int n = 0 ;
        for (int i = 0 ; i < s.length() ; i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                int d = Integer.parseInt(String.valueOf(c));
                n = (n * 10) + d;
            }
        }
        return n;
    }

    public static void PrintAllContentOfHashtable(){
        Set<Integer> setOfCountries = product_and_quantity.keySet();
        for(int key : setOfCountries) {
            System.out.println("Product ID : "  + key + " Quantity"  + product_and_quantity.get(key).second);
        }
    }


    public static void MakeCartEmpty(){
        product_and_quantity.clear();
        Cust_Address = "";
    }
}
