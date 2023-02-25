package net.sf.jmoney.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jmoney.Constants;
import net.sf.jmoney.VerySimpleDateFormat;
import net.sf.jmoney.model.Account;
import net.sf.jmoney.model.Entry;
import net.sf.jmoney.model.Session;

import dori.jasper.engine.JRDataSource;
import dori.jasper.engine.JasperFillManager;
import dori.jasper.engine.JasperPrint;
import dori.jasper.engine.data.JRBeanCollectionDataSource;
import dori.jasper.view.JRViewer;

public class AccountBalancesReportPanel extends JPanel implements Constants {

	public static final int ALL_ENTRIES = 0;

	public static final int CLEARED_ENTRIES = 1;

	public static final int DATE = 2;

	public static final String[] filters =
		{
			LANGUAGE.getString("Report.AccountBalances.AllEntries"),
			LANGUAGE.getString("Report.AccountBalances.ClearedEntries"),
			LANGUAGE.getString("Entry.date")};

	private JPanel reportPanel;
	private JPanel controlPanel = new JPanel();
	private JButton generateButton = new JButton();
	private JLabel filterLabel = new JLabel();
	private JComboBox filterBox = new JComboBox(filters);
	private JLabel dateLabel = new JLabel();
	private JTextField dateField = new JTextField();

	private Session session;
	private VerySimpleDateFormat dateFormat;
	private Date date;

	public AccountBalancesReportPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSession(Session aSession) {
		session = aSession;
	}

	public void setDateFormat(String pattern) {
		dateFormat = new VerySimpleDateFormat(pattern);
	}

	private void generateReport() {
		if (reportPanel != null) {
			remove(reportPanel);
			updateUI();
		}
		try {
			URL url =
				Constants.class.getResource("resources/AccountBalances.jasper");
			InputStream is = url.openStream();

			Map params = new HashMap();
			params.put(
				"Title",
				LANGUAGE.getString("Report.AccountBalances.Title"));
			params.put("Subtitle", getSubtitle());
			params.put("Total", LANGUAGE.getString("Report.Total"));
			params.put(
				"Account",
				LANGUAGE.getString("Report.AccountBalances.Account"));
			params.put(
				"Balance",
				LANGUAGE.getString("Report.AccountBalances.Balance"));
			params.put("DateToday", dateFormat.format(new Date()));
			params.put("Page", LANGUAGE.getString("Report.Page"));

			Collection items = getItems();
			if (items.isEmpty()) {
				JOptionPane.showMessageDialog(
					this,
					LANGUAGE.getString("Panel.Report.EmptyReport.Message"),
					LANGUAGE.getString("Panel.Report.EmptyReport.Title"),
					JOptionPane.ERROR_MESSAGE);
			} else {
				JRDataSource ds = new JRBeanCollectionDataSource(getItems());
				JasperPrint print =
					JasperFillManager.fillReport(is, params, ds);
				reportPanel = new JRViewer(print);
				add(reportPanel, BorderLayout.CENTER);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		updateUI();
	}

	private String getSubtitle() {
		switch (filterBox.getSelectedIndex()) {
			case ALL_ENTRIES :
				return LANGUAGE.getString("Report.AccountBalances.AllEntries");
			case CLEARED_ENTRIES :
				return LANGUAGE.getString(
					"Report.AccountBalances.ClearedEntries");
			case DATE :
				return dateFormat.format(date);
			default :
				return "";
		}
	}

	private Collection getItems() {
		Vector items = new Vector();

		Iterator aIt = session.getAccounts().listIterator();
		while (aIt.hasNext()) {
			Account account = (Account) aIt.next();
			long bal = account.getStartBalance();

			Iterator eIt = account.getEntries().listIterator();
			while (eIt.hasNext()) {
				Entry e = (Entry) eIt.next();
				if (accept(e))
					bal += e.getAmount();
			}

			items.add(new Item(account, bal));
		}

		Collections.sort(items);
		return items;
	}

	private boolean accept(Entry entry) {
		switch (filterBox.getSelectedIndex()) {
			case ALL_ENTRIES :
				return true;
			case CLEARED_ENTRIES :
				return entry.getStatus() == Entry.CLEARED;
			case DATE :
				return acceptTo(entry.getDate());
		}
		return true;
	}

	private boolean acceptTo(Date d) {
		if (date == null)
			return true;
		if (d == null)
			return false;
		return (d.before(date) || d.equals(date));
	}

	private void updateFilter() {
		dateField.setEnabled(filterBox.getSelectedIndex() == DATE);
	}

	private void updateDate() {
		date = dateFormat.parse(dateField.getText());
		dateField.setText(dateFormat.format(date));
	}

	private void jbInit() throws Exception {
		setLayout(new BorderLayout());

		filterLabel.setText(LANGUAGE.getString("EntryFilterPanel.filter"));
		filterBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFilter();
			}
		});

		dateLabel.setText(LANGUAGE.getString("Entry.date"));
		dateField.setEnabled(false);
		dateField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDate();
			}
		});
		dateField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateDate();
			}
		});

		generateButton.setText(LANGUAGE.getString("Panel.Report.Generate"));
		generateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateReport();
			}
		});

		controlPanel.setBorder(BorderFactory.createEtchedBorder());
		controlPanel.setLayout(new GridBagLayout());
		add(controlPanel, BorderLayout.SOUTH);

		controlPanel.add(
			filterLabel,
			new GridBagConstraints(
				0,
				0,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				new Insets(6, 6, 3, 0),
				0,
				0));
		controlPanel.add(
			filterBox,
			new GridBagConstraints(
				1,
				0,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				new Insets(6, 6, 3, 5),
				0,
				0));
		controlPanel.add(
			dateLabel,
			new GridBagConstraints(
				2,
				0,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.NONE,
				new Insets(6, 6, 3, 0),
				0,
				0));
		controlPanel.add(
			dateField,
			new GridBagConstraints(
				3,
				0,
				1,
				1,
				1.0,
				0.0,
				GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL,
				new Insets(6, 6, 3, 5),
				0,
				0));
		controlPanel.add(
			generateButton,
			new GridBagConstraints(
				4,
				0,
				1,
				1,
				0.0,
				0.0,
				GridBagConstraints.EAST,
				GridBagConstraints.NONE,
				new Insets(6, 12, 3, 4),
				0,
				0));
	}

	public class Item implements Comparable {

		private Account account;

		private long balance;

		public Item(Account anAccount, long aBalance) {
			account = anAccount;
			balance = aBalance;
		}

		public Account getAccount() {
			return account;
		}

		public String getAccountName() {
			return account.getName();
		}

		public Long getBalance() {
			return new Long(balance);
		}

		public String getBalanceString() {
			return account.formatAmount(balance);
		}

		public void addToBalance(long amount) {
		}

		public String getCurrencyCode() {
			return account.getCurrencyCode();
		}

		public int compareTo(Object o) {
			return account.compareTo(((Item) o).getAccount());
		}
	}
}