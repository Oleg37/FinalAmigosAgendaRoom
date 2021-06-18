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

package es.miapp.ad.ej2amigosagenda.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.miapp.ad.ej2amigosagenda.model.room.dao.CallDao;
import es.miapp.ad.ej2amigosagenda.model.room.dao.FriendDao;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Call;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;

@Database(entities = {Friend.class, Call.class}, version = 1, exportSchema = false)
public abstract class FriendsDB extends RoomDatabase {

    private volatile static FriendsDB INSTANCE;

    public static synchronized FriendsDB getDB(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    FriendsDB.class, "FriendsBD.sqlite")
                    .build();
        }
        return INSTANCE;
    }

    public abstract CallDao getCallDao();

    public abstract FriendDao getFriendDao();
}