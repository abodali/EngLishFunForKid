package com.fpoly.object;

public class English {
	

	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getAudio() {
		return Audio;
	}

	public void setAudio(String audio) {
		Audio = audio;
	}

	public String getDecription() {
		return Decription;
	}

	public void setDecription(String decription) {
		Decription = decription;
	}
	public String toString(){
		
		return Decription;
	}

	private int Id;
	private String Image;
	private String Audio = "";
	private String Decription = "";

	public English() {

	}

	public English(int Id, String Image, String Audio, String Decription) {
		this.Id = Id;
		this.Image = Image;
		this.Audio = Audio;
		this.Decription = Decription;

	}
	
	
	
	
	

}
