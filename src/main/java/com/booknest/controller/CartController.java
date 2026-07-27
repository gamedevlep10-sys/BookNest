package com.booknest.controller;

import com.booknest.entity.Cart;
import com.booknest.entity.User;
import com.booknest.service.CartService;
import com.booknest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for shopping cart operations
 */
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/cart")
    public String cart(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUser(currentUser);
        model.addAttribute("cart", cart);
        model.addAttribute("currentUser", currentUser);

        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long bookId,
                           @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                           RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            cartService.addToCart(currentUser, bookId, quantity);
            redirectAttributes.addFlashAttribute("success", "Book added to cart successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/buy-now")
    public String buyNow(@RequestParam Long bookId,
                         @RequestParam(value = "quantity", defaultValue = "1") Integer quantity,
                         RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            cartService.addToCart(currentUser, bookId, quantity);
            return "redirect:/checkout";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam Long bookId,
                                 @RequestParam Integer quantity,
                                 RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            cartService.updateCartItem(currentUser, bookId, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart updated successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long bookId,
                                  RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            cartService.removeFromCart(currentUser, bookId);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        cartService.clearCart(currentUser);
        redirectAttributes.addFlashAttribute("success", "Cart cleared successfully!");

        return "redirect:/cart";
    }
}
