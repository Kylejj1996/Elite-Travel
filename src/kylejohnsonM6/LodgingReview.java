package kylejohnsonM6;

public class LodgingReview {
//Members Variables

    String name;
    int rating;
    String comments;

    //Default Constructor
    public LodgingReview() {
        name = "Name";
        rating = 1;
        comments = "Comments";
    }

//Constructor    
    public LodgingReview(String Name, int rating, String comments) {
        this.name = name;
        this.rating = rating;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nRating: " + rating + "\nComments: " + comments;
    }
}
