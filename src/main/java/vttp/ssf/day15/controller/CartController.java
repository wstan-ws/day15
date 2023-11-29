package vttp.ssf.day15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.day15.Utils;
import vttp.ssf.day15.model.Item;
import vttp.ssf.day15.repo.CartRepository;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepo;

    @GetMapping
    public String getCart(@RequestParam String name) {

        if (cartRepo.hasCart(name)) {
            
        } 

        return "cart";
    }
    
    @PostMapping
    public ModelAndView postCart(@Valid @ModelAttribute Item item, BindingResult result, HttpSession sess) {

        System.out.printf("item: %s\n", item);
        System.out.printf("error: %b\n", result.hasErrors());

        ModelAndView mav = new ModelAndView("cart");

        if (result.hasErrors()) {
            mav.setStatus(HttpStatusCode.valueOf(400));
            return mav;
        }

        List<Item> cart = Utils.getCart(sess);
        cart.add(item);

        mav.addObject("item", new Item());
        mav.addObject("cart", cart);
        mav.setStatus(HttpStatusCode.valueOf(200));
        return mav;
    }

    @PostMapping ("/checkout")
    public ModelAndView postCartCheckout(HttpSession sess) {

        ModelAndView mav = new ModelAndView("cart");

        List<Item> cart = Utils.getCart(sess);
        System.out.printf("Checkout cart: %s\n", cart);

        sess.invalidate();

        mav.addObject("item", new Item());
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }
}
