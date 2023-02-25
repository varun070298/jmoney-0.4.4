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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jmoney.Constants;
import net.sf.jmoney.EntryFilter;
import net.sf.jmoney.VerySimpleDateFormat;
import net.sf.jmoney.model.Account;
import net.sf.jmoney.model.Entry;
import java.awt.*;

public class EntryFilterPanel extends JPanel implements Constants {

	static String[] filterActions() {
		String[] filterActions =
			{
				LANGUAGE.getString("EntryFilterPanel.filter"),
				LANGUAGE.getString("EntryFilterPanel.clear")};
		return filterActions;
	}

	private JComboBox filterActionBox = new JComboBox(filterActions());
	private JComboBox filterTypeBox = new JComboBox(EntryFilter.filterTypes());
	private JTextField filterField = new JTextField("");
	private EntryFilter entryFilter = new EntryFilter();

	public EntryFilterPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the entryFilter.
	 * @return EntryFilter
	 */
	public EntryFilter getEntryFilter() {
		return entryFilter;
	}

	/**
	 * Sets the entryFilter.
	 * @param entryFilter The entryFilter to set
	 */
	public void setEntryFilter(EntryFilter entryFilter) {
		this.entryFilter = entryFilter;
		filterField.setText(entryFilter.getPattern());
		filterTypeBox.setSelectedIndex(entryFilter.getType());
	}

	public boolean filterEntry(
		Entry entry,
		Account account,
		VerySimpleDateFormat dateFormat) {
		return entryFilter.filterEntry(
			entry,
			account,
			dateFormat,
			filterTypeBox.getSelectedIndex());
	}

	private void setFilterPattern() {
		entryFilter.setPattern(filterField.getText());
	}

	private void jbInit() throws Exception {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEtchedBorder());

		filterActionBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filterActionBox.getSelectedIndex() == 1) {
					filterField.setText("");
					filterActionBox.setSelectedIndex(0);
					setFilterPattern();
				}
			}
		});

		filterTypeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entryFilter.setType(filterTypeBox.getSelectedIndex());
			}
		});

		filterField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilterPattern();
			}
		});
		filterField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				setFilterPattern();
			}
		});

		add(
			filterActionBox,
			new GridBagConstraints(
				0,
				0,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(5, 4, 4, 0),
				0,
				0));
		add(
			filterTypeBox,
			new GridBagConstraints(
				1,
				0,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(5, 4, 4, 0),
				0,
				0));
		add(
			filterField,
			new GridBagConstraints(
				2,
				0,
				1,
				1,
				1.0,
				0.0,
				GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL,
				new Insets(5, 4, 4, 4),
				0,
				0));
	}

}
