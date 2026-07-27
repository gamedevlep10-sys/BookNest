package com.booknest.controller;

import com.booknest.entity.User;
import com.booknest.entity.Wishlist;
import com.booknest.service.UserService;
import com.booknest.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for wishlist operations
 */
@Controller
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;

    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Wishlist> wishlist = wishlistService.getWishlistByUser(currentUser);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("currentUser", currentUser);

        return "wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addToWishlist(@RequestParam Long bookId,
                                 RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            wishlistService.addToWishlist(currentUser, bookId);
            redirectAttributes.addFlashAttribute("success", "Book added to wishlist");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/book/" + bookId;
    }

    @PostMapping("/wishlist/remove")
    public String removeFromWishlist(@RequestParam Long bookId,
                                      RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        wishlistService.removeFromWishlist(currentUser, bookId);
        redirectAttributes.addFlashAttribute("success", "Book removed from wishlist");

        return "redirect:/wishlist";
    }

    @PostMapping("/wishlist/clear")
    public String clearWishlist(RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        wishlistService.clearWishlist(currentUser);
        redirectAttributes.addFlashAttribute("success", "Wishlist cleared");

        return "redirect:/wishlist";
    }
}
