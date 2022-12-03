package springTest.config.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springTest.config.dao.BookDAO;
import springTest.config.models.Book;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books")
public class BooksController implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpServletResponse.setHeader("Pragma", "No-cache");
        httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private final BookDAO bookDAO;

    @Autowired
    BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false, defaultValue = "id")  String sort) {
        if (sort.equals("id")) {
            return bookDAO.index();
        } else {
            return bookDAO.index().stream()
                    .sorted(new Comparator<Book>() {
                        @Override
                        public int compare(Book o1, Book o2) {
                            String s1 = "default1";
                            String s2 = "default2";
                            switch (sort.toLowerCase()) {
                                case "title":
                                    s1 = o1.getTitle().toLowerCase();
                                    s2 = o2.getTitle().toLowerCase();
                                    break;
                            }
                            return -s1.compareTo(s2);
                        }
                    }).toList();
        }
    }

    @GetMapping("grouped")
    public Map<String, List<Book>> getGroupBooks(@RequestParam(required = false, defaultValue = "author") String group) {
        return bookDAO.index().stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") int id) {
        return bookDAO.show(id);
    }

    @PostMapping
    public List<Book> setBook(@RequestBody Book book) {
        bookDAO.save(book);
        return bookDAO.index();
    }

    @PatchMapping("/{id}")
    public List<Book> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        bookDAO.update(id, book);
        return bookDAO.index();
    }

    @DeleteMapping("/{id}")
    public List<Book> getAllBooksGroupByAuthor(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return bookDAO.index();
    }

}
