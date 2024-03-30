package tw.appworks.school.example.stylish.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.user.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("""
                SELECT r FROM Role r WHERE r.name = :name
            """)
    Role findRoleByName(@Param("name") String name);
}
