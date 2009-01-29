package com.codefactory.gwt.domiterator.client;

import com.google.gwt.dom.client.Element;

/**
	Checks if the element has the class name specified in the constructor. The class name is case insensitive.
*/
public class SingleClassnameCaseInsensitiveMatcher implements IElementMatcher
{
/**
 The name we are looking for in the 'class' attribute of the DOM elements.
*/
protected String classname;
/*
	@param The class name to be matched for the DomIterator to return the element
*/
public SingleClassnameCaseInsensitiveMatcher(String classname)
	{
	 if (classname == null)
		{
		 this.classname = "";	// matches any class name
		}
	 else
		{
		 this.classname = " " + classname.toUpperCase()+ " ";
		}
	}
/*
	@param The element to check (the current element in the DomIterator).
	@return true if the element has the class name in its class attribute, false otherwise
*/
public boolean matches(Element element)
	{
	 String cn = " " + element.getClassName().toUpperCase() + " ";
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
