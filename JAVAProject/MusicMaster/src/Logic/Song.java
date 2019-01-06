package Logic;

/**
 * class of Song object that contain the details about song table.
 */
public class Song {
    //members
    private int songId;
    private String title;
    private int albumId;
    private int artistId;
    private double songHotess;
    private int year;
    private float endOfFadeIn;
    private String songYoutubeId;

    /**
     * constructor
     * @param songId is id of song
     * @param title is name of song
     * @param albumId is id of album
     * @param artistId is id of artist
     * @param songHotess is hotness of song
     * @param year is the year of song
     * @param endOfFadeIn is end of date in of song
     */
    public Song(int songId, String title, int albumId, int artistId, double songHotess, int year, float endOfFadeIn) {
        this.songId = songId;
        this.title = title;
        this.albumId = albumId;
        this.artistId = artistId;
        this.songHotess = songHotess;
        this.year = year;
        this.endOfFadeIn = endOfFadeIn;
    }
    /**
     * function that get id of song
     * @return id of song
     */
    public int getSongId() {
        return songId;
    }

    /**
     * function that get name of song
     * @return name of song
     */
    public String getTitle() {
        return title;
    }

    /**
     * function that get id of album
     * @return id of album
     */
    public int getAlbumId() {
        return albumId;
    }

    /**
     * function that get id of artist
     * @return id of artist
     */
    public int getArtistId() {
        return artistId;
    }

    /**
     * function that get hotness of song
     * @return hotness of song
     */
    public double getSongHotess() {
        return songHotess;
    }

    /**
     * function that get year of song
     * @return year of song
     */
    public int getYear() {
        return year;
    }

    /**
     * function that get end of fade in of song
     * @return end of fade in song
     */
    public float getEndOfFadeIn() {
        return endOfFadeIn;
    }

    /**
     * function that get Song youtube id
     * @return song youtube id
     */
    public String getSongYoutubeId() {
        return songYoutubeId;
    }

    /**
     * function that update the id of song youtube
     * @param value is the id of song youtube
     */
    public void setSongYoutubeId(String value) {
        this.songYoutubeId = value;
    }

}
