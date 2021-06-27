package org.olbring.items;

import org.olbring.board.Board;

import javax.swing.*;

public abstract class Item {
	protected int row; //aktuelle Zeile
	protected int column; //aktuelle spalte
	protected int oldRow;	//alte Zeile
	protected int oldColumn;	//alte Spalte
	protected ImageIcon icon;	//Das Icon diese Items

	/**
	 * lässt die Schlange an einer bestimmten stelle erscheinen ohne die alte Position zulöschen
	 *
	 * @param row Zeile in der die Schlange ersheinen soll
	 * @param column Spalte in der die Schlange ersheinen soll
	 */
	public void loadAt(int row, int column) {		
		Board.getFields()[row][column].setIcon(icon);  //lädt das Icon an der  Position

		//speichert die alten Koordinaten
		this.oldRow = this.row;
		this.oldColumn = this.column;
		//speichert die neuen Koordinaten
		this.row = row;
		this.column = column;
	}

	/**
	 * lässt die Schlange an einer bestimmten stelle erscheinen und löscht die alte Position
	 *
	 * @param row Zeile in der die Schlange ersheinen soll
	 * @param column Spalte in der die Schlange ersheinen soll
	 */
	public void loadRemoveAt(int row, int column) {
		Board.getFields()[row][column].setIcon(icon); //lädt das Icon an der neuen Position
		Board.getFields()[this.row][this.column].setIcon(null); //löscht das Icon an der Alten Stelle

		//speichert die alten Koordinaten
		this.oldRow = this.row;
		this.oldColumn = this.column;
		//speichert die neuen Koordinaten
		this.row = row;
		this.column = column;		
	}



	
	/*
	 * Getter und Setter
	 */
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public int getOldRow() {
		return oldRow;
	}

	public void setOldRow(int oldRow) {
		this.oldRow = oldRow;
	}

	public int getOldColumn() {
		return oldColumn;
	}

	public void setOldColumn(int oldColumn) {
		this.oldColumn = oldColumn;
	}
}
