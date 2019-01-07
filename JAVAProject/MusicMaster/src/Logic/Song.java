package Logic;

/**
 * Data class of Song object that contains the details about a song.
 */
public class Song {
    //members
    private int songId;
    private String title;
    private int albumId;
    private int artistId;
    private int year;
    private float endOfFadeIn;
    private String songYoutubeId;

    /**
     * Constructor
     * @param songId is the id of the song
     * @param title is the name of the song
     * @param albumId is the id of the album of the song
     * @param artistId is the id of the artist of the song
     * @param year is the year of the song
     * @param endOfFadeIn is the time of end of fade in of the song
     */
    public Song(int songId, String title, int albumId, int artistId,
                int year, float endOfFadeIn) {
        this.songId = songId;
        this.title = title;
        this.albumId = albumId;
        this.artistId = artistId;
        this.year = year;
        this.endOfFadeIn = endOfFadeIn;
    }
    /**
     * @return the id of the song
     */
    public int getSongId() {
        return songId;
    }

    /**
     * @return the name of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the id of the album of the song
     */
    public int getAlbumId() {
        return albumId;
    }

    /**
     * @return the id of artist of the song
     */
    public int getArtistId() {
        return artistId;
    }

    /**
     * @return the year of the song
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the end of fade in the song
     */
    public float getEndOfFadeIn() {
        return endOfFadeIn;
    }

    /**
     * @return the song youtube id
     */
    public String getSongYoutubeId() {
        return songYoutubeId;
    }

    /**
     * function that updates the id of song in youtube
     * @param value is the id of the song in youtube
     */
    public void setSongYoutubeId(String value) {
        this.songYoutubeId = value;
    }

}
