package com.example.nguyenhoangthach.controller;

import com.example.nguyenhoangthach.entity.Users_23110326;
import com.example.nguyenhoangthach.entity.Rating_23110326;
import com.example.nguyenhoangthach.service.SessionService_23110326;
import com.example.nguyenhoangthach.service.BookService_23110326;
import com.example.nguyenhoangthach.service.RatingService_23110326;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController_23110326 {

    @Autowired
    private SessionService_23110326 sessionService;

    @Autowired
    private BookService_23110326 bookService;

    @Autowired
    private RatingService_23110326 ratingService;

    @GetMapping({"/", "/home"})
    public String home(HttpSession session, Model model) {
        Users_23110326 currentUser = (Users_23110326) sessionService.getAttribute(session, "currentUser");
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getFullname());
            model.addAttribute("isAdmin", sessionService.getAttribute(session, "isAdmin"));
            // Truyền session data vào model để template có thể truy cập
            model.addAttribute("session", session);
            return "home";
        }
        return "redirect:/login";
    }

    @GetMapping("/products")
    public String products(HttpSession session, Model model) {
        Users_23110326 currentUser = (Users_23110326) sessionService.getAttribute(session, "currentUser");
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getFullname());
            model.addAttribute("books", bookService.getAllBooksWithAuthors());
            model.addAttribute("bookService", bookService);
            return "products";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        Users_23110326 currentUser = (Users_23110326) sessionService.getAttribute(session, "currentUser");
        Boolean isAdmin = (Boolean) sessionService.getAttribute(session, "isAdmin");
        
        if (currentUser != null && isAdmin != null && isAdmin) {
            model.addAttribute("username", currentUser.getFullname());
            model.addAttribute("session", session);
            return "admin/dashboard";
        }
        return "redirect:/home";
    }

    @GetMapping("/book/{id}")
    public String bookDetail(@PathVariable Integer id, HttpSession session, Model model) {
        Users_23110326 currentUser = (Users_23110326) sessionService.getAttribute(session, "currentUser");
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getFullname());
            model.addAttribute("currentUser", currentUser);
            
            var book = bookService.getBookById(id);
            if (book.isPresent()) {
                model.addAttribute("book", book.get());
                model.addAttribute("ratings", ratingService.getRatingsByBookId(id));
                model.addAttribute("bookService", bookService);
                return "book-detail";
            }
        }
        return "redirect:/products";
    }

    @PostMapping("/book/{id}/review")
    public String submitReview(@PathVariable Integer id, 
                              @RequestParam Byte rating, 
                              @RequestParam String review_text,
                              HttpSession session, 
                              RedirectAttributes redirectAttributes) {
        Users_23110326 currentUser = (Users_23110326) sessionService.getAttribute(session, "currentUser");
        if (currentUser != null) {
            var book = bookService.getBookById(id);
            if (book.isPresent()) {
                try {
                    // Check if user already reviewed this book
                    var existingRating = ratingService.getRatingByUserAndBook(currentUser.getId(), id);
                    
                    Rating_23110326 ratingEntity;
                    if (existingRating.isPresent()) {
                        // Update existing review
                        ratingEntity = existingRating.get();
                        ratingEntity.setRating(rating);
                        ratingEntity.setReview_text(review_text);
                        ratingService.updateRating(ratingEntity);
                        redirectAttributes.addFlashAttribute("message", "Đánh giá đã được cập nhật!");
                    } else {
                        // Create new review
                        ratingEntity = ratingService.createRating(currentUser, book.get(), rating, review_text);
                        ratingService.saveRating(ratingEntity);
                        redirectAttributes.addFlashAttribute("message", "Đánh giá đã được thêm thành công!");
                    }
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu đánh giá: " + e.getMessage());
                }
            }
        }
        return "redirect:/book/" + id;
    }
}