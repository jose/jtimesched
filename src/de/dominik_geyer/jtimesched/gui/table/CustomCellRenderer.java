package de.dominik_geyer.jtimesched.gui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

import de.dominik_geyer.jtimesched.JTimeSchedApp;
import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;
import de.dominik_geyer.jtimesched.project.ProjectTime;


@SuppressWarnings("serial")
public class CustomCellRenderer extends JLabel implements TableCellRenderer {
	public static final Color COLOR_RUNNING = new Color(0xFF, 0xE9, 0x7F);
	
	public CustomCellRenderer() {
		this.setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		ProjectTableModel tstm = (ProjectTableModel) table.getModel();
		int modelRow = table.convertRowIndexToModel(row);
		Project prj = tstm.getProjectAt(modelRow);
		
		
		String text = null;
		
		switch (column) {
		case ProjectTableModel.COLUMN_TITLE:
			this.setText((String)value);
			
			// row-color
			ProjectTable pt = (ProjectTable) table;
			if (pt.isHighlightRow(modelRow)) {
				this.setBorder(new LineBorder(Color.BLACK, 2));
			} else {
				this.setBorder(null);
			}
			break;
		case ProjectTableModel.COLUMN_TIMEOVERALL:
		case ProjectTableModel.COLUMN_TIMETODAY:
			text = ProjectTime.formatSeconds(((Integer)value).intValue());
			this.setHorizontalAlignment(SwingConstants.RIGHT);
			this.setText(text);
			break;
		case ProjectTableModel.COLUMN_CREATED:
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd" /* HH:mm:ss */);
			text = sdf.format((Date)value);
			this.setHorizontalAlignment(SwingConstants.CENTER);
			this.setText(text);
			break;
		case ProjectTableModel.COLUMN_ACTION_DELETE:
			this.setToolTipText("remove project");
			this.setIcon(new ImageIcon(JTimeSchedApp.IMAGES_PATH + "project-delete.png"));
			this.setHorizontalAlignment(SwingConstants.CENTER);
			break;
		case ProjectTableModel.COLUMN_ACTION_STARTPAUSE:
			ImageIcon ii;
			//String tooltip;
			if (prj.isRunning()) {
				//tooltip = "pause";
				ii = new ImageIcon(JTimeSchedApp.IMAGES_PATH + "pause.png");
			}
			else {
				//tooltip = "start";
				ii = new ImageIcon(JTimeSchedApp.IMAGES_PATH + "start.png");
			}
			//this.setToolTipText(tooltip);
			this.setIcon(ii);
			this.setHorizontalAlignment(SwingConstants.CENTER);
			break;
		}
		
		if (prj.isRunning()) {
			this.setFont(this.getFont().deriveFont(Font.BOLD));
			this.setBackground(CustomCellRenderer.COLOR_RUNNING);
		} else {
			this.setFont(this.getFont().deriveFont(Font.PLAIN));
			
			if (isSelected) {
				this.setBackground(table.getSelectionBackground());
			} else {
				this.setBackground(table.getBackground());
			}
		}
		
		return this;
	}
}