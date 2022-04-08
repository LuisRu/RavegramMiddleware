package com.luis.ravegram.service.util;

public class CalculadoraDistanciaUtil {

	public final static double RADIO_TIERRA_KM = 6371.01;    // Kilómetros

	public CalculadoraDistanciaUtil() {
		// TODO Auto-generated constructor stub
	}

	public static double calcularDistanciaPuntosSuperficieTierra(double latitudPunto1, double longitudPunto1,
			double latitudPunto2, double longitudPunto2) {
		latitudPunto1 = Math.toRadians(latitudPunto1);
		longitudPunto1 = Math.toRadians(longitudPunto1);
		latitudPunto2 = Math.toRadians(latitudPunto2);
		longitudPunto2 = Math.toRadians(longitudPunto2);


		double distancia = RADIO_TIERRA_KM * Math.acos(Math.sin(latitudPunto1) * Math.sin(latitudPunto2)
				+ Math.cos(latitudPunto1) * Math.cos(latitudPunto2) * Math.cos(longitudPunto1 - longitudPunto2));

		return distancia;
	}

}
