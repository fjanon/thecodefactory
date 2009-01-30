package com.codefactory.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SourcesClickEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;

public class PickerPanel extends PopupPanel {

public static final int NORTH = 1;
public static final int NORTH_EAST = 2;
public static final int EAST = 3;
public static final int SOUTH = 4;
public static final int SOUTH_EAST = 5;
protected int anchorCorner = EAST;	// Default position for the picker panel relative to the anchor widget

// The popup panel has the default styles:
//	.gwt-PopupPanel { the outside of the popup }
//	.gwt-PopupPanel .popupContent { the wrapper around the content }
// Style added to the colorpicker popup, the popup panel ends up with the 2 styles: "gwt-PopupPanel" and "colorpicker"
public static final String PICKERSTYLENAME = "picker";
// This style is added to the grid cell when the user selects a color in the grid
public static final String PICKERSELECTEDSTYLE = "selected";

// The widget to anchor the color picker to when the user clicks on the anchor widget
protected Widget anchorWidget;
protected PickerCellManager cellManager;

// hideOnClick = true : After a click on a color, the panel is closed
// hideOnClick = false : After a click on a color, the panel is not closed. The user needs to click outside the panel to close it
protected boolean hideOnClick = false;

protected Grid grid;	// where the colorpicker colors are drawn
protected int selectedRow = -1;
protected int selectedCol = -1;

// We need a widget that supports a clickListener
// TODO: change to appropriate widget, not only a Label
// TODO: remove the click listener on the anchor widget when it is unloaded, how do we do that?
public PickerPanel(final SourcesClickEvents clickSource, PickerCellManager cellManager)
	{
	 super(true);	// auto hide by default
	 if ((clickSource == null) || (cellManager == null))
		 {
		  GWT.log("ColorPicker: clickSource or cellManager null", null);
		  return;
		 }
	 this.anchorWidget = (Widget)clickSource;	// Need a widget for its dimensions
	 this.cellManager = cellManager;
	 setupPicker();
	 // Add a click listener to the Label anchor element, the ColorPicker will be built when the click occurs
	 // When the user clicks on the anchorWidget, the picker opens
	 clickSource.addClickListener(new ClickListener()
	 	{
		 public void onClick(Widget sender)
		 	{
			 if (sender != anchorWidget) return;	// Should not happen
			 showPicker();
		 	}
	 	});
	}

// Give access to the grid to be able to change format, style...
public Grid getGrid()
	{
	 return this.grid;
	}

public void setHideOnClick(boolean hideOnClick)
	{
	 this.hideOnClick = hideOnClick;
	}

public boolean getHideOnClick()
	{
	 return this.hideOnClick;
	}

protected void showPicker()
	{
	 positionPopupPanel(this);
	}

protected void hidePicker()
	{
	 this.hide(/* autoClosed */ false); // autoClosed - true if the popup was automatically closed; false if it was closed programmatically.
	}

protected void setupPicker()
	{
	 this.addStyleName(PICKERSTYLENAME);
	 this.setWidget(createGrid());
	}

protected void positionPopupPanel(final PopupPanel popup)
	{
	 popup.setPopupPositionAndShow(
		new PopupPanel.PositionCallback()
	 		{
			 public void setPosition(int offsetWidth, int offsetHeight)
			 	{
				 setPanelPosition(offsetWidth, offsetHeight);
				 PickerCellRowCol rowCol = cellManager.getSelectedCellRowCol();
				 if (rowCol != null) newCellSelection(rowCol.row, rowCol.col);	// set the initial selection if any
			 	}
	 		});
	}

protected void setPanelPosition(int offsetWidth, int offsetHeight)
	{
     int left = getAnchorRight();
     int top = getAnchorTop();
	 switch (anchorCorner)
		{
		 case NORTH:
			 // align the bottom left of the HintPanel to the top left of the parent
			 left = getAnchorLeft();
			 top = getAnchorTop() - this.getOffsetHeight();
			 break;
		 case NORTH_EAST:
			 // align the bottom left of the HintPanel to the top right of the parent
			 left = getAnchorRight();
			 top = getAnchorTop() - this.getOffsetHeight();
			 break;
		 case EAST:
			 // align the top left of the HintPanel to the bottom left of the parent
			 left = getAnchorRight();
			 top = getAnchorTop();
			 break;
		 case SOUTH:
			 // align the top left of the HintPanel to the bottom left of the parent
			 left = getAnchorLeft();
			 top = getAnchorBottom();
			 break;
		 case SOUTH_EAST:
			 // align the top left of the HintPanel to the bottom right of the parent
			 left = getAnchorRight();
			 top = getAnchorBottom();
			 break;
		}
     this.setPopupPosition(left, top);
	}

protected int getAnchorRight()
	{
	 int left = this.anchorWidget.getAbsoluteLeft();
	 int width = this.anchorWidget.getOffsetWidth();
	 int right = left + width;
//	 GWT.log("PickerPanel getAnchorRight: left: " + left + " width: " + width + " right: " + right, null);
	 return right;
	}

protected int getAnchorLeft()
	{
	 int left = this.anchorWidget.getAbsoluteLeft();
//	 GWT.log("PickerPanel getAnchorLeft: left: " + left, null);
	 return left;
	}

protected int getAnchorBottom()
	{
	 int top = this.anchorWidget.getAbsoluteTop();
	 int height = this.anchorWidget.getOffsetHeight();
	 int bottom = top + height;
//	 GWT.log("PickerPanel getAnchorRight: top: " + top + " height: " + height + " bottom: " + bottom, null);
	 return bottom;
	}

protected int getAnchorTop()
	{
	 int top = this.anchorWidget.getAbsoluteTop();
//	 GWT.log("PickerPanel getAnchorTop: top: " + top, null);
	 return top;
	}

protected Grid createGrid()
	{
	 // Really create the grid with the colors
	 // Attach click and mouse move listeners
	 int rows = cellManager.getRows();
	 int cols = cellManager.getCols();
	 GWT.log("PickerPanel createGrid rows: " + rows + " cols: " + cols, null);
	 this.grid = new Grid(rows, cols);
	 this.grid.setCellPadding(0);
	 this.grid.setCellSpacing(0);
	 this.grid.addTableListener(createTableListener());

	 // Fill in the grid cells
 	 for (int row = 0 ; row < rows ; row++)
 	 	{
 		 for (int col = 0 ; col < cols ; col++)
 			 {
 			  Widget widget = cellManager.getCellWidget(row, col);
 			  if (widget != null) grid.setWidget(row, col, widget);
 			 }
 	 	}
     return grid;
	}

protected TableListener createTableListener()
	{
	 TableListener tl = new TableListener()
		{
		 public void onCellClicked(com.google.gwt.user.client.ui.SourcesTableEvents sender, int row, int col)
		 	{
			 GWT.log("PickerPanel onCellClicked row: " + row + " col: " + col, null);
			 newCellSelection(row, col);
			 if (hideOnClick == true) hidePicker();
		 	}
		};
	 return tl;
	}

protected void newCellSelection(int row, int col)
	{
	 GWT.log("PickerPanel selectCell: row: " + row + " col: " + col, null);
	 if (selectedRow == -1)
		{
		 GWT.log("PickerPanel selectCell: selectedCell == null", null);
		 selectCellAction(row, col);
		 selectedRow = row;
		 selectedCol = col;
		}
	 else if ((selectedRow != row) || (selectedCol != col))
	 	{
		 GWT.log("PickerPanel selectCell: row or col changed", null);
		 deselectCellAction(selectedRow, selectedCol);
		 selectCellAction(row, col);
		 selectedRow = row;
		 selectedCol = col;
	 	}
	 else
		 {
		  GWT.log("PickerPanel cellClickAction: row and col not changed", null);
		 }
	}

protected void selectCellAction(int row, int col)
	{
	 addCellSelectedClassName(row, col);
	 cellManager.selectCell(row, col);
	}

protected void deselectCellAction(int row, int col)
	{
	 removeCellSelectedClassName(row, col);
	 cellManager.deselectCurrentCell();
	}

protected void removeCellSelectedClassName(int row, int col)
	{
	 // Find the selected cell and remove the "selected" style class is found
	 this.grid.getCellFormatter().removeStyleName(row, col, PICKERSELECTEDSTYLE);
	}

protected void addCellSelectedClassName(int row, int col)
	{
	 // add the "selected" style class to the selected cell
	 this.grid.getCellFormatter().addStyleName(row, col, PICKERSELECTEDSTYLE);
	}

public int getAnchorCorner()
	{
	 return this.anchorCorner;
	}

public void setAnchorCorner(int anchorCorner)
	{
	 if ((anchorCorner < NORTH) || (anchorCorner > SOUTH_EAST)) return;
	 this.anchorCorner = anchorCorner;
	}
}

