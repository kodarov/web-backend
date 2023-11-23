package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Ad;

@Repository
public interface AdRepository extends JpaRepository<Ad,Integer> {
}
