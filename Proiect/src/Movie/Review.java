package Movie;

import User.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Review {
    private int reviewId;
    private User user;
    private Item item;
    private int rating;
    private String comment;
    private LocalDateTime dateAdd;

    public Review(){
        this.reviewId = 0;
        this.user =null;
        this.item = null;
        this.rating = 0;
        this.comment = "";
        this.dateAdd = null;
    }
    public Review(int reviewId, User user, Item item, int rating, String comment, LocalDateTime dateAdd) {
        this.reviewId = reviewId;
        this.user = user;
        this.item = item;
        this.rating = rating;
        this.comment = comment;
        this.dateAdd = dateAdd;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(LocalDateTime dateAdd) {
        this.dateAdd = dateAdd;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Review other = (Review) obj;
        if (!Objects.equals(this.user, other.user))
            return false;
        if (!Objects.equals(this.comment, other.comment))
            return false;
        if (!Objects.equals(this.dateAdd, other.dateAdd))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user,comment,dateAdd);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n")
                .append("[").append(this.comment).append("\n")
                .append("Rating: ").append(this.rating).append("\n");
        if(item instanceof Movie){
            sb.append("Movie: ").append(item.getTitle()).append("\n");
        }
        else if(item instanceof Series){
            sb.append("Series: ").append(item.getTitle()).append("\n");
        }
        sb.append("User: ").append(user.getUsername()).append("\n")
                .append("Added at: ").append(dateAdd).append("]\n");
        return sb.toString();
    }
}
