package com.codefactory.gwt.domiterator.client;

import com.google.gwt.dom.client.Element;

/**
	Checks if the element id matches the regex.
*/
public class IdRegexMatcher implements IElementMatcher
{
/**
 The regular expression used to select the DOM elements with the 'id' attribute that matches the regexp.
 Caution, the regular expressions in Javascript differ from the Java ones.
*/
protected String regexp;
/**
 @param regexp regular expression to use on the element 'id' attribute
 */
public IdRegexMatcher(String regexp)
	{
	 if (regexp == null)
		{
		 this.regexp = "";	// matches any 'id'
		}
	 else
		{
		 this.regexp = regexp;
		}
	}
/**
 @param element to check
 @return true if the element <i>id</i> attribute matches the regexp specified in the matcher constructor, false otherwise.
*/
public boolean matches(Element element)
	{
	 String cn = element.getId();
	 if (cn.matches(this.regexp))	// Don't think that getId returns null, so don't waste cycles checking it
	 	{
		 return true;
	 	}
	 else
		{
		 return false;
		}
	}
}
