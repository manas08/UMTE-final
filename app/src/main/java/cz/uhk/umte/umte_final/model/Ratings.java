package cz.uhk.umte.umte_final.model;

public class Ratings {

    int id;
    String user;
    Float stars;
    String ratingText;

    public Ratings(int id, String user, String ratingText, Float stars) {
        this.id = id;
        this.user = user;
        this.stars = stars;
        this.ratingText = ratingText;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Float getStars() {
        return stars;
    }

    public void setStars(Float stars) {
        this.stars = stars;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }
}
