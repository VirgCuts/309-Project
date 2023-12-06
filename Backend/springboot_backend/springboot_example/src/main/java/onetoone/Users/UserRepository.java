package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);

    @Transactional
    void deleteById(int id);

    User findByName(String name);

    @Transactional
    void deleteByName(String name);
    User findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
}
