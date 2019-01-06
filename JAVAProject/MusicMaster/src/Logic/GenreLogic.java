package Logic;

import DataBase.DBArtists;
import DataBase.DBConnection;
import DataBase.DBGenre;
import java.util.List;

/**
 * class that manage the logic of genre screen and the their connection to correct DB.
 */
public class GenreLogic {
    //members
    private DBArtists conA = new DBArtists();
    private DBGenre conG = new DBGenre();
    /**
     * function that return the list of genre that user choose in order to play
     * @return genre list
     */
    public List<Genre> getListOfGenres(){
        return conG.GenreList();
    }
    /**
     * function that return the list of Artist that songs in one of selected genre
     * @param s is the list of genre that player selected
     * @return list of Artist that songs in one of selected genre
     */
    public List<Artist> getArtistsByGenre(List<Genre> s){
        return conA.FilterArtistByGenre(s);
    }

}
