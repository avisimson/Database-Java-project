package Logic;

/**
 * A data class of artist object.
 */
public class Album {
    //members
    private int albumId;
    private String albumName;

    /**
     * constructor.
     * @param albumId - Unique album number.
     * @param albumName - String of album name.
     */
    public Album(int albumId, String albumName){
        this.albumId = albumId;
        this.albumName = albumName;
    }

    /**
     * @return album id.
     */
    public int getAlbumId(){
        return this.albumId;
    }

    /**
     * @return album name.
     */
    public String getAlbumName(){
        return this.albumName;
    }

}
