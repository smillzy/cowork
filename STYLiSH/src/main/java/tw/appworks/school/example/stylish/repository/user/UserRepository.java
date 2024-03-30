package tw.appworks.school.example.stylish.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserDetailsRepository {

    @Query
    User findUserByEmail(@Param("email") String email);

}
