package com.codefactory.gwt.domiterator.client;

import com.google.gwt.dom.client.Element;
/**
 Interface used by the {@see com.codefactory.gwt.domiterator.client.DomIterator}.
 The {@see com.codefactory.gwt.domiterator.client.DomIterator} calls the {@link IElementMatcher#matches(Element)}
 method to check if the element matches the search criteria that this IElementMatcher implements.
 If <code>matches(element)</code> returns <code>true</code>, the element will be returned by the DomIterator.
 Can be used to check any attribute or property of an element.
 For example, a matcher could be defined to find all elements with a certain id or class name or background color.
*/
public interface IElementMatcher
{
/**
	@param element current element in the search. If matches() returns true, this element will be returned by the DomIterator on a call to <code>next()</code>.
	@return true if the element matches the search criteria, false otherwise.
*/
boolean matches(Element element);
}
