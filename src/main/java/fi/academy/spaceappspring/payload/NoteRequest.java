package fi.academy.spaceappspring.payload;

import javax.validation.constraints.NotBlank;

public class NoteRequest {
    @NotBlank
    private String text;

    public NoteRequest() {
    }

    public NoteRequest(@NotBlank String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
