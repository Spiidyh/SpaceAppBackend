package fi.academy.spaceappspring.repository;

import fi.academy.spaceappspring.model.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, Long> {
}
