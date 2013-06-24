package com.ipaste.paste;

public class Paste {
	private int id;
	private String title;
	private String description;
	private String content;
	private PasteValidStatuses status;
	private String password;
	private String source;
	private String tags;
	private PasteValidExpiryDates expiryDate;
	private PasteValidSyntaxes syntax;
	private PasteValidColors color;

	public Paste(int id, String title, String description, String content, PasteValidStatuses status, String password, String source, String tags, PasteValidExpiryDates expiryDate,
			PasteValidSyntaxes syntax, PasteValidColors color) {
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

	public PasteValidStatuses getStatus() {
		return status;
	}

	public void setStatus(PasteValidStatuses status) {
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

	public PasteValidExpiryDates getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(PasteValidExpiryDates expiryDate) {
		this.expiryDate = expiryDate;
	}

	public PasteValidSyntaxes getSyntax() {
		return syntax;
	}

	public void setSyntax(PasteValidSyntaxes syntax) {
		this.syntax = syntax;
	}

	public PasteValidColors getColor() {
		return color;
	}

	public void setColor(PasteValidColors color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Paste [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content + ", status=" + status + ", password=" + password + ", source=" + source + ", tags="
				+ tags + ", expiryDate=" + expiryDate + ", syntax=" + syntax + ", color=" + color + "]";
	}

}
