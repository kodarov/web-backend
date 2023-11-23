package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Integer> {
}
