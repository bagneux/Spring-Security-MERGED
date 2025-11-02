package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository; // нужно для получения ролей

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Лист пользователей
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin-user-list"; // html файл
    }

    // Создание пользователя (GET форма)
    @GetMapping("/user-create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin-user-create";
    }

    // Создать пользователя (POST)
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        userService.saveUser(user.getUsername(), user.getPassword(), roles);
        return "redirect:/admin/users";
    }

    // Показать форму редактирования
    @GetMapping("/user-update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin/users"; // Или обработать ошибку
        }
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin-user-update";
    }

    // Обработка обновления
    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User formUser,
                             @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
                             @RequestParam(value = "password", required = false) String password) {
        Set<Role> roles = roleIds != null ? new HashSet<>(roleRepository.findAllById(roleIds)) : null;

        userService.updateUser(formUser.getId(), formUser.getUsername(), password, roles);
        return "redirect:/admin/users";
    }

    // Удаление пользователя
    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
