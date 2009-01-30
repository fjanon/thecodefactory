package com.codefactory.gwt.client;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class PickerCellManager
{
protected int rows;	// numbers of rows in the picker
protected int cols;	// numbers of columns in the picker
protected int selectedRow = -1;
protected int selectedCol = -1;
protected IPickerCell[][] cells;		// The array used by the picker
protected IPickerCell[] cellArray;	// the original array when a size transformation is done

public PickerCellManager() { }

public PickerCellManager(IPickerCell[][] cellArray)
	{
	 setCells(cellArray);
	}

public PickerCellManager(IPickerCell[] cellArray, int rows, int cols)
	{
	 setConfig(cellArray, rows, cols);
	}

public PickerCellManager(Collection<IPickerCell> cellCollection, int rows, int cols)
	{
	 setConfig(cellCollection.toArray(this.cellArray), rows, cols);
	}

// Direct mapping from the input array to the picker array (same rows and columns)
public void setCells(IPickerCell[][] cellArray)
	{
	 rows = cells.length;
	 cols = cells[0].length; // All rows must be of same length
	 cells = cellArray;	// same size
	}

// Size transformation
protected void setConfig(IPickerCell[] cellArray, int pickerRows, int pickerCols)
	{
	 this.cellArray = cellArray;
	 setCells(pickerRows, pickerCols);
	}

// Size transformation
public void setCells(int pickerRows, int pickerCols)
	{
	 setSize(pickerRows, pickerCols);
	 initCells(pickerRows, pickerCols);
	}

protected void setSize(int pickerRows, int pickerCols)
	{
	 this.rows = pickerRows;
	 this.cols = pickerCols;
	 this.cells = new IPickerCell[pickerRows][pickerCols];
	}

protected void initCells(int pickerRows, int pickerCols)
	{
	 for (int row = 0 ; row < pickerRows ; row++)
		 for (int col = 0 ; col < pickerCols ; col++)
			 {
			  cells[row][col] = getObjectForCell(row, col);
			 }
	}

// This is done for subclasses
protected IPickerCell getObjectForCell(int row, int col)
	{
	 return cellArray[(row * cols) + col];
	}

public int getRows()
	{
//	 GWT.log("PickerCellManager getRows: " + rows, null);
	 return rows;
	}

public int getCols()
	{
//	 GWT.log("PickerCellManager getCols: " + cols, null);
	 return cols;
	}

public IPickerCell getSelectedCell()
	{
	 return (selectedRow != -1) ? cells[selectedRow][selectedCol] : null;
	}

public void selectCell(int row, int col)
	{
//	 GWT.log("PickerCellManager selectCell: " + row + " " + col, null);
	 selectedRow = row;
	 selectedCol = col;
	 cells[row][col].select();
	}

public void deselectCurrentCell()
	{
//	 GWT.log("PickerCellManager deselectCurrentCell", null);
	 if (selectedRow != -1) cells[selectedRow][selectedCol].deselect();
	}

public Widget getCellWidget(int row, int col)
	{
	 Widget widget = cells[row][col].getWidget();
//	 GWT.log("PickerCellManager getCellWidget: " + widget, null);
	 return widget;
	}

public PickerCellRowCol getSelectedCellRowCol()
	{
//	 GWT.log("PickerCellManager getSelectedCellRowCol: " + selectedRow + " " + selectedCol, null);
	 if (selectedRow != -1) return new PickerCellRowCol(selectedRow, selectedCol);
	 else return null;
	}
} // class PickerCellManager
