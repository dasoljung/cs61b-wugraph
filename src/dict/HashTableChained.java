/* HashTableChained.java */

package dict;

import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  SList[] table;



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
	// .5<= n/N <=1; N = # of buckets; n = sizeEstimate
	int lowerBound = 3*sizeEstimate / 2; // lower bound of 200 = 101; 199 = 100;
	int upperBound = 2 * sizeEstimate;
	int n = aPrime(upperBound, lowerBound);
	table = new SList[n];
	for (int i = 0; i < table.length; i++) {
		table[i] = new SList();
	}
    // Your solution here.
  }
  
  
  /**
   * 
   * @return length of the hash table.
   */
  public int length() {
	return table.length;
  }
  
  
  
  private static int aPrime(int n, int lower) {
	  boolean[] prime = new boolean[n+1];
	  int i;
	  for (i=2; i <= n; i++) {
		  prime[i] = true;
	  }
	  for (int divisor = 2; divisor * divisor <= n; divisor++) {
		  if (prime[divisor]) {
			  for (i = 2 * divisor; i <= n; i = i + divisor) {
				  prime[i] = false;
			  }
		  }
	  }
	  
	  for (int index = lower; index < n; index++) {
		  if (prime[index] == true) {
			  return index;
		  }
	  }
	  for (int index = lower; index > 2; index--) {
		  if (prime[index] == true) {
			  return index;
		  }
	  }
	  return 0;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
	this(50);
    // Your solution here.
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {	// code(i) = (((ai+b) mod p) mod N)
	int p = 16908799;
	int a = 27947;
	int b = 2100001;
	return modulus(modulus(a*code + b, p), table.length);
    // Replace the following line with your solution.
  }
  
  private int modulus(int x, int n) {	// x mod n
	  while (x < 0) {
		  x = x + n;
	  }
	  return x % n;
  }
  

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
	int size = 0;
	for (SList s : table) {
		size = size + s.length();
	}
	return size;
    // Replace the following line with your solution.
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
	for (int i = 0; i < table.length; i++) {
		if (table[i].length() != 0) {
			return false;
		}
	}
	return true;
    // Replace the following line with your solution.
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
	int tableKey = compFunction(key.hashCode());
	Entry e = new Entry();
	e.key = key;
	e.value = value;
	table[tableKey].insertBack(e);
    // Replace the following line with your solution.
    try {
		return (Entry) table[tableKey].back().item();
	} catch (InvalidNodeException e1) {
		e1.printStackTrace();
		return null;
	}
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
	int tableKey = compFunction(key.hashCode());
	if (table[tableKey].length() > 0) {
		try {
			SList s = table[tableKey];
			SListNode node = (SListNode) s.front();
			if (((Entry) node.item()).key().equals(key)) {
				return (Entry) node.item();
			}
			for (int i = 0; i < s.length()-1; i++) {
				node = (SListNode) node.next();
				if (((Entry) node.item()).key().equals(key)) {
					return (Entry) node.item();
				}
			}
			return null;
		} catch (InvalidNodeException e) {	// WILL NEVER BE CALLED
			e.printStackTrace();
			return null;
		}
	} else {
		return null;
	}
    // Replace the following line with your solution.
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
	int tableKey = compFunction(key.hashCode());
	if (table[tableKey].length() > 0) {
		try {
			Entry e;
			SList s = table[tableKey];
			SListNode node = (SListNode) s.front();
			if (((Entry) node.item()).key().equals(key)) {
				e = (Entry) node.item();
				node.remove();
				return e;
			}
			for (int i = 0; i < s.length()-1; i++) {
				node = (SListNode) node.next();
				if (((Entry) node.item()).key().equals(key)) {
					e = (Entry) node.item();
					node.remove();
					return e;
				}
			}
		} catch (InvalidNodeException e) {
			e.printStackTrace();
			return null;
		}
	}
	return null;
    // Replace the following line with your solution.
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
	for (int i = 0; i < table.length; i++) {
		table[i] = new SList();
	}
    // Your solution here.
  }
  
  /**
   * TESTING: checking number of collisions.
   */
  public int countCollisions() {
	int num = 0;
	for (int i = 0; i < table.length; i++) {
		if (table[i].length() > 1) {
			System.out.println(i + ": " + table[i].length());
			num = num + table[i].length() - 1;
		}
	}
	return num;
  }
  

  /**
   * TESTING: checking number of empty.
   */
  public int countEmpty() {
	int num = 0;
	for (int i = 0; i < table.length; i++) {
		if (table[i].length() == 0) {
			System.out.println(i + ": " + table[i].length());
			num++;
		}
	}
	return num;
  }
  
  
  
  
  
  
  
  public static void main(String[] args) {	// TESTING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	System.out.println(Integer.MAX_VALUE);
	Integer k = new Integer(1010);
	String s = new String("1010");
	HashTableChained h = new HashTableChained();
	h.insert(k, s);
	Entry e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	
	k = new Integer(1030);
	s = new String("1030");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
		
	k = new Integer(1060);
	s = new String("1060");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1070);
	s = new String("1070");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1080);
	s = new String("1080");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1090);
	s = new String("1090");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1100);
	s = new String("1100");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1110);
	s = new String("1110");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1120);
	s = new String("1120");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1130);
	s = new String("1130");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());

	k = new Integer(1140);
	s = new String("1140");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1150);
	s = new String("1150");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1160);
	s = new String("1160");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1170);
	s = new String("1170");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1180);
	s = new String("1180");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1190);
	s = new String("1190");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	k = new Integer(1200);
	s = new String("1200");
	h.insert(k, s);
	e = h.find(k);
	System.out.println(e.key() + ": " + e.value());
	
	
	
	
	
	
	
	
	
	/**HashTableChained x = new HashTableChained(200);
	System.out.println(x.insert(34, 68).key());
	
	System.out.println(x.insert(12,24).key());
	
	System.out.println(x.find(34).key());
	
	System.out.println(x.remove(34).key());
	
	System.out.println(x.find(34));
	
	System.out.println(x.insert(12,24).key());
	
	System.out.println(x.find(12).key());
	System.out.println(x.remove(12).key());
	
	System.out.println(x.find(12).key());
	
	System.out.println(x.remove(12).key());
	
	System.out.println(x.find(12));
	**/
  }

  
  
}
