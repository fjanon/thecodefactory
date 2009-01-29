package com.codefactory.gwt.domiteratordemo.client;

import com.codefactory.gwt.domiterator.client.ClassnameRegexMatcher;
import com.codefactory.gwt.domiterator.client.DomIterator;
import com.codefactory.gwt.domiterator.client.IElementMatcher;
import com.codefactory.gwt.domiterator.client.IdRegexMatcher;
import com.codefactory.gwt.domiterator.client.MultiClassnameCaseInsensitiveMatcher;
import com.codefactory.gwt.domiterator.client.MultiClassnameCaseSensitiveMatcher;
import com.codefactory.gwt.domiterator.client.SingleClassnameCaseSensitiveMatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DomIteratorDemo implements EntryPoint {

  /**
   * This is the entry point method.
   */
public void onModuleLoad()
  	{
    Image img = new Image("http://code.google.com/webtoolkit/logo-185x175.png");
    Button button = new Button("Click me");
    
    // We can add style names
    button.addStyleName("pc-template-btn");
    // or we can set an id on a specific element for styling
    img.getElement().setId("pc-template-img");
    
    VerticalPanel vPanel = new VerticalPanel();
    vPanel.setWidth("100%");
    vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    vPanel.add(img);
    vPanel.add(button);

    // Add image and button to the RootPanel
    RootPanel.get().add(vPanel);

    // Create the dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Welcome to GWT!");
    dialogBox.setAnimationEnabled(true);
    Button closeButton = new Button("close");
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.setWidth("100%");
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    dialogVPanel.add(closeButton);

    closeButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        dialogBox.hide();
      }
    });

    // Set the contents of the Widget
    dialogBox.setWidget(dialogVPanel);
    
    button.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        dialogBox.center();
        dialogBox.show();
      }
    });

    String ids = null;
    for (Element element : new DomIterator(new SingleClassnameCaseSensitiveMatcher("theclassname"), "div6"))
    	{
    	 GWT.log("for in: " + element.getNodeName() + " " + element.getId(), null);
    	}

    /*
  	<div id='div7' class='classB classA'>
		<div id='div7-div1'>
			<p id='div7-div1-p1' class='classB classA classC'>a p element with classname 'theclassname'</p>
		</div>
	</div>
*/
    // Test search with multiple class names
  	ids = getElementIdsByClassnames("classA classB", "div7");
    if ("div7div7-div1-p1".equals(ids))
    	{
    	 GWT.log("SUCCESS: div7div7-div1-p1", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div7div7-div1-p1 ids: " + ids, null);
    	}
    // Test search with multiple class names case sensitive "ClassB", should return no elements
  	ids = getElementIdsByClassnames("classA ClassB", "div7");
    if ("".equals(ids))
    	{
    	 GWT.log("SUCCESS case sensitive: div7div7-div1-p1", null);
    	}
    else
    	{
    	 GWT.log("FAILURE case sensitive: div7div7-div1-p1 ids: " + ids, null);
    	}

    // Same test case insensitive, should find the same elements
   	ids = getElementIdsByClassnamesCaseInsensitive("classA classB", "div7");
    if ("div7div7-div1-p1".equals(ids))
    	{
    	 GWT.log("SUCCESS case insensitive: div7div7-div1-p1", null);
    	}
    else
    	{
    	 GWT.log("FAILURE case insensitive: div7div7-div1-p1 ids: " + ids, null);
    	}

    /*
	<div id='div8'>
			<p id='div8-p1' class='classB classA classC'>a p element with classname 'theclassname'</p>
			<p id='div8-p2' class='classA classB'>a p element with classname 'theclassname'</p>
		</div>
	</div>
     */
    // Test search with multiple class names
  	ids = getElementIdsByClassnames("classA classB", "div8");
    if ("div8-p1div8-p2".equals(ids))
    	{
    	 GWT.log("SUCCESS: iv8-p1div8-p2", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: iv8-p1div8-p2 ids: " + ids, null);
    	}

    // Start with the 'body'
    ids = getFirstElementIdByClassname("theclassname", Document.get().getBody());
    if ("body1".equals(ids))
    	{
    	 GWT.log("SUCCESS: body1", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: body1 ids: " + ids, null);
    	}
 
    ids = getElementIdsByClassname("theclassname", "div1");
    if ("div1-div1-p1".equals(ids))
    	{
    	 GWT.log("SUCCESS: div1-div1-p1", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div1-div1-p1 ids: " + ids, null);
    	}
/*
    <div id='div2' class='anotherclassname'>
	</div>
*/
    ids = getElementIdsByClassname("theclassname", "div2");
    if ("".equals(ids))
    	{
    	 GWT.log("SUCCESS: div2", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div2 ids: " + ids, null);
    	}

/*
    <div id='div2' class='anotherclassname'>
		a text node
	</div>
*/
    ids = getElementIdsByClassname("theclassname", "div2a");
    if ("".equals(ids))
    	{
    	 GWT.log("SUCCESS: div2a", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div2a ids: " + ids, null);
    	}

    /*
 	<div id='div3' class='theclassname'>
	</div>
    */
    ids = getElementIdsByClassname("theclassname", "div3");
    if ("div3".equals(ids))
    	{
    	 GWT.log("SUCCESS: div3", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div3 ids: " + ids, null);
    	}

    /*
 	<div id='div3' class='theclassname'>
 	a text node
	</div>
    */
    ids = getElementIdsByClassname("theclassname", "div3a");
    if ("div3a".equals(ids))
    	{
    	 GWT.log("SUCCESS: div3a", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div3a ids: " + ids, null);
    	}

    /*
	<div id='div4' class='theclassname'>
		<span>some text</span>
		<span id='div4-span1' class='theclassname'>some other text</span>
	</div>
    */
    ids = getElementIdsByClassname("theclassname", "div4");
    if ("div4div4-span1".equals(ids))
    	{
    	 GWT.log("SUCCESS: div4", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div4 ids: " + ids, null);
    	}
/*
   	<div id='div4a' class='theclassname'>
		<span id='div4a-span1' class='theclassname'>some other text</span>
		<span>some text</span>
	</div>
*/
    ids = getElementIdsByClassname("theclassname", "div4a");
    if ("div4adiv4a-span1".equals(ids))
    	{
    	 GWT.log("SUCCESS: div4a", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div4a ids: " + ids, null);
    	}

    /*
	<div id='div5' class='theclassname'>
		<div id='div5-div1' class='theclassname'>
			<p id='div5-div1-p1' class='someotherclassname theclassname'>a p element with classname 'theclassname'</p>
		</div>
	</div>
    */
    ids = getElementIdsByClassname("theclassname", "div5");
    if ("div5div5-div1div5-div1-p1".equals(ids))
    	{
    	 GWT.log("SUCCESS: div5", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div5 ids: " + ids, null);
    	}

    /*
	<div id='div6' class='theclassname'>
		<div id='div6-div1'>
			<!-- Comment: DOM structure used to test the getElementByClassname method -->
			<span id='div6-div1-span1'>DOM structure used to test the getElementByClassname method</span>
			<p id='div6-div1-p1'>a p element</p>
			<p id='div6-div1-p2' class='someotherclassname theclassname'>a p element with classname 'theclassname'</p>
		</div>
	</div>
     */
    ids = getElementIdsByClassname("theclassname", "div6");
    if ("div6div6-div1-p2".equals(ids))
    	{
    	 GWT.log("SUCCESS: div6", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div6 ids: " + ids, null);
    	}

    /*
	<div id='div12' class="class12">
		<div id='div13' class="class13">
			<div id='div121' class="class121">
				<div id='div122' class="class122">
				</div>
			</div>
			<div id='div123' class="class123">
			</div>
		</div>
	</div>
    */
    // Test the 'class' regex matcher: find all the divs with a class like 'divNNN'
    ids = getElementIdsByClassnameRegex("class[0-9]{3}", "div12");
    if ("div121div122div123".equals(ids))
    	{
    	 GWT.log("SUCCESS: div12", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div12 ids: " + ids, null);
    	}

    // Test the 'id' regex matcher: find all the divs with an 'id' like 'divNNN'
    ids = getElementIdsByIdRegex("div[0-9]{3}", "div12");
    if ("div121div122div123".equals(ids))
    	{
    	 GWT.log("SUCCESS: id regexp div12", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div12 ids: " + ids, null);
    	}

    // test that calling hasNext several times doesn't change next()
    DomIterator di = new DomIterator(new SingleClassnameCaseSensitiveMatcher("theclassname"), "div6");
    Element element = null;
    ids = "";
    di.hasNext();
    di.hasNext();
    while (di.hasNext())
    	{
    	 di.hasNext();
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
    if ("div6div6-div1-p2".equals(ids))
    	{
    	 GWT.log("SUCCESS: div6", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div6 ids: " + ids, null);
    	}

    // Test with an empty classname, should return all elements under the start element and including it
	/*
		<div id='div9'>
			<div id='div10'>
				<div id='div11'>
				</div>
			</div>
		</div>
	*/
    ids = getElementIdsByClassname("", "div9");
    if ("div9div10div11".equals(ids))
    	{
    	 GWT.log("SUCCESS: div9", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div9 ids: " + ids, null);
    	}

    // Test with an empty class name
    di = new DomIterator(new MultiClassnameCaseSensitiveMatcher(""), "div9");
    element = null;
    ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
    if ("div9div10div11".equals(ids))
    	{
    	 GWT.log("SUCCESS: div9", null);
    	}
    else
    	{
    	 GWT.log("FAILURE: div9 ids: " + ids, null);
    	}

    // Test with a pretty long DOM structure borrowed from Wikipedia
//    ids = getElementIdsByClassname("visualClear", "globalWrapper");
    di = new DomIterator(new SingleClassnameCaseSensitiveMatcher("visualClear"), "globalWrapper");
    element = null;
    ids = "";
    long startTime = System.currentTimeMillis();
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
    long duration = System.currentTimeMillis() - startTime;
//    GWT.log("Long search: " + (endTime-startTime), null);
//    Window.alert("Long search: " + (endTime-startTime));
    if ("div20div21".equals(ids))
    	{
//    	 Window.alert("SUCCESS: div20 Long search: " + duration + " num elements traversed: " + cs.getNumElements());
    	 GWT.log("SUCCESS: div20", null);
    	}
    else
    	{
//    	 Window.alert("FAILURE: div20 Long search: " + duration + " num elements traversed: " + cs.getNumElements());
    	 GWT.log("FAILURE: div20 ids: " + ids, null);
    	}
  
    IElementMatcher matcher = new SingleClassnameCaseSensitiveMatcher("visualClear");
    // New DomIterator
    di = new DomIterator(matcher, "globalWrapper");
    element = null;
    ids = "";
    startTime = System.currentTimeMillis();
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
    duration = System.currentTimeMillis() - startTime;
    if ("div20div21".equals(ids))
    	{
    	 Window.alert("NEW SUCCESS: div20 Long search: " + duration + " num elements traversed: " + di.getNumElements());
    	 GWT.log("SUCCESS: div20", null);
    	}
    else
    	{
    	 Window.alert("NEW FAILURE: div20 Long search: " + duration + " num elements traversed: " + di.getNumElements());
    	 GWT.log("FAILURE: div20 ids: " + ids, null);
    	}

  	} // onModuleLoad()

protected String getElementIdsByClassname(String classname, String startElementId)
	{
     return getElementIdsByClassname(classname, Document.get().getElementById(startElementId));
	}

protected String getElementIdsByClassname(String classname, Element startElement)
	{
	IElementMatcher matcher = new SingleClassnameCaseSensitiveMatcher(classname);
    DomIterator di = new DomIterator(matcher, startElement);
    Element element = null;
    String ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
     return ids;
	}

protected String getElementIdsByClassnames(String classname, String startElementId)
	{
     return getElementIdsByClassnames(classname, Document.get().getElementById(startElementId));
	}

protected String getElementIdsByClassnames(String classname, Element startElement)
	{
	IElementMatcher matcher = new MultiClassnameCaseSensitiveMatcher(classname);
    DomIterator di = new DomIterator(matcher, startElement);
    Element element = null;
    String ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
     return ids;
	}

protected String getElementIdsByClassnamesCaseInsensitive(String classname, String startElementId)
	{
     return getElementIdsByClassnamesCaseInsensitive(classname, Document.get().getElementById(startElementId));
	}

protected String getElementIdsByClassnamesCaseInsensitive(String classname, Element startElement)
	{
	IElementMatcher matcher = new MultiClassnameCaseInsensitiveMatcher(classname);
    DomIterator di = new DomIterator(matcher, startElement);
    Element element = null;
    String ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
     return ids;
	}

protected String getFirstElementIdByClassname(String classname, Element startElement)
	{
	SingleClassnameCaseSensitiveMatcher matcher = new SingleClassnameCaseSensitiveMatcher(classname);
    DomIterator di = new DomIterator(matcher, startElement);
    Element element = null;
    String ids = "";
    if (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
     return ids;
	
	}

protected String getElementIdsByClassnameRegex(String regexp, String startElementId)
	{
	IElementMatcher matcher = new ClassnameRegexMatcher(regexp);
    DomIterator di = new DomIterator(matcher, startElementId);
    Element element = null;
    String ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
     return ids;
	}

protected String getElementIdsByIdRegex(String regexp, String startElementId)
	{
	IElementMatcher matcher = new IdRegexMatcher(regexp);
    DomIterator di = new DomIterator(matcher, startElementId);
    Element element = null;
    String ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
     return ids;
	}

protected String displayNodeName(Element element)
	{
	 String name = getNodeName(element);
	 String id = getNodeId(element);
	 GWT.log("node name: " + name + " id: " + id, null);
	 return name + " " + id;
	}

protected String getNodeName(Element element)
	{
	 return (element == null) ? "NULL element" : element.getNodeName();
	}

protected String getNodeId(Element element)
	{
	 return (element == null) ? "" : element.getId();
	}

}
