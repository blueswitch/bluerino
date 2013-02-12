package at.abraxas.amarino.plugins.timetick;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.plugins.AbstractPluginService;

public class BackgroundService extends AbstractPluginService {
	
	private static final String TAG = "TimeTick Plugin";
	
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			int minutes = new Date().getMinutes();
			
			if (DEBUG) Log.d(TAG, "send: " + minutes);
			if ((minutes % 2)  == 1)
			{
				Amarino.sendDataFromPlugin(context, pluginId, "31");
				Amarino.sendDataFromPlugin(context, pluginId, "41");
			}
			
			if ((minutes % 2)  == 0)
			{
				Amarino.sendDataFromPlugin(context, pluginId, "30");
				Amarino.sendDataFromPlugin(context, pluginId, "40");
			}
			
		}
	};
	
	
	@Override
	public void init() {
		if (!pluginEnabled){
			/* here should be your specific initialization code */
			
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			pluginId = prefs.getInt(EditActivity.KEY_PLUGIN_ID, -1);
			
			IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
			registerReceiver(receiver, filter);

			pluginEnabled = true;
		}
	}


	@Override
	public void onDestroy() {
		if (pluginEnabled){
			unregisterReceiver(receiver);
		}
		super.onDestroy();
	}
	
	@Override
	public String getTAG() {
		return TAG;
	}


}
