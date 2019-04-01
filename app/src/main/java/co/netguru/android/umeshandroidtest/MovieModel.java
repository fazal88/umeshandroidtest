package co.netguru.android.umeshandroidtest;

import java.util.ArrayList;

public class MovieModel {
    private String title;
    private String image;
    private String rating;
    private String releaseYear;
    ArrayList< String > genre = new ArrayList < String > ();


    // Getter Methods

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    // Setter Methods

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
}