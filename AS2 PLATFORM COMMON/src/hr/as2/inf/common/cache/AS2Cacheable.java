package hr.as2.inf.common.cache;

public interface AS2Cacheable {

	public Object getCachedObject();

	public String getIdentifier();

	public boolean isExpired();
}
