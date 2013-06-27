package com.ipaste.paste;

public class Paste implements Cloneable {
	private int id;
	private String title;
	private String description;
	private String content;
	private String status;
	private String password;
	private String source;
	private String tags;
	private String expiryDate;
	private String syntax;
	private String color;

	public Paste(String title, String description, String content) {
		this.title = title;
		this.description = description;
		this.content= content;
	}

	public Paste(String title, String description, String content, String status, String password, String source, String tags, String expiryDate, String syntax, String color) {
		super();
		this.title = title;
		this.description = description;
		this.content = content;
		this.status = status;
		this.password = password;
		this.source = source;
		this.tags = tags;
		this.expiryDate = expiryDate;
		this.syntax = syntax;
		this.color = color;
	}

	public Paste(int id, String title, String description, String content, String status, String password, String source, String tags, String expiryDate, String syntax, String color) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.status = status;
		this.password = password;
		this.source = source;
		this.tags = tags;
		this.expiryDate = expiryDate;
		this.syntax = syntax;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Paste clone() {
		try {
			return (Paste) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "Paste [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + ", status=" + status + ", password=" + password + ", source=" + source + ", tags="
				+ tags + ", expiryDate=" + expiryDate + ", syntax=" + syntax + ", color=" + color + "]";
	}

}
