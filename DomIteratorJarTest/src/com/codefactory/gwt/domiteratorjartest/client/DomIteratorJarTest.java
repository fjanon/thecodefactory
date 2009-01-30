package com.codefactory.gwt.domiteratorjartest.client;

import com.codefactory.gwt.domiterator.client.DomIterator;
import com.codefactory.gwt.domiterator.client.SingleClassnameCaseSensitiveMatcher;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
public class DomIteratorJarTest implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
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

    // Example that shows how to use the DomIterator.jar and its content
    /*
	<div id='div5' class='theclassname'>
		<div id='div5-div1' class='theclassname'>
			<p id='div5-div1-p1' class='someotherclassname theclassname'>a p element with classname 'theclassname'</p>
		</div>
	</div>
    */
    // Look for elements with the class name 'classname' starting from the element with id 'div5'
    DomIterator di = new DomIterator(new SingleClassnameCaseSensitiveMatcher("theclassname"), "div5");
    Element element = null;
    String ids = "";
    while (di.hasNext())
    	{
    	 element = di.next();
    	 ids += element.getId();	// concatenate the ids without space
    	}
    if ("div5div5-div1div5-div1-p1".equals(ids))
    	{
    	 Window.alert("SUCCESS: div5 ids: " + ids);
    	 GWT.log("SUCCESS: div5 ids: " + ids, null);
    	}
    else
    	{
    	 Window.alert("FAILURE: div5ids: " + ids);
    	 GWT.log("FAILURE: div5 ids: " + ids, null);
    	}
  }
}
