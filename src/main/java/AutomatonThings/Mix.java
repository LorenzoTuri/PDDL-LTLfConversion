package AutomatonThings;

import rationals.Synchronization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * All credits to Arne Laponin for the working Mix class
 */
public class Mix extends rationals.transformations.Mix {
	public Mix(){
		super(new Synchronization() {
			//Taken from the default one.
			public Object synchronize(Object t1, Object t2) {
				return t1 == null?null:(t1.equals(t2)?t1:null);
			}

			//Takes an union of alphabets, so that all of the members would be synchronized.
			public Set synchronizable(Set set, Set set1) {
				Set<String> setUnion = new HashSet<String>(set);
				setUnion.addAll(set1);
				return setUnion;
			}

			public Set synchronizing(Collection collection) {
				return null;
			}

			public boolean synchronizeWith(Object o, Set set) {
				return false;
			}
		});
	}
}
