package xyz.tahaburak.tburaknote;

/**
 * Created by tahaburaks on 02/04/2017.
 */

public class Note {
    private Integer id;
    private String text, title;

    public Note(Integer id, String title, String text) {
        this.id = id;
        this.text = text;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
