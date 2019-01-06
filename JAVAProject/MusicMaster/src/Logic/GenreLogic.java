package Logic;

import DataBase.DBArtists;
import DataBase.DBGenre;
import java.util.List;

/**
 * This class manages the logic of the genre screen and the connection to the DB.
 */
public class GenreLogic {
    //members
    private DBArtists conA = new DBArtists();
    private DBGenre conG = new DBGenre();

    /**
     * This function returns the list of all the genres in the DB.
     * @return genre list
     */
    public List<Genre> getListOfGenres(){
        return conG.GenreList();
    }

    /**
     * This function gets the list of chosen genres by the player and return a list of
     * artist that sing in those genres.
     * @param genres is the list of genres that the player chose.
     * @return a list of artists that sings in one of selected genres.
     */
    public List<Artist> getArtistsByGenre(List<Genre> genres){
        return conA.FilterArtistByGenre(genres);
    }

}
