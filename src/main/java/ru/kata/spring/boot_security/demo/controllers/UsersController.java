package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UsersController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UsersController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    /////CRUD Operations//////

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "getAll";
    }

    @GetMapping("/admin/{id}")
    public String find(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "find";
    }

    @GetMapping("/admin/new")
    public ModelAndView newUser() {
        User user = new User();
        ModelAndView mav = new ModelAndView("/new");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleService.getAllRole();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.addNewUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public ModelAndView editUser(@PathVariable(name = "id") Integer id) {
        User user = userService.findById(id);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("user", user);
        List<Role> roles = (List<Role>) roleService.getAllRole();
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
