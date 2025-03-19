package de.pbma.moa.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class GameDAO {

    @Insert
    public abstract  long insert(Game game);

    @Update
    public abstract void update(Game game);



    @Query("SELECT * FROM gamesTable WHERE tid= :tid ORDER BY gameIndex ASC")
    public abstract List<Game> getGamesFromTournament(String tid);


}
