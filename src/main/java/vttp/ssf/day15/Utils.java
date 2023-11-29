package vttp.ssf.day15;

import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import vttp.ssf.day15.model.Item;

public class Utils {

    public static final String ATTR_CART = "cart";

    public static List<Item> getCart(HttpSession sess) {
     
        List<Item> cart = (List<Item>)sess.getAttribute(ATTR_CART);
        if (cart == null) {
            cart = new LinkedList<>();
            sess.setAttribute(ATTR_CART, cart);
        }

        return cart;
    }
    
}
