package Logic;

public class Song {

    int songId;
    String title;
    int albumId;
    int artistId;
    double songHotess;
    int year;
    float endOfFadeIn;

    public Song(int songId, String title, int albumId, int artistId, double songHotess, int year, float endOfFadeIn) {
        this.songId = songId;
        this.title = title;
        this.albumId = albumId;
        this.artistId = artistId;
        this.songHotess = songHotess;
        this.year = year;
        this.endOfFadeIn = endOfFadeIn;
    }

    public int getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getArtistId() {
        return artistId;
    }

    public double getSongHotess() {
        return songHotess;
    }

    public int getYear() {
        return year;
    }

    public float getEndOfFadeIn() {
        return endOfFadeIn;
    }
}
