package fi.academy.spaceappspring.model;

import javax.persistence.*;

@Entity
@Table(name="notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;


    public Note() {
    }


    public Note(String text) {
        this.text = text;
    }
    public Note(Long id, String text) {
        this.id = id;
        this.text = text;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
