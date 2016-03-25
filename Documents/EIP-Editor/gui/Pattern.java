package gui;

import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

public class Pattern extends JLabel implements Serializable {

	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String patternName;
	private int id, variant;
	private TitledBorder BorderWithTitle;
	private String Title;
	private bibliothek.Pattern patternDataModel;

	public Pattern(String Title, Image img, bibliothek.Pattern pattern) {
		super(new ImageIcon(img), JLabel.CENTER);
		this.patternName = "";
		this.id = variant = 0;
		this.setVerticalTextPosition(JLabel.TOP);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setSize(200, 140);
		this.BorderWithTitle = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), Title);
		this.BorderWithTitle.setTitleJustification(TitledBorder.CENTER);
		this.setBorder(this.BorderWithTitle);
		this.Title = Title;
		this.patternDataModel = pattern;
	}

	public Pattern(String Title, Icon icon, bibliothek.Pattern pattern) {
		super(icon, JLabel.CENTER);
		this.patternName = "";
		this.id = variant = 0;
		this.setVerticalTextPosition(JLabel.TOP);
		this.setHorizontalTextPosition(JLabel.CENTER);
		this.setSize(200, 140);
		this.BorderWithTitle = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), Title);
		this.BorderWithTitle.setTitleJustification(TitledBorder.CENTER);
		this.setBorder(this.BorderWithTitle);
		this.Title = Title;
		this.patternDataModel = pattern;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String pattern) {
		this.patternName = pattern;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVariant() {
		return variant;
	}

	public void setVariant(int variant) {
		this.variant = variant;
		String variantText = "";
		switch (this.Title) {
		case "Aggregator":
			if (variant == 0) {
				variantText = "static";
			} else {
				variantText = "dynamic";
			}
			break;
		case "Channel Adapter":
			if (variant == 0) {
				variantText = "inbound";
			} else {
				variantText = "outbound";
			}
			break;
		case "Message Router":
			if (variant == 1) {
				variantText = "content-based";
			}
			break;

		default:
			return;
		}
		// this.BorderWithTitle.setTitle(variantText + " " + this.Title);
		this.setToolTipText(variantText);
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
		this.BorderWithTitle.setTitle(Title);
	}

	public bibliothek.Pattern getPatternDataModel() {
		return this.patternDataModel;
	}

	public void setPatternDataModel(bibliothek.Pattern pattern) {
		this.patternDataModel = pattern;
	}

}
