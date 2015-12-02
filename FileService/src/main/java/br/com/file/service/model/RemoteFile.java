package br.com.file.service.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import java.rmi.Remote;

public class RemoteFile {

	private int id;

	private boolean back = false;

	@NotNull
	@NotBlank
	private String content;

	public boolean callBack() {
		back = true;
		return back;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}

	public RemoteFile() {

	}

	public RemoteFile(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
