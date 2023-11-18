package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.Id;
import java.time.LocalDate;

//@Entity
@Data
public class Comment {

    @Id
    private Long id;
    private String authorImage;
    private String authorFirstName;
    private LocalDate localDate;
    public Comment(User user,String text)
    {
        this.authorFirstName=user.getFirstName();
        this.authorImage=user.getImage();

    }
    public Comment() {
    }

}
