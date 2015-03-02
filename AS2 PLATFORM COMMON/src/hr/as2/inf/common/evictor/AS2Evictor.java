package hr.as2.inf.common.evictor;

import java.util.LinkedHashMap;
import java.util.Set;

public class AS2Evictor implements Runnable {
	public LinkedHashMap<String, Object> _data = new LinkedHashMap<String, Object>();

	public AS2Evictor() {
	}
	public void putToCache(String key, Object value){
		_data.put(key, value);
	}
	public Object getFromCache(String key){
		return _data.get(key);
	}
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				break;
			}
			// Assume "threshold" contains the date/time such
			// that any network element accessed before it will
			// be evicted

			// Go through all the resources and see
			// which ones to evict
			Set<String> E = _data.keySet();
			for (String name : E) {
				Object value = _data.get(name);
				if (value instanceof AS2EvictionInterface) {
					AS2EvictionInterface value_evictor = (AS2EvictionInterface) value;
					if (value_evictor.isEvictable()) {
						long _last_access = value_evictor.info();
						long _current_time = System.currentTimeMillis();
						if (_last_access>0 && (_current_time - _last_access > 3000)) {
							value_evictor.beforeEviction();
							_data.remove(name);
						}
					}
				} else if (value == null) {
					_data.remove(name);
				}
			}
		}
	}
}
