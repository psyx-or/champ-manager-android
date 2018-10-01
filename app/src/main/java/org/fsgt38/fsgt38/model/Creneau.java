package org.fsgt38.fsgt38.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creneau implements Serializable {
	int id;
	int jour;
	Date heure;
}
