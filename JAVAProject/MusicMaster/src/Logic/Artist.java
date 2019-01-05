package Logic;

public class Artist {

    private int artistId;
    private String artistName;
    private double artistHotness;

    public Artist(int artistId, String artistName, double artistHotness) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistHotness = artistHotness;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public double getArtistHotness() {
        return artistHotness;
    }

}
