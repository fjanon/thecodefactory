package com.codefactory.gwt.domiterator.client;

import com.google.gwt.dom.client.Element;

/**
	Checks if the element class name matches the regex.
*/
public class ClassnameRegexMatcher implements IElementMatcher
{
/**
 The regular expression used to select the DOM elements with the 'class' attribute that matches the regexp.
 Caution, the regular expressions in Javascript differ from the Java ones.
*/
protected String regexp;
/**
 @param regexp regular expression to use on the class name attribute
 */
public ClassnameRegexMatcher(String regexp)
	{
	 if (regexp == null)
		{
		 this.regexp = "";	// matches any class name
		}
	 else
		{
		 this.regexp = regexp;
		}
	}
/**
 @param element to check
 @return true if the element <i>class</i> attribute matches the regexp specified in the matcher constructor, false otherwise.
*/
public boolean matches(Element element)
	{
	 String cn = element.getClassName();
	 if (cn.matches(this.regexp))	// Don't think that getClassname returns null, so don't waste cycles checking it
	 	{
		 return true;
	 	}
	 else
		{
		 return false;
		}
	}
}
