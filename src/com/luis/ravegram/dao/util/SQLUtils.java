package com.luis.ravegram.dao.util;

import java.util.List;

import com.luis.ravegram.model.SolicitudDTO;

public class SQLUtils {

	public static final String toIN(List<Long> ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<ids.size()-1;i++) {
			sb.append(ids.get(i)).append(",");
		}
		sb.append(ids.get(ids.size()-1));
		return sb.toString(); 
	}
	
	
	public static final String toInterrogacion(List<Long> ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<ids.size()-1;i++) {
			sb.append("?,");
		}
		sb.append("?");
		return sb.toString(); 
	}
	
	public static final String toInterrogacionSolicitudes(List<SolicitudDTO> solicitudes) {
		StringBuilder sb = new StringBuilder();
		for (SolicitudDTO solicitud: solicitudes) {
			sb.append("(?,?,?,?),");
		}
		return sb.substring(0, sb.toString().length()-1); 
	}
	
	public static final String toDatosSolicitud(List<SolicitudDTO> solicitudes) {
		StringBuilder sb = new StringBuilder();
		for(SolicitudDTO solicitud: solicitudes) {
			sb.append("(")
			.append(solicitud.getIdUsuario())
			.append(",")
			.append(solicitud.getIdEvento())
			.append(",")
			.append(solicitud.getFecha())
			.append(",")
			.append(solicitud.getIdTipoEstado())
			.append("),");
					
		}
		String sbStr = sb.toString();
		sbStr = sbStr.substring(0, sbStr.length()-1);
		return sbStr; 
	}
	
	
	
}
