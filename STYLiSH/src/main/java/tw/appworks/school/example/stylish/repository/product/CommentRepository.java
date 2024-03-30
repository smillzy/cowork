package tw.appworks.school.example.stylish.repository.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.product.Comment;

@Repository
@Slf4j
public class CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveAll(Comment comment){
        String insertSql = "INSERT INTO comment (user_id, star, product_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, comment.getUserId(), comment.getStar(), comment.getProductId());
    }


}
