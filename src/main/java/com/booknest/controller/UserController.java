package com.booknest.controller;

import com.booknest.dto.AddressDto;
import com.booknest.entity.*;
import com.booknest.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for user profile and account management
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final ReviewService reviewService;

    @GetMapping("/profile")
    public String profile(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("currentUser", currentUser);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user,
                                RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhone(user.getPhone());

        userService.updateUser(currentUser);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully");

        return "redirect:/profile";
    }

    @GetMapping({"/profile/orders", "/orders"})
    public String orders(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getOrdersByUser(currentUser);
        model.addAttribute("orders", orders);
        model.addAttribute("currentUser", currentUser);

        return "orders";
    }

    @GetMapping("/profile/order/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        Order order = orderService.getOrderById(id);
        if (order == null || !order.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/profile/orders";
        }

        model.addAttribute("order", order);
        model.addAttribute("currentUser", currentUser);

        return "order-details";
    }

    @PostMapping("/profile/order/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            orderService.cancelOrder(id, currentUser);
            redirectAttributes.addFlashAttribute("success", "Order #" + id + " cancelled successfully. Book stock has been restored.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/profile/order/" + id;
    }

    @GetMapping("/profile/addresses")
    public String addresses(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Address> addresses = addressService.getAddressesByUser(currentUser);
        model.addAttribute("addresses", addresses);
        model.addAttribute("currentUser", currentUser);

        return "addresses";
    }

    @GetMapping("/profile/address/add")
    public String addAddress(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("address", new AddressDto());
        model.addAttribute("currentUser", currentUser);

        return "address-form";
    }

    @PostMapping("/profile/address/add")
    public String saveAddress(@Valid @ModelAttribute("address") AddressDto addressDto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "address-form";
        }

        Address address = new Address();
        address.setAddressLine1(addressDto.getAddressLine1());
        address.setAddressLine2(addressDto.getAddressLine2());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPostalCode(addressDto.getPostalCode());
        address.setCountry(addressDto.getCountry());
        address.setPhone(addressDto.getPhone());
        address.setDefaultAddress(addressDto.getDefaultAddress());

        addressService.saveAddress(currentUser, address);
        redirectAttributes.addFlashAttribute("success", "Address added successfully");

        return "redirect:/profile/addresses";
    }

    @GetMapping("/profile/reviews")
    public String reviews(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        List<Review> reviews = reviewService.getReviewsByUser(currentUser);
        model.addAttribute("reviews", reviews);
        model.addAttribute("currentUser", currentUser);

        return "reviews";
    }
}
