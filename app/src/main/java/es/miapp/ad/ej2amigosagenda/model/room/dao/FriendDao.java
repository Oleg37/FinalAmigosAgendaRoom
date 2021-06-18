/*
 * Copyright (c) 2021. ArseneLupin0.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete source
 * code of licensed works and modifications, which include larger works using a licensed work,
 * under the same license. Copyright and license notices must be preserved. Contributors provide
 * an express grant of patent rights.
 */

package es.miapp.ad.ej2amigosagenda.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.NumCalls;

@Dao
public interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Friend friend);

    @Update
    int update(Friend friend);

    @Delete
    int delete(Friend friend);

    @Query("DELETE FROM friend WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM friend WHERE id = :id")
    Friend get(long id);

    @Query("SELECT * FROM friend WHERE phNumber = :phNumber")
    Friend getByPHNumber(String phNumber);

    @Query("SELECT * FROM friend ORDER BY name")
    LiveData<List<Friend>> getAllFriend();

    @Query("DELETE FROM friend")
    void deleteAll();

    @Query("SELECT * FROM Friend ORDER BY name")
    List<Friend> getAllFriendList();

    @Query("SELECT f.*, COUNT(c.id) AS times FROM friend f LEFT JOIN call c ON f.id = c.idFriend GROUP BY f.id ORDER BY COUNT(f.name)")
    LiveData<List<NumCalls>> getAllNumCalls();
}