package Logic;
/**
 * Data class of Genre object that contain the details about a genre.
 */
public class Genre {
    //members
    private int genreId;
    private String genreName;

    /**
     * Constructor
     * @param genreId is the id of the genre
     * @param genreName is the name of the genre
     */
    public Genre(int genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
    /**
     * @return the id of the genre
     */
    public int getGenreId() {
        return genreId;
    }
    /**
     * @return the name of the genre
     */
    public String getGenreName() {
        return genreName;
    }
}
