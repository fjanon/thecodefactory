package com.codefactory.gwt.domiterator.client;

import com.google.gwt.dom.client.Element;

/**
	Checks if the element has the class name specified in the constructor. The class name is case sensitive.
*/
public class SingleClassnameCaseSensitiveMatcher implements IElementMatcher
{
/**
 The name we are looking for in the 'class' attribute of the DOM elements.
 Used for case sensitive searches and when only one class name is specified

 The class name can also be blank "", the iterator will return all the elements descendants of the start element, including the start element.</p>

*/
protected String classname;
/*
	@param The class name to be matched for the DomIterator to return the element
*/
public SingleClassnameCaseSensitiveMatcher(String classname)
	{
	 if (classname == null)
		{
		 this.classname = "";	// matches any class name
		}
	 else
		{
		 this.classname = " " + classname + " ";
		}
	}
/*
	@param The element to check (the current element in the DomIterator).
	@return true if the element has the class name in its class attribute, false otherwise
*/
public boolean matches(Element element)
	{
//	 StringBuilder sb = new StringBuilder(" ").append(element.getClassName()).append(" "); // StringBuuilder and StringBuffer is 2 to 4 times slowler
//	 if (sb.toString().contains(this.classname))
	 String cn = " " + element.getClassName() + " ";
	 if (cn.contains(this.classname))
	 	{
		 return true;	// returns true even if classname is ""
	 	}
	 else
		{
		 return false;
		}
	}
}
