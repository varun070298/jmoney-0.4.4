/*
 *
 *  JMoney - A Personal Finance Manager
 *  Copyright (c) 2002 Johann Gyger <johann.gyger@switzerland.org>
 *
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package net.sf.jmoney.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JTextField;

import net.sf.jmoney.model.Entry;

/**
 * A component (panel) used by AccountPanel.entryList to show the entries.
 */
public class EntryListItemExtended extends EntryListItem {

	GridBagLayout thisGridBagLayout = new GridBagLayout();

	JTextField valutaField = new JTextField();
	JTextField categoryField = new JTextField();
	JTextField memoField = new JTextField();
	JTextField fillerField1 = new JTextField();
	JTextField fillerField4 = new JTextField();
	JTextField fillerField5 = new JTextField();
	JTextField fillerField6 = new JTextField();
	JTextField fillerField2 = new JTextField();
	JTextField fillerField3 = new JTextField();

	/**
	 * Creates a new view based on an account.
	 */
	public EntryListItemExtended() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sets the model.
	 */
	public void setModel(Entry entry) {
		super.setModel(entry);

		valutaField.setText(datef.format(entry.getValuta()));
		memoField.setText(entry.getMemo());
		categoryField.setText(entry.getFullCategoryName());
	}

	/**
	 * Build the GUI.
	 */
	private void jbInit() throws Exception {
		valutaField.setEnabled(false);
		valutaField.setBorder(rightLineBorder);
		valutaField.setOpaque(false);
		valutaField.setPreferredSize(valutaField.getMinimumSize());
		valutaField.setDisabledTextColor(Color.black);
		categoryField.setEnabled(false);
		categoryField.setBorder(rightLineBorder);
		categoryField.setOpaque(false);
		categoryField.setPreferredSize(categoryField.getMinimumSize());
		categoryField.setDisabledTextColor(Color.black);
		memoField.setEnabled(false);
		memoField.setBorder(rightLineBorder);
		memoField.setOpaque(false);
		memoField.setPreferredSize(memoField.getMinimumSize());
		memoField.setDisabledTextColor(Color.black);
		fillerField1.setEnabled(false);
		fillerField1.setBorder(rightLineBorder);
		fillerField1.setOpaque(false);
		fillerField3.setEnabled(false);
		fillerField3.setBorder(rightLineBorder);
		fillerField3.setOpaque(false);
		fillerField4.setBorder(rightLineBorder);
		fillerField4.setOpaque(false);
		fillerField5.setEnabled(false);
		fillerField5.setBorder(rightLineBorder);
		fillerField5.setOpaque(false);
		fillerField6.setEnabled(false);
		fillerField6.setBorder(leftLineBorder);
		fillerField6.setOpaque(false);
		fillerField2.setEnabled(false);
		fillerField2.setBorder(rightLineBorder);
		fillerField2.setOpaque(false);
		add(
			valutaField,
			new GridBagConstraints(
				1,
				1,
				1,
				1,
				10.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			categoryField,
			new GridBagConstraints(
				2,
				1,
				1,
				1,
				30.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			memoField,
			new GridBagConstraints(
				2,
				2,
				1,
				1,
				30.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			fillerField1,
			new GridBagConstraints(
				0,
				1,
				1,
				2,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			fillerField2,
			new GridBagConstraints(
				1,
				2,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			fillerField3,
			new GridBagConstraints(
				3,
				1,
				1,
				2,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 1, 0, 0),
				0,
				0));
		add(
			fillerField4,
			new GridBagConstraints(
				4,
				1,
				1,
				2,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			fillerField5,
			new GridBagConstraints(
				5,
				1,
				1,
				2,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 2, 0, 0),
				0,
				0));
		add(
			fillerField6,
			new GridBagConstraints(
				6,
				1,
				1,
				2,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.BOTH,
				new Insets(0, 1, 0, 0),
				0,
				0));
	}

}
