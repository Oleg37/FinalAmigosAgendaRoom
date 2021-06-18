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

package es.miapp.ad.ej2amigosagenda.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import es.miapp.ad.ej2amigosagenda.model.room.FriendsDB;
import es.miapp.ad.ej2amigosagenda.model.room.dao.CallDao;
import es.miapp.ad.ej2amigosagenda.model.room.dao.FriendDao;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Call;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.NumCalls;
import es.miapp.ad.ej2amigosagenda.util.ScheduleThread;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repository {

    private final FriendDao friendDao;
    private final CallDao callDao;
    private Friend friend;
    private LiveData<List<Friend>> friendListLiveData;
    private List<Friend> friendList;
    private List<Call> callList;
    private LiveData<List<Call>> callListLiveData;
    private LiveData<List<NumCalls>> numCallsListLiveData;

    public Repository(Context context) {
        FriendsDB db = FriendsDB.getDB(context);

        callDao = db.getCallDao();
        friendDao = db.getFriendDao();
    }

    public void insert(Friend friend) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                friendDao.insert(friend);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void update(Friend friend) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                friendDao.update(friend);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void delete(Friend friend) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                friendDao.delete(friend);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void insert(Call call) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                callDao.insert(call);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void update(Call call) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                callDao.update(call);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void delete(Call call) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                callDao.delete(call);
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public void deleteAll() {
        ScheduleThread.threadExecutorPool.execute(() -> {
            try {
                friendDao.deleteAll();
            } catch (Exception e) {
                Log.e("xyz", e.getMessage());
            }
        });
    }

    public LiveData<List<Call>> getCallListLiveData(long id) {
        return callDao.getCalls(id);
    }

    public LiveData<List<NumCalls>> getNumCallsListLiveData() {
        if (numCallsListLiveData == null) {
            numCallsListLiveData = friendDao.getAllNumCalls();
        }
        return numCallsListLiveData;
    }

    public List<Friend> getFriendList() {
        if (friendList == null) {
            friendList = friendDao.getAllFriendList();
        }
        return friendList;
    }
}