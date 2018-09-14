package org.fsgt38.fsgt38.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Sport implements Serializable
{
	private String nom;

	@Override
	public String toString() {
		return nom;
	}
}
