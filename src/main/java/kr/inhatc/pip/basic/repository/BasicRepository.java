package kr.inhatc.pip.basic.repository;

import kr.inhatc.pip.basic.entity.Basic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasicRepository extends JpaRepository<Basic, Long> {
    Optional<Basic> findByName(String name);

    List<Basic> findByAge(int age);

    Optional<Basic> findByNameAndAge(String name, int age);
}
