package com.compica.javatrading.guppy;

public class Guppy {
	private double ima3;
	double ima5;
	double ima8;
	double ima10;
	double ima12;
	double ima30;
	double ima35;
	double ima40;
	double ima45;
	double ima50;
	double ima200;
	
	public double getIma3() {
		return ima3;
	}
	public void setIma3(double ima3) {
		this.ima3 = ima3;
	}
	public double getIma5() {
		return ima5;
	}
	public void setIma5(double ima5) {
		this.ima5 = ima5;
	}
	public double getIma8() {
		return ima8;
	}
	public void setIma8(double ima8) {
		this.ima8 = ima8;
	}
	public double getIma10() {
		return ima10;
	}
	public void setIma10(double ima10) {
		this.ima10 = ima10;
	}
	public double getIma12() {
		return ima12;
	}
	public void setIma12(double ima12) {
		this.ima12 = ima12;
	}
	public double getIma30() {
		return ima30;
	}
	public void setIma30(double ima30) {
		this.ima30 = ima30;
	}
	public double getIma35() {
		return ima35;
	}
	public void setIma35(double ima35) {
		this.ima35 = ima35;
	}
	public double getIma40() {
		return ima40;
	}
	public void setIma40(double ima40) {
		this.ima40 = ima40;
	}
	public double getIma45() {
		return ima45;
	}
	public void setIma45(double ima45) {
		this.ima45 = ima45;
	}
	public double getIma50() {
		return ima50;
	}
	public void setIma50(double ima50) {
		this.ima50 = ima50;
	}
	public double getIma200() {
		return ima200;
	}
	public void setIma200(double ima200) {
		this.ima200 = ima200;
	}
	
	public boolean isOrderededDown(){
		if (ima3<ima5 	&&
			ima5<ima8 	&&
			ima8<ima10 	&&
			ima10<ima12 &&
			ima12<ima30	&&
			ima30<ima35 &&
			ima35<ima40 &&
			ima40<ima45 &&
			ima45<ima50 ) {
			return true;
		}
		else {
			return false;
		}
}
	
	public boolean isOrderedUp() {
		if (ima3>ima5 	&&
				ima5>ima8 	&&
				ima8>ima10 	&&
				ima10>ima12 &&
				ima12>ima30	&&
				ima30>ima35 &&
				ima35>ima40 &&
				ima40>ima45 &&
				ima45>ima50 ) {
				return true;
			}
		else {
			return false;		
		}
	
	}
	
	public boolean isConsolidation() {
		double shortImaDistance= (ima12-ima3);
		if(Math.abs(shortImaDistance)<0.00150) {
			return true;
		}
		
		else {
			return false;
		}
	}
}
