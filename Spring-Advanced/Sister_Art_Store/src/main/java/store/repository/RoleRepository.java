package store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByAuthority(String authority);
}
