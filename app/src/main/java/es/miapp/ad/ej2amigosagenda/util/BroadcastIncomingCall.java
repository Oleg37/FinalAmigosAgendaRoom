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

package es.miapp.ad.ej2amigosagenda.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.util.Date;
import java.util.List;

import es.miapp.ad.ej2amigosagenda.model.Repository;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Call;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

public class BroadcastIncomingCall extends BroadcastReceiver {

    private boolean noRepeat = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        Repository repository = new Repository(context);

        if (!intent.getAction().equals(ACTION_PHONE_STATE_CHANGED)) {
            return;
        }

        if (intent.getStringExtra(EXTRA_STATE).equalsIgnoreCase(EXTRA_STATE_RINGING) && noRepeat) {
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephony.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String phoneNumber) {
                    super.onCallStateChanged(state, phoneNumber);

                    if (state != CALL_STATE_RINGING) {
                        return;
                    }

                    if (!noRepeat) {
                        return;
                    }

                    saveIncomingCall(repository, phoneNumber, new Date().getTime());
                    noRepeat = false;
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    public void saveIncomingCall(Repository repository, String incomingCall, long callDate) {
        ScheduleThread.threadExecutorPool.execute(() -> {
            List<Friend> list = repository.getFriendList();

            for (int i = 0; i < list.size(); i++) {
                if (PhoneNumberUtils.compare(list.get(i).getPhNumber(), incomingCall)) {
                    repository.insert(new Call(0, list.get(i).getId(), callDate));
                    break;
                }
            }
        });
    }
}