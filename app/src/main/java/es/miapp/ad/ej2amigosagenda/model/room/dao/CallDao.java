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
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.miapp.ad.ej2amigosagenda.model.room.pojo.Call;

@Dao
public interface CallDao {

    @Insert
    long insert(Call call);

    @Delete
    int delete(Call call);

    @Update
    int update(Call call);

    @Query("DELETE FROM call WHERE id = :id")
    int delete(long id);

    @Query("SELECT * FROM call WHERE id = :id")
    Call get(long id);

    @Query("SELECT * FROM call WHERE idFriend = :id")
    LiveData<List<Call>> getCalls(long id);
}
