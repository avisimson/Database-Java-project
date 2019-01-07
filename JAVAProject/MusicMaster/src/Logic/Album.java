package Logic;

public class Album {
    //members
    private int albumId;
    private String albumName;

    public Album(int albumId, String albumName){
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public int getAlbumId(){
        return this.albumId;
    }

    public String getAlbumName(){
        return this.albumName;
    }

}
