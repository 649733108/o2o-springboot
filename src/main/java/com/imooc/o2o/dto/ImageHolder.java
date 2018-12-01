package com.imooc.o2o.dto;
/*
 * Created by wxn
 * 2018/8/26 23:46
 */


import java.io.InputStream;

public class ImageHolder {

	private String imageName;
	private InputStream image;

	public ImageHolder(String imageName, InputStream image) {
		this.imageName = imageName;
		this.image = image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
}
