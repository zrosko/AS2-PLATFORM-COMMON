package hr.as2.inf.common.cache;

import hr.as2.inf.common.core.AS2Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AS2CacheManager implements Runnable {
	private Map<String, AS2Cacheable> cacheHashMap = new HashMap<String, AS2Cacheable>();
	private boolean _cacheActive = false;
	private boolean _cacheEnabled;

	public AS2CacheManager() {
		try {
			if (AS2Context.getInstance().SERVER_CACHING_IND == true) {
				Thread cleanerThread = new Thread(this);
				cleanerThread.setPriority(Thread.MIN_PRIORITY);
				cleanerThread.start();
				activateCache();
			}
		} catch (Exception e) {
			System.out.println("CacheManager start method exception - " + e);
		}
	}

	public synchronized void activateCache() {
		_cacheEnabled = AS2Context.getInstance().SERVER_CACHING_IND;
		_cacheActive = true;
		System.out.println("CACHE ACTIVATED!");
	}

	public void cleanCache() {
		synchronized (cacheHashMap) {
			try {
				cacheHashMap.clear();
			} catch (Exception e) {
				cacheHashMap = new HashMap<String, AS2Cacheable>();
			}
		}
	}

	public synchronized void deactivateCache() {
		_cacheEnabled = AS2Context.getInstance().SERVER_CACHING_IND;
		_cacheActive = false;
		System.out.println("CACHE DEACTIVATED!");
	}

	public List<String> getAllKeys() {
		List<String> allElements = new ArrayList<String>();
		synchronized (cacheHashMap) {
			Set<String> keySet = cacheHashMap.keySet();
			Iterator<String> keys = keySet.iterator();
			while (keys.hasNext()) {
				Object key = keys.next();
				AS2Cacheable value = cacheHashMap.get(key);
				if (!value.isExpired()) {
					// puts all keys of cacheable objects in a list
					allElements.add(value.getIdentifier());
				}
			}
		}
		return allElements;
	}

	public synchronized boolean isCacheActive() {
		return _cacheActive;
	}

	public void run() {
		Thread t = Thread.currentThread();
		while (AS2Context.getInstance().SERVER_CACHING_IND) {
			try {
				Thread.sleep(AS2Context.getInstance().CACHE_CLEANER_REPEAT_TIME);
				long startTime, endTime, startCacheSize, endCacheSize;
				synchronized (cacheHashMap) {
					System.out.println("Cleaner " + t.getName()
							+ " scanning for expired objects... "
							+ new Date().toString());
					startTime = System.currentTimeMillis();
					Set<String> keySet = cacheHashMap.keySet();
					Iterator<String> keys = keySet.iterator();
					startCacheSize = cacheHashMap.size();
					while (keys.hasNext()) {
						Object key = keys.next();
						AS2Cacheable value = cacheHashMap.get(key);
						// System.out.println("ID: "+value.getIdentifier());
						// System.out.println("Object: "+value.getCachedObject().toString());
						if (value.isExpired()) {
							keys.remove();
						}
					}
					endCacheSize = cacheHashMap.size();
					endTime = System.currentTimeMillis();
				} // end synchronization

				System.out.println("Start Cache size: " + startCacheSize
						+ ", End Cache size: " + endCacheSize + ", Deleted: "
						+ (startCacheSize - endCacheSize));
				System.out.println("Time search/delete: "
						+ (endTime - startTime) + " ms");
			} catch (Exception e) {
				System.out.println("Exception in cleaner thread: "
						+ t.getName() + ". Time: " + new Date().toString());
				e.printStackTrace();
				// clean cache and make new cache object in case of error
				cleanCache();
			}
		}
		// clear all cache
		cacheHashMap = null;
		System.out.println("Cleaner thread " + t.getName() + " finished.");
	}

	public AS2Cacheable getCache(String identifier) {
		AS2Cacheable object = null;
		if (_cacheEnabled == true) {
			synchronized (cacheHashMap) {
				object = (AS2Cacheable) cacheHashMap.get(identifier);
				if (object == null) {
					// object not found in cache
					object = null;
				} else if (object.isExpired()) {
					cacheHashMap.remove(identifier);
					object = null;
				} else if (!object.isExpired()) {
					// object found in cachu
					System.out.println("Object " + object.getIdentifier()
							+ " taken from cache.");
				}
			}
		}
		return object;
	}

	public void putCache(AS2Cacheable object) {
		if (_cacheEnabled == true) {
			synchronized (cacheHashMap) {
				cacheHashMap.put(object.getIdentifier(), object);
			}
			System.out.println("Object " + object.getIdentifier()
					+ " put in cache.");
		}
	}
}
