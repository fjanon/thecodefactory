package com.codefactory.gwt.domiterator.client;

import java.util.Iterator;
//import com.google.gwt.core.client.GWT;	// Used for debug
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
/**
 @author Fred Janon
 @version 1.0.0 2009-01-22
 <p>Copyright 2009, Fred Janon - fjanon@gmail.com - The Code Factory</p>
 <p>Class that iterates through the DOM, returning elements that match a criteria defined in a matcher.
 <p>The DomIterator works in conjunction with an {@see com.codefactory.gwt.domiterator.client.IElementMatcher}.
 The matcher checks if the element passed in matches the criteria that it defines.</p>

 A few matchers are predefined, but new customized ones are easily defined by implementing the IElementMatcher interface.
 DomIterator is an Iterator, not a List builder. When <i>next()</i> is called, the code examines the DOM to find an element that fulfills the matcher criteria,
 as opposed to {@see com.google.gwt.dom.client.Document#getElementsByTagName(java.lang.String tagName)} which returns a NodeList<Element>.
 The DomIterator uses very little memory since it doesn't build a list. It keeps track of the current element and searches incrementally.</p>

 The predefined matchers provided are matching elements with:
 <ul>
<li>a <i>single</i> class name with case <i>sensitive</i> comparison: {@see com.codefactory.gwt.domiterator.client.SingleClassnameCaseSensitiveMatcher}.
 Use if you are looking for elements with a specific class name and want the comparison to be case sensitive.
 Element can have multiple class names, the matcher can find any of the individual names in the <code>class</code> attribute.
 For example if an element has an attribute <code>class='header first chapter'</code>,
 the single class name 'first' matcher will match 'first' and the DomIterator will include that element.</li>

<li>a <i>single</i> class name with case <i>insensitive</i> comparison: {@see com.codefactory.gwt.domiterator.client.SingleClassnameCaseInsensitiveMatcher}
 Same as above, except the comparison is case insensitive. For example if an element has an attribute <code>class='Header FIRST chaPTer'</code>, the single class name matcher will match any of the names 'header', 'first' or 'chapter'.
</li>

<li><i>multiple</i> class names with case <i>sensitive</i> comparison: {@see com.codefactory.gwt.domiterator.client.MultiClassnameCaseSensitiveMatcher}
 The matcher("second fourth") would match an element with the class names "first second third fourth" but not an element with "first SECOND third fourth"
</li>

<li><i>multiple</i> class names with case <i>insensitive</i> comparison: {@see com.codefactory.gwt.domiterator.client.MultiClassnameCaseInsensitiveMatcher}
 The matcher("second fourth") would match an element with the class names "first second third fourth" or "first SECOND third fourth"
</li>
<li><i>regular expression</i> applied on the element 'class' attribute: {@see com.codefactory.gwt.domiterator.client.ClassnameRegexMatcher}
 The matcher("second fourth") would match an element with the class names "first second third fourth" or "first SECOND third fourth"
</li>
<li><i>regular expression</i> applied on the element 'id' attribute: {@see com.codefactory.gwt.domiterator.client.IdRegexMatcher}
</li>
</ul>

 <p>The search starts with the DOM 'body' element by default when no start element is specified. The search is breadth first.</p>

 <p>The DomIterator implements the {@see java.util.Iterator} and the {@see java.lang.Iterable} interfaces.
 It can be used in two ways: with an {@link java.util.Iterator} or as an {@link java.lang.Iterable} with a Java 5 'for' loop:</p>
<pre>
// Different predefined matchers
IElementMatcher matcher = new SingleClassnameCaseSensitiveMatcher("theClassname");
or
IElementMatcher matcher = new SingleClassnameCaseInsensitiveMatcher("theClassname");
or
IElementMatcher matcher = new ClassnameRegexMatcher(regexp);
or
IElementMatcher matcher = new MultiClassnameCaseCensitiveMatcher("First Second");
or
IElementMatcher matcher = new MultiClassnameCaseInsensitiveMatcher("fIrsT seCond");
or
IElementMatcher matcher = new MultiClassnameCaseInsensitiveMatcher(new String[] {"first", "second"});

// Different DomIterator constructors:
DomIterator di = new DomIterator(matcher);	// start from the 'body' element
or
DomIterator di = new DomIterator(matcher, "startElementId"); // start from the element with this id
or
DomIterator di = new DomIterator(matcher, anElement);	// start from that element

// Use the DomIterator as an Iterator
Element element = null;
String ids = "";
while (di.hasNext())
   	{
   	 element = di.next();
   	 // Do something with the element with the classes "class1 class2"
   	 ids += element.getId();	// concatenate the ids without space
   	}

// Use the DomIterator as an Iterable (Java 5 and up)
for (Element element : new DomIterator(new SingleClassnameCaseSensitiveMatcher("theclassname"), "div6"))
   	{
   	 GWT.log("for in: " + element.getNodeName() + " " + element.getId(), null);
   	}
</pre>
*/
/**
 Iterates through the DOM, returning the elements that match the matcher specified, starting from the 'body' element or a specified element.
*/
public class DomIterator implements Iterable<Element>, Iterator<Element>
{
/**
 module version as a String. Version of this module: "1.0.0"
*/
static final public String VERSION = "1.0.0";
/**
 module version <i>int</>. More convenient for comparisons than the String one. Version of this module: 100
*/
static final public int VERSIONINT = 100;
/**
 The matcher used to check if the current element is to be returned by the iterator
*/
protected IElementMatcher matcher;
/**
 The DOM element that the search starts with. It is included in the search. If the start element has the classname(s) specified,
 if will be returned by the iterator
*/
protected Element startElement;
/**
 Used internally has the current/last element used for the search. The search resumes from this element when next() is called
*/
protected Element currentElement;
/**
 Flag used internally to manage the state between hasNext() and next()
*/
protected boolean searchDone = false;
/**
 Flag used internally to be able to return the startElement if it has the classname(s) looked for
*/
protected boolean firstElement = true;
//Element lastElementVisited;	// for testing only
/**
 used for benchmarking only
*/
protected int numElements = 1; // We search through at least one element, the start element
/**
	The search starts from (and includes) the 'body' element.
	@param matcher to use for the element matching method
 */
public DomIterator(IElementMatcher matcher)
	{
	 this(matcher, Document.get().getBody());
	}

/**
 	The search starts from (and includes) the element with the 'id' attribute <i>startElementId</i>.
	@param matcher to use for the element matching method
	@param startElementId String containing the id of the DOM element where to start the search from.
 */
public DomIterator(IElementMatcher matcher, String startElementId)
	{
	 this(matcher, Document.get().getElementById(startElementId));
	}
/**
 	The search starts from (and includes) the element passed in.
	@param matcher to use for the element matching method
	@param startElement DOM element where to start the search from.
 */
public DomIterator(IElementMatcher matcher, Element startElement)
	{
	 this.matcher = matcher;
	 this.startElement = startElement;
	 this.currentElement = this.startElement;
	}
/**
 Implementation of the {@link java.lang.Iterable} interface.
 @return an Iterator
*/
public Iterator<Element> iterator()
	{
	 return this;
	}

/**
 * Implementation of the {@link java.util.Iterator} interface
 * @return Returns true if there is another element with the specified class name(s).
 * The first time next() is called, the start element is returned if it has the specified class names.
 * hasNext() can be called several times without changing the currentElement, until next() is called.
*/
public boolean hasNext()
	{
	 if (!this.searchDone)
		{
		 this.searchDone = true;
		 this.currentElement = findNextElementMatching(this.currentElement);
		}
	 return (currentElement != null);
	}

/**
 * Implementation of the Iterator interface
 * @return Returns the next element with the specified class names(s) or null if we reached the end of the DOM tree under the startElement.
*/
public Element next()
	{
	 if (!this.searchDone)
		{
		 this.searchDone = true;
		 this.currentElement = findNextElementMatching(this.currentElement);
		 return this.currentElement;
		}
	 else
		{
		 this.searchDone = false;
		 return this.currentElement;
		}
	}
/**
 Internal method. Main method that starts the search.
 @param element The current element
*/
protected Element findNextElementMatching(Element element)
	{
	 if (this.firstElement) // Special case for the startElement
	 	{
//		 String nodename = displayNodeName(element);	// Debug
		 this.firstElement = false;
		 if (this.matcher.matches(element))
		 	{
			 return element;
		 	}
	 	}

	 while ((element = getNextElement(element)) != null)
		{
		 this.numElements++;	// TODO: Only for benchmarking, remove in production
		 if (this.matcher.matches(element))
		 	{
			 return element;
		 	}
	 	}
	 return null;
	}
/**
 Internal method.
 Finds the next element: a child or a sibling or the next parent up until the start element is reached (search over).
*/
protected Element getNextElement(Element element)
	{
//	 String nodename = displayNodeName(element); // debug
//	 this.lastElementVisited = element;	// for testing only
	 Element nextElement = null;
	 if (element.hasChildNodes() == true)	// Nodes, not element. Can return true for a text node which is not an element
		 {
		  nextElement = element.getFirstChildElement();
//		  nodename = displayNodeName(nextElement); // debug
		 }
	 if ((nextElement == null) && (element == this.startElement))
		{
		 return null;	// Case of an element without children
		}
	 if (nextElement == null)
		 {
		  nextElement = element.getNextSiblingElement();
//		  nodename = displayNodeName(nextElement); // debug
		 }
	 if (nextElement == null)
		 {
		  nextElement = findNextParentSibling(element);
//		  nodename = displayNodeName(nextElement); // debug
		 }
	 return nextElement;
	}
/**
 Internal method.
 Finds the next node up when the current element doesn't have any child
*/
protected Element findNextParentSibling(Element element)
	{
	 Element nextParent = element;
//	 String name = displayNodeName(nextParent);	// debug
	 Element nextParentSibling = null;
	 // Stop going back up the tree when we reach the start element
	 while (((nextParent = nextParent.getParentElement()) != this.startElement) && (nextParent != null))
		 {
//		  if (nextParent == null) GWT.log("nextParent NULL", null);	// debug
//		  name = displayNodeName(nextParent);	// debug
		  if ((nextParentSibling = nextParent.getNextSiblingElement()) != null)
			  {
//			   name = displayNodeName(nextParentSibling);	// debug
			   return nextParentSibling;
			  }
		 }
	 return null;
	}
/**
 Internal method.
 Used for debugging
 */
protected String displayNodeName(Element element)
	{
	 String name = getNodeName(element);
	 String id = getNodeId(element);
//	 GWT.log("node name: " + name + " id: " + id, null);
	 return name + " " + id;
	}

/**
 Internal method.
 Used for debugging
 */
protected String getNodeName(Element element)
	{
	 return (element == null) ? "NULL element" : element.getNodeName();
	}

/**
 Internal method.
 Used for debugging
 */
protected String getNodeId(Element element)
	{
	 return (element == null) ? "" : element.getId();
	}
/**
 Implementation of the {@link java.util.Iterator} interface
	@throws UnsupportedOperationException
	Remove is not supported by this iterator
*/
public void remove()
	{
	 throw (new UnsupportedOperationException());
	}
/**
	@return The string: "DomIterator start element: " + this.startElement.getNodeName()
*/
@Override
public String toString()
	{
	 return "DomIterator start element: " + this.startElement.getNodeName();
	}
/*
// For testing only
public Element getLastElementVisited()
	{
	 return lastElementVisited;
	}
*/
/**
	For benchmarking only, remove in production
	@return the number of DOM elements looked at so far
*/
public int getNumElements()
	{
	 return numElements;
	}
}
