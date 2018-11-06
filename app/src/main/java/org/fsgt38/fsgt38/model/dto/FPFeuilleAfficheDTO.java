package org.fsgt38.fsgt38.model.dto;

import org.fsgt38.fsgt38.model.FPFeuille;
import org.fsgt38.fsgt38.model.FPForm;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FPFeuilleAfficheDTO {
	private FPForm fpForm;
	private FPFeuille fpFeuille;
	private Map<Integer,Integer> reponses;
	private int matchId;
}
