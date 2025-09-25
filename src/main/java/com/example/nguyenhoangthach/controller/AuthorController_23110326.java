package com.example.nguyenhoangthach.controller;

import com.example.nguyenhoangthach.entity.Author_23110326;
import com.example.nguyenhoangthach.service.AuthorService_23110326;
import com.example.nguyenhoangthach.service.SessionService_23110326;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/authors")
public class AuthorController_23110326 {

    @Autowired
    private AuthorService_23110326 authorService_23110326;

    @Autowired
    private SessionService_23110326 sessionService_23110326;

    @GetMapping
    public String listAuthors(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(required = false) String keyword,
                             HttpSession session, Model model) {
        
        // Kiểm tra quyền admin
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        Page<Author_23110326> authors;
        if (keyword != null && !keyword.trim().isEmpty()) {
            authors = authorService_23110326.searchAuthors(keyword, page, size);
            model.addAttribute("keyword", keyword);
        } else {
            authors = authorService_23110326.getAllAuthors(page, size);
        }

        model.addAttribute("authors", authors);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", authors.getTotalPages());
        model.addAttribute("totalItems", authors.getTotalElements());

        return "admin/authors/list_23110326";
    }

    @GetMapping("/new")
    public String showNewAuthorForm(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        model.addAttribute("author", new Author_23110326());
        return "admin/authors/form_23110326";
    }

    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute Author_23110326 author, HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        authorService_23110326.saveAuthor(author);
        redirectAttributes.addFlashAttribute("message", "Tác giả đã được thêm thành công!");
        return "redirect:/admin/authors";
    }

    @PostMapping("/update")
    public String updateAuthor(@ModelAttribute Author_23110326 author, HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        authorService_23110326.updateAuthor(author);
        redirectAttributes.addFlashAttribute("message", "Tác giả đã được cập nhật thành công!");
        return "redirect:/admin/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditAuthorForm(@PathVariable Integer id, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        Author_23110326 author = authorService_23110326.getAuthorById(id).orElse(null);
        if (author == null) {
            return "redirect:/admin/authors";
        }

        model.addAttribute("author", author);
        return "admin/authors/form_23110326";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        authorService_23110326.deleteAuthor(id);
        redirectAttributes.addFlashAttribute("message", "Tác giả đã được xóa thành công!");
        return "redirect:/admin/authors";
    }
}
