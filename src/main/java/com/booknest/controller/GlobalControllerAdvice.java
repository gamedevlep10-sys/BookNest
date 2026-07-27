package com.booknest.controller;

import com.booknest.entity.User;
import com.booknest.service.CartService;
import com.booknest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * ControllerAdvice to automatically add common model attributes to all views
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final UserService userService;
    private final CartService cartService;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @ModelAttribute("cartItemCount")
    public Integer getCartItemCount() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            return cartService.getCartItemCount(currentUser);
        }
        return 0;
    }
}
