package Logic;

import DataBase.DBArtists;
import DataBase.DBGenre;
import DataBase.DBSongs;
import java.util.List;
import java.util.Random;

public class GenreLogic {

    private DBArtists conA = new DBArtists();
    private DBGenre conG = new DBGenre();
    private DBSongs conS = new DBSongs();

    public List<Genre> getListOfGenres(){
        return conG.GenreList();
    }

    public List<Artist> getArtistsByGenre(List<Genre> s){
        return conA.FilterArtistByGenre(s);
    }

    //for the game , can be change
    public Artist getFilterOneArtist(List<Artist> artistFilter) {return conA.FilterOneArtist(artistFilter);}

    public List<Song> getFilterSong(String oneArtist) {return conS.FilterSong(oneArtist);}

    public Song getFilterSpecificSong(List<Song> songsList) {return conS.FilterSpecificSong(songsList);}

    public String[] getThreeConfusionAns (String artistName) {
        List<Artist> confusionArtists = conA.CreateConfusionAns(artistName);
        String ans[] = new String[3];
        System.out.println("--------------The Selected CAns---------------");
        System.out.println("confusionArtists.size() :" + confusionArtists.size());
        if (confusionArtists.size() == 0) {
            ans[0] = conA.FilterArtistDifferent(artistName,null, null).getArtistName();
            ans[1] = conA.FilterArtistDifferent(artistName,ans[0],null).getArtistName();
            ans[2] = conA.FilterArtistDifferent(artistName,ans[0],ans[1]).getArtistName();
        }
        else if (confusionArtists.size() == 1)
        {
            //     System.out.println("--------------HELP1---------------");
            ans[0] = confusionArtists.get(0).getArtistName();
            ans[1] = conA.FilterArtistDifferent(artistName,ans[0],null).getArtistName();
            ans[2] = conA.FilterArtistDifferent(artistName,ans[0],ans[1]).getArtistName();
        } else if(confusionArtists.size() == 2) {
            //    System.out.println("--------------HELP2---------------");
            ans[0] = confusionArtists.get(0).getArtistName();
            ans[1] = confusionArtists.get(1).getArtistName();
            ans[2] = conA.FilterArtistDifferent(artistName,ans[0],ans[1]).getArtistName();
        } else {
            System.out.println("linoy in");

            Random rand = new Random();
            int x = rand.nextInt(confusionArtists.size());
            while(confusionArtists.get(x).getArtistName() == null)
                x = rand.nextInt(confusionArtists.size());
            ans[0] = confusionArtists.get(x).getArtistName();
            int y = rand.nextInt(confusionArtists.size());
            while((x == y) || ((confusionArtists.get(y).getArtistName() == null)))
                y = rand.nextInt(confusionArtists.size());
            ans[1] = confusionArtists.get(y).getArtistName();
            int z = rand.nextInt(confusionArtists.size());
            while((z == y) ||(z == x) || (confusionArtists.get(z).getArtistName() == null))
                z = rand.nextInt(confusionArtists.size());
            ans[2] = confusionArtists.get(z).getArtistName();
        }
        System.out.println(ans[0]);
        System.out.println(ans[1]);
        System.out.println(ans[2]);
        return ans;
    }

    public Question[] Create20Questions(List<Artist> artistFilter) {
        Question questionsForGame[] = new Question[20];
        String wrongAnswer[];
        String CAns;
        Song Q;
        int i = 0;

        for (i = 0;i < 20;i++) {
            CAns = getFilterOneArtist(artistFilter).getArtistName();
            System.out.println("------------CANS---------");
            System.out.println(CAns);
            Q = getFilterSpecificSong(getFilterSong(CAns));
            System.out.println("----------Q-------------");
            System.out.println(Q.getTitle());
            wrongAnswer = getThreeConfusionAns(CAns);
            questionsForGame[i] = new Question(Q, CAns,wrongAnswer[0],wrongAnswer[1],wrongAnswer[2]);
            System.out.println("------------Question numbar =" + i);
            System.out.println(questionsForGame[i].getSongName().getTitle());
            System.out.println(questionsForGame[i].getCurrentAnswer());
            System.out.println(questionsForGame[i].getConfAns1());
            System.out.println(questionsForGame[i].getConfAns2());
            System.out.println(questionsForGame[i].getConfAns3());
        }
        return questionsForGame;
    }

}
