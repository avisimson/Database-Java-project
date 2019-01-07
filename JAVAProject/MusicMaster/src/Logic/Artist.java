package Logic;
/**
 * Data class of Artist object that contains the details about an artist.
 */
public class Artist {
    //members
    private int artistId;
    private String artistName;
    private double artistHotness;

    /**
     * Constructor
     * @param artistId is the id of the artist
     * @param artistName is the name of the artist
     * @param artistHotness is hotness of the artist
     */
    public Artist(int artistId, String artistName, double artistHotness) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistHotness = artistHotness;
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

    /**
     * @return the hotness of the artist
     */
    public double getArtistHotness() {
        return artistHotness;
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
