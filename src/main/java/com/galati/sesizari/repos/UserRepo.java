package com.galati.sesizari.repos;

 import com.galati.sesizari.clase.User;
import com.galati.sesizari.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;
 import java.util.List;
 @Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);

    User findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);

    List<User> findByRol(Rol rol);
}
