package hr.as2.inf.common.security.user;

import hr.as2.inf.common.core.AS2Context;

import java.util.Hashtable;

/**
 * This class is an User Factory. It creates the AS2User objects and keeps them
 * to be used by the application as needed.
 */
public final class AS2UserFactory {
    /**
     * Singleton reference.
     */
    private static AS2UserFactory _instance = null;

    /**
     * Collection of already created AS2User Objects.
     */
    private Hashtable<String, AS2User> _userObjects;

    /**
     * Current User.
     */
    private AS2User _currentUser = null;
    /**
     * System User (needed to read e.g. OS/390 property files).
     */
    private AS2User _systemUser = null;

    /**
     * Prevents the instantiation by the other objects.
     */
    private AS2UserFactory() {
        _userObjects = new Hashtable<String, AS2User>();
        AS2Context.setSingletonReference(this);
    }
    public static AS2UserFactory getInstance() {
        if (_instance == null)
            _instance = new AS2UserFactory();
        return _instance;
    }
    /**
     * SERVER USERS BEGIN DONE BY SESSION----------------------
     
    public synchronized void destroyCurrentServerUser() {
        _currentServerUsers.remove(Thread.currentThread().getName());
    }
    public J2EEUser getCurrentServerUser() {
        J2EEUser value = (J2EEUser) _currentServerUsers.get(Thread.currentThread().getName());
        if(value==null)
            value = new J2EEUser();
        return value;
    }
    public void setCurrentServerUser(J2EEUser value) {
        _currentServerUsers.put(Thread.currentThread().getName(), value);
    }    
     * SERVER USERS END --------------------------
     */
    public synchronized void destroy(String userName) {
        _userObjects.remove(userName);
    }
    /**
     * Delete all the user objects.
     */
    public synchronized void destroyAll() {
        _userObjects = null;
        _userObjects = new Hashtable<String, AS2User>();
    }

    public AS2User getCurrentUser() {
        if (_currentUser == null) {
            setCurrentUser(new AS2User());
        }
        return _currentUser;
    }

    public String getCurrentUserId() {
        AS2User user = getCurrentUser();
        return user.getUserId();
    }
    public String getCurrentUserDepartment() {
        AS2User user = getCurrentUser();
        return user.get("id_sluzbe");
    }

    public AS2User getSystemUser() {
        if (_systemUser == null)
            return getCurrentUser();
        return _systemUser;
    }

    /**
     * Get the user object from the collection and return the reference to a
     * caller. If user object does not exist create one.
     */
    public AS2User getUser(String userName) {

        AS2User user = (AS2User) _userObjects.get(userName);

        if (user == null)
            user = new AS2User(userName, null);
        return user;
    }

    public void setCurrentUser(AS2User value) {
        _currentUser = value;
        _userObjects.put(value.getUserId(), value);
    }

    public void setSystemUser(AS2User value) {
        _systemUser = value;
    }

    /**
     * Add the user object to the collection.
     */
    public void setUser(AS2User user) {

        if (user != null)
            _userObjects.put(user.getUserId(), user);
    }
}