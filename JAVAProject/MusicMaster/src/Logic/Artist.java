package Logic;
/**
 * A data class of artist object that contains the details about an artist.
 */
public class Artist {
    //members
    private int artistId;
    private String artistName;
    /**
     * Constructor
     * @param artistId is the id of the artist
     * @param artistName is the name of the artist
     */
    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    /**
     * @return the id of the artist
     */
    public int getArtistId() {
        return artistId;
    }

    /**
     * @return the name of the artist
     */
    public String getArtistName() {
        return artistName;
    }


    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass()) {
            Artist a = (Artist)o;
            if (a.getArtistId() == this.getArtistId()) {
                return true;
            }
            return false;
        }
        return false;
    }
}
