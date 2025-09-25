package com.example.nguyenhoangthach.controller;

import com.example.nguyenhoangthach.entity.Books_23110326;
import com.example.nguyenhoangthach.service.BookService_23110326;
import com.example.nguyenhoangthach.service.AuthorService_23110326;
import com.example.nguyenhoangthach.service.SessionService_23110326;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/books")
public class BookController_23110326 {

    @Autowired
    private BookService_23110326 bookService_23110326;

    @Autowired
    private AuthorService_23110326 authorService_23110326;

    @Autowired
    private SessionService_23110326 sessionService_23110326;

    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size,
                           @RequestParam(defaultValue = "bookid") String sortBy,
                           @RequestParam(defaultValue = "asc") String sortDir,
                           @RequestParam(required = false) String keyword,
                           HttpSession session, Model model) {
        
        // Kiểm tra quyền admin
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        Page<Books_23110326> books;
        if (keyword != null && !keyword.trim().isEmpty()) {
            books = bookService_23110326.searchBooks(keyword, page, size, sortBy, sortDir);
            model.addAttribute("keyword", keyword);
        } else {
            books = bookService_23110326.getAllBooks(page, size, sortBy, sortDir);
        }

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("totalItems", books.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "admin/books/list_23110326";
    }

    @GetMapping("/new")
    public String showNewBookForm(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        model.addAttribute("book", new Books_23110326());
        model.addAttribute("authors", authorService_23110326.getAllAuthors());
        return "admin/books/form_23110326";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Books_23110326 book, 
                          @RequestParam(value = "authorIds", required = false) List<Integer> authorIds,
                          HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        try {
            // Kiểm tra ISBN duplicate khi tạo mới
            Optional<Books_23110326> existingBook = bookService_23110326.findByIsbn(book.getIsbn());
            if (existingBook.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "ISBN đã tồn tại! Vui lòng chọn ISBN khác.");
                return "redirect:/admin/books/new";
            }
            
            // Xử lý authors
            if (authorIds != null && !authorIds.isEmpty()) {
                bookService_23110326.assignAuthorsToBook(book, authorIds);
            }
            
            bookService_23110326.saveBook(book);
            redirectAttributes.addFlashAttribute("message", "Sách đã được thêm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm sách: " + e.getMessage());
            return "redirect:/admin/books/new";
        }
        
        return "redirect:/admin/books";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute Books_23110326 book, 
                           @RequestParam(value = "authorIds", required = false) List<Integer> authorIds,
                           HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        try {
            // Xử lý authors
            if (authorIds != null) {
                bookService_23110326.assignAuthorsToBook(book, authorIds);
            }
            
            bookService_23110326.updateBook(book);
            redirectAttributes.addFlashAttribute("message", "Sách đã được cập nhật thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật sách: " + e.getMessage());
        }
        
        return "redirect:/admin/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Integer id, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        Books_23110326 book = bookService_23110326.getBookById(id).orElse(null);
        if (book == null) {
            return "redirect:/admin/books";
        }

        model.addAttribute("book", book);
        model.addAttribute("authors", authorService_23110326.getAllAuthors());
        return "admin/books/form_23110326";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        Boolean isAdmin = (Boolean) sessionService_23110326.getAttribute(session, "isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/home";
        }

        bookService_23110326.deleteBook(id);
        redirectAttributes.addFlashAttribute("message", "Sách đã được xóa thành công!");
        return "redirect:/admin/books";
    }
}
