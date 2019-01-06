package Logic;
/**
 * class of Artist object that contain the details about artist table.
 */
public class Artist {
    //members
    private int artistId;
    private String artistName;
    private double artistHotness;

    /**
     * constructor
     * @param artistId is the id of artist
     * @param artistName is the name of artist
     * @param artistHotness is hotness of artist
     */
    public Artist(int artistId, String artistName, double artistHotness) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistHotness = artistHotness;
    }

    /**
     * function that get id of artist
     * @return id of artist
     */
    public int getArtistId() {
        return artistId;
    }

    /**
     * function that get name of artist
     * @return name of artist
     */
    public String getArtistName() {
        return artistName;
    }
    /**
     * function that get the hotness of artist
     * @return hotness of artist
     */
    public double getArtistHotness() {
        return artistHotness;
    }
}
