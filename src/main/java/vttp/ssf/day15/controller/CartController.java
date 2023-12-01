package vttp.ssf.day15.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import vttp.ssf.day15.services.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private Logger logger = Logger.getLogger(CartController.class.getName());

    @Autowired
    private CartService cartSvc;

    @GetMapping
    public String getCart(@RequestParam String name, Model model, HttpSession sess) {

        List<Item> cart = cartSvc.getCart(name);

        logger.info("CART: %s - %s\n".formatted(name, cart));

        sess.setAttribute("cart", cart);

        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        model.addAttribute("username", name);

        return "cart";
    }
    
    @PostMapping
    public ModelAndView postCart(@Valid @ModelAttribute Item item, BindingResult result, @RequestParam String username, HttpSession sess) {

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
        mav.addObject("username", username);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    @PostMapping ("/checkout")
    public String postCartCheckout(@RequestParam String username, HttpSession sess) {

        // ModelAndView mav = new ModelAndView("cart");

        List<Item> cart = Utils.getCart(sess);
        System.out.printf("Checkout cart: %s\n", cart);

        cartSvc.saveCart(username, cart);

        sess.invalidate();

        // mav.addObject("item", new Item());
        // mav.setStatus(HttpStatusCode.valueOf(200));

        return "redirect:/index.html";
    }
}
