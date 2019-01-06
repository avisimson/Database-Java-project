package Logic;
/**
 * class of Genre object that contain the details about genre table.
 */
public class Genre {
    //members
    int genreId;
    String genreName;

    /**
     * constructor
     * @param genreId is the id of genre
     * @param genreName is the name of genre
     */
    public Genre(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    /**
     * function that get id of genre
     * @return id of genre
     */
    public int getGenreId() {
        return genreId;
    }
    /**
     * function that get name of genre
     * @return name of genre
     */
    public String getGenreName() {
        return genreName;
    }
}
