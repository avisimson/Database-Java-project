package Logic;

import DataBase.DBArtists;
import DataBase.DBGenre;
import java.util.List;

public class GenreLogic {

    private DBArtists conA = new DBArtists();
    private DBGenre conG = new DBGenre();

    public List<Genre> getListOfGenres(){
        return conG.GenreList();
    }

    public List<Artist> getArtistsByGenre(List<Genre> s){
        return conA.FilterArtistByGenre(s);
    }

}
