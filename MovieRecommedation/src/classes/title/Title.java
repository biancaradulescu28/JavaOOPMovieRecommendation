package classes.title;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Title {
    private int Id;
    private String Title;
    private List<String>genre;
    private String language;

    public Title(){
        Id = 0;
        Title = "";
        genre = new ArrayList<>();
        language = "";
    }

    public Title(int id, String title, List<String> genre, String language) {
        Id = id;
        Title = title;
        this.genre = genre;
        this.language = language;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Title other = (Title) obj;
        if (!Objects.equals(this.Title, other.Title))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Title);
    }

    @Override
    public String toString() {
        return "Title{" +
                "Id=" + Id +
                ", Title='" + Title + '\'' +
                ", genre=" + genre +
                ", language='" + language + '\'' +
                '}';
    }
}
