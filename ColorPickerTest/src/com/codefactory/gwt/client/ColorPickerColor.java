package com.codefactory.gwt.client;

public class ColorPickerColor {
protected String RGBvalue;	// used to set the 'background-color' property of the grid and the anchor element
protected String title;	// Used as caption/tooltip unless setDisplayTitles(false) is called
protected String id;	// used to set the input element value when the color is selected

public ColorPickerColor(String rgb, String title)
	{
	 this.RGBvalue = rgb;
	 this.title = title;
	}

public ColorPickerColor(String rgb, String title, String id)
	{
	 this.RGBvalue = rgb;
	 this.title = title;
	 this.id = id;
	}

public String getRGBvalue()
	{
	 return (RGBvalue == null) ? "" : RGBvalue;
	}

public String getTitle()
	{
	 return (title == null) ? "" : title;
	}

public String getId()
	{
	 return (id == null) ? "" : id;
	}
}
