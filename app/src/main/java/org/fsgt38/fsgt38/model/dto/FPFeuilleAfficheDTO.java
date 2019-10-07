package org.fsgt38.fsgt38.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.fsgt38.fsgt38.model.FPFeuille;
import org.fsgt38.fsgt38.model.FPForm;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FPFeuilleAfficheDTO {
	private FPForm fpForm;
	private FPFeuille fpFeuille;
	private Map<Integer,Integer> reponses;
	private int matchId;
}
