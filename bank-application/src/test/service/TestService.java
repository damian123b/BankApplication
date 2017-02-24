package test.service;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import com.luxoft.bankapp.model.NoDB;

public class TestService {

	public static boolean isEqual(Object o1, Object o2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException  {
	 
	 if (o1 instanceof Collection && o2 instanceof Collection) {
		
		 Collection c1 = (Collection) o1;
		 Collection c2 = (Collection) o2;
		 
		 if(c1.size() != c2.size())  { return false; }
		 else if(!c1.containsAll(c2)) { return false; }
		 			 
		 
	 }
	 else {
		 Field [] fields1 = o1.getClass().getDeclaredFields();
	     Field [] fields2 = o2.getClass().getDeclaredFields();

	     for (int i = 0; i < fields1.length; i++ ) {

	         fields1[i].setAccessible(true);
	         fields2[i].setAccessible(true);
	         if (!fields1[i].isAnnotationPresent(NoDB.class) )
	         {
	             if (! fields1[i].get(o1).equals(fields2[i].get(o2)))
	             {
	                 return false;
	             }
	         }
	     }
	     return true;
	 }
	return true;
		 
	}			  
}
