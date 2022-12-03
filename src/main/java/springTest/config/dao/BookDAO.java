package springTest.config.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import springTest.config.exceptions.NotFoundEx;
import springTest.config.models.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
            return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?",
            new BeanPropertyRowMapper<>(Book.class),
            new Object[]{id} );
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, description) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getDescription());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, description=? WHERE id=? ",
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=? ",
                id);
    }
}
