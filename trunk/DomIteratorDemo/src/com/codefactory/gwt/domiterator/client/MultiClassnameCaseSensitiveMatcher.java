package com.codefactory.gwt.domiterator.client;

import com.google.gwt.dom.client.Element;

/**
 Checks if an element has at least all the class names specified in the constructor. The class names are case insensitive.
*/
public class MultiClassnameCaseSensitiveMatcher implements IElementMatcher
{
/**
 Array used internally when looking for multiple class names with case insensitive searches
*/
protected String[] classnames;
/**
 Use this constructor when the class names to match are in a single String separated by spaces.
 If the class name is blank (""), the iterator returns all the elements descendants of the start element, including the start element.
 Note that the order of the class names is not significant.
*/
public MultiClassnameCaseSensitiveMatcher(String classnames)
	{
	 this.classnames = classnames.split(" ");
	 for (int i = 0 ; i < this.classnames.length ; i++)
		{
		 this.classnames[i] = " " + this.classnames[i] + " ";
		}
	}
/**
 Use this constructor when the class names to match are specified in a String array.
 @param classnames All the class names to be found in the element class attribute
*/
public MultiClassnameCaseSensitiveMatcher(String[] classnames)
	{
	 this.classnames = new String[classnames.length];
	 for (int i = 0 ; i < classnames.length ; i++)
		{
		 this.classnames[i] = " " + classnames[i] + " ";
		}
	}
 /**
 @param element element to check
 @return true if the element class attribute contains all the class names specified in the matcher constructor, false otherwise.
 The comparison is case sensitive.
*/
public boolean matches(Element element)
	{
	 String classname = " " + element.getClassName() + " ";
	 for (String name : this.classnames)	// Java 5 'for' loop
		{
		 if (!classname.contains(name))
		 	{
			 return false;
		 	}
		}
	 return true;	// All names in class attribute
	}
}
