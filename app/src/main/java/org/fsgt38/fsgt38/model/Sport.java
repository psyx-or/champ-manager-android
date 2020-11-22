package org.fsgt38.fsgt38.model;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sport implements Serializable, Comparable<Sport>
{
	private String nom;

	@Override
	public @NonNull String toString() {
		return nom;
	}

	@Override
	public int compareTo(Sport o) {
		return this.nom.compareTo(o.nom);
	}
}
