package hr.as2.inf.common.evictor;

public interface AS2EvictionInterface {
	public boolean isEvictable();
	public long info();
	public void beforeEviction();
}
