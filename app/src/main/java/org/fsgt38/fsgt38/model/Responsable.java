package org.fsgt38.fsgt38.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Responsable implements Serializable {
	private int id;
	private String nom;
	private String tel1;
	private String tel2;
	private String mail;
}
